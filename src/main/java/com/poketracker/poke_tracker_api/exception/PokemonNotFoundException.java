package com.poketracker.poke_tracker_api.exception;

public class PokemonNotFoundException extends RuntimeException{
    public PokemonNotFoundException(String message){
        super(message);
    }
}
