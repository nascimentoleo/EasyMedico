package com.projeto.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.projeto.model.LocalizacaoMedicos;
import com.projeto.model.Medico;

import android.os.AsyncTask;

public class BuscarLocalizacaoPorMedicoTask extends AsyncTask<String, Void, Boolean> {

	private HttpTransportSE http;
	private SoapSerializationEnvelope envelope;
	private String acao;
	private LocalizacaoMedicos localizacaoMedicos;
	private String msgErro;
	private boolean result;
	
	public BuscarLocalizacaoPorMedicoTask(SoapSerializationEnvelope envelope, String acao,
			String url) {
		super();
		this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
												// URL do web service
		this.envelope = envelope;
		this.acao = acao;
	}
	
	public LocalizacaoMedicos getLocalizacaoMedicos() {
		return this.localizacaoMedicos;
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
			// Envio a requisicao HTTP contendo a acao e o envelope
			this.http.call("urn:" + this.acao, envelope);
			SoapObject resposta = (SoapObject) envelope.getResponse();
			if(resposta != null){
				this.localizacaoMedicos = new LocalizacaoMedicos();
				this.localizacaoMedicos.setUser(resposta.getProperty("user").toString());
				this.localizacaoMedicos.setLatitude(resposta.getProperty("latitude").toString());
				this.localizacaoMedicos.setLongitude(resposta.getProperty("longitude").toString());
				this.localizacaoMedicos.setAtivo(resposta.getProperty("ativo").toString());
			}else
				this.msgErro = "M�dico sem localiza��o";
		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.msgErro = e.getMessage();

		}
		this.result = true;
		return true;
	}

}
