package com.projeto.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.projeto.model.Medico;

import android.os.AsyncTask;

public class BuscarMedicoTask extends AsyncTask<String, Void, Boolean> {

	private HttpTransportSE http;
	private SoapSerializationEnvelope envelope;
	private String acao;
	private LinkedList<Medico> listamedicos;
	private String msgErro;
	private boolean result;

	public BuscarMedicoTask(SoapSerializationEnvelope envelope, String acao,
			String url) {
		super();
		this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
												// URL do web service
		this.envelope = envelope;
		this.acao = acao;
	}

	public LinkedList<Medico> getMedicos() {
		return this.listamedicos;
	}

	public String getMsgErro() {
		return this.msgErro;
	}

	public boolean getResult() {
		return this.result;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			// Envio a requisição HTTP contendo a ação e o envelope
			this.http.call("urn:" + this.acao, envelope);
			// Aqui farei uma tentativa de pegar um vetor, caso de erro
			// significa que o retorno é somente um objeto
			// foi a melhor maneira que encontrei de resolver o problema
			Vector<SoapObject> vetorRespostas;
			try {
				vetorRespostas = (Vector<SoapObject>) envelope.getResponse();
			} catch (ClassCastException e) {
				SoapObject resposta = (SoapObject) envelope.getResponse();
				vetorRespostas = new Vector<SoapObject>();
				vetorRespostas.add(resposta);
			}
			if (vetorRespostas != null) {
				this.listamedicos = new LinkedList<Medico>();
				for (SoapObject soapObject : vetorRespostas) {
					Medico medico = new Medico();
					medico.setUser(soapObject.getProperty("user").toString());
					medico.setPassword(soapObject.getProperty("password")
							.toString());
					medico.setNome(soapObject.getProperty("nome").toString());
					medico.setEspecialidade(soapObject.getProperty(
							"especialidade").toString());
					medico.setQtdPacientesPorHora(Integer.parseInt(soapObject
							.getProperty("qtdPacientesPorHora").toString()));
					medico.setAgendaManha(soapObject.getProperty("agendaManha")
							.toString());
					medico.setAgendaTarde(soapObject.getProperty("agendaTarde")
							.toString());
					medico.setCrm(soapObject.getProperty("crm")
							.toString());

					this.listamedicos.add(medico);

				}
			} else
				this.msgErro = "Usuário não encontrado";
		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.msgErro = e.getMessage();
		}
		this.result = true;
		return true;
	}

}
