package com.projeto.db;

/**
 * Created by leo on 16/03/16.
 */
public enum OperacaoLocalizacaoDAO  {

    INSERIR("inserirLocalizacao"),
    ATUALIZAR("alterarLocalizacao"),
    EXCLUIR("excluirLocalizacaoByUser"),
    BUSCAR_LOCALIZACOES("getLocalizacaoByMedicos"),
    BUSCAR_LOCALIZACAO_POR_USUARIO("getLocalizacaoByUser");

    private String funcao;

    private OperacaoLocalizacaoDAO(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
