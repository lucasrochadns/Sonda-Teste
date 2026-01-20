package br.com.sonda.aeronave.services;


import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.dto.AeronaveDTO;
import br.com.sonda.aeronave.dto.AeronavePatchDTO;
import br.com.sonda.aeronave.dto.AeronavePorDecadaDTO;
import br.com.sonda.aeronave.dto.AeronavePorFabricanteDTO;
import br.com.sonda.aeronave.domain.repository.AeronaveRepository;

import br.com.sonda.aeronave.mapper.AeronaveMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AeronaveService {


    private final AeronaveRepository aeronaveRepository;
    private final AeronaveMapper mapper;

    @Transactional(readOnly = true)
    public Page<AeronaveDTO> findAll(Pageable pageable) {
        return aeronaveRepository.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<AeronaveDTO> find(String termo) {
        return aeronaveRepository.findByTermo(termo).stream().map(mapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public AeronaveDTO findById(Long id) {
        return mapper.toDTO(aeronaveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aeronave Não Encontrada: " + id)));
    }

    @Transactional
    public AeronaveDTO save(AeronaveDTO aeronave) {
        return mapper.toDTO(aeronaveRepository.save(mapper.toEntity(aeronave)));
    }

    @Transactional
    public AeronaveDTO updateById(Long id, AeronaveDTO aeronave) {
        if(!aeronaveRepository.existsById(aeronave.id())){
            throw new EntityNotFoundException("ID não existe " + aeronave.id());
        }
        return mapper.toDTO(aeronaveRepository.save(mapper.toEntity(aeronave)));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!aeronaveRepository.existsById(id)) {
            throw new EntityNotFoundException("Aeronave Não Encontrada: " + id);
        }
        aeronaveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AeronaveDTO> findByNaoVendido(){
      return aeronaveRepository.findByVendidoFalse().stream().map(mapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<AeronavePorDecadaDTO> findByAnoFabricacao(){
        return aeronaveRepository.findAllByOrderByAnoFabricacaoAsc()
                .stream().collect(Collectors.groupingBy(
                        a -> (a.getAnoFabricacao() / 10) * 10,
                        LinkedHashMap::new, Collectors.mapping(mapper::toDTO, Collectors.toList())
                )).entrySet()
                .stream()
                .map(x -> new AeronavePorDecadaDTO(x.getKey(), x.getValue())).toList();
    }

    @Transactional(readOnly = true)
    public List<AeronavePorFabricanteDTO> findByFabricante(){
        return aeronaveRepository.findAllByOrderByFabricanteAsc()
                .stream().collect(Collectors.groupingBy(
                        Aeronave::getFabricante,
                        LinkedHashMap::new, Collectors.mapping(mapper::toDTO, Collectors.toList())
                )).entrySet().stream()
                .map(x -> new AeronavePorFabricanteDTO(x.getKey(), x.getValue())).toList();
    }

    @Transactional(readOnly = true)
    public List<AeronaveDTO> findRecent(){
        return aeronaveRepository.findRecent(OffsetDateTime.now().minusDays(7))
                .stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public AeronavePatchDTO patch(Long id, AeronavePatchDTO aeronavePatchDTO){
        Aeronave aeronave = aeronaveRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Aeronave não Encontrada " + id));

        if(aeronavePatchDTO.fabricante() != null) aeronave.setFabricante(aeronavePatchDTO.fabricante());
        if(aeronavePatchDTO.nome() != null) aeronave.setNome(aeronavePatchDTO.nome());
        if(aeronavePatchDTO.anoFabricacao() != null) aeronave.setAnoFabricacao(aeronavePatchDTO.anoFabricacao());
        if(aeronavePatchDTO.vendido() != null) aeronave.setVendido(aeronavePatchDTO.vendido());

        aeronave.setUpdateAt(OffsetDateTime.now());
        return AeronavePatchDTO.from(aeronaveRepository.save(aeronave));
    }
}
