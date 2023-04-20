package com.calculator.design;
// Início de classes de componentes do frame
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
// Fim de classes de componentes do frame

// Início de classes de estilização
import java.awt.Color;
import java.awt.Font;
// Fim de classes de estilização

// Início de classes de layout
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
// Fim de classes de layout

// Início de classes de eventos
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// Fim de classes de eventos

// Início de classes de vetores
import java.util.Arrays;
import java.util.List;
// Fim de classes de vetores

// Início de classes internas
import com.calculator.Core;
import com.calculator.service.Mathematics;
// Fim de classes internas

/**
 * @author Ariel Santos
 * @author Italo Cerqueira
 * @author Maria França
 * @author Pedro Santos
 * @version 3.0
 * @since 1.0
 */
public class Panel extends JPanel {
	
    private JButton[] basicMath, specialMath, numbers, systemFunc; // Botões da calculadora (operadores matemáticos básicos, operadores matemáticos especias, algarismos, botões do sistema)
    private JLabel content, standBy; // Labels de conteúdo (número atual, número em standBy para operação)
    private JPanel screen; // Painel de exibição
	private Mathematics math; // Manipulador da classe de cálculos
	
    public Panel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        content = new JLabel(" "); // Inicia label de cálculos
        standBy = new JLabel(" "); // Cria standBy de cálculos

        // Inicia configuração de botões
        systemFunc = styleButtonArray(this.initSystemButtons()); // Cria botões com funções de sistema
        basicMath = styleButtonArray(this.initBasicMathSignButtons()); // Cria operadores matemáricos
        specialMath = styleButtonArray(this.initSpecialMathSignButtons()); // Cria demais sinais matemáticos
        numbers = styleButtonArray(this.initNumericButtons(new JButton[10])); // Cria botões numéricos
		// Finaliza configuração de botões
		
        // Inicia configuração do painel de cálculos
        screen = new JPanel(); // Cria painel de cálculos
        GroupLayout screenLayout = new GroupLayout(screen); // Determina alinhamento do painel de cálculos
        
		screen.setLayout(screenLayout); // Define o layout que o painel deve utilizar
        this.setHorizontalScreenLayout(screenLayout); // Define layout horizontal do painel
        this.setVerticalScreenLayout(screenLayout); // Define layout vertical
        // Finaliza configuração do painel de cálculos
        
