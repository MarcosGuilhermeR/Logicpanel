/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientebiblioteca;

import com.marcos.app.bean.Message;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trabalho
 */
public class Painel {

    private static ObjectInputStream input = null;
    private static ObjectOutputStream output = null;
    private static Socket socket = null;

    public static void conectar() {
        //System.out.println("Iniciando Conexão com servidor...");

        try {
            socket = new Socket("localhost", 5557);

        } catch (Exception e) {
            //System.out.println("Exceção");
        }
        //System.out.println("Conexão estabelecida com o Painel");

        try {

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            //System.out.println("Exceção");
        }

    }

    public static boolean Ler(Chave c) {
        Message mensagem = new Message();
        mensagem.setAction(Message.Action.STATECOMPONENT);
        mensagem.setIdentificador("" + c);

        try {
            //output.flush();
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            mensagem = (Message) input.readObject(); //DETALHE: O nome do pacote da classe Message no servidor
            //deve ser IDENTICO ao do cliente
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mensagem.getResposta().equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static double Ler(Sensor s) {
        Message mensagem = new Message();
        mensagem.setAction(Message.Action.STATECOMPONENT);
        mensagem.setIdentificador("" + s);
        try {
            
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            mensagem = (Message) input.readObject(); //DETALHE: O nome do pacote da classe Message no servidor
            //deve ser IDENTICO ao do cliente
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        String resposta = mensagem.getResposta();

        return Double.parseDouble(resposta);

    }
    
    public static boolean Ler(Led l) {
        Message mensagem = new Message();
        mensagem.setAction(Message.Action.STATECOMPONENT);
        mensagem.setIdentificador("" + l);

        try {
            //output.flush();
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            mensagem = (Message) input.readObject(); //DETALHE: O nome do pacote da classe Message no servidor
            //deve ser IDENTICO ao do cliente
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mensagem.getResposta().equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static void ligar(Led componente) {
        alterarEstadoComponente(""+componente, true);
    }
    
    public static void desligar(Led componente) {
        alterarEstadoComponente(""+componente, false);
    }

    private static void alterarEstadoComponente(String componente, boolean estado) {
        Message mensagem = new Message();
        mensagem.setAction(Message.Action.CHANGESTATECOMPONENT);
        mensagem.setIdentificador(""+componente);
        mensagem.setEstado(estado);
        try {
            output.flush();
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void desconectar() {
        Message mensagem = new Message();
        mensagem.setAction(Message.Action.DISCONNECT);
        try {
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void escrever (Display d, int msg){
        escrever(d, msg+"");
    }
    
    public static void escrever (Display d, float msg){
        escrever(d, msg+"");
    }
    
    public static void escrever (Display d, boolean msg){
        escrever(d, msg+"");
    }
    
    public static void escrever(Display d, String msg){
        Message mensagem = new Message();
        mensagem.setAction(Message.Action.ESCREVER);
        mensagem.setIdentificador(""+d);
        mensagem.setEscrita(msg);
        try {
            output.flush();
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void apagar(Display d){
        String msg = "                                   ";
        escrever(d, msg);
    }
    

    public enum Display{
        D1
    }
    public enum Led{
        L1, L2, L3, L4
    }
    public enum Chave {
        C1, C2, C3, C4
    }

    public enum Sensor {
        S1, S2
    }
}
