package gamejava;

public class Inimigo {
    private String nome;
    private double vidaInimigo;
    private String padraoAtaque;
    private int dano;
    private double x, y;

    public Inimigo(String nome, int vidaInimigo, String padraoAtaque, int dano, double x, double y) {
        this.nome = nome;
        this.vidaInimigo = vidaInimigo;
        this.padraoAtaque = padraoAtaque;
        this.dano = dano;
        this.x = x;
        this.y = y;
    }

    public String getNome() {
        return nome;
    }

    public double getVidaInimigo() {
        return vidaInimigo;
    }

    public String getPadraoAtaque() {
        return padraoAtaque;
    }

    public int getDano() {
        return dano;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void atacar(Personagem alvo) {
        System.out.println(getNome() + " realiza um " + getPadraoAtaque() + " causando " + getDano() + " de dano!");
        System.out.println(this.getNome() + " ataca " + alvo.getNome());

        alvo.receberDano(getDano());

    }

    public void receberDano(double dano) {
        this.vidaInimigo = this.vidaInimigo - dano;
        if (this.vidaInimigo < 0) {
            this.vidaInimigo = 0;
            morrer();
        }
        System.out.println(this.nome + " recebeu " + dano + " de dano e agora tem " + this.vidaInimigo + " de vida.");
    }

    public void morrer(){
        System.out.println(this.nome + " morreu!");
    }
}
