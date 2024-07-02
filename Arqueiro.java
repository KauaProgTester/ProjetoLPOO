package src.personagens;

public class Arqueiro extends Personagem {

    public Arqueiro(String nome, int nivel, int saude, int mana) {
        super(nome, nivel, saude, mana);
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = 8; // Exemplo de cálculo de dano
        alvo.setSaude(alvo.getSaude() - dano);
        System.out.println(getNome() + " disparou uma flecha em " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    @Override
    public void defender() {
        System.out.println(getNome() + " está defendendo.");
    }

    public void dispararFlechaEspecial(Personagem alvo) {
        int danoEspecial = 18; // Exemplo de cálculo de dano especial
        alvo.setSaude(alvo.getSaude() - danoEspecial);
        System.out.println(getNome() + " disparou uma flecha especial em " + alvo.getNome() + " causando " + danoEspecial + " de dano.");
    }
}

    

