package src;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class ElementoJogo extends Rectangle {
    public ElementoJogo(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public abstract void atualizar();
    public abstract void desenhar(Graphics g);
}