package br.com.sonda.aeronave.services;



import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.repository.AeronaveRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AeronaveService {


    private final AeronaveRepository aeronaveRepository;

    @Transactional(readOnly = true)
    public Page<Aeronave> findAll(Pageable pageable){
        return aeronaveRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Aeronave> find(String termo){
        return aeronaveRepository.findByTermo(termo);
    }

    @Transactional(readOnly = true)
    public Aeronave findById(Long id){
        return aeronaveRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Aeronave Não Encontrada: " + id));
    }

    @Transactional
    public Aeronave save(Aeronave aeronave){
        return aeronaveRepository.save(aeronave);
    }

    @Transactional
    public Aeronave updateById(Long id, Aeronave aeronave){
        return aeronaveRepository.save(aeronave);
    }
}
