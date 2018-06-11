package server;

import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Timestamp;

/**
 * Created by mars on 5/31/2018.
 */
@ComponentScan
@SpringBootApplication
public class ServerLauncher {


    public static void main(String[] args) {


        SpringApplication.run(ServerLauncher.class, args);



    }
}
