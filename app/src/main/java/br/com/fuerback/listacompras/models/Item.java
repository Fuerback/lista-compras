package br.com.fuerback.listacompras.models;

import java.io.Serializable;

public class Item implements Serializable {

    private String nome;
    private Boolean checked;

    /* Usado para recuperar dados do Firebase diretamente */
    public Item() {
    }

    public Item(String nome, Boolean checked) {
        this.nome = nome;
        this.checked = checked;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
