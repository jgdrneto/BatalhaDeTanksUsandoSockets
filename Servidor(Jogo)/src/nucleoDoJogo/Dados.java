package nucleoDoJogo;

import comunicacao.Servidor;

public class Dados {
	
    Servidor servidor;
    int valorBotao;
	int valorPotenciometro;
	int valorFoto;
	
	public Dados(Servidor s){
	    servidor = s;
    }
	
	private void lerValorAtualSocket(){
	    
	    String dados=null;
	    
	    //Obrigando que exista algo no socket
	    while(dados==null){
	        dados = servidor.getInformacao();
	    }
	    
        String[] informacoes = dados.split(" ");
        
        for(int i=0;i<informacoes.length;i++){
            switch(informacoes[i]){
                case "B:":
                    valorBotao = Integer.parseInt(informacoes[i+1]);
                break;
                case "A:":
                    valorPotenciometro = Integer.parseInt(informacoes[i+1]);
                break;
                case "F:":
                    valorFoto = Integer.parseInt(informacoes[i+1]);
                break;    
            }
        }
	}
	
	public int getValorBotao(){
	    
	    lerValorAtualSocket();
	    
		return valorBotao;
	}
	
	public int getValorPotenciometro(){
	    
	    lerValorAtualSocket();
	    
		return valorPotenciometro;
	}
	
	public int getValorFoto(){
	    
	    lerValorAtualSocket();
	    
		return valorFoto;
	}

}
