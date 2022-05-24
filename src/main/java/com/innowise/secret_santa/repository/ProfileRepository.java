package com.innowise.secret_santa.repository;

import com.innowise.secret_santa.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
