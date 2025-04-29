package src;

import java.awt.Graphics;
import java.util.Random;

public class Bola extends ElementoJogo implements Movable {
    private final Random rand = new Random();
    private int velocidadeX, velocidadeY, velInicial = 5;

    public int getVelocidadeX() { return velocidadeX; }
    public int getVelocidadeY() { return velocidadeY; }
    public void setVelocidadeX(int vx) { velocidadeX = vx; }
    public void setVelocidadeY(int vy) { velocidadeY = vy; }

    public Bola(int x, int y, int diam) {
        super(x, y, diam, diam);
        parar();
    }

    public void parar() {
        velocidadeX = velocidadeY = 0;
    }

    public void iniciarMovimento() {
        double ang = Math.toRadians(rand.nextInt(61) - 30);
        velocidadeX = (int)Math.round(Math.cos(ang) * velInicial) * (rand.nextBoolean() ? 1 : -1);
        velocidadeY = (int)Math.round(Math.sin(ang) * velInicial);
        if (velocidadeX == 0) velocidadeX = rand.nextBoolean() ? 1 : -1;
    }

    @Override
    public void mover() {
        x += velocidadeX;
        y += velocidadeY;
    }

    @Override
    public void atualizar() {
    }

    @Override
    public void desenhar(Graphics g) {
        g.setColor(ColorPalette.PRIMARY_COLOR);
        g.fillOval(x, y, width, height);
    }
}