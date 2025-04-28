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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel credits = new JLabel("Créditos: Rauana e Bruna (⁠◍⁠•⁠ᴗ⁠•⁠◍⁠)");
        credits.setFont(new Font("HelveticaNeue", Font.PLAIN, 11));
        credits.setForeground(ColorPalette.PRIMARY_COLOR);
        credits.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 0));
        topPanel.add(credits, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
    }

    public void ganharFoco() {                     
        requestFocusInWindow();
    }
}