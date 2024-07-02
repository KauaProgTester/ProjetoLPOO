package src.personagens;

public class Mago extends Personagem {

    public Mago(String nome, int nivel, int saude, int mana) {
        super(nome, nivel, saude, mana);
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = 12; // Exemplo de dano
        alvo.setSaude(alvo.getSaude() - dano);
        System.out.println(getNome() + " lançou um feitiço em " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    @Override
    public void defender() {
        System.out.println(getNome() + " está defendendo.");
    }

    public void usarMagia(Personagem alvo) {
        int danoMagico = 20; // Exemplo de cálculo de dano mágico
        alvo.setSaude(alvo.getSaude() - danoMagico);
        System.out.println(getNome() + " usou magia em " + alvo.getNome() + " causando " + danoMagico + " de dano.");
    }
}

