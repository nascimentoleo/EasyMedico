package com.projeto.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.projeto.model.Agendamento;
import com.projeto.model.Horario;

import android.os.AsyncTask;

public class BuscarAgendamentosTask extends
		AsyncTask<String, String, Boolean> {

	private HttpTransportSE http;
	private SoapSerializationEnvelope envelope;
	private String acao;
	private LinkedList<Agendamento> listAgendamentos;
	private String msgErro;
	private boolean result;

	public BuscarAgendamentosTask(
			SoapSerializationEnvelope envelope, String acao, String url) {
		super();
		this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
												// URL do web service
		this.envelope = envelope;
		this.acao = acao;
	}

	public LinkedList<Agendamento> getListAgendamentos() {
		return this.listAgendamentos;
	}

	public String getMsgErro() {
		return this.msgErro;
	}

	public boolean getResult() {
		return this.result;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			// Envio a requisi��o HTTP contendo a a��o e o envelope
			this.http.call("urn:" + this.acao, envelope);
			Vector<SoapObject> vectorResposta;
			try {
				vectorResposta = (Vector<SoapObject>) envelope.getResponse();
			} catch (Exception e) {
				// Dispar� caso n�o seja poss�vel fazer o cast
				// S� acontece em situa��es que so h� 1 registro
				vectorResposta = new Vector<SoapObject>();
				vectorResposta.add((SoapObject) envelope.getResponse());
			}

			if (vectorResposta != null) {
				this.listAgendamentos = new LinkedList<Agendamento>();
				for (SoapObject resposta : vectorResposta) {
					Agendamento agendamento = new Agendamento();
					agendamento.setIdAgendamento(Integer.parseInt(resposta
							.getProperty("idAgendamento").toString()));
					agendamento.setNomePaciente(resposta
							.getProperty("nomePaciente").toString());
					agendamento.setData(resposta
							.getProperty("data").toString());
					agendamento.setHora(resposta
							.getProperty("hora").toString());
					agendamento.setUser(resposta
							.getProperty("user").toString());
					agendamento.setImei(resposta
							.getProperty("imei").toString());
					agendamento.setTelefone(resposta
							.getProperty("telefone").toString());
					
					this.listAgendamentos.add(agendamento);
				}
			} else
				this.msgErro = "Não foram encontrados agendamentos";
		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.msgErro = e.getMessage();
		}
		this.result = true;
		return true;
	}

}
