package src.personagens;

public class Curandeiro extends Personagem {

    public Curandeiro(String nome, int nivel, int saude, int mana) {
        super(nome, nivel, saude, mana);
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = 5; // Amostra do dano (amostrar serão revistas)
        alvo.setSaude(alvo.getSaude() - dano);
        System.out.println(getNome() + " atacou " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    @Override
    public void defender() {
        System.out.println(getNome() + " está defendendo.");
    }

    public void curar(Personagem alvo) {
        int cura = 15; // Amostra da cura
        alvo.setSaude(alvo.getSaude() + cura);
        System.out.println(getNome() + " curou " + alvo.getNome() + " restaurando " + cura + " de saúde.");
    }
 
}
