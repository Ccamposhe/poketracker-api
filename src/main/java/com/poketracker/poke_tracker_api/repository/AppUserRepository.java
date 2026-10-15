package com.poketracker.poke_tracker_api.repository;

import com.poketracker.poke_tracker_api.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);


}
