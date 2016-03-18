package com.projeto.easymedico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.projeto.fragment.HostDialogFragment;
import com.projeto.model.Host;
import com.projeto.model.IdBundle;

public class Principal extends Activity implements HostDialogFragment.AoSalvarHost {

    private static Host host;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			HostDialogFragment hostDialogFragment = HostDialogFragment.newInstance(null);
			hostDialogFragment.abrir(getFragmentManager());
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void EventoBotaoMedico(View v){
		if (this.existeHostCadastrado()){
			Intent itLoginMedico = new Intent(this, ActivityLoginMedico.class);
			startActivity(itLoginMedico);
		}
	}
	
	public void EventoBotaoPaciente(View v){
		if (this.existeHostCadastrado()){
			Intent itPaciente = new Intent(this, ActivityPaciente.class);
			startActivity(itPaciente);
		}

	}

	@Override
	public void salvouHost(Host host) {
		this.host = host;
	}

	private boolean existeHostCadastrado(){
		boolean existeHost = this.host != null;
		if (!existeHost)
			Toast.makeText(this, "Faltou cadastrar o host", Toast.LENGTH_SHORT).show();
		return existeHost;

	}

	static Host getHost(){
		return host;
	}
}
