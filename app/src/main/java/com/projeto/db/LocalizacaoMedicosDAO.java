package com.projeto.db;

import java.util.LinkedList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import com.projeto.control.BuscarLocalizacaoPorMedicoTask;
import com.projeto.control.BuscarLocalizacoesTask;
import com.projeto.control.BuscarMedicoTask;
import com.projeto.control.CRUDTask;
import com.projeto.model.LocalizacaoMedicos;
import com.projeto.model.Medico;

public class LocalizacaoMedicosDAO {

	private static final String URL = "http://192.168.0.116:8080/easyMedicoWS/services/LocalizacaoMedicosDAO?wsdl";
	private static final String NAMESPACE = "http://easymedicows.com.br";
	private static final String INSERIR = "inserirLocalizacao";
	private static final String ATUALIZAR = "alterarLocalizacao";
	private static final String EXCLUIR = "excluirLocalizacaoByUser";
	private static final String BUSCAR_LOCALIZACAO_POR_USER = "getLocalizacaoByUser";
	private static final String BUSCAR_LOCALIZACOES = "getLocalizacaoByMedicos";

	public static String inserirLocalizacao(LocalizacaoMedicos localizacao) {
		// Para manipular o Web Service, usaremos a biblioteca do kSoap2
		// Primeiro pegamos o namespace e qual opera��o iremos realizar
		SoapObject inserirSOAP = new SoapObject(NAMESPACE, INSERIR);
		// Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
		// que ser� enviado com as informa��es da localiza��o
		SoapObject localizacaoSOAP = new SoapObject(NAMESPACE, "localizacao");
		// Adicionamos todos os atributos como propriedades para o objeto SOAP,
		// semelhante a uma Tabela Hash
		localizacaoSOAP.addProperty("user", localizacao.getUser());
		localizacaoSOAP.addProperty("latitude", localizacao.getLatitude());
		localizacaoSOAP.addProperty("longitude", localizacao.getLongitude());
		localizacaoSOAP.addProperty("ativo", localizacao.getAtivo());
		// Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
		// para poder enviar
		inserirSOAP.addSoapObject(localizacaoSOAP);

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
		CRUDTask tLocalizacao = new CRUDTask(envelope, INSERIR, URL);
		tLocalizacao.execute();

		while (tLocalizacao.getResposta().equals(""))
			continue;

		return tLocalizacao.getResposta();

	}

	public static String alterarLocalizacao(LocalizacaoMedicos localizacao) {
		// Para manipular o Web Service, usaremos a biblioteca do kSoap2
		// Primeiro pegamos o namespace e qual opera��o iremos realizar
		SoapObject atualizarSOAP = new SoapObject(NAMESPACE, ATUALIZAR);
		// Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
		// que ser� enviado com as informa��es da localiza��o
		SoapObject localizacaoSOAP = new SoapObject(NAMESPACE, "localizacao");
		// Adicionamos todos os atributos como propriedades para o objeto SOAP,
		// semelhante a uma Tabela Hash
		localizacaoSOAP.addProperty("user", localizacao.getUser());
		localizacaoSOAP.addProperty("latitude", localizacao.getLatitude());
		localizacaoSOAP.addProperty("longitude", localizacao.getLongitude());
		localizacaoSOAP.addProperty("ativo", localizacao.getAtivo());
		// Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
		// para poder enviar
		atualizarSOAP.addSoapObject(localizacaoSOAP);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(atualizarSOAP);
		envelope.implicitTypes = true; // Flag obrigat�rio para funcionar

		// Enviaremos via http o envelope, para isso precisamos de um objeto da
		// classe HttpTransportSE
		// S� que a partir da vers�o 9 do android, para realizar requisi��es via
		// rede precisamos de uma classe AsyncTask,
		// que vai executar essa requisi��o em uma Thread separada
		CRUDTask tLocalizacao = new CRUDTask(envelope, ATUALIZAR, URL);
		tLocalizacao.execute();

		while (tLocalizacao.getResposta().equals(""))
			continue;

		return tLocalizacao.getResposta();

	}

	public static String excluirLocalizacaoByUser(String user) {
		return null;

	}

	public static LinkedList<Medico> getLocalizacoes() {
		SoapObject buscarSOAP = new SoapObject(NAMESPACE,
				BUSCAR_LOCALIZACOES);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(buscarSOAP);
		envelope.implicitTypes = true;
		BuscarLocalizacoesTask tLocalizacao = new BuscarLocalizacoesTask(
				envelope, BUSCAR_LOCALIZACOES, URL);
		tLocalizacao.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tLocalizacao.getResult())
			continue;

		if (tLocalizacao.getLocalizacoesMedicos() != null)
			return tLocalizacao.getLocalizacoesMedicos();

		return null;

	}

	public static LocalizacaoMedicos getLocalizacaoByUser(String user) {
		SoapObject buscarSOAP = new SoapObject(NAMESPACE,
				BUSCAR_LOCALIZACAO_POR_USER);
		buscarSOAP.addProperty("user", user);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(buscarSOAP);
		envelope.implicitTypes = true;
		BuscarLocalizacaoPorMedicoTask tLocalizacao = new BuscarLocalizacaoPorMedicoTask(
				envelope, BUSCAR_LOCALIZACAO_POR_USER, URL);
		tLocalizacao.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tLocalizacao.getResult())
			continue;

		if (tLocalizacao.getLocalizacaoMedicos() != null)
			return tLocalizacao.getLocalizacaoMedicos();

		return null;

	}

}
