/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author joao.v.silva
 */
public class Janela extends JFrame {

    JPanel[][] paineis;
    int xAtual, yAtual, xAlvo, yAlvo;
    Random random = new Random();
    String tecla = "";
    ArrayList<Ponto> cobrinha;
    int velocidade = 100;

    public Janela() {

        cobrinha = new ArrayList<>();

        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(20, 20, 1, 1));
        this.setLocationRelativeTo(null);

        paineis = new JPanel[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                paineis[i][j] = new JPanel();
                paineis[i][j].setBackground(Color.WHITE);
                this.add(paineis[i][j]);
            }
        }

        xAtual = random.nextInt(20);
        yAtual = random.nextInt(20);
        cobrinha.add(new Ponto(yAtual, xAtual));
        paineis[xAtual][yAtual].setBackground(Color.BLACK);

        xAlvo = random.nextInt(20);
        yAlvo = random.nextInt(20);
        paineis[yAlvo][xAlvo].setBackground(Color.RED);

        this.addKeyListener(new KHandler());

        this.setVisible(true);

        boolean sair = false;
        while (!sair) {

            sair = moveCobrinha();
            try {
                Thread.sleep(velocidade);
            } catch (InterruptedException ex) {
                System.err.println("Deu Pau");
            }

        }

        JOptionPane.showMessageDialog(null, "Perdeu!");

        System.exit(0);

    }

    public void limpaTela() {
        
        //this.getContentPane().removeAll();
        //paineis = new JPanel[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {

                //paineis[i][j] = new JPanel();
                paineis[i][j].setBackground(Color.WHITE);
                //this.add(paineis[i][j]);

            }
        }

    }

    public boolean moveCobrinha() {

        int xNovo = xAtual;
        int yNovo = yAtual;

        if (tecla.equals("cima")) {
            yNovo--;
        } else if (tecla.equals("baixo")) {
            yNovo++;
        } else if (tecla.equals("direita")) {
            xNovo++;
        } else if (tecla.equals("esquerda")) {
            xNovo--;
        }

        if (xNovo < 0 || xNovo >= 20 || yNovo < 0 || yNovo >= 20) {

            return true;

        }

        limpaTela();

        xAtual = xNovo;
        yAtual = yNovo;

        cobrinha.add(0, new Ponto(yNovo, xNovo));

        if (xAtual == xAlvo && yAtual == yAlvo) {
            xAlvo = random.nextInt(20);
            yAlvo = random.nextInt(20);
            velocidade -= 10;
        } else {
            cobrinha.remove(cobrinha.size() - 1);
        }

        paineis[yAlvo][xAlvo].setBackground(Color.RED);

        for (int i = 0; i < cobrinha.size(); i++) {

            paineis[cobrinha.get(i).getY()][cobrinha.get(i).getX()].setBackground(Color.BLACK);

        }
        
        this.revalidate();

        return false;

    }

    public class KHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 38) {
                tecla = "cima";
            } else if (e.getKeyCode() == 40) {
                tecla = "baixo";
            } else if (e.getKeyCode() == 39) {
                tecla = "direita";
            } else if (e.getKeyCode() == 37) {
                tecla = "esquerda";
            }

        }

    }

}
