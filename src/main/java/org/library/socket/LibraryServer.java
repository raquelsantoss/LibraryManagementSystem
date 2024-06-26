package org.library.socket;

import org.library.entity.BookCollection;
import org.library.resource.JsonUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LibraryServer {
    private static final int PORT = 12345;
    private static String FILE_PATH = "books.json";
    private static final String RESOURCES_DIR = "src/main/java/org/library/resource/";
    private BookCollection bookCollection;
    private ServerSocket serverSocket;

    public LibraryServer() {
        bookCollection = new BookCollection();

        // Load the book collection from file
        try {
            bookCollection = JsonUtil.readJsonFile(RESOURCES_DIR + FILE_PATH, BookCollection.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructor for dependency injection
    public LibraryServer(String filePath) throws IOException {
        FILE_PATH = filePath;

        // Load the book collection from file
        try {
            bookCollection = JsonUtil.readJsonFile(RESOURCES_DIR + FILE_PATH, BookCollection.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try (ServerSocket serverSocket = this.serverSocket != null ? this.serverSocket : new ServerSocket(PORT)) {
            System.out.println("Book server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket, bookCollection, RESOURCES_DIR + FILE_PATH);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LibraryServer server = new LibraryServer();
        server.start();
    }
}
