package src;

import java.awt.*;
import javax.swing.*;

public class Pontuacao {

    private static final int OFFSET_Y_NOME   = 35; 
    private static final int OFFSET_Y_SCORE  = 80;
    private static final Font FONT_NOME   = new Font("HelveticaNeue", Font.PLAIN, 14);
    private static final Font FONT_SCORE  = new Font("HelveticaNeue", Font.PLAIN, 50);
    private static final Color COR_TEXTO  = ColorPalette.PRIMARY_COLOR;

    private final int largura;
    private final int altura;

    private String nomeJogador1;
    private String nomeJogador2;
    private int    placar1;
    private int    placar2;

    private final JLabel nomeLabel1;
    private final JLabel nomeLabel2;
    private final JLabel scoreLabel1;
    private final JLabel scoreLabel2;

    public Pontuacao(int largura, int altura, String n1, String n2) {
        this.largura      = largura;
        this.altura       = altura;
        this.nomeJogador1 = n1;
        this.nomeJogador2 = n2;

        nomeLabel1  = new JLabel(n1, SwingConstants.CENTER);
        nomeLabel1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        nomeLabel2 = new JLabel(n2, SwingConstants.CENTER);
        nomeLabel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        scoreLabel1 = new JLabel("0", SwingConstants.CENTER);
        scoreLabel2 = new JLabel("0", SwingConstants.CENTER);

        nomeLabel1.setFont(FONT_NOME);
        nomeLabel2.setFont(FONT_NOME);
        scoreLabel1.setFont(FONT_SCORE);
        scoreLabel2.setFont(FONT_SCORE);

        nomeLabel1.setForeground(COR_TEXTO);
        nomeLabel2.setForeground(COR_TEXTO);
        scoreLabel1.setForeground(COR_TEXTO);
        scoreLabel2.setForeground(COR_TEXTO);

        nomeLabel1.setBounds((largura / 2) - 100, OFFSET_Y_NOME - 12, 80, 20);
        nomeLabel2.setBounds((largura / 2) + 20,  OFFSET_Y_NOME - 12, 80, 20);
        scoreLabel1.setBounds((largura / 2) - 85, OFFSET_Y_SCORE - 45, 60, 60);
        scoreLabel2.setBounds((largura / 2) + 20, OFFSET_Y_SCORE - 45, 60, 60);
    }

    public void setNomes(String n1, String n2) {
        if (n1 != null && !n1.isBlank()) {
            nomeJogador1 = n1;
            nomeLabel1.setText(n1);
        }
        if (n2 != null && !n2.isBlank()) {
            nomeJogador2 = n2;
            nomeLabel2.setText(n2);
        }
    }

    public void pontoJogador1() {
        placar1++;
        scoreLabel1.setText(String.valueOf(placar1));
    }

    public void pontoJogador2() {
        placar2++;
        scoreLabel2.setText(String.valueOf(placar2));
    }

    public void reset() {
        placar1 = placar2 = 0;
        scoreLabel1.setText("0");
        scoreLabel2.setText("0");
    }

    public boolean fimDeJogo(int limite) { return placar1 == limite || placar2 == limite; }

    public String getVencedor() {
        if (placar1 > placar2) return nomeJogador1;
        if (placar2 > placar1) return nomeJogador2;
        return "Empate";
    }

    public void desenhar(Graphics g) {
        g.setColor(COR_TEXTO);
        g.drawLine(largura / 2, 0, largura / 2, altura);
    }

    public JLabel getNomeLabel1()  { return nomeLabel1;  }
    public JLabel getNomeLabel2()  { return nomeLabel2;  }
    public JLabel getScoreLabel1() { return scoreLabel1; }
    public JLabel getScoreLabel2() { return scoreLabel2; }
}