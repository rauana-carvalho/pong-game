package src;

import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class JanelaJogo extends JFrame {

    private final CardLayout telas = new CardLayout();
    private final JPanel     cont  = new JPanel(telas);
    private final TelaInicio telaInicio;
    private final PainelJogo painel;

    private Clip  startClip;
    private Timer startTimer;
    private int   restanteMs;

    public PainelJogo getPainel() { return painel; }

    public JanelaJogo() {
        telaInicio = new TelaInicio(this);
        painel     = new PainelJogo(this);

        cont.add(telaInicio, "inicio");
        cont.add(painel,     "jogo");
        telas.show(cont, "inicio");

        add(cont);
        setTitle("Jogo Pong (POO 2024.2)");
        setResizable(false);
        setBackground(ColorPalette.BACKGROUND_COLOR);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        telaInicio.ganharFoco();
    }

    public void iniciarPartida() {

        startClip = Som.tocarClip("/sons/start.wav");
        int total = (int) (startClip.getMicrosecondLength() / 1000);

        restanteMs = total;

        startTimer = new Timer(total, e -> getPainel().iniciarMovimento());
        startTimer.setRepeats(false);
        startTimer.start();

        telas.show(cont, "jogo");
        painel.requestFocusInWindow();
    }

    public void pauseStartSequence() {
        if (startClip != null && startClip.isRunning()) {
            startClip.stop();
            int decorrido = (int) (startClip.getMicrosecondPosition() / 1000);
            restanteMs = Math.max(0, restanteMs - decorrido);
        }
        if (startTimer != null && startTimer.isRunning()) {
            startTimer.stop();
        }
    }

    public void resumeStartSequence() {
        if (startClip != null) {
            startClip.start();
        }
        if (restanteMs > 0) {
            startTimer = new Timer(restanteMs,
                                   e -> getPainel().iniciarMovimento());
            startTimer.setRepeats(false);
            startTimer.start();
        } else {
            getPainel().iniciarMovimento();
        }
    }
}