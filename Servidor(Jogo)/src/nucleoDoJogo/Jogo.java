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
    boolean terminou;
    Thread servidorThread;
	private Scanner ler;
    
    public enum ACAO{	
    	ATACAR(1), MOVER(2);
    	
    	private final int valor;
    	ACAO(int valorOpcao){
    		valor = valorOpcao;
    	}
    	public int getValor(){
    		return valor;
    	}
    }
    
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
       //Iniciando thread do servidor 
       servidorThread.start();

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
                    jogadorDaVez.atacar(jogadorAdversario, servidor);    
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
       int i = -1;
       
       String valor = servidor.getInformacao();
       Comandos c = new Comandos(valor);
       i =c.getValorFoto();
       ACAO ac;

                //Dorme ate receber valor aceitavel
       while(i==-1){
    	   Thread.sleep(5000);
       }

       if(i==0){
    	   ac = ACAO.MOVER;
       }else{
    	   ac = ACAO.ATACAR;
       }
       return ac;

    }

    private void finalizar(Jogador jA) {
       System.out.println("Parabéns ao Jogador " + "<Nome>" + "Pela vitória");
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
