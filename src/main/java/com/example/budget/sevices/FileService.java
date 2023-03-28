package com.example.budget.sevices;

import java.io.File;
import java.nio.file.Path;

public interface FileService {
    boolean saveToFile(String json);

    Path creatTempFile(String suffix);

    File getDatafile();

    String readFromFile();

    boolean CleanDataFile();
}
