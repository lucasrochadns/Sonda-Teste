package br.com.sonda.aeronave.mapper;

import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.dto.AeronaveDTO;
import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AeronaveMapper {

    AeronaveDTO toDTO(Aeronave aeronave);

    Aeronave toEntity(AeronaveDTO dto);

    void updateEntityFromDto(AeronaveDTO dto, @MappingTarget Aeronave entity);
}
