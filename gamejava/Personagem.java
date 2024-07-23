package gamejava;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public abstract class Personagem {
    private String nome;
    private double vida;
    private int forca;
    private String classe;
    private int defesa;
    private int nivel;
    private int mana;
    private Inventario inventario;
    private Armadura armaduraEquipada;
    private double x, y; // Posições do personagem no mapa

    public Personagem(String nome, int vida, int forca, String classe, int defesa, 
                      int nivel, int mana, Inventario inventario, Armadura armadura, double x, double y) {
        this.nome = nome;
        this.vida = vida;
        this.forca = forca;
        this.classe = classe;
        this.defesa = defesa;
        this.nivel = nivel;
        this.mana = mana;
        this.inventario = inventario;
        this.armaduraEquipada = armadura;
        this.x = x;
        this.y = y;
    }

    public abstract void atacar(Inimigo alvo);

    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
            morrer();
        }
        System.out.println(this.nome + " recebeu " + dano + " de dano e agora tem " + this.vida + " de vida.");
    }

    public void morrer() {
        System.out.println(this.nome + " morreu!");
    }

    public abstract void usarHabilidade(Inimigo alvo);

    public void ganharExperiencia(int quantidade) {
        this.nivel += quantidade;
    }

    public void adicionarItemAoInventario(Item item) {
        inventario.adicionarItem(item);
    }

    public String getNome() {
        return this.nome;
    }

    public double getVida() {
        return this.vida;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public int getForca() {
        return this.forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public String getClasse() {
        return this.classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getDefesa() {
        return this.defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
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

    public void mover(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
