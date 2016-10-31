package com.example.rodri.testetecnonutri;

/**
 * Created by rodri on 30/10/2016.
 * Futuramente será utilizada para manipulação do objeto. (Melhoria)
 */

public class Usuario {
    private Integer id;
    private String nome;
    private String fotoURL;
    private String objetivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
}
