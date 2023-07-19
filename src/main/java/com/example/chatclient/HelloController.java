package com.example.chatclient;

import com.example.chatclient.utils.ChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloController {


    ChatClient chatClient;
    String username;

    @FXML
    public TextField usernameTextField;
    @FXML
    public TextField serverAddressTextField;
    @FXML
    public TextField portTextField;
    @FXML
    private TextArea chatTextArea;

    @FXML
    public TextField messageTextField;


    //    send button action handler
    public void sendButtonClicked() {
        String message = username + " : " + messageTextField.getText();
        try {
            chatClient.sendMessage(message);
//            chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\t" + message + "\n");
            messageTextField.setText("");
        } catch (Exception e) {
            chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\t" + e + "\n");
        }


    }

    public void connectButtonClicked() {
        if (chatClient != null) {
            try {
                chatClient.cleanUp();
                chatClient = null;
            } catch (Exception e) {
                chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\t" + e + "\n");
                return;
            }
        }
        try {
            String serverAddress = serverAddressTextField.getText();
            int port = Integer.parseInt(portTextField.getText());
            if (usernameTextField.getText().isBlank()) throw new RuntimeException("username can't be empty!");
            username = usernameTextField.getText();
            chatClient = new ChatClient(serverAddress, port);
            chatClient.setupNetworking();
            chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\tServerAddress=" + serverAddress + ", port=" + port + "\tconnected!\n");
            chatClient.startReading(chatTextArea);
        } catch (Exception e) {
            chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\t" + e + "\n");
        }

    }

    public void shutDown() throws IOException {
        if (chatClient != null) chatClient.cleanUp();
        System.out.println("exit!");
    }

}