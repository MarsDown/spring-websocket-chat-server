package server.Control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import server.Entity.CoreChatRoom;
import server.Entity.CoreMessage;
import server.Entity.CoreUser;
import server.Service.*;
import server.model.MessageModel;
import server.utill.CalendarTool;
import server.utill.FileCodecBase64;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by mars on 6/6/2018.
 */
@Controller
public class MessagesControll {

    @Autowired
    UserService userService;

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ConversationService conversationService;

    @Autowired
    MessageService messageService;

    @Autowired
    StorageService storageService;

    @MessageMapping("/private.chat.{channelId}.{userId}.{token}")
    @SendTo("/Messenger/private.chat.{channelId}")
    public MessageModel message(@DestinationVariable String channelId, @DestinationVariable String userId, @DestinationVariable String token, MessageModel message) {

        if (!authentication(userId, token, channelId)) return null;

        CoreMessage save;
        if (message.getMessageType().equals("file")) {
            String filename = storageService.store(message.getMessagePath());

            message.setMessageType(FileCodecBase64.getTypeFileFromBase64(message.getMessagePath()));
            message.setMessagePath(filename);
        }

        CalendarTool calendarTool = new CalendarTool();

        message.setMessageDate(calendarTool.getIranianDate() + " " + getTime());
        save = messageService.save(toCoreMessage(message));
        message.setMessageId(save.getMessageId());

        return message;
    }

    @MessageMapping("/private.chat.history.{channelId}.{userId}")
    @SendTo("/Messenger/private.chat.history.{channelId}")
    public List<MessageModel> loadhistory(@DestinationVariable String channelId, @DestinationVariable String userId, server.model.Page page) {

        if (!authentication(userId, "", channelId)) return null;

        return messageService.loadMessages(Integer.parseInt(channelId), page.getTake(), page.getSkip());

    }

    @GetMapping("/api/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }


    private CoreMessage toCoreMessage(MessageModel messageModel) {

        CoreMessage coreMessage = new CoreMessage();

        if (messageModel.getMessagePath() != null)
            coreMessage.setMessagePath(messageModel.getMessagePath().replace("/api/file/", ""));

        coreMessage.setMessageType(messageModel.getMessageType());
        coreMessage.setMessageContext(messageModel.getMessageContext());
        coreMessage.setMessageDate(messageModel.getMessageDate());

        CoreChatRoom chatRoom = new CoreChatRoom();
        chatRoom.setChatId(messageModel.getChatRoom());
        coreMessage.setChatRoom(chatRoom);

        CoreUser user = new CoreUser();
        user.setUserId(messageModel.getUser());
        coreMessage.setUser(user);

        return coreMessage;
    }


    private boolean authentication(String userId, String token, String channelId) {
        if (!userService.isUser(userId, token))
            return false;

        if (!conversationService.isJoin(channelId, userId))
            return false;

        return true;
    }

    private String getTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp.toLocalDateTime().getHour() + ":" + timestamp.toLocalDateTime().getMinute();

    }

}