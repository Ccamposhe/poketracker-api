package com.poketracker.poke_tracker_api.service;

import com.poketracker.poke_tracker_api.dto.LoginRequest;
import com.poketracker.poke_tracker_api.dto.RegisterRequest;
import com.poketracker.poke_tracker_api.entity.AppUser;
import com.poketracker.poke_tracker_api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request){
        if (appUserRepository.existsByEmail(request.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        if (appUserRepository.existsByNickname(request.nickname())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nickname já em uso");
        }

        AppUser user = new AppUser();
        user.setEmail(request.email());
        user.setNickname(request.nickname());
        user.setPassword(passwordEncoder.encode(request.password()));

        appUserRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request){
        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        }

        return jwtService.generateToken(user.getEmail());
    }
}