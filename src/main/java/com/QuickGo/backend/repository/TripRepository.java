package com.QuickGo.backend.repository;

import com.QuickGo.backend.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer> {
}
