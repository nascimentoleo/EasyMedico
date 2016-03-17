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
import com.projeto.lib.PrepararSOAP;
import com.projeto.model.Agendamento;
import com.projeto.model.Horario;
import com.projeto.model.Host;
import com.projeto.model.Soap;

public class AgendamentoDAO extends DAO {


    public AgendamentoDAO(Host host) {
        super("AgendamentoDAO", host);
    }

    public String inserirAgendamento(Agendamento agendamento) {
        // Para manipular o Web Service, usaremos a biblioteca do kSoap2
        // Primeiro pegamos o namespace e qual operacao iremos realizar
        SoapObject inserirSOAP = new SoapObject(this.soap.getNamespace(), OperacaoAgendamentoDAO.INSERIR.getFuncao());
        // Agora inserimos o objeto medico dentro de um objeto Soap. Esse objeto
        // que ser� enviado com as informa��es da localiza��o
        SoapObject agendamentoSOAP = new SoapObject(this.soap.getNamespace(), "agendamento");
        // Adicionamos todos os atributos como propriedades para o objeto SOAP,
        // semelhante a uma Tabela Hash
        agendamentoSOAP.addProperty("user", agendamento.getUser());
        agendamentoSOAP.addProperty("nomePaciente",
                agendamento.getNomePaciente());
        agendamentoSOAP.addProperty("idAgendamento",
                agendamento.getIdAgendamento());
        agendamentoSOAP.addProperty("data", agendamento.getData());
        agendamentoSOAP.addProperty("hora", agendamento.getHora());
        agendamentoSOAP.addProperty("imei", agendamento.getImei());
        agendamentoSOAP.addProperty("telefone", agendamento.getTelefone());
        // Por fim adicionamos o objeto criado ao inserir, e envelopamos ele
        // para poder enviar
        inserirSOAP.addSoapObject(agendamentoSOAP);

        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(inserirSOAP);

        // Enviaremos via http o envelope, para isso precisamos de um objeto da
        // classe HttpTransportSE
        // S� que a partir da vers�o 9 do android, para realizar requisi��es via
        // rede precisamos de uma classe AsyncTask,
        // que vai executar essa requisi��o em uma Thread separada
        CRUDTask tAgendamento = new CRUDTask(envelope, OperacaoAgendamentoDAO.INSERIR.getFuncao(), this.soap.getURL());
        tAgendamento.execute();

        while (tAgendamento.getResposta().equals(""))
            continue;

        return tAgendamento.getResposta();

    }

    public String alterarAgendamento(Agendamento agendamento) {
        SoapObject atualizarSOAP = new SoapObject(this.soap.getNamespace(), OperacaoAgendamentoDAO.ATUALIZAR.getFuncao());
        SoapObject agendamentoSOAP = new SoapObject(this.soap.getNamespace(), "agendamento");
        agendamentoSOAP.addProperty("user", agendamento.getUser());
        agendamentoSOAP.addProperty("nomePaciente",
                agendamento.getNomePaciente());
        agendamentoSOAP.addProperty("idAgendamento",
                agendamento.getIdAgendamento());
        agendamentoSOAP.addProperty("data", agendamento.getData());
        agendamentoSOAP.addProperty("hora", agendamento.getHora());
        agendamentoSOAP.addProperty("imei", agendamento.getImei());
        agendamentoSOAP.addProperty("telefone", agendamento.getTelefone());
        atualizarSOAP.addSoapObject(agendamentoSOAP);

        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(atualizarSOAP);
        CRUDTask tAgendamento = new CRUDTask(envelope, OperacaoAgendamentoDAO.ATUALIZAR.getFuncao(), this.soap.getURL());
        tAgendamento.execute();

        while (tAgendamento.getResposta().equals(""))
            continue;

        return tAgendamento.getResposta();
    }

    public String excluirAgendamentoById(int id) {
        SoapObject excluirSOAP = new SoapObject(this.soap.getNamespace(), OperacaoAgendamentoDAO.EXCLUIR.getFuncao());
        excluirSOAP.addProperty("id", id);
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(excluirSOAP);
        CRUDTask tAgendamento = new CRUDTask(envelope, OperacaoAgendamentoDAO.EXCLUIR.getFuncao(), this.soap.getURL());
        tAgendamento.execute();

        while (tAgendamento.getResposta().equals(""))
            continue;

        return tAgendamento.getResposta();

    }

    public LinkedList<Agendamento> getAgendamentosPorMedicoData(String user,
                                                                String data) {
        SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
                OperacaoAgendamentoDAO.BUSCAR_AGENDAMENTOS.getFuncao());
        buscarSOAP.addProperty("user", user);
        buscarSOAP.addProperty("data", data);
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
        BuscarAgendamentosTask tAgendamentos = new BuscarAgendamentosTask(
                envelope, OperacaoAgendamentoDAO.BUSCAR_AGENDAMENTOS.getFuncao(), this.soap.getURL());
        tAgendamentos.execute();
        // S� passo daqui quando terminar de executar o Task
        while (!tAgendamentos.getResult())
            continue;

        if (tAgendamentos.getListAgendamentos() != null)
            return tAgendamentos.getListAgendamentos();

        return null;


    }

    public LinkedList<Agendamento> getAgendamentosPorIMEI(String imei) {
        SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
                OperacaoAgendamentoDAO.BUSCAR_AGENDAMENTOS_POR_IMEI.getFuncao());
        buscarSOAP.addProperty("imei", imei);
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
        BuscarAgendamentosTask tAgendamentos = new BuscarAgendamentosTask(
                envelope, OperacaoAgendamentoDAO.BUSCAR_AGENDAMENTOS_POR_IMEI.getFuncao(), this.soap.getURL());
        tAgendamentos.execute();
        // S� passo daqui quando terminar de executar o Task
        while (!tAgendamentos.getResult())
            continue;

        if (tAgendamentos.getListAgendamentos() != null)
            return tAgendamentos.getListAgendamentos();

        return null;


    }

    public LinkedList<Horario> getHorariosDisponiveisPorMedicoData(String user,
                                                                   String data) {
        SoapObject buscarSOAP = new SoapObject(this.soap.getNamespace(),
                OperacaoAgendamentoDAO.BUSCAR_HORARIOS.getFuncao());
        buscarSOAP.addProperty("user", user);
        buscarSOAP.addProperty("data", data);
        SoapSerializationEnvelope envelope = PrepararSOAP.envelopar(buscarSOAP);
        BuscarHorariosAgendamentosTask tHorarios = new BuscarHorariosAgendamentosTask(
                envelope, OperacaoAgendamentoDAO.BUSCAR_HORARIOS.getFuncao(), this.soap.getURL());
        tHorarios.execute();
        // S� passo daqui quando terminar de executar o Task
        while (!tHorarios.getResult())
            continue;

        if (tHorarios.getListHorarios() != null)
            return tHorarios.getListHorarios();

        return null;

    }

}
