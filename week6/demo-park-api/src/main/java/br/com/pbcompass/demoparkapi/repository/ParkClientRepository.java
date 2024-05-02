package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.repository.projection.ParkClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParkClientRepository extends JpaRepository<ParkClient, Long> {

    @Query("select c from ParkClient c")
    Page<ParkClientProjection> findAllPageable(Pageable pageable);

    @Query("select c from ParkClient c where c.user.id = ?1")
    ParkClient findClientByUserId(Long userId);
}
