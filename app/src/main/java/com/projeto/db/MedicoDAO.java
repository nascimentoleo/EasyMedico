package com.projeto.db;

import java.util.LinkedList;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import com.projeto.control.BuscarMedicoTask;
import com.projeto.control.CRUDTask;
import com.projeto.lib.PrepararSOAP;
import com.projeto.model.Host;
import com.projeto.model.Medico;
import com.projeto.model.Soap;

public class MedicoDAO extends DAO {

    public MedicoDAO(Host host) {
        super("MedicoDAO", host);
    }

    public String inserirMedico(Medico medico) throws InterruptedException {
        // Para manipular o Web Service, usaremos a biblioteca do kSoap2
        // Primeiro pegamos o namespace e qual opera��o iremos realizar
        SoapObject inserirSOAP = new SoapObject(this.soap.getNamespace(), OperacaoMedicoDAO.INSERIR.getFuncao());
        // Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
        // que ser� enviado com as informa��es do m�dico
        SoapObject medicoSOAP = new SoapObject(this.soap.getNamespace(), "medico");
        // Adicionamos todos os atributos como propriedades para o objeto SOAP,
        // semelhante a uma Tabela Hash
        medicoSOAP.addProperty("user", medico.getUser());
        medicoSOAP.addProperty("password", medico.getPassword());
        medicoSOAP.addProperty("nome", medico.getNome());
        medicoSOAP.addProperty("especialidade", medico.getEspecialidade());
        medicoSOAP.addProperty("qtdPacientesPorHora",
                medico.getQtdPacientesPorHora());
        medicoSOAP.addProperty("agendaManha",
                medico.getAgendaManha());
        medicoSOAP.addProperty("agendaTarde",
                medico.getAgendaTarde());
        medicoSOAP.addProperty("crm",
                medico.getCrm());
        // Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
        // para poder enviar

        inserirSOAP.addSoapObject(medicoSOAP);
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(inserirSOAP);
        // Enviaremos via http o envelope, para isso precisamos de um objeto da
        // classe HttpTransportSE
        // S� que a partir da vers�o 9 do android, para realizar requisi��es via
        // rede precisamos de uma classe AsyncTask,
        // que vai executar essa requisi��o em uma Thread separada
        CRUDTask tMedico = new CRUDTask(envelope, OperacaoMedicoDAO.INSERIR.getFuncao(), this.soap.getURL());
        tMedico.execute();

        while (tMedico.getResposta().equals(""))
            continue;

        return tMedico.getResposta();

    }

    public static boolean alterarMedico(Medico medico) {
        return false;

    }

    public static boolean excluirMedicoByUser(String user) {
        return false;

    }

    public static LinkedList<Medico> getMedicos() {
        return null;

    }

    public Medico getMedicoByUser(String user) {
        SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
                OperacaoMedicoDAO.BUSCAR_MEDICOS_POR_USUARIO.getFuncao());
        buscarSOAP.addProperty("user", user);
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
        BuscarMedicoTask tMedico = new BuscarMedicoTask(envelope,
                OperacaoMedicoDAO.BUSCAR_MEDICOS_POR_USUARIO.getFuncao(), this.soap.getURL());
        tMedico.execute();
        // S� passo daqui quando terminar de executar o Task
        while (!tMedico.getResult())
            continue;

        if (tMedico.getMedicos() != null)
            if (!tMedico.getMedicos().isEmpty())
                return tMedico.getMedicos().getFirst();

        return null;

    }


}
