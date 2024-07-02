package src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    public BufferedImage up, down, left, right;
    public String direction;

    public void update() {}

    public void draw(Graphics g) {}
}
