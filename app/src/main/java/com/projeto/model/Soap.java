package com.projeto.model;

/**
 * Created by leo on 15/03/16.
 */
public class Soap {
    private String nome;
    private String ip;
    private String porta;
    private String path;
    private String namespace;

    public Soap(String nome, String ip) {
        this.nome = nome;
        this.path = "easyMedicoWS/services";
        this.namespace = "http://easymedicows.com.br";
        this.ip = ip;
        this.porta = "8080";
    }

    public String getEndereco(){
       return this.ip  + ":" + this.porta + "/" + this.path + "/" + this.nome + "?wsdl";
    }


}
