package gamejava;

public class Espada extends Item {

    private int aumentoDano;

    public Espada(String nome, String descricao, int aumentoDano) {
        super(nome, descricao);
        this.aumentoDano = aumentoDano;
    }

    public int getAumentoDano() {
        return aumentoDano;
    }

    public void setAumentoDano(int aumentoDano) {
        this.aumentoDano = aumentoDano;
    }
}
