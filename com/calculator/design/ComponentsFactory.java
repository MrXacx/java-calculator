package com.calculator.design;

// Classes de componentes
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

// Classes de estilização
import java.awt.Color;
import java.awt.Font;

// Classes de eventos
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Classes internas
import com.calculator.Core;
import com.calculator.service.MathService;

/**
 * @author Ariel Santos
 * @author Italo Cerqueira
 */

public class ComponentsFactory{
	protected JPanel screen; // Painel de exibição
	protected JLabel content, standBy; // Labels de conteúdo (número atual, número em standBy para operação)
	protected JButton[] basicMath, specialMath, numbers, systemFunc; // Botões da calculadora (operadores matemáticos básicos, operadores matemáticos especias, algarismos, botões do sistema)

    private MathService math; // Manipulador da classe de cálculos
    private Double memory = Double.valueOf(0); // sistema de memória
    
    public void createComponents(JPanel screen){
    	this.screen = screen; // Inicia visor
    	this.content = new JLabel(" "); // Inicia label de cálculos
    	this.standBy = new JLabel(" ");  // Cria standBy de cálculos
    	
    	this.systemFunc = this.styleButtonArray(this.createSystemButtons()); // Cria botões com funções de sistema;
    	this.basicMath = this.styleButtonArray(this.createBasicMathSignButtons()); // Cria botões com funções de sistema;
    	this.specialMath = this.styleButtonArray(this.createSpecialMathSignButtons()); // Cria demais sinais matemáticos
    	this.numbers = this.styleButtonArray(this.createNumericButtons(new JButton[10])); // Cria botões numéricos;
    }
    
    private static JButton[] styleButtonArray(JButton[] buttons){
        /**
         * @param array de botões a serem estilizados
         * @return array de botões após estilização
         */
        
        for(int index = 0; index < buttons.length; index++){ // Percorre todo o array
            buttons[index].setBackground(Color.BLACK); // Define background branco
            buttons[index].setFocusPainted(false); // Desativa foco
            buttons[index].setFont(new Font("Arial", Font.PLAIN, 14)); // Define fonte
            buttons[index].setForeground(Color.WHITE);
        }      
        return buttons; // Retorna botões estilizados
    }
    
	private static JButton[] createGenericButtons(JButton[] buttons){
        /**
         * @param Array de JButton com espaços nulos
         * @return Array de JButton totalmente preenchido com instâncias de JButton
         */

    	for (int index = 0; index < buttons.length; index++){ // Percorre todo o array
            buttons[index] = new JButton(); // Instancia posição do array e incrementa índice ao fim
        }
        
        return buttons;
    }
    
    private JButton[] createSystemButtons(){
        /**
         * @return Array de botões com funções não-numéricas configuradas
         */

        JButton[] buttons = createGenericButtons(new JButton[7]); // Cria array genérico de JButton com 3 espaços
    	 
        // Configuração do botão "Clear"
        buttons[0].setText("C");
        buttons[0].addActionListener(evt -> {
               content.setText(" "); // Limpa todo o valor da atual entrada de dados
               standBy.setText(" "); 
               math = null;
        }); 

        // Configuração do botão "Clear Entry"
        buttons[1].setText("CE");
        buttons[1].addActionListener(evt -> {content.setText(" ");}); // Limpa todo o valor da atual entrada de dados

        // Configuração do botão "Delete"
        buttons[2].setText("Del");
        buttons[2].addActionListener(evt -> {            
               String nContent = content.getText();   
               nContent = (nContent.charAt(0) == ' ' ? "" : " ") + nContent;
                 
               int strlen = nContent.length();
  
               if(strlen > 1){ // Evita que espaçamento de 1 caracter seja deletado
				   content.setText(
					  (new StringBuilder(nContent)).deleteCharAt(--strlen).toString() // Retorna a string sem o último caractere
				   );
			   } 
        }); 
        
        // Configuração do botão "Memory Clear"
        buttons[3].setText("MC");
        buttons[3].addActionListener(evt -> {            
           memory = 0.0; 
        });
        
         // Configuração do botão "Memory Recall"
        buttons[4].setText("MR");
        buttons[4].addActionListener(evt -> {            
           content.setText(memory.toString());
           
        });        
        
        // Configuração de botões "Memory Add" e "Memory Subtract" 
        buttons[5].setText("M+"); // Define texto do botão "Memory Add"
        buttons[6].setText("M-"); // Define texto do botão "Memory Subtract"
       
       	for(int index = 5; index < 7; index++){
       		String op = buttons[index].getText().replace("M", ""); // Obtém operador
       		
       		buttons[index].addActionListener(evt -> {
       		
		    	String nContent = content.getText();
		    	
		    	if(!nContent.isBlank()){ // Executa se content não estiver vazio
					var localMath = new MathService(); // Cria manipulador matemático local
					localMath.setPortion(memory, 0); // Passa primeira parcela do cálculo
					localMath.setPortion(Double.valueOf(nContent), 1); // Passa segunda parcela do cálculo
					localMath.setOperatorID(op); // Passa operador
					
					try{
                        memory = localMath.getResult(); //  Armazena resultado do cálculo anterior
                    } catch (Exception e){  // Executa em caso de exceção emitida
                        Core.error(e); // Chama método de encerramento de emergência   
                    }
                    content.setText(" "); // Limpa label
				}
	    	});
       	}
		
        return buttons;
    }
    
