package com.projeto.control;

import android.os.AsyncTask;

import com.projeto.model.Especialidade;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by leo on 3/23/16.
 */
public class BuscarEspecialidadesTask extends
        AsyncTask<String, String, Boolean> {
    private HttpTransportSE http;
    private SoapSerializationEnvelope envelope;
    private String acao;
    private LinkedList<Especialidade> listEspecialidades;
    private String msgErro;
    private boolean result;

    public BuscarEspecialidadesTask(
            SoapSerializationEnvelope envelope, String acao, String url) {
        super();
        this.http = new HttpTransportSE(url); // Crio o objeto HTTP passando a
        // URL do web service
        this.envelope = envelope;
        this.acao = acao;
    }


    public String getMsgErro() {
        return msgErro;
    }

    public boolean getResult() {
        return result;
    }

    public LinkedList<Especialidade> getListEspecialidades() {
        return listEspecialidades;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            // Envio a requisicao HTTP contendo a acao e o envelope
            this.http.call("urn:" + this.acao, envelope);
            Vector<SoapObject> vectorResposta;
            try {
                vectorResposta = (Vector<SoapObject>) envelope.getResponse();
            } catch (Exception e) {
                // Dispara caso nao seja possivel fazer o cast
                // So acontece em situacoes que so ha 1 registro
                vectorResposta = new Vector<SoapObject>();
                vectorResposta.add((SoapObject) envelope.getResponse());
            }

            if (vectorResposta != null) {
                this.listEspecialidades = new LinkedList<>();
                for (SoapObject resposta : vectorResposta) {
                    Especialidade especialidade = new Especialidade();
                    especialidade.setId(Integer.parseInt(resposta
                            .getProperty("id").toString()));
                    especialidade.setNome(resposta
                            .getProperty("nome").toString());
                    this.listEspecialidades.add(especialidade);
                }
            } else
                this.msgErro = "Não foram encontradas especialidades";
        } catch (IOException | XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.msgErro = e.getMessage();
        }
        this.result = true;
        return true;
    }
}
