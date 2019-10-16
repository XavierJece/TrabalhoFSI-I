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
    ArrayList<Celula> caminhoFinal = new ArrayList<>();
    ArrayList<Celula> celulasExpandidas = new ArrayList<>();
    ArrayList<Celula> celulasVisidadas = new ArrayList<>();
    
    //Contrutor
    
    //Minhas funções
    public ArrayList<Celula> buscaEstrela(ArrayList<Celula> lista, int ordemTabuleiro, int posicao){
        
//        if(caminho.contains(lista.get(posicao))){
//            return null;
//        }
        
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
    
    
    public ArrayList<Celula> buscaEstrela2(ArrayList<Celula> lista, int ordemTabuleiro, int posicao, Celula pai){
        
//        No atual
        Celula atual = lista.get(posicao);
        atual.setPai(pai);
        
//        Add posição no caminho
        caminho.add(atual);
        
//       Verificando se chegou ao fim
        if (atual.isFim()) {
            return null;
        }
        
//        Espandindo os visinhos
        
//      Verificando se existe Visinho cima
        if((posicao - ordemTabuleiro) >= 0){
            if (!celulasVisidadas.contains(lista.get((posicao-ordemTabuleiro)))) {
                celulasExpandidas.add(lista.get((posicao-ordemTabuleiro)));
            }
        }
        
//      Verificando se existe Visinho baixo     
        if ((posicao + ordemTabuleiro) < Math.pow(ordemTabuleiro, 2)) {
            if (!celulasVisidadas.contains(lista.get((posicao+ordemTabuleiro)))) {
                celulasExpandidas.add(lista.get((posicao+ordemTabuleiro)));
            }
        }
        
//      Verificando se existe Visinho esquerda
        if ((posicao % 10) != 0) {
            if (!celulasVisidadas.contains(lista.get((posicao-1)))) {
               celulasExpandidas.add(lista.get((posicao-1))); 
            }
            
        }


//      Verificando se existe Visinho direita
        if (((posicao + 1) % 10) != 0) {
            if (!celulasVisidadas.contains(lista.get((posicao+1)))) {
                celulasExpandidas.add(lista.get((posicao+1)));
            }
            
        }

//        Variavel para quardar o menor custo
        int posicaoMenorCusto = celulasExpandidas.get(posicaoMenorCusto()).getPosicaoArray();
        celulasExpandidas.remove(lista.get(posicaoMenorCusto));
        
        celulasVisidadas.add(lista.get(posicaoMenorCusto));
        buscaEstrela2(lista, ordemTabuleiro, posicaoMenorCusto, atual);
        
        if(posicao == 0){
            
            for (int i = 0; i < caminho.size(); i++) {
                System.out.println("Caminho " + (i+1) + "ª => " + caminho.get(i).getPosicaoArray());
            }
            System.out.println("");
            for (int i = 0; i < celulasVisidadas.size(); i++) {
                System.out.println("Visitada " + (i+1) + "ª => " + celulasVisidadas.get(i).getPosicaoArray());
            }
        }
        
        return this.caminho;
    }
    
    private int posicaoMenorCusto(){
        
        //        Variavel para quardar o menor custo
        int posicaoMenorCusto = 0;
        
        for (int i = 1; i < celulasExpandidas.size(); i++) {
            posicaoMenorCusto = ((celulasExpandidas.get(i).custoEstrela() < 
                    celulasExpandidas.get(posicaoMenorCusto).custoEstrela()) ? 
                    i : posicaoMenorCusto);
        }    
        
        return posicaoMenorCusto;
    }
    
    private void caminhoFinal(Celula celula, int ordem){
        
        if(celula.getPai() == null){
            caminhoFinal.add(celula);
            return;
        }
        
        //VerificarVisinho
        if(!
            (((celula.getPosicaoArray() - 1) != celula.getPai().getPosicaoArray()) &&
            ((celula.getPosicaoArray() + 1) != celula.getPai().getPosicaoArray()) &&
            ((celula.getPosicaoArray() - ordem) != celula.getPai().getPosicaoArray()) &&
            ((celula.getPosicaoArray() + ordem) != celula.getPai().getPosicaoArray()))
        ){
           
           caminhoFinal(celula.getPai(), ordem);
        }else{
            caminhoFinal(celula.getPai().getPai(), ordem);
        }
        
        caminhoFinal.add(celula);
        
        
    }
    
    //Gets end Sets

    public ArrayList<Celula> getCaminhoFinal(Celula celula, int ordem) {
        
        caminhoFinal(celula, ordem);
        
        return caminhoFinal;
    }
    
    
}
