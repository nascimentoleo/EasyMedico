package com.projeto.db;

/**
 * Created by leo on 16/03/16.
 */
public enum OperacaoMedicoDAO {

    INSERIR("inserirMedico"),
    ATUALIZAR("alterarMedico"),
    EXCLUIR("excluirMedicoByUser"),
    BUSCAR_MEDICOS("getMedicos"),
    BUSCAR_MEDICOS_POR_USUARIO("getMedicoByUser");

    private String funcao;

    private OperacaoMedicoDAO(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
