package com.cesmac.tarefa.api.shared.interfaces;

import com.cesmac.tarefa.api.shared.uteis.MensagemUtils;

public interface IEnumLabel<E extends Enum<E>> {
    default String getDescricao() {
        return MensagemUtils.getEnumLabel(this);
    }

    default String getDescricao(String[] mensagem) {
        return MensagemUtils.getEnumLabel(this, mensagem);
    }
}
