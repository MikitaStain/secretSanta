package com.innowise.secret_santa.repository;

import com.innowise.secret_santa.model.postgres.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    List<Game> findAllByOrganizerAccountId(Long id);

    Game findGameByNameGame(String nameGame);


}