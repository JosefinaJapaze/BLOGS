package com.jjapaze.boilerplate.springboot.service;

import com.jjapaze.boilerplate.springboot.model.Comment;
import com.jjapaze.boilerplate.springboot.model.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class BlogService {
    private final String POSTS_API_URL = "https://jsonplaceholder.typicode.com/posts";

    public List<Post> getPostsFromEndpoint() throws IOException {
        URL url = new URL(POSTS_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.toString(), new TypeReference<List<Post>>() {
            });
        } else {
            throw new IOException("Error al obtener datos de la API. Código de respuesta: " + responseCode);
        }
    }

    public List<Comment> getCommentsForPostFromEndpoint(long postId) throws IOException {
        String commentsApiUrl = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
        URL url = new URL(commentsApiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.toString(), new TypeReference<List<Comment>>() {});
        } else {
            throw new IOException("Error al obtener datos de comentarios de la API. Código de respuesta: " + responseCode);
        }
    }
}



