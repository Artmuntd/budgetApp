package com.example.budget.controller;

import com.example.budget.sevices.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {
    private  final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> dowloadDataFile() throws FileNotFoundException {
      File file =  fileService.getDatafile();
      if(file.exists()) {
          InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
          return  ResponseEntity.ok()
                  .contentType(MediaType.APPLICATION_JSON)
                  .contentLength(file.length())
                  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"TransactionLog.json\"")
                  .body(resource);
      } else  {
          return  ResponseEntity.noContent().build();
      }
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // Возможен Put метод
    public  ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file){
        fileService.CleanDataFile();
        File dataFile = fileService.getDatafile();
       try(FileOutputStream fos = new FileOutputStream(dataFile)){
           IOUtils.copy(file.getInputStream(),fos);
           return  ResponseEntity.ok().build();
       } catch (IOException e) {
           e.printStackTrace();
       }

         return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();





    }
}
//    public  ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file){
//       try(   BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
//                FileOutputStream fos =new FileOutputStream(dataFile);
//          BufferedOutputStream bos = new BufferedOutputStream(fos))
//        {   byte [] buffer = new byte[1024];
//            while (bis.read(buffer)> 0) {
//            bos.write(buffer);
//        }
//
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } Как это пишется без библиотек