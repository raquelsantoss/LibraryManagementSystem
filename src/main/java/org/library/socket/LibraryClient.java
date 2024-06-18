package org.library.socket;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class LibraryClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the book server.");

            while (true) {
                System.out.println("Enter command (list, register, rent, return, exit): ");
                String command = userInput.readLine();

                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                switch (command.toLowerCase()) {
                    case "list":
                        writer.println("list");
                        break;
                    case "register":
                        System.out.println("Enter author: ");
                        String author = userInput.readLine();
                        System.out.println("Enter title: ");
                        String title = userInput.readLine();
                        System.out.println("Enter genre: ");
                        String genre = userInput.readLine();
                        System.out.println("Enter number of copies: ");
                        String copies = userInput.readLine();
                        String registerDetails = String.join(",", author, title, genre, copies);
                        writer.println("register," + registerDetails);
                        break;
                    case "rent":
                        System.out.println("Enter title: ");
                        String rentTitle = userInput.readLine();
                        writer.println("rent," + rentTitle);
                        break;
                    case "return":
                        System.out.println("Enter title: ");
                        String returnTitle = userInput.readLine();
                        writer.println("return," + returnTitle);
                        break;
                    default:
                        System.out.println("Unknown command.");
                        continue;
                }

                // Get and print the server's response
                String response = reader.readLine();
                if (response != null) {
                    response = response.replace("_", "\n");
                }
                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
