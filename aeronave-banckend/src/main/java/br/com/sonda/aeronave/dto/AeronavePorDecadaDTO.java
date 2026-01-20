package br.com.sonda.aeronave.dto;

import java.util.List;

public record AeronavePorDecadaDTO(
        Integer anoFabricacao,
        List<AeronaveDTO> aeronaveDTO
) {
}
