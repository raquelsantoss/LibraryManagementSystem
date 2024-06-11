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
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            try {
                PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);

                String request;
                try {
                    while((request = in.readLine()) != null) {
                        System.out.println("Received request: " + request);
                        String response = this.processRequest(request);
                        out.println(response);
                    }
                } catch (Throwable var7) {
                    try {
                        out.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }

                    throw var7;
                }

                out.close();
            } catch (Throwable var8) {
                try {
                    in.close();
                } catch (Throwable var5) {
                    var8.addSuppressed(var5);
                }

                throw var8;
            }

            in.close();
        } catch (IOException var9) {
            IOException e = var9;
            e.printStackTrace();
        }

    }

    private String processRequest(String request) throws IOException {
        String[] parts = request.split(",");
        String var10000;
        switch (parts[0].trim().toLowerCase()) {
            case "list":
                var10000 = this.listBooks();
                break;
            case "register":
                var10000 = parts.length != 5 ? "Invalid request format. Use: register,author,title,genre,copies" : this.registerBook(parts[1], parts[2], parts[3], parts[4]);
                break;
            case "rent":
                if (parts.length != 2) {
                    var10000 = "Invalid request format. Use: rent,title";
                } else {
                    System.out.println(parts[0]);
                    var10000 = this.rentBook(parts[1]);
                }
                break;
            case "return":
                var10000 = parts.length != 2 ? "Invalid request format. Use: return,title" : this.returnBook(parts[1]);
                break;
            default:
                var10000 = "Unknown command.";
        }

        return var10000;
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
        } catch (IOException | NumberFormatException var7) {
            Exception e = var7;
            return "Error registering book: " + e.getMessage();
        }
    }

    private String rentBook(String title) {
        Iterator var2 = this.bookCollection.getBooks().iterator();

        Book book;
        do {
            if (!var2.hasNext()) {
                return "Book not found.";
            }

            book = (Book)var2.next();
        } while(!book.getTitle().equalsIgnoreCase(title.trim()));

        if (book.getCopies() > 0) {
            try {
                this.bookCollection.updateBookCopies(this.filePath, title, book.getCopies() - 1);
                return "Book rented successfully.";
            } catch (IOException var5) {
                IOException e = var5;
                return "Error updating book: " + e.getMessage();
            }
        } else {
            return "No copies available for rent.";
        }
    }

    private String returnBook(String title) {
        Iterator var2 = this.bookCollection.getBooks().iterator();

        Book book;
        do {
            if (!var2.hasNext()) {
                return "Book not found.";
            }

            book = (Book)var2.next();
        } while(!book.getTitle().equalsIgnoreCase(title.trim()));

        try {
            this.bookCollection.updateBookCopies(this.filePath, title, book.getCopies() + 1);
            return "Book returned successfully.";
        } catch (IOException var5) {
            IOException e = var5;
            return "Error updating book: " + e.getMessage();
        }
    }
}
