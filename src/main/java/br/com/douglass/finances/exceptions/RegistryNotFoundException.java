package br.com.douglass.finances.exceptions;

public class RegistryNotFoundException extends RuntimeException {

    public RegistryNotFoundException() {
        super("Registry not found");
    }

}
