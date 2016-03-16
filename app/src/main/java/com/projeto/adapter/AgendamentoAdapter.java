package com.projeto.adapter;

import java.util.LinkedList;

import com.projeto.easymedico.R;
import com.projeto.model.Agendamento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AgendamentoAdapter extends BaseAdapter {
	private Context ctx;
	private LinkedList<Agendamento> listaAgendamentos;
	
	public AgendamentoAdapter(Context ctx, LinkedList<Agendamento> agendamento){
		this.listaAgendamentos = agendamento;
		this.ctx = ctx;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listaAgendamentos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.listaAgendamentos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Agendamento agendamento = this.listaAgendamentos.get(position);
		//Transformo o xml que contem a estrutura da lista em um objeto do tipo View
		View itemLista = LayoutInflater.from(ctx).inflate(R.layout.item_lista_agendamentos, null);
		
		TextView txtNomePaciente    = (TextView) itemLista.findViewById(R.id.txtNomePaciente);
		TextView txtHoraAgendamento = (TextView) itemLista.findViewById(R.id.txtHora);	
		TextView txtTelefone        = (TextView) itemLista.findViewById(R.id.txtTelefone);
		txtNomePaciente.setText(agendamento.getNomePaciente());
		txtHoraAgendamento.setText(agendamento.getHora());
		txtTelefone.setText(agendamento.getTelefone());
		return itemLista;
	}

}
