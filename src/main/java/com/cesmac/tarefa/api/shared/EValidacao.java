package com.cesmac.tarefa.api.shared;

import com.cesmac.tarefa.api.shared.interfaces.IEnumLabel;
import lombok.Getter;

@Getter
public enum EValidacao implements IEnumLabel<EValidacao> {
    ENTRADA_DE_DADOS_INVALIDA(-1),
    CAMPO_INVALIDO_NAO_IDENTIFICADO(-2),
    NAO_IDENTIFICADO(-999);
    private final Integer codigo;

    EValidacao(Integer codigo) {
        this.codigo = codigo;
    }

    public static EValidacao valueOf(Integer codigo) {
        for (EValidacao val : EValidacao.values()) {
            if (val.codigo.equals(codigo)) return val;
        }
        throw new RuntimeException("Erro não cadastrado");
    }
}
