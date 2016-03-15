package com.projeto.control;

import java.io.Serializable;

import com.projeto.db.LocalizacaoMedicosDAO;
import com.projeto.db.MedicoDAO;
import com.projeto.model.Medico;

public class Login implements Serializable {

	private String msgErro = "";

	public String getMsgErro() {
		return this.msgErro;
	}

	public Medico realizarLogin(String user, String password) {
		Medico medico = MedicoDAO.getMedicoByUser(user);
		// Se pegou um médico
		if (medico != null) {
			if (medico.getPassword().equals(password)) {
				// Pego a localizacao caso haja
				medico.setLocalizacao(LocalizacaoMedicosDAO
						.getLocalizacaoByUser(user));
				return medico;
			}

			else
				this.msgErro = "Senha incorreta";
		} else
			this.msgErro = "Não foi encontrado usuário no banco";

		return null;

	}

}
