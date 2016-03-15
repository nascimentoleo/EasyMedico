package com.projeto.easymedico;

import com.projeto.control.Login;
import com.projeto.model.Medico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLoginMedico extends Activity {

	private EditText edLoginUser;
	private EditText edLoginPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_medico);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.edLoginUser = (EditText) findViewById(R.id.edLoginUser);
		this.edLoginPassword = (EditText) findViewById(R.id.edLoginSenha);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login_medico, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent it = null;
		switch (id) {
		case R.id.cadastraMedico:
			it = new Intent(this, ActivityCadastrarMedico.class);
			startActivity(it);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	public void realizarLogin(View v) {
		Login login = new Login();
		//Valido se os campos est�o vazios
		if (!edLoginUser.getText().toString().equals("")) {
			if (!edLoginPassword.getText().toString().equals("")) {
				Medico medico = login.realizarLogin(edLoginUser.getText()
						.toString(), edLoginPassword.getText().toString());
				if (medico != null) {
					Toast.makeText(this, "Bem vindo " + medico.getNome(),
							Toast.LENGTH_SHORT).show();
					;
					Intent it = new Intent(this, ActivityMedico.class);
					Bundle parametros = new Bundle();
					parametros.putSerializable("medico", medico);
					it.putExtras(parametros);
					startActivity(it);
				} else
					Toast.makeText(this, login.getMsgErro(), Toast.LENGTH_SHORT)
							.show();
			}
			else{
				Toast.makeText(this, "Preencha uma senha", Toast.LENGTH_SHORT).show();
				edLoginPassword.setFocusable(true);
			}
		}else{
			Toast.makeText(this, "Preencha um usuário", Toast.LENGTH_SHORT).show();
			edLoginUser.setFocusable(true);
		}

	}
}
