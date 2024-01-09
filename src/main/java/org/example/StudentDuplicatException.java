package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class StudentDuplicatException extends Exception {
    public StudentDuplicatException(String message, String outputFilePath) {
        super(message);
        writeMessageToFile(message, outputFilePath);
    }

    private void writeMessageToFile(String message, String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

