package com.mitugui.avaliacaotrabalhos.exceptions;

public class ConexaoBancoException extends RuntimeException {
    public ConexaoBancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
