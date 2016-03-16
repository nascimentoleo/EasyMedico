package com.projeto.model;

/**
 * Created by leo on 15/03/16.
 */
public class Soap {
    private String nome;
    private String path;
    private String namespace;
    private Host host;

    public Soap(String nome, Host host) {
        this.nome = nome;
        this.path = "easyMedicoWS/services";
        this.namespace = "http://easymedicows.com.br";
        this.host = host;
    }

    public String getURL(){
       return host.toString() + "/" + this.path + "/" + this.nome + "?wsdl";
    }

    public String getNamespace() {
        return namespace;
    }


}
