package com.projeto.model;

/**
 * Created by leo on 15/03/16.
 */
public class Soap {
    private String nome;
    private String path;
    private String namespace;

    public Soap(String nome) {
        this.nome = nome;
        this.path = "easyMedicoWS/services";
        this.namespace = "http://easymedicows.com.br";
    }

    public String getURL(Host host){
       return host.toString() + "/" + this.path + "/" + this.nome + "?wsdl";
    }


}
