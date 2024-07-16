package com.cesmac.tarefa.api.shared;

import com.cesmac.tarefa.api.shared.interfaces.IEnumLabel;
import lombok.Getter;

@Getter
public enum EValidacao implements IEnumLabel<EValidacao> {
    ENTRADA_DE_DADOS_INVALIDA(-1),
    CAMPO_INVALIDO_NAO_IDENTIFICADO(-2),
    TARAFA_NAO_LOCALIZADA_POR_ID(-3),
    ALUNO_NAO_LOCALIZADA_POR_ID(-4),
    GRUPO_NAO_LOCALIZADA_POR_ID(-5),
    ALUNO_POSSUI_GRUPO_ATIVO(-6),
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
