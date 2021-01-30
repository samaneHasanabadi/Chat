package AdvancedChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Formatter;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5606);
             Formatter socketOut = new Formatter(socket.getOutputStream())) {
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            String next;
            String recieved;
            while (true) {
                if (systemIn.ready()) {
                    next = systemIn.readLine();
                    socketOut.format(next + "\n");
                    socketOut.flush();
                }
                if (socketIn.ready()) {
                    recieved = socketIn.readLine();
                    System.out.println("Server: " + recieved);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
