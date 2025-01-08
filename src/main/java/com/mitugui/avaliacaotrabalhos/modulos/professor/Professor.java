package com.mitugui.avaliacaotrabalhos.modulos.professor;

public class Professor {
    private int usuario_id;
    private int siape;

    public Professor(int usuario_id, int siape) {
        this.usuario_id = usuario_id;
        this.siape = siape;
    }

    public int getUsuario_id() {
        return usuario_id;
    }
    
    public int getSiape() {
        return siape;
    }
}
