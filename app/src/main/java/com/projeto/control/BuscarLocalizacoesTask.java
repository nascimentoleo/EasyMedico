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

public class BuscarLocalizacoesTask extends AsyncTask<String, String, Boolean> {

	private HttpTransportSE http;
	private SoapSerializationEnvelope envelope;
	private String acao;
	private LinkedList<Medico> listlocalizacoesMedicos;
	private String msgErro;
	private boolean result;

	public BuscarLocalizacoesTask(SoapSerializationEnvelope envelope,
			String acao, String url) {
		super();
		this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
												// URL do web service
		this.envelope = envelope;
		this.acao = acao;
	}

	public LinkedList<Medico> getLocalizacoesMedicos() {
		return this.listlocalizacoesMedicos;
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
			// Envio a requisi��o HTTP contendo a a��o e o envelope
			this.http.call("urn:" + this.acao, envelope);
			Vector<SoapObject> vectorResposta;
			try{
				vectorResposta = (Vector<SoapObject>) envelope
						.getResponse();
			}catch(Exception e){
				//Dispar� caso n�o seja poss�vel fazer o cast
				//S� acontece em situa��es que so h� 1 registro
				vectorResposta = new Vector<SoapObject>();
				vectorResposta.add((SoapObject) envelope.getResponse());
			}
			
			if (vectorResposta != null) {
				this.listlocalizacoesMedicos = new LinkedList<Medico>();
				for (SoapObject resposta : vectorResposta) {
					Medico medico = new Medico();
					medico.setUser(resposta.getProperty("user").toString());
					medico.setEspecialidade(resposta.getProperty(
							"especialidade").toString());
					medico.setNome(resposta.getProperty("nome")
							.toString());
					medico.setQtdPacientesPorHora(Integer.parseInt(resposta
							.getProperty("qtdPacientesPorHora").toString()));

					SoapObject respostaLoc = (SoapObject) resposta
							.getProperty("localizacao");
					LocalizacaoMedicos localizacao = new LocalizacaoMedicos();
					localizacao.setUser(respostaLoc.getProperty("user")
							.toString());
					localizacao.setLatitude(respostaLoc.getProperty("latitude")
							.toString());
					localizacao.setLongitude(respostaLoc.getProperty(
							"longitude").toString());
					localizacao.setAtivo(respostaLoc.getProperty("ativo")
							.toString());
					medico.setLocalizacao(localizacao);

					this.listlocalizacoesMedicos.add(medico);

				}
			} else
				this.msgErro = "Não foram encontrados médicos ativos";
		} catch (IOException | XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.msgErro = e.getMessage();
		}
		this.result = true;
		return true;
	}

}
