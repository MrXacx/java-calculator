package com.calculator;

import javax.swing.JFrame;

import com.calculator.design.Panel;
/**
 *
 * @author laboratorio.software
 */
public class Core {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadora"); // Instancia e define título do frame
        frame.add(new Panel()); // Adiciona painel da calculadore
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que código deve ser encerrado no fechamento do frame
        frame.setSize(220,370); // Define dimensões
        frame.setLocationRelativeTo(null); // Centraliza frame na tela
        frame.setVisible(true); // Torna frame visível
        return;
    }
    
}
