/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscasegainformada.view;

import buscasegainformada.control.Buscas;
import buscasegainformada.control.Celula;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    String path = "C:\\Users\\Jece Xavier\\Documents\\MY-Projects\\"
                + "00-FACULDADE\\FSI\\TRABALHO_01\\BuscaSegaInformada\\"
                + "src\\buscasegainformada\\img\\";
    
    private ArrayList<Celula> celulas;
    private  Celula objetivo;
    
    private int posicaoXObjetivo;
    private int posicaoYObjetivo;
    
    private ArrayList<JLabel> tabuleiro;
    private JLabel lblPosicaoFinal;
    private JLabel lblTipoBusca;
    
    private JPanel jpDados;
    private JPanel jpLegenda;
    private JPanel jpTabuleiro;
    
    private JTextField txtPosicaoFinal;
  
    private JButton btnBuscarEstrela;
    private JButton btnAtualizarTabuleiro;
    private JButton btnAtualizarPosicaoFinal;
    
    private boolean caminhoApresentado;
    
    
    //Contrutor
    public Tabuleiro() {
        definirLayoutTela();
        
    }
    
    
    //Minhas funções
    
    private void definirLayoutDados(int ordem, int dimencao){
//        Instanciar Companentes
        this.jpDados = new JPanel();
        this.lblPosicaoFinal = new JLabel("Digite a posição final: ");
        this.lblPosicaoFinal.setFont(new Font("Arial", Font.BOLD, 13));
        this.txtPosicaoFinal = new JTextField("9x10");
        this.btnAtualizarTabuleiro = new JButton("Atualizar Tabuleiro");
        this.btnAtualizarPosicaoFinal = new JButton("Atualizar Posição Final");
        this.btnBuscarEstrela = new JButton("Bucar Rota Por A*");
        
        /*Definindo Layout que podemos alterar*/
        this.jpDados.setLayout(null);
        
//        dimenção do panel
        int posicaoX = (ordem * dimencao + 15);
        int aturaComponentes = 30;
        int larguraPanelDados = 350;
        int aturaPanelDados = (aturaComponentes*4 + 20 + 10 + 30 + 10);
        this.jpDados.setBounds(posicaoX, 10, larguraPanelDados, aturaPanelDados);
        this.jpDados.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        
//        Definindo dimentos componentes
        int larguraComponentes = (larguraPanelDados/2 - 2);
        
        
        this.lblPosicaoFinal.setBounds(15, 10, larguraComponentes, aturaComponentes);
        this.txtPosicaoFinal.setBounds(larguraComponentes - 10, 10, larguraComponentes, aturaComponentes);
        this.btnAtualizarPosicaoFinal.setBounds(15, aturaComponentes + 20, larguraComponentes - 10, aturaComponentes);
        this.btnAtualizarTabuleiro.setBounds(larguraComponentes + 20, aturaComponentes + 20, larguraComponentes - 30, aturaComponentes);
        this.btnBuscarEstrela.setBounds(15, aturaComponentes*2 + 30 + 20, larguraComponentes - 10, aturaComponentes);
        
//        Add componentes
        this.jpDados.add(this.lblPosicaoFinal);
        this.jpDados.add(this.txtPosicaoFinal);
        this.jpDados.add(this.btnAtualizarPosicaoFinal);
        this.jpDados.add(this.btnAtualizarTabuleiro);
        this.jpDados.add(this.btnBuscarEstrela);
        
//        Definindo eventos dos btns
        definirEventosBtns();
        
//        Add na tela
        this.add(this.jpDados);
    }
    
    private void definirEventosBtns(){
//        Criando Evendo
        ActionListener buscaEstrela = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                renderCaminho(buscar());
            }
        };
        
        ActionListener atualizarObjetivo = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                definirCelulaObjetivo(10);
            }
        };
        
