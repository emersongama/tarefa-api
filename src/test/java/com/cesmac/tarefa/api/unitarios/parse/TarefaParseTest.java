package com.cesmac.tarefa.api.unitarios.parse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import com.cesmac.tarefa.api.shared.parse.TarefaParse;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TarefaParseTest {
    @InjectMocks private TarefaParse parse;

    @Test
    public void deveriaConverterParaEntidadeSucesso() {
        TarefaDTO dto = obterTarefaDTO();
        dto.setId(5L);
        Tarefa retorno = parse.converterParaEntidade(dto);
        assertEquals(dto.getId(), retorno.getId());
        assertEquals(dto.getTitulo(), retorno.getTitulo());
        assertEquals(dto.getDescricao(), retorno.getDescricao());
    }

    private Tarefa obterTarefa() {
        return obterTarefa(LocalDateTime.now());
    }

    private Tarefa obterTarefa(LocalDateTime dataHoraCriacao) {
        return Tarefa.builder()
                .id(1L)
                .titulo("Nova Tarefa")
                .descricao("Desc Nota Tarefa")
                .dataHoraUltimaAlteracao(dataHoraCriacao)
                .build();
    }

    private TarefaDTO obterTarefaDTO() {
        Tarefa tarefa = obterTarefa();
        return TarefaDTO.builder()
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .build();
    }
}
