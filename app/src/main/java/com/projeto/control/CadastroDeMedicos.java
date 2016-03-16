package com.projeto.control;

import android.widget.Toast;

import com.projeto.db.MedicoDAO;
import com.projeto.easymedico.Principal;
import com.projeto.model.Host;
import com.projeto.model.Medico;

/**
 * Created by leo on 16/03/16.
 */
public class CadastroDeMedicos {

    private String msgErro;

    public boolean cadastrar(Medico medico, Host host) throws InterruptedException {
        MedicoDAO medicoDAO = new MedicoDAO(Principal.getHost());
        String result = medicoDAO.inserirMedico(medico);

        if (!result.equals("true")) {
            msgErro = result;
            return false;
        }
        return true;
    }

    public String getMsgErro() {
        return msgErro;
    }
}
