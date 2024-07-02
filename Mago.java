package src.personagens;

public class Mago extends Personagem {

    public Mago(String nome, int vida, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura) {
        
        super(nome, vida, 8, classe, defesa, nivel, mana, inventario, armadura);        

    }

    public void atacar(Personagem alvo) {
        int dano = 12; 
        alvo.setVida(alvo.getVida() - dano);
        System.out.println(getNome() + " lançou um feitiço em " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    public void defender() {
        System.out.println(getNome() + " está defendendo.");
    }

    public void usarMagia(Personagem alvo) {
        int danoMagico = 20; //cálculo de dano mágico
        alvo.setVida(alvo.getVida() - danoMagico);
        System.out.println(getNome() + " usou magia em " + alvo.getNome() + " causando " + danoMagico + " de dano.");
    }
}

