package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkClientRepository extends JpaRepository<ParkClient, Long> {
}
