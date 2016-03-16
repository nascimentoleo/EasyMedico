package com.projeto.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.projeto.model.Medico;

import android.os.AsyncTask;

public class CRUDTask extends AsyncTask<String, String, Boolean> {

	private HttpTransportSE http;
	private SoapSerializationEnvelope envelope;
	private String acao;
	private String resposta = "";

	public CRUDTask(SoapSerializationEnvelope envelope, String acao,
			String url) {
		super();
		this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
												// URL do web service
		this.envelope = envelope;
		this.acao = acao;
	}

	public String getResposta() {
		return resposta;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			// Envio a requisição HTTP contendo a ação e o envelope
			this.http.call("urn:" + this.acao, envelope);
			// Pego a resposta
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			this.resposta = resposta.toString();

		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.resposta = e.getMessage();
		}
		return true;
	}

}
