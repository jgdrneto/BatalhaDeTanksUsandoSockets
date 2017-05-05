package nucleoDoJogo;



public class Comandos {
	int valorBotao;
	int valorPotenciometro;
	int valorFoto;
	
	public Comandos(String s){
		String[] valorBotaoC = s.split("B: ");
        String[] valorBotaoC1 = valorBotaoC[1].split(" ");
        valorBotao =Integer.valueOf(valorBotaoC1[0]);  
        
        //aqui talvez seja o primeiro campo da split
        String[] valorPotenciometroC = s.split("A: ");
        String[] valorPotenciometroC1 = valorPotenciometroC[1].split(" ");
        valorPotenciometro =Integer.valueOf(valorPotenciometroC1[0]);
        
      
        String[] valorFotoC = s.split("F: ");
        valorFoto =Integer.valueOf(valorFotoC[1]);
    }
	
	public int getValorBotao(){
		return valorBotao;
	}
	
	public int getValorPotenciometro(){
		return valorPotenciometro;
	}
	
	public int getValorFoto(){
		return valorFoto;
	}

}
