package org.library.resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static <T> T readJsonFile(String filePath, Class<T> clazz) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        return objectMapper.readValue(file, clazz);
    }

    public static <T> void writeJsonFile(String filePath, T object) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), object);
    }
}