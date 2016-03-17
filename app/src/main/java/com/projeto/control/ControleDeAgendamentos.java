package com.projeto.control;

import com.projeto.db.AgendamentoDAO;
import com.projeto.model.Agendamento;
import com.projeto.model.Horario;
import com.projeto.model.Host;
import com.projeto.model.Medico;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by leo on 17/03/16.
 */
public class ControleDeAgendamentos {
    private AgendamentoDAO agendamentoDAO;

    public ControleDeAgendamentos(Host host) {
        this.agendamentoDAO = new AgendamentoDAO(host);
    }

    public LinkedList<Agendamento> getAgendamentos(Medico medico, String data){
        return this.agendamentoDAO.getAgendamentosPorMedicoData(medico.getUser(), data);

    }

    public LinkedList<Agendamento> getAgendamentos(String imei){
        return this.agendamentoDAO.getAgendamentosPorIMEI(imei);
    }

    public ArrayList<String> getHorarios(String user, String data){
        LinkedList<Horario> listaHorarios = this.agendamentoDAO
                .getHorariosDisponiveisPorMedicoData(user,data);
        ArrayList<String> horarios = new ArrayList<String>();
        if (listaHorarios == null)
            return null;
        for (Horario horario : listaHorarios) {
            horarios.add(horario.getHora());
        }
        return horarios;
    }

    public String cadastrar(Agendamento agendamento){
        return this.agendamentoDAO.inserirAgendamento(agendamento);
    }

    public String alterar(Agendamento agendamento){
        return this.agendamentoDAO.alterarAgendamento(agendamento);
    }

    public String remover(Agendamento agendamento){
        return this.agendamentoDAO.excluirAgendamentoById(agendamento.getIdAgendamento());

    }

}
