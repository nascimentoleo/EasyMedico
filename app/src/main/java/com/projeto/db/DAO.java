package com.projeto.db;

import com.projeto.model.Host;
import com.projeto.model.Soap;

/**
 * Created by leo on 16/03/16.
 */
public class DAO {
    Soap soap;

    public DAO(String nome, Host host) {
        this.soap = new Soap(nome, host);
    }
}
