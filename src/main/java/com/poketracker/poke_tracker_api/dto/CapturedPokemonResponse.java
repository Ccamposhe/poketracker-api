package com.poketracker.poke_tracker_api.dto;

import com.poketracker.poke_tracker_api.entity.CapturedPokemon;
import com.poketracker.poke_tracker_api.enums.Game;

import java.time.LocalDate;

public record CapturedPokemonResponse (
        Long id,
        Long pokedexId,
        String name,
        boolean shiny,
        String nickname,
        Integer level,
        Game game,
        String spriteUrl,
        LocalDate capturedAt
) {
    public static CapturedPokemonResponse from(CapturedPokemon p){
        return new CapturedPokemonResponse(
                p.getId(), p.getPokedexId(), p.getName(), p.isShiny(), p.getNickname(),
                p.getLevel(), p.getGame(), p.getSpriteUrl(), p.getCapturedAt()
        );
    }
}
