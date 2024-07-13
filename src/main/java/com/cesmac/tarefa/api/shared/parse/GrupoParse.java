package com.cesmac.tarefa.api.shared.parse;

import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;

public class GrupoParse {
    public Grupo converterParaEntidade(GrupoDTO grupoDTO) {
        return Grupo.builder().id(grupoDTO.getId()).nome(grupoDTO.getNome()).build();
    }
}
