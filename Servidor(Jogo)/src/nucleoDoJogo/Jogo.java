package nucleoDoJogo;

import java.util.Random;
import java.util.Scanner;

import comunicacao.Servidor;

public class Jogo {
	
	public static final int ANGULO_MIN =18;
    public static final int ANGULO_MAX =82;    
    
    Servidor servidor;
    Jogador jogador1;
    Jogador jogador2;
    Dados dados;
    boolean terminou;
    Thread servidorThread;
	private Scanner ler;
    
    public enum ACAO{	
    	ATACAR, 
    	MOVER;
    }
    
    public Jogo(int numeroPorta){
        //Criando servidor
        servidor = new Servidor(numeroPorta);
        
        //Criando Thread do servidor
        servidorThread = new Thread(servidor);
        
        //Definindo com Daemon (Execução em segundo plano)
        servidorThread.setDaemon(true); 
        
        //Iniciando thread do servidor 
        servidorThread.start();
        
        this.dados = new Dados(servidor);
        
        //Definindo nome do jogador 1
        String nomeJ1 = definirNome(1);
            
        //Definindo posicao do jogador 1
        int pJ1 = novaPosicao();
            
        //Definindo nome do jogador 2
        String nomeJ2 = definirNome(2);
            
        //Definindo posicao do jogador 2
        int pJ2 = novaPosicao();
        
        //Criando os jogadores
        jogador1 = new Jogador(nomeJ1, pJ1);
        jogador2 = new Jogador(nomeJ2, pJ2);
        
        terminou=false;
        
    }
    
    public void executar() throws InterruptedException{
      
       //Game loop
       while(!terminou){
           //Implementar
           
           logica();
           
           desenharTela();
           
       }
      
       finalizar(jogador1);
       
    }
        
    private void logica() throws InterruptedException {
    	
        Jogador jogadorDaVez;  
        Jogador jogadorAdversario;
        
        jogadorDaVez = jogador2;
        jogadorAdversario = jogador1;
        
        while(!terminou){
            //Troca a vez do jogador    
            jogadorDaVez = trocaDeJogador(jogadorDaVez);
            jogadorAdversario = trocaDeJogador(jogadorDaVez);
            
            ACAO acao = escolherAcao(jogadorDaVez);
            
            switch(acao){
                case ATACAR :
                    // Jogador da Vez ataca o jogador Adversário
                    jogadorDaVez.atacar(jogadorAdversario, dados);    
                    break;
                case MOVER :
                    
                    boolean seMover = false;
                    
                    //Enquanto jogador não se mover
                    while(!seMover){
                        seMover = jogadorDaVez.mover(novaPosicao());
                    }
                    
                    break;
            }
        }
    }

    
    ACAO escolherAcao(Jogador jogadorDaVez) throws InterruptedException{
  	    
       desenharTela();
       System.out.println(jogadorDaVez.getNome() + ",  o que você deseja fazer:  "); 
       System.out.println( "Cubra o fotosensor para escolher se mover" );
       System.out.println("Não cubra o fotosensor para escolher atacar");
       System.out.println("Você tem 5 segundos para decidir");
                
       Thread.sleep(5000);
       
       int fotossensor = dados.getValorFoto();
       
       ACAO ac;

       if(fotossensor==0){
    	   ac = ACAO.MOVER;
       }else{
    	   ac = ACAO.ATACAR;
       }
       return ac;

    }

    private void finalizar(Jogador jA) {
       System.out.println("Parabéns ao jogador " + jA.getNome() + " pela vitória");
    }

    public void desenharTela(){
    	System.out.println("---------------------------------------------");
    }
    
    String definirNome(int i){
        ler = new Scanner(System.in);
        String nome;
            
        desenharTela();
            
        System.out.println("Qual o nome do jogador " + i + " : ");
        nome = ler.nextLine();
            
        return nome;
    }
    
    int novaPosicao(){
        
        Random gerador = new Random();
            
        return (gerador.nextInt(ANGULO_MAX+1-ANGULO_MIN)) + ANGULO_MIN;
    } 
    
    Jogador trocaDeJogador(Jogador jogadorDaVez){
        
        //Comparando jogadores
        if(jogador1.equals(jogadorDaVez)){
            return jogador2;
        }else{
            return jogador1;
        }
    }
}
