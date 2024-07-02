package gamejava;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    
    private String nome;
    private int vida;
    private int forca;
    private String classe;
    private int defesa;
    private int nivel;
    private int mana;
    private Inventario inventario;
    private Armadura armaduraEquipada;

    public Personagem(String nome, int vida, int forca, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura){

        this.nome = nome;
        this.vida = vida;
        this.forca = forca;
        this.classe = classe;
        this.defesa = defesa;
        this.nivel = nivel;
        this.mana = mana;
        this.inventario = inventario;
        this.armaduraEquipada = armadura;

    }

    public abstract void ataque(Personagem alvo);

    public abstract void usarHabilidade(Personagem alvo);

    public void ganharExperiencia(int quantidade) {
        this.nivel = this.nivel + quantidade;
    }

    public void adicionarItemAoInventario(Item item) {
        inventario.adicionarItem(item);
    }

    public String getNome(){
        return this.nome;
    }
    
    public int getVida (){
        return this.vida;
    }
    
    public void setVida (int vida){
        this.vida = vida;
        }


    public int getForca (){
        return this.forca;
    }

    public void setForca (int ataque){
        this.forca = ataque;
        }

        
    public String getClasse (){
        return this.classe;
    }

    public void setClasse (String classe){
        this.classe = classe;
        }


    public int getDefesa (){
        return this.defesa;
    }

    public void setDefesa (int defesa){
        this.defesa = defesa;
        }


    public int getNivel (){
        return this.nivel;
    }

    public void setNivel (int nivel){
        this.nivel = nivel;
        }


    public int getMana (){
        return this.mana;
    }

    public void setMana (int mana){
        this.mana = mana;
        }

    















}
