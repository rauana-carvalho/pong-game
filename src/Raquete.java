package src;
import java.awt.*;
import java.awt.event.*;

public class Raquete extends Rectangle {

    int id;
    int yVelocity;
    int velocidade = 10;

    Raquete(int x, int y, int largura, int altura, int id) {
        super(x, y, largura, altura);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setDirecaoY(-velocidade);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setDirecaoY(velocidade);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setDirecaoY(-velocidade);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setDirecaoY(velocidade);
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                    setDirecaoY(0);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setDirecaoY(0);
                }
                break;
        }
    }

    public void setDirecaoY(int direcaoY) {
        yVelocity = direcaoY;
    }

    public void mover() {
        y = y + yVelocity;
    }

    public void desenhar(Graphics g) {
        if (id == 1)
            g.setColor(ColorPalette.PRIMARY_COLOR);
        else
            g.setColor(ColorPalette.PRIMARY_COLOR);
        g.fillRect(x, y, width, height);
    }
}