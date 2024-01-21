package fr.uge.jee.springmvc.pokematch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Downloader {
    public static byte[] downloadFile(String fileUrl) throws IOException {
        System.out.println(fileUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(fileUrl, byte[].class);
        return response.getBody();
        //passer par un tableau de bytes et un map
        // graphql charger juste dans le lien id sprites et name
    }
}
