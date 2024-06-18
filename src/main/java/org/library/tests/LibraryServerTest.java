package org.library.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.library.socket.LibraryServer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LibraryServerTest {

    private static final String TEMP_FILE_PATH = "temp_books.json";
    private static final String RESOURCES_DIR = "src/main/java/org/library/resource/";
    private LibraryServer libraryServer;
    private Thread serverThread;

    @Before
    public void setUp() throws IOException {
        // Create a temporary JSON file for testing
        Path tempFilePath = Paths.get(RESOURCES_DIR + TEMP_FILE_PATH);
        Files.createDirectories(tempFilePath.getParent());
        Files.write(tempFilePath, getTempJsonContent().getBytes());

        libraryServer = new LibraryServer(TEMP_FILE_PATH);

        // Start the server in a separate thread
        serverThread = new Thread(() -> libraryServer.start());
        serverThread.start();
    }

    @After
    public void tearDown() throws IOException {
        serverThread.interrupt();
    }

    @Test
    public void testClientHandlerList() throws IOException {
        // Connect a client to the server
        try (Socket clientSocket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Send the list command
            out.println("list");
            String response = in.readLine();

            // Verify the server's response
            assertTrue(response.contains("Test Book"));
        }
    }

    @Test
    public void testClientHandlerRegister() throws IOException {
        // Connect a client to the server
        try (Socket clientSocket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Send the register command
            out.println("register,Author,New Book,Genre,10");
            String response = in.readLine();

            // Verify the server's response
            assertEquals("Book registered successfully.", response);
        }
    }

    @Test
    public void testClientHandlerRent() throws IOException {
        // Connect a client to the server
        try (Socket clientSocket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Send the rent command
            out.println("rent,Test Book");
            String response = in.readLine();

            // Verify the server's response
            assertEquals("Book rented successfully.", response);
        }
    }

    @Test
    public void testClientHandlerReturn() throws IOException {
        // Connect a client to the server
        try (Socket clientSocket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Send the return command
            out.println("return,Test Book");
            String response = in.readLine();

            // Verify the server's response
            assertEquals("Book returned successfully.", response);
        }
    }

    private String getTempJsonContent() {
        return "{\n" +
                "  \"livros\": [\n" +
                "    {\n" +
                "      \"titulo\": \"Test Book\",\n" +
                "      \"autor\": \"Author Name\",\n" +
                "      \"genero\": \"Genre\",\n" +
                "      \"exemplares\": 10\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
