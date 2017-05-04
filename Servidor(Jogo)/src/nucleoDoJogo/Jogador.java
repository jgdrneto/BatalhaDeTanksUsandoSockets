package nucleoDoJogo;

import java.util.Objects;
import java.util.Vector;

import comunicacao.Servidor;

public class Jogador {
	
	private final String nome;
    Vector<Integer> atingido = new Vector<>();
    private int posicao;
    ESTADO estado;
    
    public enum ESTADO{	
    	MORTO(1), VIVO(2);
    	
    	private final int valor;
    	ESTADO(int valorOpcao){
    		valor = valorOpcao;
    	}
    	public int getValor(){
    		return valor;
    	}
    }
	
	Jogador(String nNome, int nPosicao) {
        nome = nNome;
        posicao = nPosicao;
        estado = ESTADO.VIVO;
    }
	
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Jogador)) 
            return false; 
        if(obj == this) 
            return true;

        // aqui o cast é seguro por causa do teste feito acima
        Jogador jogador = (Jogador) obj; 

        //aqui você compara a seu gosto, o ideal é comparar atributo por atributo
        return this.nome.equals(jogador.getNome()) && this.posicao == jogador.getposicao();
    } 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.nome);
        hash = 29 * hash + this.posicao;
        return hash;
    }
    
    String getNome() {
        return this.nome;
    }

    private int getposicao() {
        return this.posicao;
    }
    
    boolean mover(int nPosicao) {
    	for(int p : atingido){
    		if(p==nPosicao){
    			return false;
    		}
    	}

    	posicao = nPosicao;

    	//Utilidades::limpaTela();

    	System.out.println(nome + "se mexeu para um novo lugar");

    	return true;
    }
    
    void atacar(Jogador jAdversario, Servidor serv) throws InterruptedException {
    	String valor = serv.getInformacao();
        int i=0;
        int valorBotao = -1;
        int valorPotenciometro=-1;
        
        Comandos comando = new Comandos(valor);
        valorBotao = comando.getValorBotao();
        valorPotenciometro = comando.getValorPotenciometro(); 
         
        
      
      //Thread tPot(potenciometro,ref(valorPotenciometro),j.escolherAngulo());	      
      //Thread tBot(botao,ref(valorBotao),j.valorBotao());
      
      //tPot.join();
      //tBot.join();
      
        while(valorPotenciometro==-1){
        	Thread.sleep(1000);
        }
        
        while(valorBotao==-1){
	      	Thread.sleep(1000);
	    }
        /*Thread valorP;
        Thread valorB;*/
 		
        while(valorBotao==0){

        	//Utilidades::limpaTela();

        	//Impressão das posições dos tiros ja realizados

        	System.out.println( "Posições já atiradas por você : { ");
        	
        	for( int c=0;c< jAdversario.atingido.size();c++){
        		if(c+1<jAdversario.atingido.size()){
        			System.out.print( jAdversario.atingido.get(c) +", ");
        		}else{
        			System.out.print( jAdversario.atingido.get(c) +" ");
        		}    
        	}	 	
        	System.out.println("}");        
      	
        	//valorP = new Thread(potenciometro,ref(valorPotenciometro),j.escolherAngulo());
        	//valorP.join();
        	//criar uma thread para receber o valor da informação, dando prioridade maior potenciometro.
        	//usar a priodidade de thread, para que n importe do valor do botão nesse momento.
        	Thread potenciometro = new Thread(serv);
    		potenciometro.setPriority(Thread.MAX_PRIORITY);
    		potenciometro.start();
    		
    		Thread botao = new Thread(serv);
    		botao.setPriority(Thread.MIN_PRIORITY);
    		botao.start();
	
        	System.out.println(nome + ", escolha o ângulo do tiro: ");
        	System.out.println(valorPotenciometro);
	
        	if(i==valorPotenciometro){
        		//valorB = new Thread(botao,ref(valorBotao),j.valorBotao());	
        		//valorB.join();	
        		
        	}
	
        	i=valorPotenciometro;

        	Thread.sleep(2000);
      }
      
      System.out.println("Botão pressionado, mudando vez para o adversário");	
	
      boolean jaExiste=false;	

      for(int p : jAdversario.atingido){
    	  if(p==i){
    		  jaExiste=true; 	
    	  }
      }
 			
      //Adicioanando valor aos locais que ele não pode mais ir
      if (jaExiste==false){
    	  jAdversario.atingido.add(i);
      }      
      System.out.println( "Valor da sua posição" + posicao);
	
      //Verificar se o ataque acertou o advsário
      if(i==jAdversario.posicao){
        jAdversario.estado = ESTADO.MORTO;
      }	
    }

}
