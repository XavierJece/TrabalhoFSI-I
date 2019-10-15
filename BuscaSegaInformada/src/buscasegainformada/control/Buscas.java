/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscasegainformada.control;

import java.util.ArrayList;

/**
 *
 * @author Jece Xavier
 */
public class Buscas {
    //Atributos
    ArrayList<Celula> caminho = new ArrayList<>();
    
    //Contrutor
    
    //Minhas funções
    public ArrayList<Celula> buscaEstrela(ArrayList<Celula> lista, int ordemTabuleiro, int posicao){
        Celula atual = lista.get(posicao);
        System.out.println("Posição Atual: " + atual.getPosicaoArray());
        int custos[] = new int[4];
        
//        Verificando se chegamos no fim
        if(atual.isFim()){
            return this.caminho;
        }else{
//            Criando visinho
            Celula cima, baixo, esquerda, direita;

//            add a posição atual
            this.caminho.add(atual);
            
//            Verificando se existe Visinho cima
            cima = ((((posicao - ordemTabuleiro) < 0)) ? null : lista.get((posicao-ordemTabuleiro)));
            
//            Verificando se existe Visinho baixo
            baixo = ((((posicao + ordemTabuleiro) >= Math.pow(ordemTabuleiro, 2))) ? null : lista.get((posicao+ordemTabuleiro)));

//            Verificando se existe Visinho esquerda
            esquerda = ((((posicao % 10) == 0)) ? null : lista.get((posicao-1)));

            
//            Verificando se existe Visinho direita
            direita = (((((posicao + 1) % 10) == 0)) ? null : lista.get((posicao+1)));
            
            if(cima == null){
               custos[0] = 1000;
            }else{
                 custos[0] = cima.custoEstrela();
            }
            
            if(baixo == null){
                 custos[1] = 1000;
            }else{
               custos[1] = baixo.custoEstrela();
            }
            
            if(esquerda == null){
                custos[2] = 1000;
            }else{
                custos[2] = esquerda.custoEstrela();
            }
            
            if(direita == null){
                custos[3] = 1000;
            }else{
                custos[3] = direita.custoEstrela();
            }
            
            
            if(
                (custos[0] < custos[1]) &&
                (custos[0] < custos[2]) &&
                (custos[0] < custos[3])
            ){
                buscaEstrela(lista, ordemTabuleiro, cima.getPosicaoArray());
            }else if(
                (custos[1] < custos[0]) &&
                (custos[1] < custos[2]) &&
                (custos[1] < custos[3])
            ){
                buscaEstrela(lista, ordemTabuleiro, baixo.getPosicaoArray());
            }else if(
                (custos[2] < custos[0]) &&
                (custos[2] < custos[1]) &&
                (custos[2] < custos[3])
            ){
                buscaEstrela(lista, ordemTabuleiro, esquerda.getPosicaoArray());
            }else{
                buscaEstrela(lista, ordemTabuleiro, direita.getPosicaoArray());
            }
            
        }
        
        
        
        
        
        return null;
        
    }
    
    
    //Gets end Sets
}
