package SnakeGame;

import javax.swing.*;

public class FrameGame extends JFrame {
    FrameGame(){

        this.add(new PainelDoGame()); //Instancia
        this.setTitle("Jogo da cobrinha"); // Titulo do jogo
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Fechando em quadros redimensionaveis
        this.setResizable(false);
        this.pack(); //Componentes ao quadro
        this.setVisible(true); //Janela aparecendo
        this.setLocationRelativeTo(null);

    }
}
