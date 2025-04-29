package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PainelJogo extends JPanel implements Runnable {

    static final int LARGURA_JOGO = 800;
    static final int ALTURA_JOGO  = (int)(LARGURA_JOGO * 0.75);
    static final Dimension TAMANHO = new Dimension(LARGURA_JOGO, ALTURA_JOGO);
    static final int DIAM_BOLA    = 15;
    static final int LARG_RAQUETE = 10;
    static final int ALT_RAQUETE  = 100;

    private String nomeJogador1 = "Rauana";
    private String nomeJogador2 = "Bruna";

    private boolean jogoIniciado = false;
    private boolean jogoPausado  = false;

    private Thread threadJogo;
    private Image imagem;
    private Graphics graficos;

    private final JanelaJogo janela;
    private final Collection<ElementoJogo> elementos = new ArrayList<>();
    private final Pontuacao pontuacao;

    private final JLabel lblName1;
    private final JLabel lblName2;
    private final JLabel lblScore1;
    private final JLabel lblScore2;

    private Bola bola;
    private Raquete raquete1;
    private Raquete raquete2;

    public PainelJogo(JanelaJogo janelaJogo) {
        this.janela = janelaJogo;
        setPreferredSize(TAMANHO);
        setBackground(ColorPalette.BACKGROUND_COLOR);
        setLayout(null);
        setFocusable(true);
        addKeyListener(new AL());

        lblName1  = createLabel(nomeJogador1, 14, (LARGURA_JOGO/2) - 100, 10, 100, 20);
        lblName2  = createLabel(nomeJogador2, 14, (LARGURA_JOGO/2) + 0,  10, 100, 20);
        add(lblName1);
        add(lblName2);

        lblScore1 = createLabel("0", 50, (LARGURA_JOGO/2) - 100, 30, 80, 50);
        lblScore2 = createLabel("0", 50, (LARGURA_JOGO/2) + 20,  30, 80, 50);
        add(lblScore1);
        add(lblScore2);

        raquete1 = new Raquete(0, (ALTURA_JOGO - ALT_RAQUETE) / 2, LARG_RAQUETE, ALT_RAQUETE, 1);
        raquete2 = new Raquete(LARGURA_JOGO - LARG_RAQUETE, (ALTURA_JOGO - ALT_RAQUETE) / 2,
                              LARG_RAQUETE, ALT_RAQUETE, 2);
        bola      = new Bola((LARGURA_JOGO - DIAM_BOLA) / 2,
                             new Random().nextInt(ALTURA_JOGO - DIAM_BOLA),
                             DIAM_BOLA);

        elementos.add(raquete1);
        elementos.add(raquete2);
        elementos.add(bola);

        pontuacao = new Pontuacao(LARGURA_JOGO, ALTURA_JOGO);

        threadJogo = new Thread(this);
        threadJogo.start();
    }

    private JLabel createLabel(String texto, int fontSize, int x, int y, int width, int height) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(ColorPalette.PRIMARY_COLOR);
        label.setFont(new Font("HelveticaNeue", Font.PLAIN, fontSize));
        label.setBounds(x, y, width, height);
        return label;
    }

    public void setNomes(String p1, String p2) {
        if (p1 != null && !p1.isBlank()) {
            nomeJogador1 = p1;
            lblName1.setText(p1);
        }
        if (p2 != null && !p2.isBlank()) {
            nomeJogador2 = p2;
            lblName2.setText(p2);
        }
    }

    private void reiniciarPartida() {
        pontuacao.jogador1 = 0;
        pontuacao.jogador2 = 0;
        updateScoreLabels();
        jogoPausado = false;

        bola.parar();
        bola.x = (LARGURA_JOGO - DIAM_BOLA) / 2;
        bola.y = (ALTURA_JOGO - DIAM_BOLA) / 2;

        raquete1.y = (ALTURA_JOGO - ALT_RAQUETE) / 2;
        raquete2.y = (ALTURA_JOGO - ALT_RAQUETE) / 2;

        jogoIniciado = false;
        janela.iniciarPartida();
    }

    private void resetRound() {
        bola.parar();
        bola.x = (LARGURA_JOGO - DIAM_BOLA) / 2;
        bola.y = (ALTURA_JOGO - DIAM_BOLA) / 2;

        raquete1.y = (ALTURA_JOGO - ALT_RAQUETE) / 2;
        raquete2.y = (ALTURA_JOGO - ALT_RAQUETE) / 2;

        jogoIniciado = false;
        javax.swing.Timer delay = new javax.swing.Timer(500, e -> iniciarMovimento());
        delay.setRepeats(false);
        delay.start();
    }

    public void iniciarMovimento() {
        bola.iniciarMovimento();
        jogoIniciado = true;
    }

    private void updateScoreLabels() {
        lblScore1.setText(String.valueOf(pontuacao.jogador1));
        lblScore2.setText(String.valueOf(pontuacao.jogador2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagem   = createImage(getWidth(), getHeight());
        graficos = imagem.getGraphics();
        for (ElementoJogo e : elementos) {
            e.desenhar(graficos);
        }
        if (jogoPausado) drawPauseOverlay(graficos);
        g.drawImage(imagem, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawPauseOverlay(Graphics g) {
        g.setColor(new Color(255, 255, 255, 150));
        g.fillRect(0, 0, LARGURA_JOGO, ALTURA_JOGO);
        g.setColor(ColorPalette.PRIMARY_COLOR);
        g.setFont(new Font("HelveticaNeue", Font.BOLD, 40));
        g.drawString("PAUSADO", LARGURA_JOGO/2 - 100, ALTURA_JOGO/2 - 40);
        g.setFont(new Font("HelveticaNeue", Font.PLAIN, 18));
        g.drawString("Esc - continuar", LARGURA_JOGO/2 - 90, ALTURA_JOGO/2 + 10);
        g.drawString("R - reiniciar",      LARGURA_JOGO/2 - 90, ALTURA_JOGO/2 + 40);
        g.drawString("Q - sair",           LARGURA_JOGO/2 - 90, ALTURA_JOGO/2 + 70);
    }

    private void mover() {
        for (ElementoJogo e : elementos) {
            if (e instanceof Movable) {
                ((Movable)e).mover();
            }
        }
    }

    private void verificarColisoes() {
        if (bola.y <= 0) {
            bola.y = 0;
            bola.setVelocidadeY(-bola.getVelocidadeY());
            Som.tocar("/sons/wall.wav");
        } else if (bola.y >= ALTURA_JOGO - DIAM_BOLA) {
            bola.y = ALTURA_JOGO - DIAM_BOLA;
            bola.setVelocidadeY(-bola.getVelocidadeY());
            Som.tocar("/sons/wall.wav");
        }

        for (ElementoJogo e : elementos) {
            if (e instanceof Raquete && bola.intersects(e)) {
                Raquete r = (Raquete)e;
                if (bola.getVelocidadeX() < 0) bola.x = r.x + r.width;
                else                          bola.x = r.x - DIAM_BOLA;

                bola.setVelocidadeX(-bola.getVelocidadeX());
                if (bola.getVelocidadeY() > 0) bola.setVelocidadeY(bola.getVelocidadeY() + 1);
                else                             bola.setVelocidadeY(bola.getVelocidadeY() - 1);

                double maxY = Math.abs(bola.getVelocidadeX()) * 1.5;
                if (Math.abs(bola.getVelocidadeY()) > maxY) {
                    bola.setVelocidadeY((int)(Math.signum(bola.getVelocidadeY()) * maxY));
                }
                Som.tocar("/sons/paddle.wav");
                break;
            }
        }

        if (bola.x <= 0) {
            pontuacao.jogador2++;
            Som.tocar("/sons/score.wav");
            updateScoreLabels();
            resetRound();
            return;
        } else if (bola.x >= LARGURA_JOGO - DIAM_BOLA) {
            pontuacao.jogador1++;
            Som.tocar("/sons/score.wav");
            updateScoreLabels();
            resetRound();
            return;
        }

        if (pontuacao.jogador1 == 11 || pontuacao.jogador2 == 11) {
            Som.pauseBackground();
            Som.tocar("/sons/gameover.wav");
            String vencedor = pontuacao.jogador1 == 11 ? nomeJogador1 : nomeJogador2;
            JOptionPane.showMessageDialog(this, vencedor + " venceu!");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        long ultimo = System.nanoTime();
        double ns    = 1_000_000_000.0 / 60.0;
        double delta = 0;
        while (true) {
            long agora = System.nanoTime();
            delta += (agora - ultimo) / ns;
            ultimo = agora;
            if (delta >= 1) {
                if (jogoIniciado && !jogoPausado) {
                    mover();
                    verificarColisoes();
                }
                repaint();
                delta--;
            }
        }
    }

    private class AL extends KeyAdapter {
        @Override
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
                if (kc == KeyEvent.VK_R) reiniciarPartida();
                if (kc == KeyEvent.VK_Q) System.exit(0);
                return;
            }
            raquete1.keyPressed(e);
            raquete2.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (jogoPausado) return;
            raquete1.keyReleased(e);
            raquete2.keyReleased(e);
        }
    }
}