package com.poketracker.poke_tracker_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PokeApiResponse(
        Long id,
        String name,
        Sprites sprites,
        @JsonProperty("game_indices") List<GameIndex> gameIndices
) {
    public record Sprites(
            @JsonProperty("front_default") String frontDefault,
            @JsonProperty("front_shiny") String frontShiny

    ){}

    public record GameIndex(
            NamedResource version
    ){}

    public record Species(
            @JsonProperty("evolution_chain") ChainRef evolutionChain
    ){}

    public record ChainRef(String url){}

    public record EvolutionChain(ChainLink chain){}

    public record ChainLink(
            NamedResource species,
            @JsonProperty("evolution_details") List<EvolutionDetail> evolutionDetails,
            @JsonProperty("evolves_to") List<ChainLink> evolvesTo
    ){}

    public record EvolutionDetail(
            @JsonProperty("min_level") Integer minLevel,
            NamedResource trigger
    ){}

    public record NamedResource(String name){}
}