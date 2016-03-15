package com.projeto.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.projeto.model.Horario;
import com.projeto.model.LocalizacaoMedicos;
import com.projeto.model.Medico;

import android.os.AsyncTask;

public class BuscarHorariosAgendamentosTask extends
		AsyncTask<String, String, Boolean> {
	private HttpTransportSE http;
	private SoapSerializationEnvelope envelope;
	private String acao;
	private LinkedList<Horario> listHorarios;
	private String msgErro;
	private boolean result;

	public BuscarHorariosAgendamentosTask(SoapSerializationEnvelope envelope,
			String acao, String url) {
		super();
		this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
												// URL do web service
		this.envelope = envelope;
		this.acao = acao;
	}

	public LinkedList<Horario> getListHorarios() {
		return this.listHorarios;
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
			Vector<SoapObject> vectorResposta;
			try {
				vectorResposta = (Vector<SoapObject>) envelope.getResponse();
			} catch (Exception e) {
				// Dispará caso não seja possível fazer o cast
				// Só acontece em situações que so há 1 registro
				vectorResposta = new Vector<SoapObject>();
				vectorResposta.add((SoapObject) envelope.getResponse());
			}

			if (vectorResposta != null) {
				this.listHorarios = new LinkedList<Horario>();
				for (SoapObject resposta : vectorResposta) {
					this.listHorarios.add(new Horario(resposta.getProperty("hora").toString()));
				}
			} else
				this.msgErro = "Não foram encontrados horários";
		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.msgErro = e.getMessage();
		}
		this.result = true;
		return true;
	}

}
