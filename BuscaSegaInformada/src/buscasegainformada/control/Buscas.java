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
//    ArrayList<Celula> caminho;
    ArrayList<Celula> caminhoFinal;
    ArrayList<Celula> celulasExpandidas;
    ArrayList<Celula> celulasVisidadas;
    ArrayList<Celula> listaDeExpancao;
    
    //Contrutor
    public Buscas(){    
//        caminho = new ArrayList<>();
        caminhoFinal = new ArrayList<>();
        celulasExpandidas = new ArrayList<>();
        celulasVisidadas = new ArrayList<>();
        listaDeExpancao = new ArrayList<>();
    }

    //Minhas funções
    
    /**
     * Função que realmente faz a busca GULOSA
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param ordemTabuleiro A dimenção do tabuleiro
     * @param posicao Posição onde a celula atual se encontra na lista (Grafo)
     */
    private void gulosa(ArrayList<Celula> lista, int ordemTabuleiro, int posicao){
        
//        No atual
        Celula atual = lista.get(posicao);
        
        
//       Verificando se chegou ao fim
        if (atual.isFim()) {
            return;
        }
        
//        espandindo os visinhos
        this.expandirVizinhos(lista, ordemTabuleiro, posicao);
        
//        Definindo filho
        Celula proximo  = this.menorHeuristica();
        
//        Verificando se não caio em um loop
        if(atual.getPai() == proximo){
            return;
        }
        
//        Defini que o pai da proxima é o atual
        proximo.setPai(atual);
        
//        Limpa todas as listas
        this.clearLists();
        
//        Faz a recursividade
        this.gulosa(lista, ordemTabuleiro, posicao);
    }
    
    /**
     * Função para definir qual será o proximo no dá lista por base na menor
     * Heuristica
     * @return Celula com a menor Heristica
     */
    private Celula menorHeuristica(){
        Celula proxima  = this.listaDeExpancao.get(0);
        
        for (int i = 1; i < this.listaDeExpancao.size(); i++) {
            if(this.listaDeExpancao.get(i).getHeuristica() < proxima.getHeuristica()){
                proxima = this.listaDeExpancao.get(i);
            }
        }
        
        return proxima;
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
//        caminho.add(atual);
        
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
//        if(!espandidoAgora){
////            caminho.remove(atual)S;
//        }
    }
    
    /**
     * Função que busca a caminho encontrando pela busca A Estrela
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param ordemTabuleiro A dimenção do tabuleiro
     * @return retorma a caminho encontrando
     */
    public ArrayList<Celula> getCaminhoFinalBuscaEstrela(ArrayList<Celula> lista, int ordemTabuleiro) {
        
        this.clearLists();  
        
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
    
        /**
     * Função que busca a caminho encontrando pela busca Gulosa  
     * @param lista Lista onde estão as celulas para fazer a busca (O grafo)
     * @param ordemTabuleiro A dimenção do tabuleiro
     * @return retorma a caminho encontrando
     */
    public ArrayList<Celula> getCaminhoFinalBuscaGulosa(ArrayList<Celula> lista, int ordemTabuleiro) {
        
        this.clearLists();  
        
//        Celula que será o objetivo
        Celula celula = null;
        
//        Encontrando o Objetivo
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).isFim()){
                celula = lista.get(i);
            }
        }
        
//        Função que realmente faz a busca
        this.gulosa(lista, ordemTabuleiro, 0);
        
//        Chamando Recursividade;
        this.caminhoFinal(celula);
        
//        Verifica se não caiu em nenhum loop
        if (this.caminhoFinal.get(this.caminhoFinal.size()-1).isInicio()) {
            return caminhoFinal;
        }else{
            return null;
        }
        
        
    }
    
    
    private void clearLists(){
        this.caminhoFinal.clear();
        this.celulasExpandidas.clear();
        this.celulasVisidadas.clear();
        this.listaDeExpancao.clear();
    }
    
}
