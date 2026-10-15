package com.poketracker.poke_tracker_api.service;

import com.poketracker.poke_tracker_api.dto.PokeApiResponse;
import com.poketracker.poke_tracker_api.enums.Game;
import com.poketracker.poke_tracker_api.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class PokeApiService {

    private final RestClient restClient;

    public PokeApiService(){
        this.restClient = RestClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
    }

    public PokeApiResponse fetchPokemonData(String pokemonName){
        String name = pokemonName.toLowerCase().trim();
        try{
            return restClient.get()
                    .uri("/pokemon/{name}", name)
                    .retrieve()
                    .body(PokeApiResponse.class);
        }catch (HttpClientErrorException.NotFound e){
            throw new PokemonNotFoundException("Pokémon não encontrado na PokéAPI: " + name);
        }
    }

    public int minLevelFor(String pokemonName){
        String name = pokemonName.toLowerCase().trim();

        PokeApiResponse.Species species = restClient.get()
                .uri("/pokemon-species/{name}", name)
                .retrieve()
                .body(PokeApiResponse.Species.class);

        PokeApiResponse.EvolutionChain chain = restClient.get()
                .uri(species.evolutionChain().url())
                .retrieve()
                .body(PokeApiResponse.EvolutionChain.class);

        return findMinLevel(chain.chain(), name, 1).orElse(1);
    }

    private Optional<Integer> findMinLevel(PokeApiResponse.ChainLink node, String target, int levelToReach){
        if (node.species().name().equals(target)){
            return Optional.of(levelToReach);
        }
        for (PokeApiResponse.ChainLink next : node.evolvesTo()){
            int requiredLevel = next.evolutionDetails().stream()
                    .filter(d -> d.trigger() != null && "level-up".equals(d.trigger().name()))
                    .map(PokeApiResponse.EvolutionDetail::minLevel)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(1);

            Optional<Integer> found = findMinLevel(next, target, requiredLevel);
            if (found.isPresent()){
                return found;
            }
        }
        return Optional.empty();
    }

    private void validateGame(PokeApiResponse pokeApiData, Game game){
        boolean existsInGame = pokeApiData.gameIndices() != null
                && pokeApiData.gameIndices().stream()
                .anyMatch(gi -> gi.version().name().equals(game.getApiName()));

        if (!existsInGame){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    pokeApiData.name() + " não existe no jogo " + game
            );
        }
    }
}