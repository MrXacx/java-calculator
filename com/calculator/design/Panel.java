package com.calculator.design;
// Início de classes de componentes do frame
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
// Fim de classes de componentes do frame

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
 *
 * @author Ariel Santos
 * @author Italo Cerqueira
 * @author Maria França
 * @author Pedro Santos
 * @version 3.0
 * @since 1.0
 */
public class Panel extends JPanel {
	// Botões da calculadora
    private JButton[] basicMath, specialMath, numbers, systemFunc; // operadores matemáticos básicos, operadores matemáticos especias, algarismos, botões do sistema

    // Labels de conteúdo
    private JLabel content, standbye; // número atual, número em standbye para operação

    // Painel de exibição
    private JPanel screen;

    // Manipulador da classe de cálculos
	private Mathematics math;
	
    public Panel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        //math = new Mathematics();
        // Inicia configuração da label de cálculos
        content = new JLabel();
        content.setText(" ");
        // Finaliza configuração da label de cálculos
        
        // Inicia configuração da label de standbye
        standbye = new JLabel();
        standbye.setText(" ");
        // Finaliza configuração da label de standbye

        // Inicia configuração de botões
        numbers = this.initNumericButtons(new JButton[10]); // Cria botões numéricos
        systemFunc = this.initSystemButtons(); // Cria botões com funções de sistema
        basicMath = this.initBasicMathSignButtons(); // Cria operadores matemáricos
        specialMath = this.initSpecialMathSignButtons(); // Cria demais sinais matemáticos
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

        // Índice para percorrer array
        int index = 0;
    	
        // Percorre todo o array
    	while (index < buttons.length){
            buttons[index++] = new JButton(); // Instancia posição do array e incrementa índice ao fim
        }
        
