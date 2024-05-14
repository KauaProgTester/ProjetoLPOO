package corpo;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;
import main.GamePanel;

public class Jogador extends Corpo {

    GamePanel gp;
    KeyHandler keyH;

    public Jogador(GamePanel gp, KeyHandler keyH) {
        
        this.gp = gp;
        this.keyH = keyH;
    }

    public Jogador(GamePanel gp2, main.KeyHandler keyH2) {
    }
}
