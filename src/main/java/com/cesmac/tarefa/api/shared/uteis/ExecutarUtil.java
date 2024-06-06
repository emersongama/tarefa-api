package com.cesmac.tarefa.api.shared.uteis;

import com.cesmac.tarefa.api.configuration.exceptions.ApiTarefaRuntimeException;
import com.cesmac.tarefa.api.exception.RecursoNaoEncontradoException;
import com.cesmac.tarefa.api.exception.RecursoObrigatorioException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class ExecutarUtil {

    public static void executarComandoComTratamentoSemRetornoComMensagem(
            Runnable comando, String mensagem) {
        try {
            comando.run();
        } catch (RecursoNaoEncontradoException | RecursoObrigatorioException ex) {
            throw ex;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.warn(mensagem);
            throw new ApiTarefaRuntimeException(mensagem);
        }
    }

    public static <T> T executarComandoComTratamentoErroComMensagem(
            Supplier<T> comando, String mensagem) {
        try {
            return comando.get();
        } catch (RecursoNaoEncontradoException | RecursoObrigatorioException ex) {
            throw ex;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.warn(mensagem);
            throw new ApiTarefaRuntimeException(mensagem);
        }
    }
}
