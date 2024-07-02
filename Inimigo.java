package src.personagens;

public class Inimigo extends Personagem {
    public Inimigo(String nome, int vida, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura) {
        
        super(nome, vida, 1, classe, defesa, nivel, mana, inventario, armadura);        

    }

    public void atacar(Personagem alvo) {
        int dano = 10;
        alvo.setVida(alvo.getVida() - dano);
        System.out.println(getNome() + " atacou " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    public void defender() {
        System.out.println(getNome() + " se defendeu.");
    }
}

