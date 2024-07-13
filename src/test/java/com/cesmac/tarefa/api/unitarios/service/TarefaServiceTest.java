package com.cesmac.tarefa.api.unitarios.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cesmac.tarefa.api.configuration.exceptions.ApiTarefaRuntimeException;
import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoNotFoundException;
import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.repository.TarefaRepository;
import com.cesmac.tarefa.api.service.impl.TarefaServiceImpl;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import com.cesmac.tarefa.api.shared.uteis.MensagemUtils;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TarefaServiceTest {
    @InjectMocks private TarefaServiceImpl service;
    @Mock private TarefaRepository repository;

    /*@Test
    public void deveriaCadastrarComSucesso() {
        LocalDateTime dataHoraCricao = LocalDateTime.now();
        Tarefa tarefa = obterTarefa(dataHoraCricao);
        TarefaDTO request = obterTarefaDTO();
        doReturn(tarefa).when(repository).save(any(Tarefa.class));

        Tarefa retorno = service.salvar(request);
        assertNotNull(retorno.getId());
        assertEquals(request.getTitulo(), retorno.getTitulo());
        assertEquals(request.getDescricao(), retorno.getDescricao());
        assertEquals(dataHoraCricao, retorno.getDataHoraUltimaAlteracao());
        verify(repository, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void deveriaRetornarErroNaoIdentificadoAoCadastrar() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");
            TarefaDTO request = obterTarefaDTO();
            doThrow(RuntimeException.class).when(repository).save(any(Tarefa.class));
            assertThrows(ApiTarefaRuntimeException.class, () -> service.salvar(request));
        }
    }*/

    @Test
    public void deveriaEditarComSucesso() {
        LocalDateTime dataHoraCricao = LocalDateTime.now();
        Long idTarefa = 1L;
        Tarefa tarefa = obterTarefa(dataHoraCricao);
        TarefaDTO request = obterTarefaDTO();
        doReturn(Optional.of(tarefa))
                .when(repository)
                .findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        doReturn(tarefa).when(repository).save(any(Tarefa.class));

        Tarefa retorno = service.alterar(request, idTarefa);
        assertEquals(idTarefa, retorno.getId());
        assertEquals(request.getTitulo(), retorno.getTitulo());
        assertEquals(request.getDescricao(), retorno.getDescricao());
        assertEquals(dataHoraCricao, retorno.getDataHoraUltimaAlteracao());
        verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        verify(repository, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void deveriaRetornarErroNaoIdentificadoAoEditar() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            LocalDateTime dataHoraCricao = LocalDateTime.now();
            Long idTarefa = 1L;
            Tarefa tarefa = obterTarefa(dataHoraCricao);
            TarefaDTO request = obterTarefaDTO();
            doReturn(Optional.of(tarefa))
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));

            doThrow(RuntimeException.class).when(repository).save(any(Tarefa.class));

            assertThrows(ApiTarefaRuntimeException.class, () -> service.alterar(request, idTarefa));
            verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        }
    }

    @Test
    public void deveriaValidacaoNotFoundExceptionAoAlterar() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            Long idTarefa = 1L;
            TarefaDTO request = obterTarefaDTO();
            doReturn(Optional.empty())
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));

            assertThrows(
                    ValidacaoNotFoundException.class, () -> service.alterar(request, idTarefa));
        }
    }

    @Test
    public void deveriaConcluirComSucesso() {
        Long idTarefa = 1L;
        Tarefa tarefa = obterTarefa();

        doReturn(Optional.of(tarefa))
                .when(repository)
                .findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        doReturn(tarefa).when(repository).save(any(Tarefa.class));

        service.concluir(idTarefa);
        verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        verify(repository, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void deveriaRetornarErroNaoIdentificadoAoConcluir() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            Long idTarefa = 1L;
            Tarefa tarefa = obterTarefa();
            doReturn(Optional.of(tarefa))
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));
            doThrow(RuntimeException.class).when(repository).save(any(Tarefa.class));

            assertThrows(ApiTarefaRuntimeException.class, () -> service.concluir(idTarefa));
            verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        }
    }

    @Test
    public void deveriaValidacaoNotFoundExceptionAoConcluir() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            Long idTarefa = 1L;
            Tarefa tarefa = obterTarefa();
            doReturn(Optional.empty())
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));

            assertThrows(ValidacaoNotFoundException.class, () -> service.concluir(idTarefa));
        }
    }

    @Test
    public void deveriaExcluirComSucesso() {
        Long idTarefa = 1L;
        Tarefa tarefa = obterTarefa();

        doReturn(Optional.of(tarefa))
                .when(repository)
                .findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        doReturn(tarefa).when(repository).save(any(Tarefa.class));

        service.excluir(idTarefa);
        verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        verify(repository, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void deveriaRetornarErroNaoIdentificadoAoExcluir() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            Long idTarefa = 1L;
            Tarefa tarefa = obterTarefa();
            doReturn(Optional.of(tarefa))
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));
            doThrow(RuntimeException.class).when(repository).save(any(Tarefa.class));

            assertThrows(ApiTarefaRuntimeException.class, () -> service.excluir(idTarefa));
            verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
        }
    }

    @Test
    public void deveriaValidacaoNotFoundExceptionAoExcluir() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            Long idTarefa = 1L;
            doReturn(Optional.empty())
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));

            assertThrows(ValidacaoNotFoundException.class, () -> service.excluir(idTarefa));
        }
    }

    @Test
    public void deveriaListarComSucesso() {
        Tarefa tarefa = obterTarefa();

        doReturn(Collections.singletonList(tarefa))
                .when(repository)
                .findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();

        List<TarefaDTO> listaRetorno = service.consultarTodas();

        assertEquals(tarefa.getTitulo(), listaRetorno.get(0).getTitulo());
        assertEquals(tarefa.getDescricao(), listaRetorno.get(0).getDescricao());
        verify(repository, times(1))
                .findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();
    }

    @Test
    public void deveriaRetornarErroNaoIdentificadoAoListar() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            doThrow(RuntimeException.class)
                    .when(repository)
                    .findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();

            assertThrows(ApiTarefaRuntimeException.class, () -> service.consultarTodas());
        }
    }

    @Test
    public void deveriaConsultarPorIdComSucesso() {
        Long idTarefa = 1L;
        Tarefa tarefa = obterTarefa();

        doReturn(Optional.of(tarefa))
                .when(repository)
                .findByIdAndDataHoraExclusaoIsNull(any(Long.class));

        TarefaDTO retorno = service.buscar(idTarefa);

        assertEquals(tarefa.getTitulo(), retorno.getTitulo());
        assertEquals(tarefa.getDescricao(), retorno.getDescricao());
        verify(repository, times(1)).findByIdAndDataHoraExclusaoIsNull(any(Long.class));
    }

    @Test
    public void deveriaRetornarErroNaoIdentificadoAoConsultarPorId() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("Teste");

            Long idTarefa = 1L;

            doThrow(RuntimeException.class)
                    .when(repository)
                    .findByIdAndDataHoraExclusaoIsNull(any(Long.class));

            assertThrows(ApiTarefaRuntimeException.class, () -> service.buscar(idTarefa));
        }
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
