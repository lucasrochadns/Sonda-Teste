package br.com.sonda.aeronave.dto;

import br.com.sonda.aeronave.domain.model.Aeronave;
import br.com.sonda.aeronave.domain.model.Fabricante;

public record AeronavePatchDTO(
        String nome,
        Fabricante fabricante,
        Integer anoFabricacao,
        String descricao,
        Boolean vendido
) {

    public static AeronavePatchDTO from(Aeronave aeronave){
        return new AeronavePatchDTO (aeronave.getNome(),
                aeronave.getFabricante(), aeronave.getAnoFabricacao(),
                aeronave.getDescricao(), aeronave.getVendido());
    }
}

