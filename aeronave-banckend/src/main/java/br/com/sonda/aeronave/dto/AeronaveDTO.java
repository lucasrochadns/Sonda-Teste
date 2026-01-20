package br.com.sonda.aeronave.dto;

import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.domain.model.Fabricante;


import java.time.OffsetDateTime;

public record AeronaveDTO (
        Long id,
        String nome,
        Fabricante fabricante,
        Integer anoFabricacao,
        String descricao,
        Boolean vendido,
        OffsetDateTime createdAt,
        OffsetDateTime updateAt
) {
}
