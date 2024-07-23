package gamejava;

public class Guerreiro extends Personagem {
    
    private Espada espadaEquipada; 

    public Guerreiro(String nome, Inventario inventario, Armadura armadura, double x, double y) {
        super(nome, 100, 15, "Guerreiro", 5, 1, 100, inventario, armadura, x, y);
    }

    public void equiparEspada(Espada espada) {
        this.espadaEquipada = espada;
        System.out.println(getNome() + " equipou " + espada.getNome() + ".");
    }

    public void desequiparEspada() {
        this.espadaEquipada = null;
        System.out.println(getNome() + " desequipou a espada.");
    }

    public void atacar(Inimigo alvo) {
        if (espadaEquipada != null) {
            int danoTotal = getForca() + espadaEquipada.getAumentoDano();
            System.out.println(getNome() + " ataca " + alvo.getNome() + " com força usando " + espadaEquipada.getNome() + ". Dano: " + danoTotal);
            alvo.receberDano(danoTotal);
        } else {
            System.out.println(getNome() + " ataca " + alvo.getNome() + " com força. Dano: " + getForca());
            alvo.receberDano(getForca());
        }
    }

    public void usarHabilidade(Inimigo alvo) {
        System.out.println(getNome() + " usa habilidade especial contra " + alvo.getNome() +", realizando um poderoso ataque");
        if (espadaEquipada != null) {
            double danoTotal = ((getForca() + espadaEquipada.getAumentoDano())*1.5);
            alvo.receberDano(danoTotal);
        } else {
            double danoTotal = (getForca()*1.5);
            alvo.receberDano(danoTotal);
        }
    }

}
