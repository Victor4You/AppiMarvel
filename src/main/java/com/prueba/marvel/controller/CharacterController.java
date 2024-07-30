package com.prueba.marvel.controller;

import com.prueba.marvel.model.ErrorResponse;
import com.prueba.marvel.model.Character; // Importa la clase correcta
import com.prueba.marvel.service.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private MarvelService marvelService;

    @GetMapping
    public ResponseEntity<?> getCharacters(@RequestParam(required = false, defaultValue = "25") int limit) {
        if (limit < 1 || limit > 100) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Limit must be between 1 and 100"));
        }
        List<Character> characters = marvelService.getCharacters(limit);
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacterDetail(@PathVariable String id) {
        Character character = marvelService.getCharacterDetail(id);
        return ResponseEntity.ok(character);
    }
}