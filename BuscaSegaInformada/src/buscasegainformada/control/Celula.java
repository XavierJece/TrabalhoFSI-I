/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscasegainformada.control;

import java.util.Random;

/**
 *
 * @author Jece Xavier
 */
public class Celula {
    //Atributos
    private int custo;
    private int powerUp;
    private int posicaoX;
    private int posicaoY;
    private int posicaoArray;
    private int heuristica;
    private boolean inicio;
    private boolean  fim;
    
    private Celula pai;

    public Celula getPai() {
        return pai;
    }

    public void setPai(Celula pai) {
        this.pai = pai;
    }

    
    
    //Contrutor

    public Celula(int posicao, int posicaoX, int posicaoY, int objetivoX, int objetivoY, 
            int incioX, int inicioY) {
        
        this.posicaoArray = posicao;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        
        this.setFim(objetivoX, objetivoY);
        this.setInicio(incioX, inicioY);
        
        this.definirCusto();
        this.definirPowerUp();
        this.definirHeuristica(objetivoX, objetivoY);
        
        this.setPai(null);
    }
    
    
    //Minhas funções
    private void definirCusto(){
        
        if(this.isFim() || this.isInicio()){
            this.setCusto(0);
        }else{

            Random rand = new Random();

            int opcao = rand.nextInt(4);

            /*
                0 => Solido
                1 => Arenoso
                2 => Rochoso
                3 => Pântano
            */

            if(opcao == 0){
                this.setCusto(1);
            }else if(opcao == 1){
                this.setCusto(4);
            }else if(opcao == 2){
                this.setCusto(10);
            }else{
                this.setCusto(20);
            }
        }
    }
    
    private void definirPowerUp(){
        
        if(this.isFim() || this.isInicio()){
            this.setPowerUp(0);
        }else{
        
            Random rand = new Random();

            int opcao = rand.nextInt(3);

            /*
                0 => Nada
                1 => Bonus
                2 => Prejuiso
            */

            if(opcao == 0){
                this.setPowerUp(1);
            }else if(opcao == 1){
                this.setPowerUp(0);
            }else{
                this.setPowerUp(5);
            }
        }
    }
    
    private void definirHeuristica(int obejtivoX, int obejtivoY){
        int distanciaX = this.getPosicaoX() - obejtivoX;
        int distanciaY = this.getPosicaoY() - obejtivoY;
        
        this.setHeuristica((int) ((Math.pow(distanciaX,2)) + (Math.pow(distanciaY,2))));
        
//        System.out.println(this.posicaoX + " x " + this.posicaoY + " = " + this.heuristica);
    }
    
    public int custoEstrela(){
        return (this.heuristica + this.custo + this.powerUp);
    }
    
    //Gets end Sets

    public int getCusto() {
        return custo;
    }

    private void setCusto(int custo) {
        this.custo = custo;
    }

    public int getPowerUp() {
        return powerUp;
    }

    private void setPowerUp(int powerUp) {
        this.powerUp = powerUp;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    private void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    private void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;
    }
    
    public int getPosicaoArray() {
        return posicaoArray;
    }

    public int getHeuristica() {
        return heuristica;
    }

    private void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public boolean isInicio() {
        return inicio;
    }

    private void setInicio(int x, int y) {
        
        if(
            ((x == this.posicaoX) && (y == this.posicaoY))
        ){
            this.inicio = true;
        }else{
            this.inicio = false;
        }
        
        
    }

    public boolean isFim() {
        return fim;
    }

    private void setFim(int x, int y) {
        
        if(
            ((x == this.posicaoX) && (y == this.posicaoY))
        ){
            this.fim = true;
        }else{
            this.fim = false;
        }
        
        
    }
    
}
