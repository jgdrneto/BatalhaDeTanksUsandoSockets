package nucleoDoJogo;

import java.util.Random;
import java.util.Scanner;

import comunicacao.Servidor;
import nucleoDoJogo.Jogador.ESTADO;

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
        
        terminou=false;
        
        introducao();
    }
    
    private void introducao(){
        
        Utilidades.LimpaTela();
        
        System.out.println("Bem vindo ao jogo Batalha de Tanques o/");
        
        System.out.println("\n\n");
        
        //Definindo nome do jogador 1
        String nomeJ1 = definirNome(1);
        
        System.out.println("");
        
        //Definindo posicao do jogador 1
        int pJ1 = novaPosicao();
            
        //Definindo nome do jogador 2
        String nomeJ2 = definirNome(2);
            
        //Definindo posicao do jogador 2
        int pJ2 = novaPosicao();
        
        //Criando os jogadores
        jogador1 = new Jogador(nomeJ1, pJ1);
        jogador2 = new Jogador(nomeJ2, pJ2);
    }
            
    public void executar() throws InterruptedException {
    	
        Jogador jogadorDaVez;  
        Jogador jogadorAdversario;
        
        jogadorDaVez = jogador2;
        jogadorAdversario = jogador1;
        
        while(!terminou){
            
            Utilidades.LimpaTela();
            
            //Troca a vez do jogador    
            jogadorDaVez = trocaDeJogador(jogadorDaVez);
            jogadorAdversario = trocaDeJogador(jogadorDaVez);
            
            desenharTela(jogadorDaVez);
            
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
            
            Thread.sleep(2000);
            
            if(jogadorAdversario.getEstado()==ESTADO.MORTO){
                terminou=true;
            }
            
        }
        
        finalizar(jogadorDaVez);
    }

    
    ACAO escolherAcao(Jogador jogadorDaVez) throws InterruptedException{
  	   
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
        
       Utilidades.LimpaTela();
        
       System.out.println("Parabéns ao jogador " + jA.getNome() + " pela vitória");
    }

    public void desenharTela(Jogador jogadorDaVez) throws InterruptedException{
        
        System.out.println(jogadorDaVez.getNome() + ", o que você deseja fazer:  "); 
        System.out.println("Cubra o fotosensor para escolher se mover" );
        System.out.println("Não cubra o fotosensor para escolher atacar");
        System.out.println("Você tem 5 segundos para decidir");
                 
        Thread.sleep(5000);
    }
    
    String definirNome(int i){
        ler = new Scanner(System.in);
        String nome;
                      
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
