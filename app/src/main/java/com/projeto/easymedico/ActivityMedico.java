package com.projeto.easymedico;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.projeto.adapter.AgendamentoAdapter;
import com.projeto.control.Localizacao;
import com.projeto.db.AgendamentoDAO;
import com.projeto.db.LocalizacaoMedicosDAO;
import com.projeto.lib.IMEI;
import com.projeto.model.Agendamento;
import com.projeto.model.LocalizacaoMedicos;
import com.projeto.model.Medico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMedico extends Activity {
	private Medico medico;
	protected boolean localizacaoAtiva;
	private Menu menu;
	protected Localizacao loc;
	private EditText edDataBuscaAgendamento;
	private ListView listAgendamentos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medico);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle parametros = this.getIntent().getExtras();
		this.medico = (Medico) parametros.getSerializable("medico");
		this.edDataBuscaAgendamento = (EditText) findViewById(R.id.edDataBuscaAgendamento);
		this.listAgendamentos = (ListView) findViewById(R.id.listaAgendamentos);

		// Na primeira instancia da activity, irei carregar os agendamentos do
		// dia atual
		// Para isso, pego a data corrente, jogo no EditText da data e chamo a
		// fun��o para carregar os agendamentos
		String currentDateTimeString = DateFormat.getDateInstance().format(
				new Date());
		this.edDataBuscaAgendamento.setText(currentDateTimeString);
		this.carregarAgendamentos(null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_medico, menu);
		this.menu = menu;
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
		case R.id.action_localizacao:
			ativarLocalizacao();
			break;
		case R.id.action_settings:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void ativarLocalizacao() {
		// Utilizaremos um alertDialog para confirmar a exclus�o
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("Confirmar");
		if (!localizacaoAtiva)
			alertBuilder.setMessage("Deseja ativar a localização?");
		else
			alertBuilder.setMessage("Deseja desativar a localização");

		alertBuilder.setPositiveButton("Sim", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (loc == null)
					loc = new Localizacao(ActivityMedico.this);
				if (localizacaoAtiva) {
					loc.desativarLocalizacao();
					localizacaoAtiva = false;
					menu.getItem(1).setIcon(R.drawable.ic_action_spot_off);
					removerLocalizacao();
				} else {
					loc.ativarLocalizacao();
					localizacaoAtiva = true;
					menu.getItem(1).setIcon(R.drawable.ic_action_spot);
					// Aqui utilizarei uma thread porque demora um pouco para
					// pegar as coordenadas
					// Ent�o deixo rodando a thread, assim que o gps pegar as
					// coordenadas, insiro no banco
					// e a thread eh finalizada
					new Thread() {
						public void run() {
							while (loc.getCoordenadas() == null)
								continue;
							inserirLocalizacao();

						}

					}.start();
					Toast.makeText(ActivityMedico.this, "Localização Ativada",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
		alertBuilder.setNegativeButton("Não", null);
		alertBuilder.create().show();

	}

	// Fun��o que atualiza a localiza��o do m�dico, passando as novas
	// coordenadas e alterando o status
	protected void inserirLocalizacao() {
		boolean isInsert = false;
		LocalizacaoMedicos localizacaoMedico = this.medico.getLocalizacao();
		if (localizacaoMedico == null) {
			localizacaoMedico = new LocalizacaoMedicos();
			isInsert = true;
		}

		localizacaoMedico
				.setLatitude(Double.toString(this.loc.getCoordenadas().latitude));
		localizacaoMedico.setLongitude(Double.toString(this.loc
				.getCoordenadas().longitude));
		localizacaoMedico.setAtivo("S");
		localizacaoMedico.setUser(this.medico.getUser());
		LocalizacaoMedicosDAO localizacaoMedicosDAO = new LocalizacaoMedicosDAO(Principal.getHost());
		if (isInsert)
			localizacaoMedicosDAO.inserirLocalizacao(localizacaoMedico);
		else
			localizacaoMedicosDAO.alterarLocalizacao(localizacaoMedico);
	}

	private void removerLocalizacao() {
		LocalizacaoMedicos localizacaoMedico = this.medico.getLocalizacao();
		localizacaoMedico.setAtivo("N");
		LocalizacaoMedicosDAO localizacaoMedicosDAO = new LocalizacaoMedicosDAO(Principal.getHost());
		localizacaoMedicosDAO.alterarLocalizacao(localizacaoMedico);
		Toast.makeText(this, "Localização desativada", Toast.LENGTH_SHORT)
				.show();

	}

	public void carregarAgendamentos(View v) {
		if (!edDataBuscaAgendamento.getText().toString().equals("")) {
			LinkedList<Agendamento> listaAgendamentos = AgendamentoDAO
					.getAgendamentosPorMedicoData(medico.getUser(),
							edDataBuscaAgendamento.getText().toString());
			if (listaAgendamentos != null) {
				AgendamentoAdapter adapter = new AgendamentoAdapter(this,
						listaAgendamentos);
				this.listAgendamentos.setAdapter(adapter);

			} else
				Toast.makeText(this, "Não existe agendamentos para esse dia",
						Toast.LENGTH_SHORT).show();

		}
		this.listAgendamentos.requestFocus();

	}
}