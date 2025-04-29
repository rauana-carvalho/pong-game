package src;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
public class Raquete extends ElementoJogo implements Movable {
    private final int id;
    private int yVel = 0, velocidade = 10;
    public Raquete(int x, int y, int w, int h, int id) {
        super(x, y, w, h);
        this.id = id;
    }
    public void keyPressed(KeyEvent e) {
        if (id == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W) yVel = -velocidade;
            if (e.getKeyCode() == KeyEvent.VK_S) yVel = velocidade;
        } else {
            if (e.getKeyCode() == KeyEvent.VK_UP) yVel = -velocidade;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) yVel = velocidade;
        }
    }
    public void keyReleased(KeyEvent e) {
        if ((id == 1 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S)) ||
            (id == 2 && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN))) {
            yVel = 0;
        }
    }
    @Override
    public void mover() {
        y = Math.max(0, Math.min(y + yVel, PainelJogo.ALTURA_JOGO - height));
    }
    @Override
    public void atualizar() {
    }
    @Override
    public void desenhar(Graphics g) {
        g.setColor(ColorPalette.PRIMARY_COLOR);
        g.fillRect(x, y, width, height);
    }
}
