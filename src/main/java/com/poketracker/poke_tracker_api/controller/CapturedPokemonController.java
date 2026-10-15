package com.poketracker.poke_tracker_api.controller;

import com.poketracker.poke_tracker_api.dto.CapturedPokemonRequest;
import com.poketracker.poke_tracker_api.dto.CapturedPokemonResponse;
import com.poketracker.poke_tracker_api.entity.CapturedPokemon;
import com.poketracker.poke_tracker_api.service.CapturedPokemonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pokemon")
@RequiredArgsConstructor

public class CapturedPokemonController {
    private final CapturedPokemonService service;

    @PostMapping
    public ResponseEntity<CapturedPokemonResponse> createdCapture(@Valid @RequestBody CapturedPokemonRequest pokemon){
        CapturedPokemon created = service.capturedPokemon(pokemon);
        return ResponseEntity.status(HttpStatus.CREATED).body(CapturedPokemonResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<CapturedPokemonResponse>> listCapture(){
        return ResponseEntity.ok(service.capturedPokemonList().stream().map(CapturedPokemonResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapturedPokemonResponse> findCaptureById(@PathVariable Long id){
        return ResponseEntity.ok(CapturedPokemonResponse.from(service.capturedPokemonId(id)));
    }

    @GetMapping("/pokedex/{pokedexId}")
    public ResponseEntity<List<CapturedPokemonResponse>> listCapturedPokedexId(@PathVariable Long pokedexId){
        return ResponseEntity.ok(service.pokedexId(pokedexId).stream()
                .map(CapturedPokemonResponse::from).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapturedPokemonResponse> updateCapturedById(@PathVariable Long id, @RequestBody @Valid CapturedPokemonRequest pokemonRequest){
        return ResponseEntity.ok(CapturedPokemonResponse.from(service.updateCaptured(id, pokemonRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deletCapture(id);
        return ResponseEntity.noContent().build();
    }

}
