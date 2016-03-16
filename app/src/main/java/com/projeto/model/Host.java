package com.projeto.model;

import java.io.Serializable;

/**
 * Created by leo on 16/03/16.
 */
public class Host implements Serializable {
    private String endereco;
    private String porta;

    public Host(String endereco) {
        this.endereco = endereco;
        this.porta = "8080";
    }

    @Override
    public String toString() {
        return this.endereco + ":" + this.porta;
    }
}
