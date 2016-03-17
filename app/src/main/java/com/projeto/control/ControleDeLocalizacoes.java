package com.projeto.control;

import com.google.android.gms.maps.model.LatLng;
import com.projeto.db.LocalizacaoMedicosDAO;
import com.projeto.model.Host;
import com.projeto.model.LocalizacaoMedicos;
import com.projeto.model.Medico;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by leo on 17/03/16.
 */
public class ControleDeLocalizacoes {

    private LocalizacaoMedicosDAO localizacaoMedicosDAO;

    public ControleDeLocalizacoes(Host host) {
        this.localizacaoMedicosDAO = new LocalizacaoMedicosDAO(host);
    }

    public boolean removerLocalizacao(Medico medico) {
        LocalizacaoMedicos localizacaoMedico = medico.getLocalizacao();
        localizacaoMedico.setAtivo("N");
        this.localizacaoMedicosDAO.alterarLocalizacao(localizacaoMedico);
        return true;

    }

    // Funcao que atualiza a localizacao do medico, passando as novas
    // coordenadas e alterando o status
    public void inserirLocalizacao(Medico medico, LatLng coordenadas) {
        boolean isInsert = false;
        LocalizacaoMedicos localizacaoMedico = medico.getLocalizacao();
        if (localizacaoMedico == null) {
            localizacaoMedico = new LocalizacaoMedicos();
            isInsert = true;
        }

        localizacaoMedico
                .setLatitude(Double.toString(coordenadas.latitude));
        localizacaoMedico.setLongitude(Double.toString(coordenadas.longitude));
        localizacaoMedico.setAtivo("S");
        localizacaoMedico.setUser(medico.getUser());
        if (isInsert)
            this.localizacaoMedicosDAO.inserirLocalizacao(localizacaoMedico);
        else
            this.localizacaoMedicosDAO.alterarLocalizacao(localizacaoMedico);
    }

    public LinkedList<Medico> getLocalizacoes(){
        return this.localizacaoMedicosDAO.getLocalizacoes();

    }
}
