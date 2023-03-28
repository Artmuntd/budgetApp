package com.example.budget.sevices.imlp;

import com.example.budget.sevices.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileserviceImpl  implements FileService {
   @Value("${path.to.data.file}")
    private  String dataFilePath;
    @Value("${name.of.data.file}")
    private  String dataFileName;

    @Override
    public boolean saveToFile(String json){
        try {
            CleanDataFile();
            Files.writeString(Path.of(dataFilePath , dataFileName), json);
            return  true;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    public Path creatTempFile(String suffix){
        try {
          return   Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
@Override
    public File getDatafile(){
        return  new File(dataFilePath + "/" + dataFileName);
    }
    @Override
    public String readFromFile(){
        try {
           return  Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 @Override
    public boolean CleanDataFile() {

        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }

    }
}
