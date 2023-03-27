package com.example.budget.sevices;

public interface FileService {
    boolean saveToFile(String json);

    String readFromFile();
}
