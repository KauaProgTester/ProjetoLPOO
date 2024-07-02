package src.personagens;

public class Arqueiro extends Personagem {

    public Guerreiro(String nome, int vida, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura) {
        
        super(nome, vida, 8, classe, defesa, nivel, mana, inventario, armadura);        

    }

    public void ataque(Personagem alvo) {
        int dano = 8; // Exemplo de cálculo de dano
        alvo.setVida(alvo.getVida() - dano);
        System.out.println(getNome() + " disparou uma flecha em " + alvo.getNome() + " causando " + dano + " de dano.");
    }

    public void dispararFlechaEspecial(Personagem alvo) {
        int danoEspecial = 18; // Exemplo de cálculo de dano especial
        alvo.setVida(alvo.getVida() - danoEspecial);
        System.out.println(getNome() + " disparou uma flecha especial em " + alvo.getNome() + " causando " + danoEspecial + " de dano.");
    }
}

    

