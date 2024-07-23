package gamejava;

public class CaixaDeColisao {
    double x, y, width, height;

    public CaixaDeColisao(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(CaixaDeColisao outra) {
        return (this.x < outra.x + outra.width &&
                this.x + this.width > outra.x &&
                this.y < outra.y + outra.height &&
                this.y + this.height > outra.y);
    }
}
