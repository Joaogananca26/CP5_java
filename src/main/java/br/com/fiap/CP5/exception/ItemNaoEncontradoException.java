package br.com.fiap.CP5.exception;

public class ItemNaoEncontradoException extends RuntimeException {
    public ItemNaoEncontradoException(String message) {
        super(message);
    }
}