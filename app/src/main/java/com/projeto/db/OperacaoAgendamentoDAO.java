package com.projeto.db;

/**
 * Created by leo on 17/03/16.
 */
public enum OperacaoAgendamentoDAO {

    INSERIR("inserirAgendamento"),
    ATUALIZAR("alterarAgendamento"),
    EXCLUIR("excluirAgendamentoById"),
    BUSCAR_AGENDAMENTOS("getAgendamentosPorMedicoData"),
    BUSCAR_HORARIOS("getHorariosDisponiveisPorMedicoData"),
    BUSCAR_AGENDAMENTOS_POR_IMEI("getAgendamentosPorIMEI");

    private String funcao;

    private OperacaoAgendamentoDAO(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
