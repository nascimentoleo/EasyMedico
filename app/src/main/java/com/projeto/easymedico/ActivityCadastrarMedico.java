package com.projeto.easymedico;

import com.projeto.control.ControleDeEspecialidades;
import com.projeto.control.ControleDeMedicos;
import com.projeto.model.Especialidade;
import com.projeto.model.Medico;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

public class ActivityCadastrarMedico extends Activity {
	
	private EditText txtUser;
	private EditText txtSenha;
	private EditText txtNome;
	private EditText txtEspecialidade;
	private EditText txtQtdPacientesPorHora;
	private EditText txtCrm;
	private CheckBox chkAgendaManha;
	private CheckBox chkAgendaTarde;
	private ControleDeMedicos controleDeMedicos;
	private ControleDeEspecialidades controleDeEspecialidades;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_medico);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.txtUser = (EditText) findViewById(R.id.edUser);
		this.txtSenha = (EditText) findViewById(R.id.edLoginSenha);
		this.txtNome = (EditText) findViewById(R.id.edNome);
		this.txtEspecialidade = (EditText) findViewById(R.id.edEspecialidade);
		this.txtQtdPacientesPorHora = (EditText) findViewById(R.id.edQtdPacientesPorHora);
		this.txtCrm  = (EditText) findViewById(R.id.edCRM);
		this.chkAgendaManha = (CheckBox) findViewById(R.id.chkAgendaManha);
		this.chkAgendaTarde = (CheckBox) findViewById(R.id.chkAgendaTarde);
		this.controleDeMedicos = new ControleDeMedicos(Principal.getHost());
		this.controleDeEspecialidades = new ControleDeEspecialidades(Principal.getHost());
        this.carregaEspecialidades();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cadastrar_medico, menu);
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
	
	public void cadastrarMedico(View v){
		Medico novoMedico = new Medico();
		novoMedico.setUser(this.txtUser.getText().toString());
		novoMedico.setPassword(this.txtSenha.getText().toString());
		novoMedico.setNome(this.txtNome.getText().toString());
		novoMedico.setEspecialidade(this.txtEspecialidade.getText().toString());
		novoMedico.setQtdPacientesPorHora(Integer.parseInt(this.txtQtdPacientesPorHora.getText().toString()));
		novoMedico.setAgendaManha(chkAgendaManha.isChecked() ? "S" : "N");
		novoMedico.setAgendaTarde(chkAgendaTarde.isChecked() ? "S" : "N");
		novoMedico.setCrm(this.txtCrm.getText().toString());

		try {
			if(this.controleDeMedicos.cadastrar(novoMedico))
				Toast.makeText(this,"Médico Cadastrado!",Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, this.controleDeMedicos.getMsgErro(),Toast.LENGTH_SHORT).show();

			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

    public void carregaEspecialidades(){
        LinkedList<Especialidade> especialidades = this.controleDeEspecialidades.getLocalizacoes();
    }


}
