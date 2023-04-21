package com.calculator;

// Início de classes de interface gráfica
import javax.swing.JFrame;
import javax.swing.JOptionPane;
// Início de classes de interface gráfica

// Início de classes internas
import com.calculator.design.Panel;
// Início de classes internas

/**
 * @author Ariel Santos
 * @author Italo Cerqueira
 */
public class Core {

    private static JFrame frame; // Frame da calculadora
	
    public static void main(String[] args) {
    	
        frame = new JFrame("Calculadora"); // Instancia e define título do frame
        frame.add(new Panel()); // Adiciona painel da calculadore
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que código deve ser encerrado no fechamento do frame
        
        frame.setSize(245,390); // Define dimensões
        
        frame.setLocationRelativeTo(null); // Centraliza frame na tela
        frame.setVisible(true); // Torna frame visível
        
    }
    public static void error(Exception e){
        /**
         * @param exceção emitida durante a interação do usuário
         */

        System.out.print(e.getMessage()); // Exibe erro no terminal
        JOptionPane.showMessageDialog(frame,  "Erro interno. Consulte o terminal para saber mais.", "Error", 1); // Informa erro ao usuário
        frame.setVisible(false); // Fecha frame
        System.exit(1); // Encerra código
        
    }
    
}
