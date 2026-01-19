package br.com.sonda.aeronave.services;

import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.repository.AeronaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AeronaveService {

    @Autowired
    private final AeronaveRepository aeronaveRepository;

    @Transactional
    public Page<AeronaveDTO> findAll(Pageable pageable){
        return aeronaveRepository.findAll(pageable).map(AeronaveDTO::from);
    }
}
