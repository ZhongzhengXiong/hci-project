package edu.fudan;

import edu.fudan.exception.ImageIOException;
import edu.fudan.exception.WrongFileTypeException;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public static boolean isImage(MultipartFile file){
        String mimetype = file.getContentType();
        String type = mimetype.split("/")[0];
        if(type.equalsIgnoreCase("image"))
            return true;
        return false;
    }

    public static String saveFile(MultipartFile file, long id, String fileDirPath){
        if (file != null) {
            if(!Utils.isImage(file))
                throw new WrongFileTypeException();
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newfileName = id + fileSuffix;
            Path filePath = Paths.get(fileDirPath + newfileName);
            File localFile = filePath.toFile();
            try {
                file.transferTo(localFile);
                return newfileName;
            } catch (IOException e) {
                e.printStackTrace();
                throw new ImageIOException(e.getMessage());
            }
        }
        return "";
    }

}
