package vn.techmasterr.jobhunt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.techmasterr.jobhunt.exception.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;



@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    public String saveFile(MultipartFile file,String id) throws IOException{ // Id của Employer Id
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        String extension = getFileExtendsion(file.getOriginalFilename());
        String newFileName =path + id+"." + extension;
        Path pathToFile = Paths.get(newFileName).toAbsolutePath();
        try {
            var is = file.getInputStream();
            Files.copy(is,pathToFile,StandardCopyOption.REPLACE_EXISTING);
            return id+"."+extension;
        } catch (IOException e) {
            var msg = String.format("Failed to store file %s", newFileName);
            throw new StorageException(msg, e);
        }
    }
    /*
    Bóc tách kiểu file
    Ex:
    input: pic1.png
    output: png
     */
    public String getFileExtendsion(String fileName){
        int postOfDot = fileName.lastIndexOf(".");
        if(postOfDot>=0){
            return fileName.substring(postOfDot+1);
        }else {
            return null;
        }
    }
}
