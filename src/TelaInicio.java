package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TelaInicio extends JPanel {

    private final JanelaJogo janela;          

    public TelaInicio(JanelaJogo janelaJogo) {    
        this.janela = janelaJogo;                 

        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND_COLOR);

        JLabel titulo = new JLabel("Jogo Pong", SwingConstants.CENTER);
        titulo.setFont(new Font("HelveticaNeue", Font.BOLD, 48));
        titulo.setForeground(ColorPalette.PRIMARY_COLOR);
        add(titulo, BorderLayout.CENTER);

        JLabel dica = new JLabel("Pressione ENTER para começar",
                                 SwingConstants.CENTER);
        dica.setFont(new Font("HelveticaNeue", Font.PLAIN, 18));
        dica.setForeground(ColorPalette.PRIMARY_COLOR);
        add(dica, BorderLayout.SOUTH);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    janela.iniciarPartida();
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            }
        });
    }

    public void ganharFoco() {                     
        requestFocusInWindow();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ColorPalette.PRIMARY_COLOR);
        g.setFont(new Font("HelveticaNeue", Font.BOLD, 10));
        String texto = "Feito por: Rauana e Bruna";
        FontMetrics metrics = g.getFontMetrics();
        int x = 10; // Margem à esquerda
        int y = metrics.getAscent() + 10; // Pequena margem do topo
        g.drawString(texto, x, y);
    }
}