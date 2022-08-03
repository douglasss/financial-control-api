package br.com.douglass.finances.exceptions;

public class RegistryNotFoundException extends BusinessException {

    public RegistryNotFoundException() {
        super("Registry not found");
    }

}
