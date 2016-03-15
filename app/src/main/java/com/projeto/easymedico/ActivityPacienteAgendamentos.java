package com.projeto.easymedico;

import java.util.LinkedList;

import com.projeto.adapter.PacienteAgendamentoAdapter;
import com.projeto.db.AgendamentoDAO;
import com.projeto.model.Agendamento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ActivityPacienteAgendamentos extends Activity implements
		OnItemClickListener {

	private ListView listaPacientesAgendamentos;
	private String imei;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paciente_agendamentos);
		this.listaPacientesAgendamentos = (ListView) findViewById(R.id.listaPacientesAgendamentos);
		this.listaPacientesAgendamentos.setOnItemClickListener(this);
		this.imei = getIntent().getExtras().getString("imei");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		carregarAgendamentos();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_paciente_agendamentos, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//Ao clicar em um agendamento, abro a tela para edi��o
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Bundle parametros = new Bundle();
		//Pego o agendamento selecionado
		Agendamento agendamento = (Agendamento) listaPacientesAgendamentos.getAdapter().getItem(position);
		parametros.putSerializable("agendamento",agendamento);

		Intent it = new Intent(this,ActivityAgendamento.class);
		it.putExtras(parametros);
		startActivity(it);
	}
	
	public void carregarAgendamentos(){
		// Pego o imei pelo bundle
		if(!this.imei.equals("")){
			LinkedList<Agendamento> listaAgendamentos = AgendamentoDAO.getAgendamentosPorIMEI(this.imei);
			if(listaAgendamentos != null){
				PacienteAgendamentoAdapter adapter = new PacienteAgendamentoAdapter(this, listaAgendamentos);
				this.listaPacientesAgendamentos.setAdapter(adapter);
			}else{
				Toast.makeText(this, "Não existem agendamentos", Toast.LENGTH_SHORT).show();
				this.finish();
			}
				
		}
	}
}
