var stompClient = null;
var userId = 63;
var channelId = 4;
var token = 36;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/marsserver');
    stompClient = Stomp.over(socket);


    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/Messenger/private.chat.' + channelId, function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function getBase64(file, message) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
        console.log(reader.result);
        message.messagePath = reader.result;
        message.messageType = "file";

        sendData(message);
    };
    reader.onerror = function (error) {
        console.log('Error: ', error);
    };
}


function sendName() {

    var messageInput = document.querySelector('#name');

    var file = document.querySelector('#filename');
    // prints the base64 string
    var message = {
        messageId: 5,
        messageContext: messageInput.value.trim(),
        messageDate: "",
        messagePath: "",
        messageType: "text",
        user: userId,
        chatRoom: channelId
    };

    if (file.files[0])
        getBase64(file.files[0], message)
    else
        sendData(message);


}

function sendData(message) {
    stompClient.send('/app/private.chat.' + channelId + '.' + userId + '.' + token, {}, JSON.stringify(message));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});