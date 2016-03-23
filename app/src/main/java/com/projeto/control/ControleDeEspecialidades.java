package com.projeto.control;

import com.projeto.db.EspecialidadeDAO;
import com.projeto.model.Especialidade;
import com.projeto.model.Host;

import java.util.LinkedList;

/**
 * Created by leo on 3/23/16.
 */
public class ControleDeEspecialidades {
    private String msgErro;
    private EspecialidadeDAO especialidadeDAO;

    public ControleDeEspecialidades(Host host) {
        this.especialidadeDAO = new EspecialidadeDAO(host);
    }

    public LinkedList<Especialidade> getLocalizacoes(){
        return this.especialidadeDAO.getEspecialidades();

    }
}
