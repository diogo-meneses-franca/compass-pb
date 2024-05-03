package br.com.pbcompass.demoparkapi.service;

import br.com.pbcompass.demoparkapi.entity.ParkClient;
import br.com.pbcompass.demoparkapi.exception.CpfUniqueViolationException;
import br.com.pbcompass.demoparkapi.repository.ParkClientRepository;
import br.com.pbcompass.demoparkapi.repository.projection.ParkClientProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public ParkClient findById(Long id) {
        return parkClientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Client with id " + id + " not found")
        );
    }

    @Transactional(readOnly = true)
    public Page<ParkClientProjection> findAll(Pageable pageable) {
        return parkClientRepository.findAllPageable(pageable);
    }

    public ParkClient findClientByUserId(Long id) {
        return parkClientRepository.findClientByUserId(id);
    }

    @Transactional(readOnly = true)
    public ParkClient findByCpf(String cpf) {
        return parkClientRepository.findByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException("Client with CPF " + cpf + " not found")
        );
    }
}
