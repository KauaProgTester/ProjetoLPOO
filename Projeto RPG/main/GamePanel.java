package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import corpo.Jogador;

public class GamePanel extends JPanel implements Runnable{
    // Screen Settings
    public final int originalTileSize = 16; // 16x16
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;


    KeyHandler keyH = new KeyHandler();

    Thread gameThread;
    Jogador jogador = new Jogador(this,keyH);

    // Posição inicial do jogador
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    //public void run() {

       // double drawInterval = 1000000000/FPS;
        //double nextDrawTime = System.nanoTime() + drawInterval;
    
       // while(gameThread != null){
            
        //    long currentTime = System.nanoTime();
        //    System.out.println("Tempo:"+currentTime);

         //   update();
            
         //   repaint();

          //  double remainingTime = nextDrawTime - System.nanoTime();
          //  remainingTime = remainingTime/1000000;
          //  if(remainingTime < 0){
          //      remainingTime = 0;
          //  }
          //  
          //  try {
           //     Thread.sleep((long)remainingTime);

           //     nextDrawTime += drawInterval;
           // } catch (InterruptedException e) {
                
           //     e.printStackTrace();
           // }

        //}
    //}
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;



        while (gameThread != null){
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;

            }
            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }

        while (gameThread != null) {
            update();
            repaint();
            
        }
    }
    public void update (){



    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        Jogador.draw(g2);

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();


    }
}
