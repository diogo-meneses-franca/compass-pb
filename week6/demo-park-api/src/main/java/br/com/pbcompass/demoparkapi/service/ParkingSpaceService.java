package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.ParkingSpace;
import br.com.pbcompass.demoparkapi.exception.ParkingSpaceUniqueCodeViolationException;
import br.com.pbcompass.demoparkapi.repository.ParkingSpaceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional
    public ParkingSpace save(ParkingSpace parkingSpace) {
        try{
            return parkingSpaceRepository.save(parkingSpace);
        }catch (DataIntegrityViolationException e){
            throw new ParkingSpaceUniqueCodeViolationException(String.format("Parking space code %s already exists", parkingSpace.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public ParkingSpace findByCode(String code) {
        return parkingSpaceRepository.findByCode(code).orElseThrow(
                () ->  new EntityNotFoundException(String.format("Parking space with %s code not found.", code))
        );
    }

    @Transactional(readOnly = true)
    public ParkingSpace findFirstByStatusFree() {
        return parkingSpaceRepository.findFirstByStatus(ParkingSpace.ParkingSpaceStatus.FREE).orElseThrow(
                () -> new EntityNotFoundException("Parking space with status free not found.")
        );
    }
}
