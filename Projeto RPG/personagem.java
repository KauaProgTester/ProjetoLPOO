package gamejava;

import java.util.ArrayList;
import java.util.List;

public class Personagem {
    
    private String nome;
    private int vida;
    private int ataque;
    private String classe;
    private int defesa;
    private int nivel;
    private int mana;
    private Inventario inventario;
    private Armadura armadura;
    private Arma arma;

    public Personagem(String nome, int vida, int ataque, String classe, int defesa, 
    int nivel, int mana, Inventario inventario, Armadura armadura, Arma arma){

        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.classe = classe;
        this.defesa = defesa;
        this.nivel = nivel;
        this.mana = mana;
        this.inventario = inventario;
        this.armadura = armadura;
        this.arma = arma;

    }

    public int getVida (){
        return this.vida;
    }
    
    public void setVida (int vida){
        this.vida = vida;
        }


    public int getAtaque (){
        return this.ataque;
    }

    public void setAtaque (int ataque){
        this.ataque = ataque;
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
