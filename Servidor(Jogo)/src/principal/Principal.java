package principal;

import java.io.IOException;

import nucleoDoJogo.Jogo;

public class Principal {

    public static void main(String[] args) throws IOException, InterruptedException {
          
        Jogo j = new Jogo(4325);
        
        j.executar();
        
    }

}
