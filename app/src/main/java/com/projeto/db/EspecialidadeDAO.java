package com.projeto.db;

import com.projeto.control.BuscarEspecialidadesTask;
import com.projeto.lib.PrepararSOAP;
import com.projeto.model.Especialidade;
import com.projeto.model.Host;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;

/**
 * Created by leo on 3/23/16.
 */
public class EspecialidadeDAO extends DAO {

    public EspecialidadeDAO(Host host) {
        super("EspecialidadeDAO", host);
    }

    public LinkedList<Especialidade> getEspecialidades() {
        SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
                OperacaoEspecialidadeDAO.BUSCAR_ESPECIALIDADES.getFuncao());
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
        BuscarEspecialidadesTask tEspecialidades = new BuscarEspecialidadesTask(
                envelope, OperacaoEspecialidadeDAO.BUSCAR_ESPECIALIDADES.getFuncao(), this.soap.getURL());
        tEspecialidades.execute();
        // So passo daqui quando terminar de executar o Task
        while (!tEspecialidades.getResult())
            continue;

        if (tEspecialidades.getListEspecialidades() != null)
            return tEspecialidades.getListEspecialidades();

        return null;


    }

}
