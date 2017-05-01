package nucleoDoJogo;

import java.io.IOException;

import comunicacao.Servidor;

public class Jogo {
    
    Servidor servidor;
    Jogador jogador1;
    Jogador jogador2;
    boolean acabou;
    Thread servidorThread;
    
    public final static void clearConsole(){
        
        for(int i=0;i<50;i++){
            System.out.println();
        }
    }
    
    public Jogo(int numeroPorta){
        //Criando servidor
        servidor = new Servidor(numeroPorta);
        
        //Criando Thread do servidor
        servidorThread = new Thread(servidor);
        
        //Definindo com Daemon (Execução em segundo plano)
        servidorThread.setDaemon(true); 
        
        //Criando os jogadores
        jogador1 = new Jogador();
        jogador2 = new Jogador();
        
        acabou=false;
        
    }
    
    public void executar(){
       //Iniciando thread do servidor 
       servidorThread.start();
       
       System.out.println("Olá");
       
       //Game loop
       while(!acabou){
           
       }
      
       finalizar(jogador1);
       
    }
        
    private void finalizar(Jogador jA) {
       System.out.println("Parabêns ao Jogador " + "<Nome>" + "Pela vitória");
    }

    public void desenharTela(){
        
    }
}
