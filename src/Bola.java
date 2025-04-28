package src;
import java.awt.*;
import java.util.*;

public class Bola extends Rectangle {

    Random aleatorio = new Random();
    int velocidadeX;
    int velocidadeY;
    int velocidadeInicial = 5;

    Bola(int x, int y, int largura, int altura) {
        super(x, y, largura, altura);
        parar();
    }

    public void parar(){
        velocidadeX = 0;
        velocidadeY = 0;
    }

    public void iniciarMovimento() {
        double angulo = Math.toRadians(aleatorio.nextInt(61) - 30);
        velocidadeX = (int) Math.round(Math.cos(angulo) * velocidadeInicial);
        velocidadeY = (int) Math.round(Math.sin(angulo) * velocidadeInicial);

        if (aleatorio.nextBoolean())
            velocidadeX *= -1;
        
        if (velocidadeX == 0)
            velocidadeX = aleatorio.nextBoolean() ? 1 : -1;
    }

    public void setDirecaoX(int direcaoX) {
        velocidadeX = direcaoX;
    }

    public void setDirecaoY(int direcaoY) {
        velocidadeY = direcaoY;
    }

    public void mover() {
        x += velocidadeX;
        y += velocidadeY;
    }

    public void desenhar(Graphics g) {
        g.setColor(ColorPalette.PRIMARY_COLOR);
        g.fillOval(x, y, height, width);
    }
}