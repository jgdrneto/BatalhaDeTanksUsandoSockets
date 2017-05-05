package nucleoDoJogo;

import java.util.Vector;

public class Jogador {
	
	private String nome;
    Vector<Integer> atingido = new Vector<Integer>();
    private int posicao;
    ESTADO estado;
    
    public enum ESTADO{	
    	MORTO, 
    	VIVO;
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

    	Utilidades.LimpaTela();

    	System.out.println(nome + " se mexeu para um novo lugar");

    	return true;
    }
    
    void atacar(Jogador jAdversario, Dados dados) throws InterruptedException {
        
        int i=0;
        int valorBotao = -1;
        int valorPotenciometro=-1;
        
        valorBotao = dados.getValorBotao();
        valorPotenciometro = dados.getValorPotenciometro(); 
	
        while(valorBotao==0){

        	System.out.println("---------------------------------------------");;

        	//Impressão das posições dos tiros ja realizados

        	System.out.print( "Posições já atiradas por você : { ");
        	
        	for( int c=0;c< jAdversario.atingido.size();c++){
        		if(c+1<jAdversario.atingido.size()){
        			System.out.print( jAdversario.atingido.get(c) +", ");
        		}else{
        			System.out.print( jAdversario.atingido.get(c) +" ");
        		}    
        	}	 	
        	System.out.println("}");        
        	
        	int poteAnt = dados.getValorPotenciometro();
        	
        	Thread.sleep(100);
        	
        	int poteDepois = dados.getValorPotenciometro();
        		
        	if(valorPotenciometro==poteAnt && valorPotenciometro==poteDepois){
        	    valorBotao = dados.getValorBotao();
        	}

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
