package com.projeto.db;

/**
 * Created by leo on 3/23/16.
 */
public enum OperacaoEspecialidadeDAO {
    BUSCAR_ESPECIALIDADES("getEspecialidades");

    private String funcao;

    private OperacaoEspecialidadeDAO(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
