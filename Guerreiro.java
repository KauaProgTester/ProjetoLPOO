package gamejava;

public class Guerreiro extends Personagem {
    
    private Espada espadaEquipada; 

    public Guerreiro(String nome, int vida, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura) {
        
        super(nome, vida, 15, classe, defesa, nivel, mana, inventario, armadura);        

    }

    public void equiparEspada(Espada espada) {
        this.espadaEquipada = espada;
        System.out.println(getNome() + " equipou " + espada.getNome() + ".");
    }

    public void desequiparEspada() {
        this.espadaEquipada = null;
        System.out.println(getNome() + " desequipou a espada.");
    }

    public void ataque(Personagem alvo) {
        if (espadaEquipada != null) {
            int danoTotal = calcularDano() + espadaEquipada.getAumentoDano();
            System.out.println(getNome() + " ataca " + alvo.getNome() + " com força usando " + espadaEquipada.getNome() + ". Dano: " + danoTotal);
            // logica pra dar o dano
        } else {
            System.out.println(getNome() + " ataca " + alvo.getNome() + " com força. Dano: " + calcularDano());
        }
    }

    private int calcularDano() {
        // logica pra calcular o dano baseado na forca
        return getForca();
    }

    public void usarHabilidade(Personagem alvo) {
        System.out.println(getNome() + " usa habilidade especial contra " + alvo.getNome());
        // logica de como funciona a  habilidade especial do guerreiro
    }

}
