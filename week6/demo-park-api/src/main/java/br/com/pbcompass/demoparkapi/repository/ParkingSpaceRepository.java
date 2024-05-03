package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    Optional<ParkingSpace> findByCode(String code);

    Optional<ParkingSpace> findFirstByStatus(ParkingSpace.ParkingSpaceStatus status);
}
