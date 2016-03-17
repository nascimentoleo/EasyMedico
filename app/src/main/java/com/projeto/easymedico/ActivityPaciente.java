package com.projeto.easymedico;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.projeto.control.ControleDeLocalizacoes;
import com.projeto.control.Localizacao;
import com.projeto.lib.IMEI;
import com.projeto.model.Medico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityPaciente extends Activity implements
		OnInfoWindowClickListener {

	private LatLng coordenadas;
	private GoogleMap mapaFragmento;
	private Localizacao loc;
    private ControleDeLocalizacoes controleDeLocalizacoes;
	private Map<String, Medico> mapSpots;   // Tabela hash que ir� guardar os
	          								// id dos spots gerados e pra qual
											// m�dico
											// pertence

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paciente);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mapaFragmento = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapaFragmento)).getMap();
		// Passo a refer�ncia do mapa e digo que o zoom ser� autom�tico
		// Assim quando atualizar as coordenadas, automaticamente dar� zoom no
		// mapa
		this.loc = new Localizacao(this, true, mapaFragmento);
		mapaFragmento.setOnInfoWindowClickListener(this);
        this.controleDeLocalizacoes = new ControleDeLocalizacoes(Principal.getHost());
		this.carregarLocalizacoes();


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.loc.ativarLocalizacao();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.loc.desativarLocalizacao();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_paciente, menu);
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
		} else if (id == R.id.action_agendamentos) {
			// Se clicou em agendamentos, irei pegar o IMEI do celular e abrir a
			// tela para visualizar os agendamentos j� realizados
			Bundle parametros = new Bundle();
			parametros.putString("imei", IMEI.numCel(this));
			Intent it = new Intent(this,ActivityPacienteAgendamentos.class);
			it.putExtras(parametros);
			startActivity(it);
		}
		return super.onOptionsItemSelected(item);
	}

	// Carrega as localiza��es ativas no banco e exibe no mapa
	public void carregarLocalizacoes() {
		LinkedList<Medico> listaMedicos = this.controleDeLocalizacoes.getLocalizacoes();
		if(listaMedicos != null){
			this.mapSpots = new HashMap<String, Medico>();
			
			for (Medico medico : listaMedicos) {
				String tituloSpot = medico.getNome() + " - "
						+ medico.getEspecialidade();
				// Gero o spot, passando as coordenadas, o mapa que receber� e o
				// T�tulo do spot
				Marker spot = this.loc.marcarLocalizacao(mapaFragmento,
						Double.parseDouble(medico.getLocalizacao().getLatitude()),
						Double.parseDouble(medico.getLocalizacao().getLongitude()),
						tituloSpot);
				// Agora salvo o Id do spot e o m�dico na tabela hash, fa�o isso
				// para
				// poder manipular o agendamento
				this.mapSpots.put(spot.getId(), medico);
			}
		}else
			Toast.makeText(this, "Não foi possível carregar os médicos! Tente novamente mais tarde", Toast.LENGTH_LONG).show();
		

	}

	// Evento de clique no bal�o para agendar
	@Override
	public void onInfoWindowClick(Marker spotMapa) {
		Bundle parametros = new Bundle();
		if (this.mapSpots.containsKey(spotMapa.getId()))
			parametros.putSerializable("medico",
					this.mapSpots.get(spotMapa.getId()));

		Intent it = new Intent(this, ActivityAgendamento.class);
		it.putExtras(parametros);
		startActivity(it);

	}

}
