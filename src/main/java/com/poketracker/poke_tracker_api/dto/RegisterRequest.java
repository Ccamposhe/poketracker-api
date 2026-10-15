package com.poketracker.poke_tracker_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "O email é obrigatorio")
        @Email(message = "Email é invalido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 25, message = "A senha deve ter entre 6 e 25 caracteres")
        String password,

        @NotBlank(message = "O nickname é obrigatorio")
        @Size(max = 50, message = "O nickname deve ter no maximo 50 caracteres")
        String nickname
) {
}
