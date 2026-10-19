package com.poketracker.poke_tracker_api.service;

import com.poketracker.poke_tracker_api.dto.CapturedPokemonRequest;
import com.poketracker.poke_tracker_api.dto.PokeApiResponse;
import com.poketracker.poke_tracker_api.entity.AppUser;
import com.poketracker.poke_tracker_api.entity.CapturedPokemon;
import com.poketracker.poke_tracker_api.enums.Game;
import com.poketracker.poke_tracker_api.exception.PokemonNotFoundException;
import com.poketracker.poke_tracker_api.repository.AppUserRepository;
import com.poketracker.poke_tracker_api.repository.CapturedPokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CapturedPokemonService {

    private final CapturedPokemonRepository capturedPokemonRepository;
    private final AppUserRepository appUserRepository;
    private final PokeApiService pokeApiService;

    public CapturedPokemon capturedPokemon(CapturedPokemonRequest request){
        PokeApiResponse pokeApiData = pokeApiService.fetchPokemonData(request.name());
        validateLevel(pokeApiData.name(), request.level());
        validateGame(pokeApiData, request.game());

        CapturedPokemon pokemon = new CapturedPokemon();
        pokemon.setOwner(currentUser());
        pokemon.setPokedexId(pokeApiData.id());
        pokemon.setName(pokeApiData.name());
        pokemon.setNickname(request.nickname());
        pokemon.setLevel(request.level());
        pokemon.setGame(request.game());
        pokemon.setShiny(request.shiny());
        pokemon.setSpriteUrl(spriteFor(pokeApiData, request.shiny()));
        pokemon.setCapturedAt(request.capturedAt() != null ? request.capturedAt() : LocalDate.now());

        return capturedPokemonRepository.save(pokemon);
    }

    @Transactional(readOnly = true)
    public List<CapturedPokemon> capturedPokemonList(){
        return capturedPokemonRepository.findByOwner(currentUser());
    }

    @Transactional(readOnly = true)
    public CapturedPokemon capturedPokemonId(Long id){
        return findOwned(id);
    }

    @Transactional(readOnly = true)
    public List<CapturedPokemon> pokedexId(Long pokedexId){
        return capturedPokemonRepository.findByPokedexIdAndOwner(pokedexId, currentUser());
    }

    @Transactional
    public CapturedPokemon updateCaptured(Long id, CapturedPokemonRequest request){
        CapturedPokemon pokemon = findOwned(id);
        PokeApiResponse pokeApiData = pokeApiService.fetchPokemonData(request.name());
        validateLevel(pokeApiData.name(), request.level());
        validateGame(pokeApiData, request.game());

        pokemon.setPokedexId(pokeApiData.id());
        pokemon.setName(pokeApiData.name());
        pokemon.setNickname(request.nickname());
        pokemon.setLevel(request.level());
        pokemon.setGame(request.game());
        pokemon.setShiny(request.shiny());
        pokemon.setSpriteUrl(spriteFor(pokeApiData, request.shiny()));
        pokemon.setCapturedAt(request.capturedAt() != null ? request.capturedAt() : pokemon.getCapturedAt());

        return capturedPokemonRepository.save(pokemon);
    }

    @Transactional
    public void deletCapture(Long id){
        CapturedPokemon pokemon = findOwned(id);
        capturedPokemonRepository.delete(pokemon);
    }

    private AppUser currentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));
    }

    private CapturedPokemon findOwned(Long id){
        return capturedPokemonRepository.findByIdAndOwner(id, currentUser())
                .orElseThrow(() -> new PokemonNotFoundException("Registro não encontrado: id " + id));
    }

    private void validateLevel(String name, int level){
        int minLevel = pokeApiService.minLevelFor(name);
        if (level < minLevel){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    name + " só pode estar no nível " + minLevel + " ou acima"
            );
        }
    }

    private void validateGame(PokeApiResponse pokeApiData, Game game){
        if (game.getApiName() == null){
            return;
        }

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

    private String spriteFor(PokeApiResponse data, boolean shiny){
        if (data.sprites() == null) return null;
        return shiny ? data.sprites().frontShiny() : data.sprites().frontDefault();
    }
}