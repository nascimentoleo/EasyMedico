package com.projeto.db;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import com.projeto.control.BuscarMedicoTask;
import com.projeto.control.CRUDTask;
import com.projeto.model.Host;
import com.projeto.model.Medico;
import com.projeto.model.Soap;

public class MedicoDAO {

	//private static final String URL = "http://192.168.0.116:8080/easyMedicoWS/services/MedicoDAO?wsdl";
	//private static final String NAMESPACE = "http://easymedicows.com.br";
	private Soap soap;
	private Host host;

	public MedicoDAO(Host host) {
		this.soap = new Soap("MedicoDAO");
		this.host = host;
	}

	private static final String INSERIR = "inserirMedico";
	private static final String ATUALIZAR = "alterarMedico";
	private static final String EXCLUIR = "excluirMedicoByUser";
	private static final String BUSCAR_MEDICO_POR_USER = "getMedicoByUser";
	private static final String BUSCAR_MEDICOS = "getMedicos";

	public String inserirMedico(Medico medico) throws InterruptedException {
		// Para manipular o Web Service, usaremos a biblioteca do kSoap2
		// Primeiro pegamos o namespace e qual opera��o iremos realizar
		SoapObject inserirSOAP = new SoapObject(this.soap.getNamespace(), INSERIR);
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

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(inserirSOAP);
		envelope.implicitTypes = true; // Flag obrigat�rio para funcionar

		// Enviaremos via http o envelope, para isso precisamos de um objeto da
		// classe HttpTransportSE
		// S� que a partir da vers�o 9 do android, para realizar requisi��es via
		// rede precisamos de uma classe AsyncTask,
		// que vai executar essa requisi��o em uma Thread separada
		CRUDTask tMedico = new CRUDTask(envelope, INSERIR, this.soap.getURL(this.host));
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
				BUSCAR_MEDICO_POR_USER);
		buscarSOAP.addProperty("user", user);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(buscarSOAP);
		envelope.implicitTypes = true;
		BuscarMedicoTask tMedico = new BuscarMedicoTask(envelope,
				BUSCAR_MEDICO_POR_USER,  this.soap.getURL(this.host));
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
