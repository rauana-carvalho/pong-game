package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PainelJogo extends JPanel implements Runnable {

    //--------------------------- constantes ---------------------------
    static final int LARGURA_JOGO = 800;
    static final int ALTURA_JOGO  = (int) (LARGURA_JOGO * 0.75);
    static final Dimension TAMANHO = new Dimension(LARGURA_JOGO, ALTURA_JOGO);

    static final int DIAM_BOLA    = 15;
    static final int LARG_RAQUETE = 10;
    static final int ALT_RAQUETE  = 100;

    //------------------------ estado de jogo -------------------------
    private boolean jogoIniciado = false;
    private boolean jogoPausado  = false;

    private final Thread threadJogo;

    //--------------------------- desenho -----------------------------
    private Image imagem;
    private Graphics graficos;

    //------------------------ composição -----------------------------
    private final JanelaJogo janela;
    private final Collection<ElementoJogo> elementos = new ArrayList<>();
    private final Pontuacao pontuacao;

    private Bola     bola;
    private Raquete  raquete1;
    private Raquete  raquete2;

    //--------------- componentes do menu de pausa -------------------
    private final JPanel pausePanel;
    private final JLabel Paused;
    private final JLabel Esc;
    private final JLabel R;
    private final JLabel Q;

    //------------------------- construtor ----------------------------
    public PainelJogo(JanelaJogo janelaJogo) {
        this.janela = janelaJogo;

        setPreferredSize(TAMANHO);
        setBackground(ColorPalette.BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(new AL());
        setLayout(null);

        raquete1 = new Raquete(
                0,
                (ALTURA_JOGO - ALT_RAQUETE) / 2,
                LARG_RAQUETE,
                ALT_RAQUETE,
                1);

        raquete2 = new Raquete(
                LARGURA_JOGO - LARG_RAQUETE,
                (ALTURA_JOGO - ALT_RAQUETE) / 2,
                LARG_RAQUETE,
                ALT_RAQUETE,
                2);

        bola = new Bola(
                (LARGURA_JOGO - DIAM_BOLA) / 2,
                new Random().nextInt(ALTURA_JOGO - DIAM_BOLA),
                DIAM_BOLA);

        elementos.add(raquete1);
        elementos.add(raquete2);
        elementos.add(bola);

        String jogador1 = "Rauana";
        String jogador2 = "Bruna";
        pontuacao = new Pontuacao(LARGURA_JOGO, ALTURA_JOGO, jogador1, jogador2);

        add(pontuacao.getNomeLabel1());
        add(pontuacao.getNomeLabel2());
        add(pontuacao.getScoreLabel1());
        add(pontuacao.getScoreLabel2());

        //------------------- painel de pausa ------------------------
        pausePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pausePanel.setBounds(0, 0, LARGURA_JOGO, ALTURA_JOGO);
        pausePanel.setOpaque(false);
        pausePanel.setVisible(false);

        Paused = new JLabel("PAUSADO", SwingConstants.CENTER);
        Esc    = new JLabel("Esc - continuar", SwingConstants.CENTER);
        R      = new JLabel("R - reiniciar",   SwingConstants.CENTER);
        Q      = new JLabel("Q - sair",        SwingConstants.CENTER);

        Paused.setFont(new Font("HelveticaNeue", Font.BOLD, 40));
        Esc.setFont   (new Font("HelveticaNeue", Font.PLAIN, 18));
        R.setFont     (new Font("HelveticaNeue", Font.PLAIN, 18));
        Q.setFont     (new Font("HelveticaNeue", Font.PLAIN, 18));

        Paused.setForeground(ColorPalette.PRIMARY_COLOR);
        Esc.setForeground   (ColorPalette.PRIMARY_COLOR);
        R.setForeground     (ColorPalette.PRIMARY_COLOR);
        Q.setForeground     (ColorPalette.PRIMARY_COLOR);

        Paused.setBounds((LARGURA_JOGO / 2) - 100, (ALTURA_JOGO / 2) - 80, 200, 40);
        Esc.setBounds   ((LARGURA_JOGO / 2) - 90,  (ALTURA_JOGO / 2) - 20, 180, 25);
        R.setBounds     ((LARGURA_JOGO / 2) - 90,  (ALTURA_JOGO / 2) + 10, 180, 25);
        Q.setBounds     ((LARGURA_JOGO / 2) - 90,  (ALTURA_JOGO / 2) + 40, 180, 25);

        pausePanel.add(Paused);
        pausePanel.add(Esc);
        pausePanel.add(R);
        pausePanel.add(Q);
        add(pausePanel);
        
        //------------------------------------------------------------

        threadJogo = new Thread(this);
        threadJogo.start();
    }

    public void setNomes(String p1, String p2) {
        pontuacao.setNomes(p1, p2);
    }

    //------------------- ciclo de partida --------------------
    private void reiniciarPartida() {
        pontuacao.reset();
        jogoPausado = false;
        pausePanel.setVisible(false);

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

    //------------------------------ draw -----------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        imagem   = createImage(getWidth(), getHeight());
        graficos = imagem.getGraphics();

        elementos.forEach(e -> e.desenhar(graficos));

        pontuacao.desenhar(graficos);

        g.drawImage(imagem, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
    }

    //--------------------------- lógica -----------------------------
    private void mover() {
        elementos.stream()
                 .filter(e -> e instanceof Movable)
                 .forEach(e -> ((Movable) e).mover());
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
                Raquete r = (Raquete) e;

                bola.x = (bola.getVelocidadeX() < 0) ? r.x + r.width
                                                     : r.x - DIAM_BOLA;

                bola.setVelocidadeX(-bola.getVelocidadeX());
                bola.setVelocidadeY(bola.getVelocidadeY() + (bola.getVelocidadeY() > 0 ? 1 : -1));

                double maxY = Math.abs(bola.getVelocidadeX()) *4.5;
                if (Math.abs(bola.getVelocidadeY()) > maxY) {
                    bola.setVelocidadeY((int) (Math.signum(bola.getVelocidadeY()) * maxY));
                }

                Som.tocar("/sons/paddle.wav");
                break;
            }
        }

        if (bola.x <= 0) {
            pontuacao.pontoJogador2();
            Som.tocar("/sons/score.wav");
            resetRound();
            return;
        } else if (bola.x >= LARGURA_JOGO - DIAM_BOLA) {
            pontuacao.pontoJogador1();
            Som.tocar("/sons/score.wav");
            resetRound();
            return;
        }

        if (pontuacao.fimDeJogo(11)) {
            Som.pauseBackground();
            Som.tocar("/sons/gameover.wav");
            JOptionPane.showMessageDialog(this, pontuacao.getVencedor() + " venceu!");
            System.exit(0);
        }
    }

    //------------------------- game-loop -----------------------------
    @Override
    public void run() {
        long ultimo = System.nanoTime();
        final double ns   = 1_000_000_000.0 / 70.0;
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

    //---------------------- controle de teclado ---------------------
    private class AL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int kc = e.getKeyCode();

            if (kc == KeyEvent.VK_ESCAPE) {
                jogoPausado = !jogoPausado;
                pausePanel.setVisible(jogoPausado);
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
            if (!jogoPausado) {
                raquete1.keyReleased(e);
                raquete2.keyReleased(e);
            }
        }
    }
}