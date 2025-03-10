package com.gestionHopital.serv_utilisateur.exception;

public class ResourceAlreadyExistException extends RuntimeException{

    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