    private JButton[] createBasicMathSignButtons(){
        /**
         * @return Array de botões com operações matemáticas básicas configuradas
         */

        JButton[] basicMathSign = createGenericButtons(new JButton[4]); // Cria array genérico de JButton com 4 espaços
        
        basicMathSign[0].setText("÷"); // Define texeto do botão de divisão
        basicMathSign[1].setText("x"); // Define texeto do botão de multiplicação
        basicMathSign[2].setText("-"); // Define texeto do botão de subtração
        basicMathSign[3].setText("+"); // CDefine texeto o botão de adição
        
        // Listener geral de botões de operações básicas
        for(int index = 0; index < basicMathSign.length; index++){
            final String op = basicMathSign[index].getText(); // Obtém operador como string final

            basicMathSign[index].addActionListener(evt -> { // Adiciona listener
                    String nContent = content.getText(); // Obtém cópia do texto de content
                    String nstandBy = standBy.getText(); // Obtém cópia do texto de standBy

                    if(nContent.isBlank()){ // Executa caso a label de cálculos esteja vazia
                        if(math instanceof MathService){ // Executa se math já estiver instanciada     
                            math.setOperatorID(op); // Altera operador a ser utilizado
                            standBy.setText(nstandBy.substring(0, nstandBy.length()-1) + op); // Altera operador exibido
                        }
                        return; // Impede que função continue a ser executada
                    }
                    else if(!nstandBy.isBlank()){ // Executa standBy não estiver vazio, ou seja, esse é o segundo valor da operação
                        math.setPortion(Double.valueOf(nContent), 1); // Passa valor da segunda posição

                        try{
                            nContent = math.getResult().toString(); //  Armazena resultado do cálculo anterior
                        } catch (Exception e){  // Executa em caso de exceção emitida
                            Core.error(e); // Chama método de encerramento de emergência   
                        } 
                    }
                    
                    math = new MathService(); // Intancia classe de cálculos
                    math.setPortion(Double.valueOf(nContent), 0); // Pass primeira parcela da operação
                    math.setOperatorID(op); // Define operador
                    
                    standBy.setText(" " +nContent+" "+op); // Informa valor passado e operação escolhida
                    content.setText(" "); //  Limpa label de cálculos
            });
        }

        return styleButtonArray(basicMathSign);
    }

    private JButton[] createSpecialMathSignButtons(){
        /**
         * @return Array de botões com operações matemáticas complementares configuradas
         */

    	JButton[] specialMathSign = createGenericButtons(new JButton[7]); // Cria array genérico de JButton com 7 espaços
    	
        specialMathSign[0].setText("√"); // Define texto do botão de raíz quadrada
        specialMathSign[1].setText("1/x"); // Define texto do botão de inverso
        specialMathSign[2].setText("x²"); // Define texto do botão de potência quadrada
        specialMathSign[3].setText("±"); // Define texto do botão de oposto
        
        for(int index = 0; index < 4; index++){ // Cria listener padrão para raíz quadrada, inverso, potência queadrada e oposto
        	String text = specialMathSign[index].getText();
        	specialMathSign[index].addActionListener(evt -> {
		    	String nContent = content.getText();
		    	
		        if(!nContent.isBlank()){
		        	if(!(math instanceof MathService)) // Executa se math não for instância de MathService
			            math = new MathService();

		            try{
		                content.setText(math.getSubresult(Double.valueOf(nContent), text).toString());
		            } catch(Exception e){
		                Core.error(e);
		            }
		        }
		    });
        }

        // Configuração do botão de porcentagem
        specialMathSign[4].setText("%");
        specialMathSign[4].addActionListener(evt -> {
                if(math instanceof MathService) {
                   String nContent = content.getText();
                    try{
                        content.setText(math.getSubresult(Double.valueOf(nContent), "%").toString());
                    } catch(Exception e){
                        Core.error(e);
                    }
                }
        });

        // Configuração do botão de igualdade
        specialMathSign[5].setText("=");
        specialMathSign[5].addActionListener(evt -> {
                if(!(standBy.getText().isBlank() || content.getText().isBlank())){ // Executa somente quanto standBy e content estão preenchidos
                	math.setPortion(Double.valueOf(content.getText()), 1); // Passa segunda parcela do cálculos
                    try{
                        String nResult = math.getResult().toString();
                        if(nResult.endsWith(".0")) // Executa se os últimos caracteres forem ".0"
                            nResult = nResult.replace(".0", "");

                        content.setText(nResult); //  Armazena resultado do cálculo anterior
                    } catch (Exception e) {          
                        Core.error(e); // Chama método de encerramento de emergência
                    } 

                	standBy.setText(" "); // Limpa standBy
                	math = null;
                }
            
        });

        // Configuração do botão de vírgula
        specialMathSign[6].setText(".");
        specialMathSign[6].addActionListener(evt -> {
            	String nContent = content.getText();   
                if(!nContent.contains(".")) // Executa caso não haja "," na string
                    content.setText((nContent.charAt(0) == ' ' ? "" : " ") + nContent + "."); // Adiciona "." ao final da string
            
        });

        return styleButtonArray(specialMathSign);
    }
    
    private JButton[] createNumericButtons(JButton[] numericButtons){
    	/**
         * @param Array genérico de JButton
         * @return Array de botões numéricos com índice inicial 0
         */
        
        for (int index = 0; index < numericButtons.length; index++){ // Percorre todo o array passado por parâmetro
            final String i = Integer.toString(index);

            numericButtons[index] = new JButton(i); // instancia espaço do array       
            numericButtons[index].addActionListener(evt -> { // Listener padrão          
                    String nContent = content.getText();
                    content.setText( (nContent.charAt(0) == ' ' ? nContent : " ") + i); // Insere número ao fim da string
            });
        }
        
        return styleButtonArray(numericButtons);
    }
}
