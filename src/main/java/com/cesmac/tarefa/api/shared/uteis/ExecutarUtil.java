package com.cesmac.tarefa.api.shared.uteis;

import com.cesmac.tarefa.api.configuration.exceptions.ApiTarefaRuntimeException;
import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoNotFoundException;
import com.cesmac.tarefa.api.exception.RecursoNaoEncontradoException;
import com.cesmac.tarefa.api.exception.RecursoObrigatorioException;
import com.cesmac.tarefa.api.shared.EValidacao;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutarUtil {

    public static void executarComandoComTratamentoSemRetornoComMensagem(
            Runnable comando, String mensagem) {
        try {
            comando.run();
        } catch (RecursoNaoEncontradoException | RecursoObrigatorioException ex) {
            log.warn(ex.getLocalizedMessage());
            throw new ValidacaoNotFoundException(EValidacao.TARAFA_NAO_LOCALIZADA_POR_ID);
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
            log.warn(ex.getLocalizedMessage());
            throw new ValidacaoNotFoundException(EValidacao.TARAFA_NAO_LOCALIZADA_POR_ID);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.warn(mensagem);
            throw new ApiTarefaRuntimeException(mensagem);
        }
    }
}
