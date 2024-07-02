package src.personagens;

public class Curandeiro extends Personagem {

    public Curandeiro(String nome, int vida, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura) {
        
        super(nome, vida, 2, classe, defesa, nivel, mana, inventario, armadura);        

    }

    public void atacar(Personagem alvo) {
        int dano = 5; // Amostra do dano (amostrar serão revistas)
        alvo.setVida(alvo.getVida() - dano);
        System.out.println(getNome() + " atacou " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    @Override
    public void defender() {
        System.out.println(getNome() + " está defendendo.");
    }

    public void curar(Personagem alvo) {
        int cura = 15; // Amostra da cura
        alvo.setVida(alvo.getVida() + cura);
        System.out.println(getNome() + " curou " + alvo.getNome() + " restaurando " + cura + " de Vida.");
    }
 
}
