package src;
import java.awt.*;

public class Pontuacao extends Rectangle {

    static int LARGURA_JOGO;
    static int ALTURA_JOGO;
    int jogador1;
    int jogador2;

    Pontuacao(int larguraJogo, int alturaJogo) {
        Pontuacao.LARGURA_JOGO = larguraJogo;
        Pontuacao.ALTURA_JOGO = alturaJogo;
    }

    public void desenhar(Graphics g) {
        g.setColor(ColorPalette.PRIMARY_COLOR);
        g.setFont(new Font("HelveticaNeue", Font.PLAIN, 60));

        g.drawLine(LARGURA_JOGO / 2, 0, LARGURA_JOGO / 2, ALTURA_JOGO);

        g.drawString(String.valueOf(jogador1 / 10) + String.valueOf(jogador1 % 10), (LARGURA_JOGO / 2) - 85, 50);
        g.drawString(String.valueOf(jogador2 / 10) + String.valueOf(jogador2 % 10), (LARGURA_JOGO / 2) + 20, 50);
    }
}