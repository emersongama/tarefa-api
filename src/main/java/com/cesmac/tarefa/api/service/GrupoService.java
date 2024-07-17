package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.dto.AlunoGrupoDTO;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface GrupoService {

    Grupo salvar(GrupoDTO grupoDTO);

    Grupo alterar(GrupoDTO grupoDTO, Long id);

    void excluir(Long id);

    List<GrupoDTO> consultarTodas();

    GrupoDTO buscar(Long id);

    List<AlunoDTO> consultarAlunosDoGrupo(Long idGrupo);

    void vincular(AlunoGrupoDTO dto);

    Grupo buscarPorId(Long id);

    void desvincular(AlunoGrupoDTO alunoGrupoDTO);
}