        // Inicia configuração do painel geral
        GroupLayout layout = new GroupLayout(this); // Cria layout geral
        this.setLayout(layout); // Define o layout que o painel deve utilizar
        this.setHorizontalGeneralGroup(layout); // Define layout horizontal do painel
        this.setVerticalGeneralGroup(layout); // Define layout vertical
        // Finaliza configuração do painel geral
    }
    
    // INÍCIO DE MÉTODOS PARA CONFIGURAÇÃO DE COMPONENTES
    private JButton[] createGenericButtons(JButton[] buttons){
        /**
         * @param Array de JButton com espaços nulos
         * @return Array de JButton totalmente preenchido com instâncias de JButton
         * @version 1.0
         * @since 2.0
         */

    	for (int index = 0; index < buttons.length; index++){ // Percorre todo o array
            buttons[index] = new JButton(); // Instancia posição do array e incrementa índice ao fim
        }
        
        return buttons;
    }
    
    private JButton[] initSystemButtons(){
        /**
         * @return Array de botões com funções não-numéricas configuradas
         * @version 1.1
         * @since 2.0
         */

        JButton[] buttons = this.createGenericButtons(new JButton[3]); // Cria array genérico de JButton com 3 espaços
    	 
        // Inicia configuração do botão "C"
        buttons[0].setText("C");
        buttons[0].addActionListener(evt -> {
               content.setText(" "); // Limpa todo o valor da atual entrada de dados
               standBy.setText(" "); 
               math = null;
        }); 
        // Finaliza configuração do botão "C"

        // Inicia configuração do botão "CE"
        buttons[1].setText("CE");
        buttons[1].addActionListener(evt -> {content.setText(" ");}); // Limpa todo o valor da atual entrada de dados
        // Finaliza configuração do botão "CE"

        // Inicia configuração do botão "Del"
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
        // Finaliza configuração do botão "Del"

        return buttons;
    }
    
    private JButton[] initBasicMathSignButtons(){
        /**
         * @return Array de botões com operações matemáticas básicas configuradas
         * @version 2.0
         * @since 2.0
         */

        JButton[] basicMathSign = this.createGenericButtons(new JButton[4]); // Cria array genérico de JButton com 4 espaços
        
        basicMathSign[0].setText("÷"); // Define texeto do botão de divisão
        basicMathSign[1].setText("x"); // Define texeto do botão de multiplicação
        basicMathSign[2].setText("-"); // Define texeto do botão de subtração
        basicMathSign[3].setText("+"); // CDefine texeto o botão de adição
        
        // Inicia configuração do listener geral de botões de operações básicas
        for(int index = 0; index < basicMathSign.length; index++){
            final String op = basicMathSign[index].getText(); // Obtém operador como string final
            
            // Adiciona listener
            basicMathSign[index].addActionListener(evt -> {
                    String nContent = content.getText(); // Obtém cópia do texto de content
                    String nstandBy = standBy.getText(); // Obtém cópia do texto de standBy

                    if(nContent.isBlank()){ // Executa caso a label de cálculos esteja vazia
                        if(math instanceof Mathematics){ // Executa se math já estiver instanciada     
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
                    
                    math = new Mathematics(); // Intancia classe de cálculos
                    math.setPortion(Double.valueOf(nContent), 0); // Pass primeira parcela da operação
                    math.setOperatorID(op); // Define operador
                    
                    standBy.setText(" " +nContent+" "+op); // Informa valor passado e operação escolhida
                    content.setText(" "); //  Limpa label de cálculos
            });
        }
        // Finaliza configuração do listener geral de botões de operações básicas

        return basicMathSign; // Retorna botões configurados
    }

    private JButton[] initSpecialMathSignButtons(){
        /**
         * @return Array de botões com operações matemáticas complementares configuradas
         * @version 2.0
         * @since 2.0
         */

    	JButton[] specialMathSign = this.createGenericButtons(new JButton[7]); // Cria array genérico de JButton com 7 espaços
    	
        specialMathSign[0].setText("√"); // Define texto do botão de raíz quadrada
        specialMathSign[1].setText("1/x"); // Define texto do botão de inverso
        specialMathSign[2].setText("x²"); // Define texto do botão de potência quadrada
        specialMathSign[3].setText("±"); // Define texto do botão de oposto
        
        for(int index = 0; index < 4; index++){
        	String text = specialMathSign[index].getText();
        	specialMathSign[index].addActionListener(evt -> {
		    	String nContent = content.getText();
		    	
		        if(!nContent.isBlank()){
		        	if(!(math instanceof Mathematics))
			            math = new Mathematics();

		            try{
		                content.setText(math.getSubresult(Double.valueOf(nContent), text).toString());
		            } catch(Exception e){
		                Core.error(e);
		            }
		        }
		    });
        }

        // Inicia configuração do botão de porcentagem
        specialMathSign[4].setText("%");
        specialMathSign[4].addActionListener(evt -> {
                if(math instanceof Mathematics) {
                   String nContent = content.getText();
                    try{
                        content.setText(math.getSubresult(Double.valueOf(nContent), "%").toString());
                    } catch(Exception e){
                        Core.error(e);
                    }
                }
        });
        // Finaliza configuração do botão de porcentagem

        // Inicia configuração do botão de igualdade
        specialMathSign[5].setText("=");
        specialMathSign[5].addActionListener(evt -> {
                if(!(standBy.getText().isBlank() || content.getText().isBlank())){ // Executa somente quanto standBy e content estão preenchidos
                	math.setPortion(Double.valueOf(content.getText()), 1); // Passa segunda parcela do cálculos
                    try{
                        String nResult = math.getResult().toString();
                        if(nResult.endsWith(".0"))
                            nResult = nResult.replace(".0", "");

                        content.setText(nResult); //  Armazena resultado do cálculo anterior
                    } catch (Exception e) {          
                        Core.error(e); // Chama método de encerramento de emergência
                    } 

                	standBy.setText(" "); // Limpa standBy
                	math = null;
                }
            
        });
        // Finaliza configuração do botão de igualdade

        // Inicia configuração do botão de vírgula
        specialMathSign[6].setText(".");
        specialMathSign[6].addActionListener(evt -> {
            	String nContent = content.getText();   
                if(!nContent.contains(".")) // Executa caso não haja "," na string
                    content.setText((nContent.charAt(0) == ' ' ? "" : " ") + nContent + "."); // Adiciona "," ao final da string
            
        });
        // Finaliza configuração do botão de vírgula

        return specialMathSign; // Retorna botões configurados
    }
    
    private JButton[] initNumericButtons(JButton[] numericButtons){
    	/**
         * @param Array genérico de JButton
         * @return Array de botões numéricos com índice inicial 0
         * @version 1.1
         * @since 1.0
         */
        
        for (int index = 0; index < numericButtons.length; index++){ // Percorre todo o array passado por parâmetro
            final String i = Integer.toString(index);

            numericButtons[index] = new JButton(i); // instancia espaço do array       
            numericButtons[index].addActionListener(evt -> { // Listener padrão          
                    String nContent = content.getText();
                    content.setText( (nContent.charAt(0) == ' ' ? nContent : " ") + i); // Insere número ao fim da string
            });
        }
        
        return numericButtons; // Retorna botões configurados
    }

    private JButton[] styleButtonArray(JButton[] buttons){
        /**
         * @param array de botões a serem estilizados
         * @return array de botões após estilização
         * @version 1.0
         * @since 3.0
         */
        
        for(int index = 0; index < buttons.length; index++){ // Percorre todo o array
            buttons[index].setBackground(Color.BLACK); // Define background branco
            buttons[index].setFocusPainted(false); // Desativa foco
            buttons[index].setFont(new Font("Arial", Font.PLAIN, 14)); // Define fonte
            buttons[index].setForeground(Color.WHITE);
        }
        
        return buttons; // Retorna botões estilizados
    }


    private void setHorizontalScreenLayout(GroupLayout screenLayout){
    	/** 
    	* @param layout do label
    	* @version 1.0
    	* @since 1.0
    	*/
    	
        screenLayout.setHorizontalGroup(
            screenLayout.createSequentialGroup() // Cria grupo de componentes paralelos
        	.addComponent(standBy, 110, 110, 110) // adiciona label de standBy com comprimento 110
            .addGap(20, 45, 45)
        	.addComponent(content, 110, 110, 110) // adiciona label de conteúdo com comprimento 110
        );
    }
    
    private void setVerticalScreenLayout(GroupLayout screenLayout){
        screenLayout.setVerticalGroup(
            screenLayout.createParallelGroup(GroupLayout.Alignment.LEADING) // Cria grupo de componentes paralelos
			.addGroup(screenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            	.addComponent(standBy, 40, 40, 40) // adiciona label de standBy com comprimento 40
                .addComponent(content, 40, 40, 40)) // adiciona label de conteúdo com comprimento 40
        );
    }

    private Group alignComponents(Group group, List<Component> componentList, int preferredSize){
    	/** 
    	* @param Objeto de qualquer classe-filha de Group. Componente macro que contém linhas/colunas de componentes
    	* @param Lista de componentes a serem enfileirados
    	* @param Comprimento do componente
    	*
    	* @return Fileira de componentes
    	* @version 1.0
    	* @since 2.0
    	*/
    	
    	for(Component comp : componentList.toArray(new Component[0]))
    		group = group.addComponent(comp, preferredSize-5, preferredSize, preferredSize+15);

    	return group;
    }

    private void setHorizontalGeneralGroup(GroupLayout layout){
    	final int BUTTON_WIDTH = 55; // Determina tamanho horizontal padrão

    	layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(screen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)                                 
                .addGroup( // Linha com porcentagem, raíz quadrada, potência quadrada, inverso
                	this.alignComponents(layout.createSequentialGroup(), Arrays.asList(specialMath[4], specialMath[0], specialMath[2], specialMath[1]), BUTTON_WIDTH)
                )
                .addGroup( // Linha 7, 8, 9 e divisão
                    this.alignComponents(layout.createSequentialGroup(), Arrays.asList(numbers[7], numbers[8], numbers[9], basicMath[1]), BUTTON_WIDTH)
                )
                .addGroup( // Linha com C, CE, Del e multiplicação
                    this.alignComponents(layout.createSequentialGroup(), Arrays.asList(systemFunc[0], systemFunc[1], systemFunc[2], basicMath[0]), BUTTON_WIDTH)
               )               
               .addGroup( // Linha com 4, 5, 6 e subtração
                    this.alignComponents(layout.createSequentialGroup(), Arrays.asList(numbers[4], numbers[5], numbers[6], basicMath[2]), BUTTON_WIDTH)
               )
              .addGroup(  // LInha com 1, 2, 3 e soma
                    this.alignComponents(layout.createSequentialGroup(), Arrays.asList(numbers[1], numbers[2], numbers[3], basicMath[3]), BUTTON_WIDTH)
               )
              .addGroup( // Linha com oposto, 0, vírgula e igualdade
                    this.alignComponents(layout.createSequentialGroup(), Arrays.asList(specialMath[3], numbers[0], specialMath[6], specialMath[5]), BUTTON_WIDTH)
               ) 
        );
    }
    
    private void setVerticalGeneralGroup(GroupLayout layout){
    	final int BUTTON_HEIGHT = 45;
    		
    	layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(screen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup( // Linha com porcentagem, raíz quadrada, potência quadrada, inverso
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(specialMath[0], specialMath[4], specialMath[1], specialMath[2]), BUTTON_HEIGHT)
              		)
              		.addGroup( // Coluna com C, CE, divisão, e Del
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(systemFunc[0], systemFunc[1], basicMath[0], systemFunc[2]), BUTTON_HEIGHT)
              		)
              		.addGroup( // Linha com porcentagem, raíz quadrada, potência quadrada, inverso
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(numbers[8], basicMath[1], numbers[9], numbers[7]), BUTTON_HEIGHT)
              		)
					.addGroup( // Linha com 4, 5, 6 e subtração
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(numbers[5], numbers[4], basicMath[2], numbers[6]), BUTTON_HEIGHT)
              		)
					.addGroup( // LInha com 1, 2, 3 e soma
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(numbers[2], numbers[1], basicMath[3], numbers[3]), BUTTON_HEIGHT)
              		)
              		.addGroup( // Linha com oposto, 0, vírgula e igualdade
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(numbers[0], specialMath[3], specialMath[5], specialMath[6]), BUTTON_HEIGHT)
              		)
            )
        );
	}	

}
