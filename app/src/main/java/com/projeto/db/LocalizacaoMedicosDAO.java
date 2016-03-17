package com.projeto.db;

import java.util.LinkedList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import com.projeto.control.BuscarLocalizacaoPorMedicoTask;
import com.projeto.control.BuscarLocalizacoesTask;
import com.projeto.control.CRUDTask;
import com.projeto.lib.PrepararSOAP;
import com.projeto.model.Host;
import com.projeto.model.LocalizacaoMedicos;
import com.projeto.model.Medico;
import com.projeto.model.Soap;

public class LocalizacaoMedicosDAO extends DAO{


	public LocalizacaoMedicosDAO(Host host) {
		super("LocalizacaoMedicosDAO", host);
	}

	public String inserirLocalizacao(LocalizacaoMedicos localizacao) {
		// Para manipular o Web Service, usaremos a biblioteca do kSoap2
		// Primeiro pegamos o namespace e qual opera��o iremos realizar
		SoapObject inserirSOAP = new SoapObject(this.soap.getNamespace(), OperacaoLocalizacaoDAO.INSERIR.getFuncao());
		// Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
		// que ser� enviado com as informa��es da localiza��o
		SoapObject localizacaoSOAP = new SoapObject(this.soap.getNamespace(), "localizacao");
		// Adicionamos todos os atributos como propriedades para o objeto SOAP,
		// semelhante a uma Tabela Hash
		localizacaoSOAP.addProperty("user", localizacao.getUser());
		localizacaoSOAP.addProperty("latitude", localizacao.getLatitude());
		localizacaoSOAP.addProperty("longitude", localizacao.getLongitude());
		localizacaoSOAP.addProperty("ativo", localizacao.getAtivo());
		// Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
		// para poder enviar
		inserirSOAP.addSoapObject(localizacaoSOAP);

		SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(inserirSOAP);

		// Enviaremos via http o envelope, para isso precisamos de um objeto da
		// classe HttpTransportSE
		// S� que a partir da vers�o 9 do android, para realizar requisi��es via
		// rede precisamos de uma classe AsyncTask,
		// que vai executar essa requisi��o em uma Thread separada
		CRUDTask tLocalizacao = new CRUDTask(envelope, OperacaoLocalizacaoDAO.INSERIR.getFuncao(), this.soap.getURL());
		tLocalizacao.execute();

		while (tLocalizacao.getResposta().equals(""))
			continue;

		return tLocalizacao.getResposta();

	}

	public String alterarLocalizacao(LocalizacaoMedicos localizacao) {
		// Para manipular o Web Service, usaremos a biblioteca do kSoap2
		// Primeiro pegamos o namespace e qual opera��o iremos realizar
		SoapObject atualizarSOAP = new SoapObject(this.soap.getNamespace(),  OperacaoLocalizacaoDAO.ATUALIZAR.getFuncao());
		// Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
		// que ser� enviado com as informa��es da localiza��o
		SoapObject localizacaoSOAP = new SoapObject(this.soap.getNamespace(), "localizacao");
		// Adicionamos todos os atributos como propriedades para o objeto SOAP,
		// semelhante a uma Tabela Hash
		localizacaoSOAP.addProperty("user", localizacao.getUser());
		localizacaoSOAP.addProperty("latitude", localizacao.getLatitude());
		localizacaoSOAP.addProperty("longitude", localizacao.getLongitude());
		localizacaoSOAP.addProperty("ativo", localizacao.getAtivo());
		// Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
		// para poder enviar
		atualizarSOAP.addSoapObject(localizacaoSOAP);

		SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(atualizarSOAP);

		// Enviaremos via http o envelope, para isso precisamos de um objeto da
		// classe HttpTransportSE
		// S� que a partir da vers�o 9 do android, para realizar requisi��es via
		// rede precisamos de uma classe AsyncTask,
		// que vai executar essa requisi��o em uma Thread separada
		CRUDTask tLocalizacao = new CRUDTask(envelope, OperacaoLocalizacaoDAO.ATUALIZAR.getFuncao(), this.soap.getURL());
		tLocalizacao.execute();

		while (tLocalizacao.getResposta().equals(""))
			continue;

		return tLocalizacao.getResposta();

	}

	public static String excluirLocalizacaoByUser(String user) {
		return null;

	}

	public LinkedList<Medico> getLocalizacoes() {
		SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
				OperacaoLocalizacaoDAO.BUSCAR_LOCALIZACOES.getFuncao());
		SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
		BuscarLocalizacoesTask tLocalizacao = new BuscarLocalizacoesTask(
				envelope, OperacaoLocalizacaoDAO.BUSCAR_LOCALIZACOES.getFuncao(), this.soap.getURL());
		tLocalizacao.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tLocalizacao.getResult())
			continue;

		if (tLocalizacao.getLocalizacoesMedicos() != null)
			return tLocalizacao.getLocalizacoesMedicos();

		return null;

	}

	public LocalizacaoMedicos getLocalizacaoByUser(String user) {
		SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
				OperacaoLocalizacaoDAO.BUSCAR_LOCALIZACAO_POR_USUARIO.getFuncao());
		buscarSOAP.addProperty("user", user);
		SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
		BuscarLocalizacaoPorMedicoTask tLocalizacao = new BuscarLocalizacaoPorMedicoTask(
				envelope, OperacaoLocalizacaoDAO.BUSCAR_LOCALIZACAO_POR_USUARIO.getFuncao(), this.soap.getURL());
		tLocalizacao.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tLocalizacao.getResult())
			continue;

		if (tLocalizacao.getLocalizacaoMedicos() != null)
			return tLocalizacao.getLocalizacaoMedicos();

		return null;

	}

}
