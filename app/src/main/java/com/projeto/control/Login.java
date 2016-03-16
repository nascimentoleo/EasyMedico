package com.projeto.control;

import java.io.Serializable;

import com.projeto.db.LocalizacaoMedicosDAO;
import com.projeto.db.MedicoDAO;
import com.projeto.easymedico.Principal;
import com.projeto.model.Host;
import com.projeto.model.Medico;

public class Login implements Serializable {

    private String msgErro;

    public String getMsgErro() {
        return this.msgErro;
    }

    public Medico realizarLogin(String user, String password, Host host) {
        MedicoDAO medicoDAO = new MedicoDAO(host);
        Medico medico = medicoDAO.getMedicoByUser(user);
        // Se pegou um m�dico
        if (medico != null) {
            if (medico.getPassword().equals(password)) {
                // Pego a localizacao caso haja
                LocalizacaoMedicosDAO localizacaoMedicosDAO = new LocalizacaoMedicosDAO(Principal.getHost());
                medico.setLocalizacao(localizacaoMedicosDAO.getLocalizacaoByUser(user));
                return medico;
            } else
                this.msgErro = "Senha incorreta";
        } else
            this.msgErro = "Não foi encontrado usuário no banco";

        return null;

    }

}
