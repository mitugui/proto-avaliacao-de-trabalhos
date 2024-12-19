package br.edu.ifpr.pgua.eic.tads.exceptions;

public class ConexaoBancoException extends RuntimeException {
    public ConexaoBancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
