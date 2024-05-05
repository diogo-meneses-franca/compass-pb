package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.Parking;
import br.com.pbcompass.demoparkapi.repository.ParkingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public Parking save(Parking parking) {
        return parkingRepository.save(parking);
    }

    public Parking findByInvoice(String invoice) {
        return (Parking) parkingRepository.findByInvoiceAndCheckoutIsNull(invoice).orElseThrow(
                () -> new EntityNotFoundException("Parking with Invoice " + invoice + " not found or checkout carried out")
        );
    }

    @Transactional(readOnly = true)
    public long getHowManyTimesTheClientMadeACompleteParking(String cpf) {
        return parkingRepository.countHowManyTimesTheClientMadeCheckout(cpf);
    }
}
