package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
