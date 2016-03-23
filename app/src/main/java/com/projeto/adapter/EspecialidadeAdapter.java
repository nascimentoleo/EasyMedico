package com.projeto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projeto.easymedico.R;
import com.projeto.model.Especialidade;

import java.util.LinkedList;

/**
 * Created by leo on 3/23/16.
 */
public class EspecialidadeAdapter extends BaseAdapter {

    private Context ctx;
    private LinkedList<Especialidade> listaEspecialidades;

    public EspecialidadeAdapter(Context ctx, LinkedList<Especialidade> agendamento){
        this.listaEspecialidades = agendamento;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return this.listaEspecialidades.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaEspecialidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Especialidade especialidade = this.listaEspecialidades.get(position);
        //Transformo o xml que contem a estrutura da lista em um objeto do tipo View
        View itemLista = LayoutInflater.from(ctx).inflate(R.layout.item_lista_especialidades, null);
        TextView txtNomeEspecialidade    = (TextView) itemLista.findViewById(R.id.txtNomeEspecialidade);
        txtNomeEspecialidade.setText(especialidade.getNome());
        return itemLista;
    }
}
