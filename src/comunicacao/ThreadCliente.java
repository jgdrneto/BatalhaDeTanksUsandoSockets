package comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadCliente implements Runnable{
    
    Socket cliente;
    String informacao;
    
    public ThreadCliente(Socket c) {
        this.cliente = c;
    }
    
    @Override
    public void run() {
                 
        try {
                       
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    
            this.informacao = inFromClient.readLine();
                                    
            inFromClient.close();
                        
            cliente.close();
                
        } catch (IOException e) {
                
            System.out.println("Erro de leitura");
                
            e.printStackTrace();
        } 
    }

    public String getInformacao() {
        return informacao;
    }
       
}