/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcos.app.bean;


import clientebiblioteca.Painel;
import java.io.Serializable;

/**
 *
 * @author trabalho
 */
public class Message implements Serializable{
    private Action action; 
    private String resposta;
    private boolean estado;
    private String identificador, escrita;

    public String getEscrita() {
        return escrita;
    }

    public void setEscrita(String escrita) {
        this.escrita = escrita;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String  getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String  identificador) {
        this.identificador = identificador;
    }
    
    
    public enum Action {
        CONNECT, DISCONNECT, STATECOMPONENT, CHANGESTATECOMPONENT, ESCREVER
    }
}