        return buttons;
    }
    
    private JButton[] initSystemButtons(){
        /**
         * @return Array de botões com funções não-numéricas configuradas
         * @version 1.1
         * @since 2.0
         */

    	// Cria array genérico de JButton com 3 espaços
        JButton[] buttons = this.createGenericButtons(new JButton[3]);
    	 
        // Inicia configuração do botão "C"
        buttons[0].setText("C");
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               content.setText(" "); // Limpa todo o valor da atual entrada de dados
               standbye.setText(" ");
            }
        }); 
        // Finaliza configuração do botão "C"

        // Inicia configuração do botão "CE"
        buttons[1].setText("CE");
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               content.setText(" "); // Limpa todo o valor da atual entrada de dados 

            }
        }); 
        // Finaliza configuração do botão "CE"

        // Inicia configuração do botão "Del"
        buttons[2].setText("Del");
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               String text = content.getText();
               int strlen = text.length();
               
               if(strlen > 1){ // Evita que espaçamento de 1 caracter seja deletado
				   content.setText(
					  (new StringBuilder(text)).deleteCharAt(--strlen).toString() // Retorna a string sem o último caractere
				   );
			   }
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

        // Cria array genérico de JButton com 4 espaços
        JButton[] basicMathSign = this.createGenericButtons(new JButton[4]);

        // Inicia configuração do botão de divisão
        basicMathSign[0].setText("÷");
        // Finaliza configuração do botão de divisão

        // Inicia configuração do botão de multiplicação
        basicMathSign[1].setText("x");
        // Finaliza configuração do botão de multiplicação

        // Inicia configuração do botão de subtração
        basicMathSign[2].setText("-");
        // Finaliza configuração do botão de subtração

        // Inicia configuração do botão de adição
        basicMathSign[3].setText("+");
        
        // Inicia configuração do listener geral de botões de operações básicas
        for(int index = 0; index < basicMathSign.length; index++){
            // Garante que todos os botões serão incluídos

            final String op = basicMathSign[index].getText(); // Obtém operador como string final

            // Adiciona listener
            basicMathSign[index].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String nContent = content.getText(); // Obtém cópia do texto de content
                    String nStandbye = standbye.getText(); // Obtém cópia do texto de standbye

                    if(nContent.isBlank()){
                        // Executa caso não haja valores em content

                        if(math instanceof Mathematics){
                            // Executa se math já estiver instanciada
                            math.setOperatorID(op); // Altera operador a ser utilizado
                            standbye.setText(nStandbye.substring(0, nStandbye.length()-1) + op); // Altera operador exibido
                        }

                        return; // Impede que função continue a ser executada
                    }

                    /*else if(nStandbye.isBlank()){
                        // Executa standbye estiver vazio, ou seja, o primeiro valor da operação está sendo inserido

                       
                    
                    }*/
                    else if(!nStandbye.isBlank()){
                        // Executa standbye não estiver vazio, ou seja, esse é o segundo valor da operação
                        // Operação sequenciais terão o resultado do primeiro cálculo como primeiro valor da operação seguinte

                        math.setPortion(Double.valueOf(nContent), 1); // Passa valor da segunda posição

                        try {
                            nContent = (Double.valueOf(math.getResult()).toString()); //  Armazena resultado do cálculo anterior
                        } catch (Exception e) {
                            // Executa em caso de exceção emitida

                            content.setText(" "); // Limpa conteúdo da tela
                            Core.error(e); // Chama método de encerramento de emergência

                            // Código será encerrado aqui
                        }

                    }
                    
                    math = new Mathematics(); // Intancia classe de cálculos
                    math.setPortion(Double.valueOf(nContent), 0); // Pass primeira parcela da operação
                    math.setOperatorID(op); // Define operador
                    
                    standbye.setText(" " +nContent+" "+op); // Informa valor passado e operação escolhida
                    content.setText(" ");
                }
            });
        }
        // Finaliza configuração do listener geral de botões de operações básicas

        return basicMathSign; // Retorna botões configurados
    }

    private JButton[] initSpecialMathSignButtons(){
        /**
         * @return Array de botões com operações matemáticas complementares configuradas
         * @version 1.1
         * @since 2.0
         */

        // Cria array genérico de JButton com 7 espaços
    	JButton[] SpecialMathSign = this.createGenericButtons(new JButton[7]);
    	
        // Inicia configuração do botão de raíz quadrada
        SpecialMathSign[0].setText("√");
        // Finaliza configuração do botão de raíz quadrada

        // Inicia configuração do botão de porcentagem
        SpecialMathSign[1].setText("%");
        // Finaliza configuração do botão de porcentagem

        // Inicia configuração do botão de 
        SpecialMathSign[2].setText("1/x");
        // Finaliza configuração do botão de 

        // Inicia configuração do botão de potência quadrada
        SpecialMathSign[3].setText("x²");
        // Finaliza configuração do botão de potência quadrada

        // Inicia configuração do botão de mais ou menos
        SpecialMathSign[4].setText("±");
        // Finaliza configuração do botão de mais ou menos

        // Inicia configuração do botão de igualdade
        SpecialMathSign[5].setText("=");
        SpecialMathSign[5].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!(standbye.getText().isBlank() || content.getText().isBlank())){
                    // Executa somente quanto standbye e content estão preenchidos

                	math.setPortion(Double.valueOf(content.getText()), 1); // Passa segunda parcela do cálculos

                    try {
                        content.setText(Double.valueOf(math.getResult()).toString()); //  Armazena resultado do cálculo anterior
                    } catch (Exception e) {
                        // Executa em caso de exceção emitida

                        content.setText(" "); // Limpa conteúdo da tela
                        Core.error(e); // Chama método de encerramento de emergência

                        // Código será encerrado aqui
                    }

                	standbye.setText(" "); // Limpa standbye
                }
            }
        });
        // Finaliza configuração do botão de igualdade

        // Inicia configuração do botão de vírgula
        SpecialMathSign[6].setText(".");
        SpecialMathSign[6].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(!content.getText().contains(".")) // Executa caso não haja "," na string
                    content.setText(content.getText() + "."); // Adiciona "," ao final da string
            }
        });
        // Finaliza configuração do botão de vírgula

        return SpecialMathSign; // Retorna botões configurados
    }
    
    private JButton[] initNumericButtons(JButton[] numericButtons){
    	/**
         * @param Array genérico de JButton
         * @return Array de botões numéricos com índice inicial 0
         * @version 1.1
         * @since 1.0
         */
        
        // Percorre todo o array passado por parâmetro
        for (int index = 0; index < numericButtons.length; index++){
            // instancia espaço do array
            numericButtons[index] = new JButton();
            
            /**
             * Listener parece oferecer resistência ao uso de variáveis de valor constante
             * 
             * TODO: tentar utitlizar a variável sem a palavra reservada "final"
             * TODO: pesquisar o porquê i aceita sobreposição, mesmo assemelhando-se a uma constante
             */
            final String i = Integer.toString(index); 
            
            // Determina texto de exibição
            numericButtons[index].setText(i);
            
            // Listener padrão
            numericButtons[index].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String nContent = content.getText();

                    content.setText( (nContent.charAt(0) == ' ' ? nContent : " ") + i); // Insere número ao fim da string
                }
            });
        }

        return numericButtons; // Retorna botões configurados
    }

    /**
     *  INÍCIO DE MÉTODOS PARA CONFIGURAÇÃO DE LAYOUT
     *      INÍCIO DE MÉTODOS DE LAYOUT DO VISOR DE CÁLCULOS
     */

    private void setHorizontalScreenLayout(GroupLayout screenLayout){
    	/** 
    	* @param layout do label
    	* @version 1.0
    	* @since 1.0
    	*/
    	
        screenLayout.setHorizontalGroup(
            //layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            screenLayout.createSequentialGroup() // Cria grupo de componentes paralelos
        	.addComponent(standbye, 110, 110, 110) // adiciona label de standbye com comprimento 110
            .addGap(10, 30, 50)
        	.addComponent(content, 110, 110, 110) // adiciona label de conteúdo com comprimento 110
        );
    }
    
    private void setVerticalScreenLayout(GroupLayout screenLayout){
        screenLayout.setVerticalGroup(
            screenLayout.createParallelGroup(GroupLayout.Alignment.LEADING) // Cria grupo de componentes paralelos
			.addGroup(screenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            	.addComponent(standbye, 40, 40, 40) // adiciona label de standbye com comprimento 40
                .addComponent(content, 40, 40, 40)) // adiciona label de conteúdo com comprimento 40
        );
    }
        // FIM DE MÉTODOS DE LAYOUT DO VISOR DE CÁLCULOS
    
        // INÍCIO DE MÉTODOS DE LAYOUT DO PAINEL GERAL
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
    	
    	for(Component comp : componentList.toArray(new Component[0])){
    		group = group.addComponent(comp, preferredSize, preferredSize, preferredSize);
    	}
    	return group;
    }


    private void setHorizontalGeneralGroup(GroupLayout layout){
    	// Determina tamanho horizontal padrão
    	final int BUTTON_WIDTH = 55;

    	layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(screen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                
                 // INÍCIO DA COLUNA 1
                .addGroup(
                	this.alignComponents(
                		layout.createSequentialGroup(),  // Grupo de componentes paralelos
                		Arrays.asList(specialMath[1], specialMath[0], specialMath[3], specialMath[2]), // Componentes a serem enfileirados
                		BUTTON_WIDTH
                	)
                )
                // FIM DA COLUNA 1
                
                // INÍCIO DA COLUNA 2
                .addGroup(
                    this.alignComponents(
                		layout.createSequentialGroup(), // Grupo de componentes paralelos
                		Arrays.asList(numbers[7], numbers[8], numbers[9], basicMath[1]), // Componentes a serem enfileirados
                		BUTTON_WIDTH
                	)
                )
                // FIM DA COLUNA 2
                
                // INÍCIO DA COLUNA 3
                .addGroup(
                    this.alignComponents(
                		layout.createSequentialGroup(), // Grupo de componentes paralelos
                		Arrays.asList(systemFunc[0], systemFunc[1], systemFunc[2], basicMath[0]), // Componentes a serem enfileirados
                		BUTTON_WIDTH
                	)
               )
               // FIM DA COLUNA 3
               
               // INÍCIO DA COLUNA 4
               .addGroup(
                    this.alignComponents(
                		layout.createSequentialGroup(), // Grupo de componentes paralelos
                		Arrays.asList(numbers[4], numbers[5], numbers[6], basicMath[2]), // Componentes a serem enfileirados
                		BUTTON_WIDTH
                	)
               )
               // FIM DA COLUNA 4
               
               // INÍCIO DA COLUNA 5
              .addGroup(
                    this.alignComponents(
                		layout.createSequentialGroup(), // Grupo de componentes paralelos
                		Arrays.asList(numbers[1], numbers[2], numbers[3], basicMath[3]), // Componentes a serem enfileirados
                		BUTTON_WIDTH
                	)
               )
               // FIM DA COLUNA 5
               
               // INÍCIO DA COLUNA 6
              .addGroup(
                    this.alignComponents(
                		layout.createSequentialGroup(), // Grupo de componentes paralelos
                		Arrays.asList(specialMath[4], numbers[0], specialMath[6], specialMath[5]), // Componentes a serem enfileirados
                		BUTTON_WIDTH
                	)
               )
               // FIM DA COLUNA 6
     
        );
    }
    
    private void setVerticalGeneralGroup(GroupLayout layout){
    	final int BUTTON_HEIGHT = 45;
    		
    	layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(screen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
					
					// INÍCIO DA LINHA 1
					.addGroup(
				            this.alignComponents(
				        		layout.createParallelGroup(GroupLayout.Alignment.BASELINE),
				        		Arrays.asList(specialMath[0], specialMath[1], specialMath[2], specialMath[3]),
				        		BUTTON_HEIGHT
				        	)
              		)
              		// FIM DA LINHA 1
              		
              		// INÍCIO DA LINHA 2
              		.addGroup(
				            this.alignComponents(
				        		layout.createParallelGroup(GroupLayout.Alignment.BASELINE),
				        		Arrays.asList(systemFunc[0], systemFunc[1], basicMath[0], systemFunc[2]),
				        		BUTTON_HEIGHT
				        	)
              		)
              		// FIM DA LINHA 2
              		
              		// INÍCIO DA LINHA 3
              		.addGroup(
				            this.alignComponents(
				        		layout.createParallelGroup(GroupLayout.Alignment.BASELINE),
				        		Arrays.asList(numbers[8], basicMath[1], numbers[9], numbers[7]),
				        		BUTTON_HEIGHT
				        	)
              		)
              		// FIM DA LINHA 3
              		
              		// INÍCIO DA LINHA 4
					.addGroup(
				            this.alignComponents(
				        		layout.createParallelGroup(GroupLayout.Alignment.BASELINE),
				        		Arrays.asList(numbers[5], numbers[4], basicMath[2], numbers[6]),
				        		BUTTON_HEIGHT
				        	)
              		)
              		// FIM DA LINHA 4
              		
              		// INÍCIO DA LINHA 5
					.addGroup(
				            this.alignComponents(
				        		layout.createParallelGroup(GroupLayout.Alignment.BASELINE),
				        		Arrays.asList(numbers[2], numbers[1], basicMath[3], numbers[3]),
				        		BUTTON_HEIGHT
				        	)
              		)
              		// FIM DA LINHA 5
              		
              		// INÍCIO DA LINHA 6
              		.addGroup(
				            this.alignComponents(
				        		layout.createParallelGroup(GroupLayout.Alignment.BASELINE),
				        		Arrays.asList(numbers[0], specialMath[4], specialMath[5], specialMath[6]),
				        		BUTTON_HEIGHT
				        	)
              		)
              		// FIM DA LINHA 6
                    //.addGap(0, 19, Short.MAX_VALUE)
                )
        );
    }
    /**
     *      FIM DE MÉTODOS DE LAYOUT DO PAINEL GERAL
     *  FIM DE MÉTODOS PARA CONFIGURAÇÃO DE LAYOUT
     */

}