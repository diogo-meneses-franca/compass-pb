package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.ParkUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkUserRepository extends JpaRepository<ParkUser, Long> {

   Optional<ParkUser> findByUsername(String username);
}
