package com.example.chatclient.utils;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {
    private final String serverAddress;
    private final int port;
    private BufferedReader reader;
    private PrintWriter writer;

    ExecutorService executor;


    public ChatClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void setupNetworking() throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress(serverAddress, port);
        SocketChannel socketChannel = SocketChannel.open(socketAddress);
        reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
        writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8));
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }


    public void cleanUp() throws IOException {
        if (writer != null) writer.close();
        if (reader != null) reader.close();
        if (executor != null) executor.shutdownNow();
    }

    public void startReading(TextArea chatTextArea) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String incomingMessage;
            try {
                while ((incomingMessage = reader.readLine()) != null) {
                    chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\t" + incomingMessage + "\n");
                }
            } catch (IOException e) {
                chatTextArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss ")) + "\t" + e + "\n");
            }
        });
    }
}
