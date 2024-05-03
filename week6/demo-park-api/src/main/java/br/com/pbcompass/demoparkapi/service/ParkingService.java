package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.Parking;
import br.com.pbcompass.demoparkapi.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public Parking save(Parking parking) {
        return parkingRepository.save(parking);
    }
}
