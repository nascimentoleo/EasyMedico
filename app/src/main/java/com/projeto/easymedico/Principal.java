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

public class Principal extends Activity {

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
			this.buildAlertHost();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void EventoBotaoMedico(View v){
		Intent itLoginMedico = new Intent(this, ActivityLoginMedico.class);
		startActivity(itLoginMedico);
	}
	
	public void EventoBotaoPaciente(View v){
		Intent itPaciente = new Intent(this, ActivityPaciente.class);
		startActivity(itPaciente);
	}

	private void buildAlertHost(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Host");
		builder.setMessage("Insira o endereço IP");
		final EditText input = new EditText(this);
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				Toast.makeText(getApplicationContext(), input.getText().toString().trim(),
						Toast.LENGTH_SHORT).show();
			}

		});
		builder.show();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
}
