package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JPanel;
public class PainelDoGame extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600; //largura da tela
    static final int SCREEN_HEIGHT = 600; //altura da tela
    static final int UNIT_SIZE = 20; //tamanho da unidade
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //Calculando objetos da tela
    static final int DELAY = 75; //Delay

    final  int x [] = new int[GAME_UNITS];
    final  int y [] = new int[GAME_UNITS]; //tamanho da cobrinha

    int parteCorpo = 6; // começa com 6 partes
    int comerMaca;
    int macaX;
    int macaY;
    char direction = 'R'; //direção que a cobra ira começar (direita)
    boolean running = false; //começar o jogo
    Timer timer;
    Random random;

    PainelDoGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black); //cor de fundo fundo
        this.setFocusable(true);
        this.addKeyListener(new MinhaChaveAdaptadora());
        startGame();
    }

    public void startGame() { //metodo para iniciar o jogo
        newMaca();
        running = true;
        timer = new Timer(DELAY,this); //dita quao rapido o jogo ira rodar
        timer.start();
    }
    public void paintComponent(Graphics g){ //pintura do jogo
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if (running) {
           /* for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); */
            //Linhas de grade (Opcional)

                g.setColor(Color.red); //cor da maça
                g.fillOval(macaX, macaY, UNIT_SIZE, UNIT_SIZE); //formato

        for (int i = 0; i< parteCorpo;i++){ // cor da cobrinha
            if ((i == 0)) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else {
                g.setColor(new Color(45,180,0));
                g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
             g.setColor(Color.red);
            g.setFont(new Font("ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: "+comerMaca, (SCREEN_WIDTH - metrics.stringWidth("Pontos: +comerMaca"))/2
                    ,g.getFont().getSize());
    }
        else {
            gameOver(g);
        }
    }
    public void newMaca() {
        macaX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        macaY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;


    }
    public void movimentacao() { //definindo movimentação

        for (int i = parteCorpo;i>0;i--) { //mudando as partes do corpo da cobrinha
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case  'U': //para cima (Up)
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D': //para baixo (Down)
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': //para esquerda (Left)
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': //para direita (Right)
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkMaca() { //Definindo como a maça ira funcionar no jogo

        if (x[0] == macaX && (y[0]) == macaY) {
            parteCorpo++; //quando a cobrinha pegar a maça, irá crescer parte de seu corpo
            comerMaca++; //Definindo metodo para a cobrinha comer a maça
            newMaca();
        }



    }
    public void checkColisoes() { //Definindo colisoes
        //Checando se a cabeça colide com seu corpo
        for (int i = parteCorpo;i>0;i--) {
            if ((x[0] == x[i])&& (y[0]) == y[i]){
                running = false;
            }
        }

        //checando se a cabeça toca a borda esquerda
        if (x[0] < 0) {
            running = false;
        }

        //checando se a cabeça toca a borda direita
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        //checando se a cabeça toca a borda inferior
        if (y[0] < 0) {
            running = false;
        }

        //checando se a cabeça toca a borda superior
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {// Definindo fim de jogo

        //Pontuação
        g.setColor(Color.red);
        g.setFont(new Font("ink Free", Font.BOLD, 20));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Feito por: Luan HenriqueTS", (SCREEN_WIDTH - metrics1.stringWidth("Feito por: LuanHenriqueTS"))/2, SCREEN_HEIGHT);

        g.setColor(Color.red);
        g.setFont(new Font("ink Free", Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("FIM DE JOGO!", (SCREEN_WIDTH - metrics2.stringWidth("FIM DE JOGO!"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.red);
        g.setFont(new Font("ink Free", Font.BOLD, 40));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Pontos: "+comerMaca, (SCREEN_WIDTH - metrics3.stringWidth("Pontos: +comerMaca"))/2,g.getFont().getSize());;
    }
    @Override
    public void actionPerformed(ActionEvent e) { //Definindo ações da cobrinha no jogo
        if (running) {
            movimentacao();
            checkMaca();
            checkColisoes();
        }
        repaint();
    }

    public class MinhaChaveAdaptadora extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }

    }
}