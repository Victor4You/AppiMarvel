package com.prueba.marvel.service;

import com.prueba.marvel.model.MarvelApiResponse;
import com.prueba.marvel.model.Character; // Importa la clase correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class MarvelService {

    @Value("${marvel.api.public.key}")
    private String publicKey;

    @Value("${marvel.api.private.key}")
    private String privateKey;

    private final RestTemplate restTemplate;

    @Autowired
    public MarvelService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Character> getCharacters(int limit) {
        String url = buildUrl("/v1/public/characters", limit);
        ResponseEntity<MarvelApiResponse> response = restTemplate.getForEntity(url, MarvelApiResponse.class);
        return response.getBody().getData().getResults();
    }

    public Character getCharacterDetail(String id) {
        String url = buildUrl("/v1/public/characters/" + id, 1);
        ResponseEntity<MarvelApiResponse> response = restTemplate.getForEntity(url, MarvelApiResponse.class);
        return response.getBody().getData().getResults().get(0);
    }

    private String buildUrl(String path, int limit) {
        long timestamp = System.currentTimeMillis();
        String hash = DigestUtils.md5DigestAsHex((timestamp + privateKey + publicKey).getBytes());
        return "https://gateway.marvel.com" + path + "?ts=" + timestamp + "&apikey=" + publicKey + "&hash=" + hash + "&limit=" + limit;
    }
}
