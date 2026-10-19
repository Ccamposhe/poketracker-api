package com.poketracker.poke_tracker_api.dto;

import com.poketracker.poke_tracker_api.enums.Game;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CapturedPokemonRequest(

        @NotBlank(message = "O nome é obrigatorio")
        String name,

        boolean shiny,

        String nickname,

        @NotNull(message = "O level é obrigatorio")
        @Min(value = 1, message = "O level deve ser ao menos 1")
        @Max(value = 100, message = "O level nao pode exceder 100")
        Integer level,

        @NotNull(message = "O jogo é obrigatorio")
        Game game,

        LocalDate capturedAt

) {
}
