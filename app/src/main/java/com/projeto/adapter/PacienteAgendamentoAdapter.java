package com.projeto.adapter;

import java.util.LinkedList;

import com.projeto.db.MedicoDAO;
import com.projeto.easymedico.R;
import com.projeto.model.Agendamento;
import com.projeto.model.Medico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PacienteAgendamentoAdapter extends BaseAdapter {
	private Context ctx;
	private LinkedList<Agendamento> listaAgendamentos;

	public PacienteAgendamentoAdapter(Context ctx,
			LinkedList<Agendamento> agendamento) {
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
		// Transformo o xml que contem a estrutura da lista em um objeto do tipo
		// View
		View itemLista = LayoutInflater.from(ctx).inflate(
				R.layout.item_lista_pacientes_agendamentos, null);
		// Pego o médico referente a esse agendamento
		Medico medico = MedicoDAO.getMedicoByUser(agendamento.getUser());
		if (medico != null) {
			TextView txtNomeMedico = (TextView) itemLista
					.findViewById(R.id.txtNomeMedico);
			TextView txtNomePaciente = (TextView) itemLista
					.findViewById(R.id.txtNomePaciente);
			TextView txtEspecialidade = (TextView) itemLista
					.findViewById(R.id.txtEspecialidade);
			TextView txtData     = (TextView) itemLista.findViewById(R.id.txtData);
			TextView txtHora     = (TextView) itemLista.findViewById(R.id.txtHora);
			TextView txtTelefone = (TextView) itemLista.findViewById(R.id.txtTelefone);

			txtNomeMedico.setText(txtNomeMedico.getText() + medico.getNome());
			txtEspecialidade.setText(txtEspecialidade.getText()
					+ medico.getEspecialidade());
			txtData.setText(txtData.getText() + agendamento.getData());
			txtHora.setText(txtHora.getText() + agendamento.getHora());
			txtTelefone.setText(txtTelefone.getText() + agendamento.getTelefone());
			txtNomePaciente.setText(txtNomePaciente.getText() + agendamento.getNomePaciente());
		}

		return itemLista;

	}

}
