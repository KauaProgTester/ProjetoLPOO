package corpo;

import java.awt.Graphics2D;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;
import main.GamePanel;

public class Jogador extends Corpo {

    GamePanel gp;
    KeyHandler keyH;

    public Jogador(GamePanel gp, KeyHandler keyH) {
        
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
    }

    public Jogador(GamePanel gp2, main.KeyHandler keyH2) {
    }
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
    }
    

    public void update (){
        

            if (keyH.upPressed == true){
                y -= speed;
            }
            else if (keyH.downPressed == true) {
                y += speed;
                
            }
            else if (keyH.leftPressed == true){
                x -= speed;
            }
            else if (keyH.rightPressed == true){
                x += speed;
            }
    

    }

    
    public void draw (Graphics2D){
        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

    }

	public static void draw(Graphics2D g2) {
	}
}
