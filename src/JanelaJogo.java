package src;import java.awt.*;

import javax.sound.sampled.Clip;
import javax.swing.*;

public class JanelaJogo extends JFrame {

    private final CardLayout telas = new CardLayout();   
    private final JPanel container = new JPanel(telas);  
    private final TelaInicio telaInicio;                 
    private final PainelJogo painel;  
    private Clip startClip;
    private javax.swing.Timer startTimer;  
    
    public PainelJogo getPainel(){
        return painel;
    }

    JanelaJogo() {
        telaInicio = new TelaInicio(this);               
        painel      = new PainelJogo(this);                  

        container.add(telaInicio, "inicio");             
        container.add(painel,      "jogo");              
        telas.show(container, "inicio");                 

        this.add(container);                             

        this.setTitle("Jogo Pong (POO 2024.2)");
        this.setResizable(false);
        this.setBackground(ColorPalette.BACKGROUND_COLOR);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        telaInicio.ganharFoco();                         
    }

    public void iniciarJogo() {                          
        telas.show(container, "jogo");
        painel.requestFocusInWindow();                   
    }

    public void iniciarPartida(){
        startClip = Som.tocarClip("/sons/start.wav");
        int delay = (int)(startClip.getMicrosecondLength() / 1000);

        // 2) guardamos o Timer
        startTimer = new javax.swing.Timer(delay, event -> {
            event.hashCode();
            getPainel().iniciarMovimento();
        });
        startTimer.setRepeats(false);
        startTimer.start();

        iniciarJogo();
    }
    
    public void pauseStartSequence() {
        if (startClip != null && startClip.isRunning()) {
            startClip.stop();
        }
        if (startTimer != null && startTimer.isRunning()) {
            startTimer.stop();
        }
    }

    public void resumeStartSequence() {
        if (startClip != null) {
            startClip.start();
        }
        if (startTimer != null) {
            startTimer.start();
        }
    }
}