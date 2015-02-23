

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Humberto Lugo
 */
public class ClienteThread   {
    
    private Socket socket;
    
    String datos="";
        
    private OutputStream outputStream;
    private InputStream inputStream;
    
    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;
    
    //variable de instancia "LISTENER"
    public AvisaServer avisaServer;
    
    private boolean conexionActiva;
    
    
    
    
    
    
    
    
    
    public ClienteThread(Socket socket){
        this.socket=socket;
        
        conexionActiva=true;
        Thread hiloServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(conexionActiva){
                    recibirDatos();
                    
                    if(!datos.equals("")){
                        enviarDatos(datos);
                        datos="";
                    }
                }
                cerrarConexion();
            }
        });
        hiloServer.start();   
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * @param avisaServer*********/
    public void addListener(AvisaServer avisaServer){
        
        this.avisaServer = avisaServer;
        
    }
    
    /*****************/
    
    
    
    
    
    
    
    
    public void recibirDatos(){
        try{
            inputStream = socket.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            
            /*******************/
            if(this.avisaServer != null){
            
                this.avisaServer.onClientReceive(entradaDatos.readUTF());
                 
            }
            
            datos = entradaDatos.readUTF();
           
            
            System.out.println("RECIBIRDATOS CLIENTETHREAD"+entradaDatos.readUTF());
        }catch(Exception ex){
            //ex.printStackTrace();
            conexionActiva=false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    public void enviarDatos(String datos){
        try{
            outputStream = socket.getOutputStream();
            salidaDatos = new DataOutputStream(outputStream);
            salidaDatos.writeUTF(datos);
            salidaDatos.flush();
            
            System.out.println("ENVIADO!");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    public void cerrarConexion(){
        try{
            salidaDatos.close();
            entradaDatos.close();
            socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    
    
    
}