package server.model;


/**
 * Created by mars on 2/10/2018.
 */
public class MessageModel {

    private int messageId;
    private String messageContext;
    private String messageDate;
    private String messagePath;
    private String messageType;

    private int user;
    private int chatRoom;

    public MessageModel() {

    }

    public MessageModel(int messageId, String messageContext, String messageDate, String messagePath, String messageType) {
        this.messageId = messageId;
        this.messageContext = messageContext;
        this.messageDate = messageDate;
        this.messagePath = messagePath;
        this.messageType = messageType;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessagePath() {
        if (!this.messageType.equals("text"))
            return "/api/file/" + messagePath;
        return messagePath;
    }

    public void setMessagePath(String messagePath) {
        this.messagePath = messagePath;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(Integer chatRoom) {
        this.chatRoom = chatRoom;
    }
}
