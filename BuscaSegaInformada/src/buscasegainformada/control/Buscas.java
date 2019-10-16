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
        
        if(caminho.contains(lista.get(posicao))){
            return null;
        }
        
        Celula atual = lista.get(posicao);
//        System.out.println("Posição Atual: " + atual.getPosicaoArray());;
        
        int custoCima, custoBaixo, custoEsquerda, custoDireita;
        
//        Verificando se chegamos no fim
        if(atual.isFim()){
//            System.err.println("*************ENCONTROU****************");
            return null;
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
               custoCima = 1000;
            }else{
                custoCima = cima.custoEstrela();
            }
            
            if(baixo == null){
                 custoBaixo = 1000;
            }else{
               custoBaixo = baixo.custoEstrela();
            }
            
            if(esquerda == null){
                custoEsquerda = 1000;
            }else{
                custoEsquerda = esquerda.custoEstrela();
            }
            
            if(direita == null){
                custoDireita = 1000;
            }else{
                custoDireita = direita.custoEstrela();
            }
            
//            System.out.println("Posição Atual: " + atual.getPosicaoArray() + 
//                    " Cima: " + custoCima + 
//                    " Baixo: " + custoBaixo + 
//                    " Esquerda: " + custoEsquerda + 
//                    " Direita: " + custoDireita);
            
            if((custoCima < custoBaixo) && (custoCima < custoEsquerda) && (custoCima < custoDireita)){
                
//                System.out.println("Indo para cima => Posicao: " + cima.getPosicaoArray());
                buscaEstrela(lista, ordemTabuleiro, cima.getPosicaoArray());
                
            }else if((custoBaixo < custoCima) && (custoBaixo < custoEsquerda) && (custoBaixo < custoDireita)){
                
//                System.out.println("Indo para baixoo => Posicao: " + baixo.getPosicaoArray());
                buscaEstrela(lista, ordemTabuleiro, baixo.getPosicaoArray());
                
            }else if((custoEsquerda < custoCima) && (custoEsquerda < custoBaixo) && (custoEsquerda < custoDireita)){
                
//                System.out.println("Indo para esquerda => Posicao: " + esquerda.getPosicaoArray());
                buscaEstrela(lista, ordemTabuleiro, esquerda.getPosicaoArray());
                
            }else{
//                System.out.println("Indo para direita => Posicao: " + direita.getPosicaoArray());
                buscaEstrela(lista, ordemTabuleiro, direita.getPosicaoArray());
            }
//            *******************************************************
            
        }
        
        
        
        
        
        return this.caminho;
        
    }
    
    
    //Gets end Sets
}
