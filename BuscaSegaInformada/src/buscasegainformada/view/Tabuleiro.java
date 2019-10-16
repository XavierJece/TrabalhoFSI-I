/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscasegainformada.view;

import buscasegainformada.control.Buscas;
import buscasegainformada.control.Celula;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Jece Xavier
 */
public class Tabuleiro extends JFrame{
    //Atributos
    private ArrayList<Celula> celulas;
    private  Celula objetivo;
    
    private ArrayList<JLabel> tabuleiro;
    private JLabel lblPosicaoFinal;
    private JLabel lblTipoBusca;
    
    private JPanel jpDados;
    private JPanel jpLegenda;
    private JPanel jpTabuleiro;
    
    private JTextField txtPosicaoFinal;
  
    private JButton btnBuscar;
    
    private JRadioButton rbnAEstrela;
    private JRadioButton rbnGulosa;
    private JRadioButton rbnLargura;
    private JRadioButton rbnProfundidade;
    
    
    //Contrutor
    public Tabuleiro() {
        definirLayoutTela();
        
//        Teste Busca
        Buscas b = new Buscas();
        
        definindoCelulaObjetivo();
        
//        ArrayList<Celula> caminho = b.buscaEstrela2(celulas, 10, 0);
        ArrayList<Celula> caminho = b.getCaminhoFinalBuscaEstrela(celulas, (int) Math.sqrt(celulas.size()));

        
//        System.err.println("\n **** Terminou  Tabuleiro****");
        
        JLabel lblCelula;
        
        for (int i = 0; i < caminho.size(); i++) {
            System.out.println("Celula " + (i+1) + "ª Vizitada => " + caminho.get(i).getPosicaoArray());
            
            lblCelula = tabuleiro.get(caminho.get(i).getPosicaoArray());
            
            lblCelula.setBackground(Color.WHITE);
            
        }
    }
    
    
    //Minhas funções
    
    private void definirLayoutTela(){
        /*instacionado os componentes e add Não tela*/
        renderTabuleiro(10);
        
        /*Para conseguir Fechar*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*Definico a dimenção da tela*/
        int tamanhoTelaWidth = this.jpTabuleiro.getWidth();
        int tamanhoTelaHeight = (this.jpTabuleiro.getHeight()); // this.jpDados.getHeight() + 
                
        this.setBounds(new Rectangle(tamanhoTelaWidth, tamanhoTelaHeight));
        this.setLocationRelativeTo(null); //Para deixar centralizado
        this.setResizable(Boolean.FALSE); //Para não redimencionar
        
        /*Para conseguir ver*/
        this.setVisible(true);
    }
    
    private void renderTabuleiro(int ordemTabuleiro) {
        this.jpTabuleiro = new JPanel();
        this.tabuleiro = new ArrayList<>();
        this.celulas = new ArrayList<>();
        
        /*Definindo Layout que podemos alterar*/
        this.jpTabuleiro.setLayout(null);
        
        /*Definindo Dor de fundo*/
        this.jpTabuleiro.setBackground(Color.WHITE);
        
        //Definindo como será a borda
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        int posicaoX = 10, posicaoY = 10, tamanhoCelula = 60, posicaoArray = 0;
        
        for (int i = 0; i < ordemTabuleiro; i++) {
            //Iniciando A linha
            posicaoX = 10;
            
            for (int j = 0; j < ordemTabuleiro; j++) {
               
                //Crindo Celular e add
                celulas.add(new Celula((i * ordemTabuleiro + j), i, j, 9, 9, 0, 0));                
                
//                Criando Visual Celula
//                JLabel lblCelula = new JLabel(((i * ordemTabuleiro + j) + " H " + (celulas.get(posicaoArray).custoEstrela())), JLabel.CENTER);
                JLabel lblCelula = new JLabel();
            
                
                //Definindo o tamanho da celula
                lblCelula.setBounds(posicaoX, posicaoY, tamanhoCelula, tamanhoCelula);
                posicaoX += tamanhoCelula;

//                Deixando o fundo visinho
                lblCelula.setOpaque(true);
                lblCelula.setBackground(definirCorCelula(celulas.get(posicaoArray).getCusto()));
                
//                Definindo Icon
                lblCelula.setIcon(definirIcon(celulas.get(posicaoArray).getPowerUp(), celulas.get(posicaoArray).isInicio(), tamanhoCelula));
                
                //Definindo a borda da celula
                lblCelula.setBorder(border);

                //Add celula no Array
                this.tabuleiro.add(lblCelula);

                //Add celula no Painel Tabuleiro
                this.jpTabuleiro.add(lblCelula);
                
                //Incrimentando Contador
                posicaoArray++;
            
            }
            
            //Indo para linha de baixo
            posicaoY += tamanhoCelula;
        }
        
        //Definindo o tamanho do Tabuleiro
        this.jpTabuleiro.setBounds(0, 0, (30 + ordemTabuleiro * tamanhoCelula), (60 + ordemTabuleiro * tamanhoCelula));
        
        //Add Tabueiro na Tela
        this.add(this.jpTabuleiro);
    }
    
    private void definindoCelulaObjetivo(){
        for (int i = 0; i < celulas.size(); i++) {
            if(celulas.get(i).isFim()){
                objetivo = celulas.get(i);
            }
        }
    }
    
    private Color definirCorCelula(int custo){
        if(custo == 1){
            return new Color(0, 0, 0, 50);
        }else if(custo == 4){
            return new Color(235, 168, 168, 200);
        }else if(custo == 10){
            return new Color(0, 0, 0, 150);
        }else if(custo == 20){
            return new Color(50, 114, 10, 170);
        }else{
            return Color.WHITE;
        }
    }
    
    private Icon definirIcon(int custo, boolean inicio, int dimencao){
        String path = "C:\\Users\\Jece Xavier\\Documents\\MY-Projects\\"
                + "00-FACULDADE\\FSI\\TRABALHO_01\\BuscaSegaInformada\\"
                + "src\\buscasegainformada\\img\\";
        
        
        if(custo == 1){
            ImageIcon img = new ImageIcon(path + "PowerUpBom.png"); 
            Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            return imageIcon;
        }else if(custo == 2){
            return null;
        }else if(custo == 5){
            ImageIcon img = new ImageIcon(path + "/img/PowerUpRuim.png"); 
            Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            return imageIcon;
        }else{
            if(inicio){
                ImageIcon img = new ImageIcon(path + "/img/Inicio.png"); 
                Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(image);
                return imageIcon;
            }else{
                ImageIcon img = new ImageIcon(path + "/img/Objetivo.png"); 
                Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(image);
                return imageIcon;
            }
        }
    }
    
    //Gets end Sets

}
