package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.exception.CpfUniqueViolationException;
import br.com.pbcompass.demoparkapi.repository.ParkClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkClientService {

    private final ParkClientRepository parkClientRepository;

    @Transactional
    public ParkClient save(ParkClient parkClient) {
        try {
            return parkClientRepository.save(parkClient);
        }catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException("Cpf already registered");
        }
    }

}
