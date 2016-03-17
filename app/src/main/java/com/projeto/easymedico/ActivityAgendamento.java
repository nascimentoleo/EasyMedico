package com.projeto.easymedico;

import java.util.ArrayList;
import com.projeto.control.ControleDeAgendamentos;
import com.projeto.lib.Data;
import com.projeto.lib.IMEI;
import com.projeto.model.Agendamento;
import com.projeto.model.Horario;
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
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityAgendamento extends Activity implements
        OnFocusChangeListener {

    private EditText edNomePaciente;
    private EditText edDataAgendamento;
    private EditText edTelefone;
    private Spinner spHoraAgendamento;
    private Medico medico;
    protected Agendamento agendamento;
    private ControleDeAgendamentos controleDeAgendamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.edNomePaciente = (EditText) findViewById(R.id.edNomePaciente);
        this.edDataAgendamento = (EditText) findViewById(R.id.edDataAgendamento);
        this.edTelefone = (EditText) findViewById(R.id.edTelefone);
        this.spHoraAgendamento = (Spinner) findViewById(R.id.spHoraAgendamento);
        this.edDataAgendamento.setOnFocusChangeListener(this);
        this.controleDeAgendamentos = new ControleDeAgendamentos(Principal.getHost());
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Intent it = getIntent();
        Bundle parametros = it.getExtras();
        // S� pego o m�dico se for uma inser��o
        this.medico = (Medico) parametros.getSerializable("medico");
        // S� pego o agendamento se for uma altera��o
        this.agendamento = (Agendamento) parametros
                .getSerializable("agendamento");
        // Caso seja diferente de nulo, jogo as informa��es do agendamento na
        // tela
        if (this.agendamento != null) {
            this.edNomePaciente.setText(this.agendamento.getNomePaciente());
            this.edDataAgendamento.setText(this.agendamento.getData());
            this.edTelefone.setText(this.agendamento.getTelefone());
            carregarHorarios();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_agendamento, menu);
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
        if (id == R.id.action_remover) {
            // S� permite remover se for uma altera��o
            if (this.agendamento != null)
                this.removerAgendamento();
            else
                Toast.makeText(
                        this,
                        "Não é possível remover um agendamento que não foi concluído",
                        Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub

        if (!hasFocus) {
            if (Data.dataValida(edDataAgendamento.getText().toString()))
                carregarHorarios();
            else
                Toast.makeText(this, "Data inválida", Toast.LENGTH_SHORT)
                        .show();
        }

    }

    public void carregarHorarios() {
        // Zero o adapter
        this.spHoraAgendamento.setAdapter(null);
        if (!this.edDataAgendamento.getText().toString().equals("")) {
            String user;
            // Se for alteracao, pego o usuario armazenado no agendamento
            // Se for insercao, pego o usuario do medico selecionado no mapa
            if (this.agendamento != null)
                user = agendamento.getUser();
            else
                user = this.medico.getUser();
            ArrayList<String> horarios = this.controleDeAgendamentos.getHorarios(user,
                    this.edDataAgendamento.getText().toString());

            if (horarios != null){
                ArrayAdapter<String> horariosAdapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, horarios);
                spHoraAgendamento.setAdapter(horariosAdapter);
            } else
                Toast.makeText(this, "Não há horários disponíveis",
                        Toast.LENGTH_SHORT).show();

        }

    }

    public void cadastrarAgendamento(View v) {
        if (!edDataAgendamento.getText().toString().equals("")
                && (Data.dataValida(edDataAgendamento.getText().toString()))) {
            if (!edNomePaciente.getText().toString().equals("")) {
                if (spHoraAgendamento.getAdapter() != null) {
                    Agendamento Agendamento = new Agendamento();
                    Agendamento.setData(edDataAgendamento.getText().toString());
                    Agendamento.setTelefone(edTelefone.getText().toString());
                    Agendamento.setNomePaciente(edNomePaciente.getText()
                            .toString());
                    Agendamento.setHora(spHoraAgendamento.getSelectedItem()
                            .toString());

                    // Pego o IMEI, n�mero serial do celular
                    // Com o IMEI irei relacionar os agendamentos ao dispositivo
                    Agendamento.setImei(IMEI.numCel(this));
                    String result;
                    // Se agendamento n�o � nulo, significa que � uma altera��o
                    // Ent�o pego o Id do agendamento e o user do agendamento
                    // que est� sendo alterado
                    if (this.agendamento != null) {
                        Agendamento.setIdAgendamento(this.agendamento
                                .getIdAgendamento());
                        Agendamento.setUser(this.agendamento.getUser());
                        result = this.controleDeAgendamentos.alterar(Agendamento);
                    } else {
                        Agendamento.setUser(this.medico.getUser());
                        result = this.controleDeAgendamentos.cadastrar(Agendamento);
                    }

                    if (result.equals("true")) {
                        Toast.makeText(this, "Agendamento Realizado",
                                Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(this,
                            "Não há horários disponíveis para esse dia",
                            Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Preencha um nome", Toast.LENGTH_SHORT)
                        .show();
                edNomePaciente.setFocusable(true);
            }

        } else {
            Toast.makeText(this, "Preencha uma data", Toast.LENGTH_SHORT)
                    .show();
            edDataAgendamento.setFocusable(true);
        }

    }

    private void removerAgendamento() {
        // Utilizaremos um alertDialog para confirmar a exclus�o
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Excluir");
        alertBuilder.setMessage("Deseja excluir esse Agendamento?");
        alertBuilder.setPositiveButton("Sim", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (controleDeAgendamentos.remover(
                        agendamento).equals("true")) {
                    Toast.makeText(ActivityAgendamento.this,
                            "Agendamento excluído!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityAgendamento.this,
                            "Não foi possível excluir agendamento!",
                            Toast.LENGTH_SHORT).show();
                }
                ActivityAgendamento.this.finish();
            }
        });
        alertBuilder.setNegativeButton("Não", null);
        alertBuilder.create().show();

    }

}