//        add evento nos btns
        this.btnBuscarEstrela.addActionListener(buscaEstrela);
        this.btnAtualizarPosicaoFinal.addActionListener(atualizarObjetivo);
    }
    
    
    private ArrayList<Celula> buscar(){
        Buscas b = new Buscas();
        
        return b.getCaminhoFinalBuscaEstrela(celulas, (int) Math.sqrt(celulas.size()));
    }
    
    private void renderCaminho(ArrayList<Celula> caminho){
        
        if(this.caminhoApresentado){
            return;
        }
        
        this.caminhoApresentado = true;
        
         for (int i = 1; i < caminho.size()-1; i++) {
            
            JLabel lblCelula = tabuleiro.get(caminho.get(i).getPosicaoArray());
            
            ImageIcon img = new ImageIcon(path + "Run2.png"); 
            Image image = img.getImage().getScaledInstance(lblCelula.getHeight()-5, lblCelula.getHeight()-5, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            
//            if(lblCelula.getIcon() == null){
//                lblCelula.setIcon(imageIcon);
//            }
            
            lblCelula.setIcon(imageIcon);
            
        }
    }
    
    private void definirLayoutTela(){
        /*instacionado os componentes e add na tela*/
        definirLayoutDados(10, 70);
        renderTabuleiro(10, 70);
        this.caminhoApresentado = false;
        
        
        /*Para conseguir Fechar*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*Definico a dimenção da tela*/
        int tamanhoTelaWidth = this.jpTabuleiro.getWidth() + this.jpDados.getWidth();
        int tamanhoTelaHeight = (this.jpTabuleiro.getHeight()); // this.jpDados.getHeight() + 
                
        this.setBounds(new Rectangle(tamanhoTelaWidth, tamanhoTelaHeight));
        this.setLocationRelativeTo(null); //Para deixar centralizado
        this.setResizable(Boolean.FALSE); //Para não redimencionar
        
        /*Para conseguir ver*/
        this.setVisible(true);
    }
    
    private void renderTabuleiro(int ordemTabuleiro, int dimencao) {
        
        
        this.jpTabuleiro = new JPanel();
        this.tabuleiro = new ArrayList<>();
        this.celulas = new ArrayList<>();
        
        /*Definindo Layout que podemos alterar*/
        this.jpTabuleiro.setLayout(null);
        
        /*Definindo Dor de fundo*/
        this.jpTabuleiro.setBackground(Color.WHITE);
        
        //Definindo como será a borda
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        
        int posicaoX = 10, posicaoY = 10, tamanhoCelula = dimencao, posicaoArray = 0;
        
//        Rebevendo coordenadas Objetivo
        definindoCordenadasObjetivo(ordemTabuleiro);
        
        
        for (int i = 0; i < ordemTabuleiro; i++) {
            //Iniciando A linha
            posicaoX = 10;
            
            for (int j = 0; j < ordemTabuleiro; j++) {
               
                //Crindo Celular e add
                celulas.add(new Celula((i * ordemTabuleiro + j), i, j, this.posicaoXObjetivo, this.posicaoYObjetivo, 0, 0));                
                
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
    
    private void definindoCordenadasObjetivo(int ordemTabuleiro){
        String cordenadas[] = txtPosicaoFinal.getText().split("x");
            try {
            this.posicaoXObjetivo = Integer.parseInt(cordenadas[0]) - 1;
            this.posicaoYObjetivo = Integer.parseInt(cordenadas[1]) - 1;
            
            if(
                (this.posicaoXObjetivo < 0) ||
                (this.posicaoXObjetivo >= ordemTabuleiro) ||
                (this.posicaoYObjetivo < 0) ||
                (this.posicaoYObjetivo >= ordemTabuleiro)
            ){
                JOptionPane.showMessageDialog(null, "Error! :(\nCoodenadas Inválidas");
                this.posicaoXObjetivo = 9;
                this.posicaoXObjetivo = 9;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! :(\nPara converter as coodenadas\nCodigo: " + e);
            this.posicaoXObjetivo = 9;
            this.posicaoXObjetivo = 9;
        }
        
    }
    
    private void definirCelulaObjetivo(int ordemTabuleiro){
            celulas.get(this.posicaoXObjetivo * ordemTabuleiro + this.posicaoYObjetivo).setFim(false);
            definindoCordenadasObjetivo(ordemTabuleiro);
            celulas.get(this.posicaoXObjetivo * ordemTabuleiro + this.posicaoYObjetivo).setFim(true);
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
            ImageIcon img = new ImageIcon(path + "PowerUpRuim.png"); 
            Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            return imageIcon;
        }else{
            if(inicio){
                ImageIcon img = new ImageIcon(path + "Inicio2.png"); 
                Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(image);
                return imageIcon;
            }else{
                ImageIcon img = new ImageIcon(path + "Objetivo.png"); 
                Image image = img.getImage().getScaledInstance(dimencao, dimencao, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(image);
                return imageIcon;
            }
        }
    }
    
    //Gets end Sets

}
