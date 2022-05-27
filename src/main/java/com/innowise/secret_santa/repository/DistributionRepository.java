package com.innowise.secret_santa.repository;

import com.innowise.secret_santa.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution,Long> {
}
