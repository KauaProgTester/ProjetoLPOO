package src.personagens;

public abstract class Personagem {
    private String nome;
    private int nivel;
    private int saude;
    private int mana;
    private int experiencia;

    public Personagem(String nome, int nivel, int saude, int mana) {
        this.nome = nome;
        this.nivel = nivel;
        this.saude = saude;
        this.mana = mana;
        this.experiencia = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getSaude() {
        return saude;
    }

    public void setSaude(int saude) {
        if (saude >= 0) {
            this.saude = saude;
        } else {
            throw new IllegalArgumentException("Saúde não pode ser negativa");
        }
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        if (mana >= 0) {
            this.mana = mana;
        } else {
            throw new IllegalArgumentException("Mana não pode ser negativa");
        }
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void ganharExperiencia(int experiencia) {
        this.experiencia += experiencia;
        if (this.experiencia >= 100) {
            nivelUp();
            this.experiencia -= 100;
        }
    }

    private void nivelUp() {
        nivel++;
        saude += 10;
        mana += 5;
        System.out.println(nome + " subiu para o nível " + nivel + "!");
    }

    public abstract void atacar(Personagem alvo);
    public abstract void defender();
}
