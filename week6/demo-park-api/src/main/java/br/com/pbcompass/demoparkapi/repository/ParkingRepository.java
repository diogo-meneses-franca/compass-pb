package br.com.pbcompass.demoparkapi.repository;

import br.com.pbcompass.demoparkapi.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Object> findByInvoiceAndCheckoutIsNull(String invoice);

    @Query("select count(p) from Parking p where p.client.cpf = ?1 and p.checkout != null")
    long countHowManyTimesTheClientMadeCheckout(String cpf);
}
