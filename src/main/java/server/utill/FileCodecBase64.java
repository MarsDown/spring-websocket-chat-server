package server.utill;

/**
 * Created by mars on 6/6/2018.
 */

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileCodecBase64 {


    public static byte[] convertToByte(String base64) throws IOException {
        String[] split = base64.split(",");
        return Base64.decodeBase64(split[1]);
    }

    public static void writeByteToFile(byte[] imgBytes, String pathFile) throws IOException {


        Path path = Paths.get(pathFile);
        Files.write(path, imgBytes);
    }

    public static String getFormatFromBase64(String base64) {

        String[] split = base64.split(",");
        String data = split[0].split(";")[0].split(":")[1].split("/")[1];

        return data;

    }

    public static String getTypeFileFromBase64(String base64) {

        String[] split = base64.split(",");
        String data = split[0].split(";")[0].split(":")[1].split("/")[0];

        return data;

    }


}