package gamejava;

public class Armadura extends Item{

    private int aumentoDefesa; 

    public Armadura(String nome, String descricao, int aumentoDefesa) {
            super(nome, descricao);
            this.aumentoDefesa = aumentoDefesa;
    }

    public int getAumentoDefesa() {
        return aumentoDefesa;
    }

    public void setAumentoDefesa(int aumentoDefesa) {
        this.aumentoDefesa = aumentoDefesa;
    }
    
}
