package src.personagens;

public class Inimigo extends Personagem {
    public Inimigo(String nome, int nivel, int saude, int mana) {
        super(nome, nivel, saude, mana);
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = 10;
        alvo.setSaude(alvo.getSaude() - dano);
        System.out.println(getNome() + " atacou " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    @Override
    public void defender() {
        System.out.println(getNome() + " se defendeu.");
    }
}

