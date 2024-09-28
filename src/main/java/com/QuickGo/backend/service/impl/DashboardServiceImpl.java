package com.QuickGo.backend.service.impl;

import com.QuickGo.backend.dto.DashboardAnalyticsDto;
import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.Trip;
import com.QuickGo.backend.models.User;
import com.QuickGo.backend.models.enums.ERole;
import com.QuickGo.backend.repository.RoleRepository;
import com.QuickGo.backend.repository.TripRepository;
import com.QuickGo.backend.repository.UserRepository;
import com.QuickGo.backend.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TripRepository tripRepository;

    public DashboardServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public DashboardAnalyticsDto getDashboardAnalytics() {

        Role driverRole = roleRepository.findByName(ERole.ROLE_DRIVER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Role passengerRole = roleRepository.findByName(ERole.ROLE_PASSENGER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        long driversCount = userRepository.findByRolesContains(driverRole).size();
        long passengerCount = userRepository.findByRolesContains(passengerRole).size();

        List<Trip> trips = tripRepository.findAll();
        long totalActiveTrips = trips.stream()
                .filter(trip -> "ACCEPTED".equals(trip.getStatus()))
                .count();

        long totalCompletedTrips = trips.stream()
                .filter(trip -> "COMPLETED".equals(trip.getStatus()))
                .count();

        return new DashboardAnalyticsDto(
                driversCount,
                passengerCount,
                totalActiveTrips,
                totalCompletedTrips
        );
    }
}
