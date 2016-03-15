package com.projeto.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import com.projeto.control.BuscarAgendamentosTask;
import com.projeto.control.BuscarHorariosAgendamentosTask;
import com.projeto.control.BuscarLocalizacaoPorMedicoTask;
import com.projeto.control.CRUDTask;
import com.projeto.model.Agendamento;
import com.projeto.model.Horario;
import com.projeto.model.Soap;

public class AgendamentoDAO {

	private static final String URL = "http://192.168.0.116:8080/easyMedicoWS/services/AgendamentoDAO?wsdl";
	private static final String NAMESPACE = "http://easymedicows.com.br";
	private static final String INSERIR = "inserirAgendamento";
	private static final String ATUALIZAR = "alterarAgendamento";
	private static final String EXCLUIR = "excluirAgendamentoById";
	private static final String BUSCAR_AGENDAMENTOS = "getAgendamentosPorMedicoData";
	private static final String BUSCAR_HORARIOS = "getHorariosDisponiveisPorMedicoData";
	private static final String BUSCAR_AGENDAMENTOS_POR_IMEI = "getAgendamentosPorIMEI";

	public static String inserirAgendamento(Agendamento agendamento) {
		// Para manipular o Web Service, usaremos a biblioteca do kSoap2
		// Primeiro pegamos o namespace e qual operacao iremos realizar
		SoapObject inserirSOAP = new SoapObject(NAMESPACE, INSERIR);
		// Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
		// que ser� enviado com as informa��es da localiza��o
		SoapObject agendamentoSOAP = new SoapObject(NAMESPACE, "agendamento");
		// Adicionamos todos os atributos como propriedades para o objeto SOAP,
		// semelhante a uma Tabela Hash
		agendamentoSOAP.addProperty("user", agendamento.getUser());
		agendamentoSOAP.addProperty("nomePaciente",
				agendamento.getNomePaciente());
		agendamentoSOAP.addProperty("idAgendamento",
				agendamento.getIdAgendamento());
		agendamentoSOAP.addProperty("data", agendamento.getData());
		agendamentoSOAP.addProperty("hora", agendamento.getHora());
		agendamentoSOAP.addProperty("imei",agendamento.getImei());
		agendamentoSOAP.addProperty("telefone",agendamento.getTelefone());
		// Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
		// para poder enviar
		inserirSOAP.addSoapObject(agendamentoSOAP);

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
		CRUDTask tAgendamento = new CRUDTask(envelope, INSERIR, URL);
		tAgendamento.execute();

		while (tAgendamento.getResposta().equals(""))
			continue;

		return tAgendamento.getResposta();

	}

	public static String alterarAgendamento(Agendamento agendamento) {
		SoapObject atualizarSOAP = new SoapObject(NAMESPACE, ATUALIZAR);
		SoapObject agendamentoSOAP = new SoapObject(NAMESPACE, "agendamento");
		agendamentoSOAP.addProperty("user", agendamento.getUser());
		agendamentoSOAP.addProperty("nomePaciente",
				agendamento.getNomePaciente());
		agendamentoSOAP.addProperty("idAgendamento",
				agendamento.getIdAgendamento());
		agendamentoSOAP.addProperty("data", agendamento.getData());
		agendamentoSOAP.addProperty("hora", agendamento.getHora());
		agendamentoSOAP.addProperty("imei",agendamento.getImei());
		agendamentoSOAP.addProperty("telefone",agendamento.getTelefone());
		atualizarSOAP.addSoapObject(agendamentoSOAP);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(atualizarSOAP);
		envelope.implicitTypes = true; // Flag obrigat�rio para funcionar
		CRUDTask tAgendamento = new CRUDTask(envelope, ATUALIZAR, URL);
		tAgendamento.execute();

		while (tAgendamento.getResposta().equals(""))
			continue;

		return tAgendamento.getResposta();
	}

	public static String excluirAgendamentoById(int id) {
		SoapObject excluirSOAP = new SoapObject(NAMESPACE, EXCLUIR);
		excluirSOAP.addProperty("id",id);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(excluirSOAP);
		envelope.implicitTypes = true; // Flag obrigat�rio para funcionar
		CRUDTask tAgendamento = new CRUDTask(envelope, EXCLUIR, URL);
		tAgendamento.execute();

		while (tAgendamento.getResposta().equals(""))
			continue;

		return tAgendamento.getResposta();

	}

	public static LinkedList<Agendamento> getAgendamentosPorMedicoData(String user,
			String data) {
		SoapObject buscarSOAP = new SoapObject(NAMESPACE,
				BUSCAR_AGENDAMENTOS);
		buscarSOAP.addProperty("user", user);
		buscarSOAP.addProperty("data",data);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(buscarSOAP);
		envelope.implicitTypes = true;
		BuscarAgendamentosTask tAgendamentos = new BuscarAgendamentosTask(
				envelope, BUSCAR_AGENDAMENTOS, URL);
		tAgendamentos.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tAgendamentos.getResult())
			continue;

		if (tAgendamentos.getListAgendamentos() != null)
			return tAgendamentos.getListAgendamentos();

		return null;


	}
	
	public static LinkedList<Agendamento> getAgendamentosPorIMEI(String imei) {
		SoapObject buscarSOAP = new SoapObject(NAMESPACE,
				BUSCAR_AGENDAMENTOS_POR_IMEI);
		buscarSOAP.addProperty("imei", imei);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(buscarSOAP);
		envelope.implicitTypes = true;
		BuscarAgendamentosTask tAgendamentos = new BuscarAgendamentosTask(
				envelope, BUSCAR_AGENDAMENTOS_POR_IMEI, URL);
		tAgendamentos.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tAgendamentos.getResult())
			continue;

		if (tAgendamentos.getListAgendamentos() != null)
			return tAgendamentos.getListAgendamentos();

		return null;


	}

	public static LinkedList<Horario> getHorariosDisponiveisPorMedicoData(String user,
			String data) {
		SoapObject buscarSOAP = new SoapObject(NAMESPACE,
				BUSCAR_HORARIOS);
		buscarSOAP.addProperty("user", user);
		buscarSOAP.addProperty("data",data);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Adicionamos ao envelope o objeto que queremos enviar
		envelope.setOutputSoapObject(buscarSOAP);
		envelope.implicitTypes = true;
		BuscarHorariosAgendamentosTask tHorarios = new BuscarHorariosAgendamentosTask(
				envelope, BUSCAR_HORARIOS, URL);
		tHorarios.execute();
		// S� passo daqui quando terminar de executar o Task
		while (!tHorarios.getResult())
			continue;

		if (tHorarios.getListHorarios() != null)
			return tHorarios.getListHorarios();

		return null;

	}

}
