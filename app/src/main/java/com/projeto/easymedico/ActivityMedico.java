package com.projeto.easymedico;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import com.projeto.adapter.AgendamentoAdapter;
import com.projeto.control.ControleDeAgendamentos;
import com.projeto.control.ControleDeLocalizacoes;
import com.projeto.control.Localizacao;
import com.projeto.fragment.DataDialogFragment;
import com.projeto.fragment.DataDialogListener;
import com.projeto.lib.DataLib;
import com.projeto.model.Agendamento;
import com.projeto.model.Medico;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
	private ControleDeLocalizacoes controleDeLocalizacoes;
    private ControleDeAgendamentos controleDeAgendamentos;

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
		// funcao para carregar os agendamentos
		this.edDataBuscaAgendamento.setText(DataLib.dataAtual());
        this.controleDeAgendamentos = new ControleDeAgendamentos(Principal.getHost());
        this.controleDeLocalizacoes = new ControleDeLocalizacoes(Principal.getHost());
		this.carregarAgendamentos(null);

		this.edDataBuscaAgendamento.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DataDialogListener dataListener = new DataDialogListener(edDataBuscaAgendamento);
				DataDialogFragment dataFrag = new DataDialogFragment(ActivityMedico.this, dataListener);
				dataFrag.show();
			}
		});

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
                    menu.getItem(0).setIcon(R.drawable.ic_action_spot_off);
                    if (controleDeLocalizacoes.removerLocalizacao(medico))
                        Toast.makeText(ActivityMedico.this, "Localização desativada", Toast.LENGTH_SHORT)
                                .show();
                } else {
                    loc.ativarLocalizacao();
                    localizacaoAtiva = true;
                    menu.getItem(0).setIcon(R.drawable.ic_action_spot);
                    // Aqui utilizarei uma thread porque demora um pouco para
                    // pegar as coordenadas
                    // Entao deixo rodando a thread, assim que o gps pegar as
                    // coordenadas, insiro no banco
                    // e a thread eh finalizada
                    new Thread() {
                        public void run() {
                            while (loc.getCoordenadas() == null)
                                continue;
                            controleDeLocalizacoes.inserirLocalizacao(medico, loc.getCoordenadas());

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

	public void carregarAgendamentos(View v) {
		if (!edDataBuscaAgendamento.getText().toString().equals("")) {
            LinkedList<Agendamento> listaAgendamentos = this.controleDeAgendamentos.getAgendamentos(medico,
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
