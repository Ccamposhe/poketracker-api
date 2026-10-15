package com.poketracker.poke_tracker_api.repository;

import com.poketracker.poke_tracker_api.entity.AppUser;
import com.poketracker.poke_tracker_api.entity.CapturedPokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CapturedPokemonRepository extends JpaRepository<CapturedPokemon, Long> {


    List<CapturedPokemon> findByOwner(AppUser owner);

    List<CapturedPokemon> findByPokedexIdAndOwner(Long pokedexId, AppUser owner);

    Optional<CapturedPokemon> findByIdAndOwner(Long id, AppUser owner);
}
