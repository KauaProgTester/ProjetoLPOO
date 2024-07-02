package gamejava;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public void removerItem(Item item) {
        itens.remove(item);
    }

    public List<Item> getItens() {
        return itens;
    }
//metodo arcaico de listar itens, que vamos implementar na interface gráfica: 
    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("Inventário vazio.");
        } else {
            System.out.println("Itens no inventário:");
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + ". " + itens.get(i).getNome() + " - " + itens.get(i).getDescricao());
            }
        }
    }

    public Item getItem(String nomeItem) {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nomeItem)) {
                return item;
            }
        }
        return null;
    }
}

