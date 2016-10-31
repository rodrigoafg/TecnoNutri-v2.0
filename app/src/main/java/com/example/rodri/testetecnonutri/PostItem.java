package com.example.rodri.testetecnonutri;

/**
 * Created by rodri on 30/10/2016.
 * Futuramente será utilizada para manipulação do objeto. (Melhoria)
 */

public class PostItem {
    private String feedHash;
    private Integer id;
    private String date;
    private String fotoURL;
    private Usuario usuario;


    public String getFeedHash() {
        return feedHash;
    }

    public void setFeedHash(String feedHash) {
        this.feedHash = feedHash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
