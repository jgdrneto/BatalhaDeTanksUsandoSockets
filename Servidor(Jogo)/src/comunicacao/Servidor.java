package comunicacao;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{
    
    ServerSocket server;
    String informacao;

    public Servidor(int NumeroPorta){
        try {
            server = new ServerSocket(NumeroPorta);
        } catch (IOException e) {
            
            System.out.println("Erro ao criar o servidor");
            
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            
            while(true){
                
                Socket client = server.accept();
                 
                ThreadCliente tc = new ThreadCliente(client);
                
                tc.run();
                                 
                this.informacao = tc.getInformacao();
                
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String getInformacao(){
    	return this.informacao;
    }
}
