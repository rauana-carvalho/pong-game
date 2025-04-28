package src;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PainelJogo extends JPanel implements Runnable {

    static final int LARGURA_JOGO = 800;
    static final int ALTURA_JOGO = (int)(LARGURA_JOGO * (0.75));
    static final Dimension TAMANHO_TELA = new Dimension(LARGURA_JOGO, ALTURA_JOGO);
    static final int DIAMETRO_BOLA = 15;
    static final int LARGURA_RAQUETE = 10;
    static final int ALTURA_RAQUETE = 100;

    private boolean jogoIniciado = false;
    private boolean jogoPausado = false;

    Thread threadJogo;
    Image imagem;
    Graphics graficos;
    Random aleatorio;
    Raquete raquete1;
    Raquete raquete2;
    Bola bola;
    Pontuacao pontuacao;
    private final JanelaJogo janela;          
 

    PainelJogo(JanelaJogo janelaJogo) {
        novaRaquete();
        novaBola();
        pontuacao = new Pontuacao(LARGURA_JOGO, ALTURA_JOGO);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(TAMANHO_TELA);
        this.janela = janelaJogo; 

        threadJogo = new Thread(this);
        threadJogo.start();
        
    }

    private void reiniciarPartida() {
        pontuacao.jogador1 = 0;
        pontuacao.jogador2 = 0;
        novaRaquete();
        novaBola();
        jogoIniciado = true;
        janela.iniciarPartida();
        bola.parar();
        jogoPausado = false;
    }


    public void iniciarMovimento(){
        jogoIniciado = true;
        bola.iniciarMovimento();
    }

    public void novaBola() {
        aleatorio = new Random();
        bola = new Bola((LARGURA_JOGO / 2) - (DIAMETRO_BOLA / 2), aleatorio.nextInt(ALTURA_JOGO - DIAMETRO_BOLA), DIAMETRO_BOLA, DIAMETRO_BOLA);
        
        if (jogoIniciado){
            bola.iniciarMovimento();
        }
    }

    public void novaRaquete() {
        raquete1 = new Raquete(0, (ALTURA_JOGO / 2) - (ALTURA_RAQUETE / 2), LARGURA_RAQUETE, ALTURA_RAQUETE, 1);
        raquete2 = new Raquete(LARGURA_JOGO - LARGURA_RAQUETE, (ALTURA_JOGO / 2) - (ALTURA_RAQUETE / 2), LARGURA_RAQUETE, ALTURA_RAQUETE, 2);
    }

    public void paint(Graphics g) {
        imagem = createImage(getWidth(), getHeight());
        graficos = imagem.getGraphics();
        desenhar(graficos);
        g.drawImage(imagem, 0, 0, this);
    }

    public void desenhar(Graphics g) {
        raquete1.desenhar(g);
        raquete2.desenhar(g);
        bola.desenhar(g);
        pontuacao.desenhar(g);

        //Tela de pausar
        if (jogoPausado) {
            g.setColor(new Color(255, 255, 255, 150));
            g.fillRect(0, 0, LARGURA_JOGO, ALTURA_JOGO);

            g.setColor(ColorPalette.PRIMARY_COLOR);
            g.setFont(new Font("HelveticaNeue", Font.BOLD, 40));
            g.drawString("PAUSADO", LARGURA_JOGO / 2 - 100, ALTURA_JOGO / 2 - 40);

            g.setFont(new Font("HelveticaNeue", Font.BOLD, 18));
            g.drawString("Esc - continuar", LARGURA_JOGO / 2 - 90, ALTURA_JOGO / 2 + 10);
            g.drawString("R - reiniciar",  LARGURA_JOGO / 2 - 90, ALTURA_JOGO / 2 + 40);
            g.drawString("Q - sair",       LARGURA_JOGO / 2 - 90, ALTURA_JOGO / 2 + 70);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void mover() {
        raquete1.mover();
        raquete2.mover();
        bola.mover();
    }

    public void verificarColisoes() {
        if (bola.y <= 0 || bola.y >= ALTURA_JOGO - DIAMETRO_BOLA) {
            bola.setDirecaoY(-bola.velocidadeY);
            Som.tocar("/sons/wall.wav"); 
        }

        if (bola.intersects(raquete1)) {
            bola.velocidadeX = Math.abs(bola.velocidadeX);
            if (bola.velocidadeY > 0)
                bola.velocidadeY++;
            else
                bola.velocidadeY--;
            bola.setDirecaoX(bola.velocidadeX);
            bola.setDirecaoY(bola.velocidadeY);
            Som.tocar("/sons/paddle.wav");
        }

        if (bola.intersects(raquete2)) {
            bola.velocidadeX = Math.abs(bola.velocidadeX);
            if (bola.velocidadeY > 0)
                bola.velocidadeY++;
            else
                bola.velocidadeY--;
            bola.setDirecaoX(-bola.velocidadeX);
            bola.setDirecaoY(bola.velocidadeY);
            Som.tocar("/sons/paddle.wav");
        }

        if (raquete1.y <= 0)
            raquete1.y = 0;
        if (raquete1.y >= (ALTURA_JOGO - ALTURA_RAQUETE))
            raquete1.y = ALTURA_JOGO - ALTURA_RAQUETE;
        if (raquete2.y <= 0)
            raquete2.y = 0;
        if (raquete2.y >= (ALTURA_JOGO - ALTURA_RAQUETE))
            raquete2.y = ALTURA_JOGO - ALTURA_RAQUETE;

        if (bola.x <= 0) {
            pontuacao.jogador2++;
            Som.tocar("/sons/score.wav");
            novaRaquete();
            novaBola();
        }
        if (bola.x >= LARGURA_JOGO - DIAMETRO_BOLA) {
            pontuacao.jogador1++;
            Som.tocar("/sons/score.wav");
            novaRaquete();
            novaBola();
        }
    }

    public void verificarFimDeJogo() {
        if (pontuacao.jogador1 == 11) {
            Som.tocar("/sons/gameover.wav");
            JOptionPane.showMessageDialog(this, "Jogador 1 venceu!");
            System.exit(0);
        } else if (pontuacao.jogador2 == 11) {
            Som.tocar("/sons/gameover.wav");
            JOptionPane.showMessageDialog(this, "Jogador 2 venceu!");
            System.exit(0);
        }
    }

    public void run() {
        long ultimoTempo = System.nanoTime();
        double quantidadeTicks = 60.0;
        double ns = 1000000000 / quantidadeTicks;
        double delta = 0;
        while (true) {
            long agora = System.nanoTime();
            delta += (agora - ultimoTempo) / ns;
            ultimoTempo = agora;
            if (delta >= 1) {
                if (!jogoPausado){
                    mover();
                    verificarColisoes();
                    verificarFimDeJogo();
                }

                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int kc = e.getKeyCode();

            if (kc == KeyEvent.VK_ESCAPE) {
                jogoPausado = !jogoPausado;

                if (jogoPausado) {
                    janela.pauseStartSequence();
                    Som.pauseBackground();
                } else {
                    janela.resumeStartSequence();
                    Som.resumeBackground();
                }
                return;
            }

            if (jogoPausado) {
                if (kc == KeyEvent.VK_Q) {
                    System.exit(0);
                } else if (kc == KeyEvent.VK_R) {
                    reiniciarPartida();
                }
                return; 
            }


            raquete1.keyPressed(e);
            raquete2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            if (jogoPausado) return;
            raquete1.keyReleased(e);
            raquete2.keyReleased(e);
        }
    }
}