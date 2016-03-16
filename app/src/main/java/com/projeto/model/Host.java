package com.projeto.model;

import java.io.Serializable;

/**
 * Created by leo on 16/03/16.
 */
public class Host implements Serializable {
    private String endereco;
    private String porta;
    private String protocolo;

    public Host(String endereco) {
        this.endereco = endereco;
        this.porta = "8080";
        this.protocolo = "http://";
    }

    @Override
    public String toString() {

        return this.protocolo + this.endereco + ":" + this.porta;
    }

    public void setEndereco(String endereco) {

        this.endereco = endereco;
    }
}
