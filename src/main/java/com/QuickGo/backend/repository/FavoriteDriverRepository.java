package com.QuickGo.backend.repository;

import com.QuickGo.backend.models.FavoriteDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteDriverRepository extends JpaRepository<FavoriteDriver, Integer> {
}
