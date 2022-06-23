package com.innowise.secret_santa.repository;

import com.innowise.secret_santa.model.postgres.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findPlayerByProfileAccountId(Long idAccount);


}