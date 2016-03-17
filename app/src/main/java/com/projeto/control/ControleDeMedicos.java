package com.projeto.control;

import android.widget.Toast;

import com.projeto.db.MedicoDAO;
import com.projeto.easymedico.Principal;
import com.projeto.model.Host;
import com.projeto.model.Medico;

/**
 * Created by leo on 16/03/16.
 */
public class ControleDeMedicos {

    private String msgErro;
    private MedicoDAO medicoDAO;

    public ControleDeMedicos(Host host) {
        this.medicoDAO = new MedicoDAO(host);
    }

    public boolean cadastrar(Medico medico) throws InterruptedException {
         String result = this.medicoDAO.inserirMedico(medico);

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
