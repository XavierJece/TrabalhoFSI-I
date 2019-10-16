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
    ArrayList<Celula> caminho;
    ArrayList<Celula> caminhoFinal;
    ArrayList<Celula> celulasExpandidas;
    ArrayList<Celula> celulasVisidadas;
    ArrayList<Celula> listaDeExpancao;
    
    //Contrutor
    public Buscas(){    
        caminho = new ArrayList<>();
        caminhoFinal = new ArrayList<>();
        celulasExpandidas = new ArrayList<>();
        celulasVisidadas = new ArrayList<>();
        listaDeExpancao = new ArrayList<>();
    }

    //Minhas funções
    private ArrayList<Celula> buscaEstrela(ArrayList<Celula> lista, int ordemTabuleiro, int posicao) {
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
    
    /**
     * Função que realmente faz a busca estrela
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param ordemTabuleiro A dimenção do tabuleiro
     * @param posicao Posição onde a celula atual se encontra na lista (Grafo)
     */
    private void estrela(ArrayList<Celula> lista, int ordemTabuleiro, int posicao){
        
//        No atual
        Celula atual = lista.get(posicao);
        
//        Add posição no caminho
        caminho.add(atual);
        
//       Verificando se chegou ao fim
        if (atual.isFim()) {
            return;
        }
        
//        Espandindo os visinhos
        this.expandirVizinhos(lista, ordemTabuleiro, posicao);
        

//        Variavel para quardar o menor custo
        int posicaoMenorCusto = celulasExpandidas.get(posicaoMenorCusto()).getPosicaoArray();
        celulasExpandidas.remove(lista.get(posicaoMenorCusto));
        
//        Add a celula que será vizitada na lista de visitadas
        celulasVisidadas.add(lista.get(posicaoMenorCusto));
        
//        Verifica caminho invalido
        this.veridicaCaminhoInvalido(lista, lista.get(posicaoMenorCusto), atual);
        
//        Aplica a recursividade
        estrela(lista, ordemTabuleiro, posicaoMenorCusto);
    }
    
    /**
     * Função para saber qual das celulas expandidas tem o menor custo
     * @return A posição a lista real (Grafo)
     */
    private int posicaoMenorCusto(){
        
        //        Variavel para quardar o menor custo
        int posicaoMenorCusto = 0;
        
        for (int i = 1; i < celulasExpandidas.size(); i++) {
            
//            Confição ternaria 
            posicaoMenorCusto = ((celulasExpandidas.get(i).custoEstrela() <= 
                    celulasExpandidas.get(posicaoMenorCusto).custoEstrela()) ? 
                    i : posicaoMenorCusto);
        }    
        
        return posicaoMenorCusto;
    }
    
    /**
     * Função para fazer de forma recursiva a caminhada da celula objetivo até
     * a celula inicial
     * @param celula A celula que é o objetivo
     */
    private void caminhoFinal(Celula celula){
        
        //Verifica se é a celula inicial
        if(celula.getPai() == null || celula.getPosicaoArray() == 0){
            caminhoFinal.add(celula);
            return;
        }
        
        //Add a posição na lista
        caminhoFinal.add(celula);
        //Recurvificade, voltando
        caminhoFinal(celula.getPai());
        
    }
    
    /**
     * Função destinada para Expardir as celulas vizinho da celula atual.
     * Função também defini os pais das celulas expandidas
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param ordemTabuleiro A dimenção do tabuleiro
     * @param posicao Posição onde a celula atual se encontra na lista (Grafo)
     */
    private void expandirVizinhos(ArrayList<Celula> lista, int ordemTabuleiro, int posicao){
        
        //        No atual (Pai dos que serão) Expandidos
        Celula atual = lista.get(posicao);
        
        //      Verificando se existe Visinho cima
        if((posicao - ordemTabuleiro) >= 0){
            if (!celulasVisidadas.contains(lista.get((posicao-ordemTabuleiro)))) {
                celulasExpandidas.add(lista.get((posicao-ordemTabuleiro)));
                listaDeExpancao.add(lista.get((posicao-ordemTabuleiro)));
                
//                Verificando se já não existe Pai
                if(lista.get((posicao-ordemTabuleiro)).getPai() == null){
                    lista.get((posicao-ordemTabuleiro)).setPai(atual);
                }
            }
        }
        
//      Verificando se existe Visinho baixo     
        if ((posicao + ordemTabuleiro) < Math.pow(ordemTabuleiro, 2)) {
            if (!celulasVisidadas.contains(lista.get((posicao+ordemTabuleiro)))) {
                celulasExpandidas.add(lista.get((posicao+ordemTabuleiro)));
                listaDeExpancao.add(lista.get((posicao+ordemTabuleiro)));
                                
//                Verificando se já não existe Pai
                if(lista.get((posicao+ordemTabuleiro)).getPai() == null){
                    lista.get((posicao+ordemTabuleiro)).setPai(atual);
                }
            }
        }
        
//      Verificando se existe Visinho esquerda
        if ((posicao % 10) != 0) {
            if (!celulasVisidadas.contains(lista.get((posicao-1)))) {
               celulasExpandidas.add(lista.get((posicao-1)));
               listaDeExpancao.add(lista.get((posicao-1)));
                               
//                Verificando se já não existe Pai
               if(lista.get((posicao-1)).getPai() == null){
                 lista.get((posicao-1)).setPai(atual);  
               }
               
            }
            
        }
        
//      Verificando se existe Visinho direita
        if (((posicao + 1) % 10) != 0) {
            if (!celulasVisidadas.contains(lista.get((posicao+1)))) {
                celulasExpandidas.add(lista.get((posicao+1)));
                listaDeExpancao.add(lista.get((posicao+1)));
                                
//                Verificando se já não existe Pai
                if(lista.get((posicao+1)).getPai() == null){
                    lista.get((posicao+1)).setPai(atual);
                }
            }
            
        }
    }
    
    /**
     * Verifica se a celula atual é um caminho invalido ou não e 
     * REMOVE da lista de caminhos
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param proxima Celula que será a proxima a ser vizitada
     * @param atual  Celula atual que está sendo verificada
     */
    private void veridicaCaminhoInvalido(ArrayList<Celula> lista, Celula proxima, Celula atual){
        
        /** Ideia da Função
         *  Partindo do presuposto que o caminho é valido.
         * Verifica-se se a celula que tem o menor custo é Vizinha do no atual
         * Para ser vizinha precisa ter sido expandida agora
         * Caso não expandida agora, logo esse no atual é invalido.
         */
        
        boolean espandidoAgora = false;
        
//        Procurando se foi expandida agora
        for (int i = 0; i < listaDeExpancao.size(); i++) {
            if(listaDeExpancao.contains(proxima)){
                espandidoAgora = true;
            }
        }
//        Limpando a linda de Expanção agora
        listaDeExpancao.clear();
        
//        Removendo celula caso seja invalida
        if(!espandidoAgora){
            caminho.remove(atual);
        }
    }
    
    /**
     * Função que busca a caminho encontrando pela busca A Estrela
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param ordemTabuleiro A dimenção do tabuleiro
     * @return retorma a caminho encontrando
     */
    public ArrayList<Celula> getCaminhoFinalBuscaEstrela(ArrayList<Celula> lista, int ordemTabuleiro) {
        
//        Celula que será o objetivo
        Celula celula = null;
        
//        Encontrando o Objetivo
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).isFim()){
                celula = lista.get(i);
            }
        }
        
//        Função que realmente faz a busca
        this.estrela(lista, ordemTabuleiro, 0);
        
//        Chamando Recursividade;
        this.caminhoFinal(celula);
        
        return caminhoFinal;
    }
    
    
}
