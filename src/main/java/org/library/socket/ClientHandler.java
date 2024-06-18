package org.library.socket;

import org.library.entity.Book;
import org.library.entity.BookCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BookCollection bookCollection;
    private String filePath;

    public ClientHandler(Socket socket, BookCollection bookCollection, String filePath) {
        this.clientSocket = socket;
        this.bookCollection = bookCollection;
        this.filePath = filePath;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(this.clientSocket.getOutputStream(), true);

            String clientRequest;
            while ((clientRequest = reader.readLine()) != null) {
                System.out.println("Received request: " + clientRequest);
                String serverResponse = this.processRequest(clientRequest);
                writer.println(serverResponse);
            }

            writer.close();
            reader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String processRequest(String request) throws IOException {
        String[] requestParts = request.split(",");
        String response;
        switch (requestParts[0].trim().toLowerCase()) {
            case "list":
                response = this.listBooks();
                break;
            case "register":
                response = (requestParts.length != 5) ?
                        "Invalid request format. Use: register,author,title,genre,copies" :
                        this.registerBook(requestParts[1], requestParts[2], requestParts[3], requestParts[4]);
                break;
            case "rent":
                response = (requestParts.length != 2) ?
                        "Invalid request format. Use: rent,title" :
                        this.rentBook(requestParts[1]);
                break;
            case "return":
                response = (requestParts.length != 2) ?
                        "Invalid request format. Use: return,title" :
                        this.returnBook(requestParts[1]);
                break;
            default:
                response = "Unknown command.";
        }
        return response;
    }

    private String listBooks() {
        return this.bookCollection.booksString();
    }

    private String registerBook(String author, String title, String genre, String copiesStr) {
        try {
            int copies = Integer.parseInt(copiesStr.trim());
            Book newBook = new Book(author, title, genre, copies);
            this.bookCollection.addBook(this.filePath, newBook);
            return "Book registered successfully.";
        } catch (IOException | NumberFormatException exception) {
            return "Error registering book: " + exception.getMessage();
        }
    }

    private String rentBook(String title) {
        Iterator<Book> bookIterator = this.bookCollection.getBooks().iterator();
        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                if (book.getCopies() > 0) {
                    try {
                        this.bookCollection.updateBookCopies(this.filePath, title, book.getCopies() - 1);
                        return "Book rented successfully.";
                    } catch (IOException ioException) {
                        return "Error updating book: " + ioException.getMessage();
                    }
                } else {
                    return "No copies available for rent.";
                }
            }
        }
        return "Book not found.";
    }

    private String returnBook(String title) {
        Iterator<Book> bookIterator = this.bookCollection.getBooks().iterator();
        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                try {
                    this.bookCollection.updateBookCopies(this.filePath, title, book.getCopies() + 1);
                    return "Book returned successfully.";
                } catch (IOException ioException) {
                    return "Error updating book: " + ioException.getMessage();
                }
            }
        }
        return "Book not found.";
    }
}
