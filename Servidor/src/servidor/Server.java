/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Humberto Lugo
 */
public class Server implements AvisaServer{
    
    
    
    private ServerSocket serverSocket;    
    
    Vector<ClienteThread> clientes;
    
    public Server(){
        
    }
    
    void conexion(int puerto){
        try {
            
            clientes = new Vector<ClienteThread>();
                        
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor escuchando en el puerto: "+puerto);
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Un usuario se ha conectado");
                ClienteThread cliente = new ClienteThread(socket);
                cliente.addListener(this);
                clientes.add(cliente);
               
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 
    
    
    
    public void cerrarConexion(){
        try{
            serverSocket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onClientReceive(String msg) {
        
        for(int i =0; i<clientes.size(); i++){
            ClienteThread cliente = clientes.get(i);
            
            cliente.enviarDatos(msg);
        }
      
    }
    
}