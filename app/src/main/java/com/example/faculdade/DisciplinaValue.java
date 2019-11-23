package com.example.faculdade;

import java.io.Serializable;

public class DisciplinaValue implements Serializable {

    private Long id;
    private String disciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String toString(){
        return this.getDisciplina();
    };
}
