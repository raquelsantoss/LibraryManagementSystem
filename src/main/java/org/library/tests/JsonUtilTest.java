package org.library.tests;

import org.junit.Before;
import org.junit.Test;
import org.library.entity.Book;
import org.library.entity.BookCollection;
import org.library.resource.JsonUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class JsonUtilTest {

    private static final String TEMP_FILE = "temp_books.json";
    private static final String RESOURCES_DIR = "src/main/java/org/library/resource/";

    @Before
    public void setUp() throws IOException {
        // Cria um arquivo JSON temporário para o teste de leitura
        FileWriter file = new FileWriter(RESOURCES_DIR + TEMP_FILE);
        file.write("{\"books\":[" +
                "{\"author\":\"Jane Austen\",\"title\":\"Pride and Prejudice\",\"genre\":\"Romance\",\"copies\":\"5\"}," +
                "{\"author\":\"George Orwell\",\"title\":\"1984\",\"genre\":\"Dystopian\",\"copies\":\"5\"}" +
                "]}");
        file.close();
    }

    @Test
    public void testReadJsonFileFileNotFound() {
        assertThrows(IOException.class, () -> JsonUtil.readJsonFile("nonexistent.json", BookCollection.class));
    }

    @Test
    public void testWriteJsonFile() throws IOException {
        Book book1 = new Book("Jane Austen", "Pride and Prejudice", "Romance", 5);
        Book book2 = new Book("George Orwell", "1984", "Dystopian", 4);

        BookCollection bookCollection = new BookCollection();
        bookCollection.setBooks(List.of(book1, book2));

        JsonUtil.writeJsonFile(RESOURCES_DIR + TEMP_FILE, bookCollection);

        // Ler o arquivo de volta para verificar seu conteúdo
        BookCollection readCollection = JsonUtil.readJsonFile(RESOURCES_DIR + TEMP_FILE, BookCollection.class);
        assertEquals(2, readCollection.getBooks().size());
        assertEquals("Jane Austen", readCollection.getBooks().get(0).getAuthor());
        assertEquals("George Orwell", readCollection.getBooks().get(1).getAuthor());
    }
}
