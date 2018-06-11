package server.Service;

/**
 * Created by mars on 2/8/2018.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import static server.utill.FileCodecBase64.*;

@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    String pathroot = "d:\\mars\\upload";
    private final Path rootLocation = Paths.get(pathroot);

    public void store(MultipartFile file) {

        try {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String[] split = file.getContentType().split("/");
            String typeFile = split[0];
            String format = split[1];
            String name = timestamp.toString() + "." + format;

            name = name.replace(":", "-");

            Files.copy(file.getInputStream(), this.rootLocation.resolve(name));

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

    }

    private String getName() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String name = timestamp.toString();

        return name.replace(":", "-").replace(" ","");
    }

    public String store(String file) {
        String s = "";
        try {
            s = getName() + "." + getFormatFromBase64(file);
            writeByteToFile(convertToByte(file), pathroot+"\\"+s);

        } catch (IOException e) {

        }

        return s;

    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}
