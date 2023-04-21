package com.calculator.design;

// Classe de componentes
import java.awt.Component;

// Classes de layout
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;

// Classes de vetores
import java.util.Arrays;
import java.util.List;

/**
 * @author Maria França
 * @author Pedro Santos
 */

// Classe interna
import com.calculator.design.ComponentsFactory;

public class LayoutFactory extends ComponentsFactory{

	public void setHorizontalScreenLayout(GroupLayout screenLayout){
    	/** 
    	* @param layout do label
    	*/
    
        screenLayout.setHorizontalGroup(
            screenLayout.createSequentialGroup() // Cria grupo de componentes paralelos
        	.addComponent(standBy, 110, 110, 110) // adiciona label de standBy com comprimento 110
            .addGap(20, 45, 45)
        	.addComponent(content, 110, 110, 110) // adiciona label de conteúdo com comprimento 110
        );
    }
    
    public void setVerticalScreenLayout(GroupLayout screenLayout){
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
    	* @return Fileira de componentes
    	*/
    	
    	for(Component comp : componentList.toArray(new Component[0]))
    		group = group.addComponent(comp, preferredSize-5, preferredSize, preferredSize+15);

    	return group;
    }

    public void setHorizontalGeneralGroup(GroupLayout layout){
    	final int BUTTON_WIDTH = 55; // Determina tamanho horizontal padrão

    	layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(screen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup( // Linha com Memory Cleat, Memory Recall, Memory Add, Memory Subtract
                	this.alignComponents(layout.createSequentialGroup(), Arrays.asList(systemFunc[3], systemFunc[4], systemFunc[5], systemFunc[6]), BUTTON_WIDTH)
                )                          
                .addGroup( // Linha com porcentagem, raíz quadrada, potência quadrada, inverso
                	this.alignComponents(layout.createSequentialGroup(), Arrays.asList(specialMath[4], specialMath[0], specialMath[2], specialMath[1]), BUTTON_WIDTH)
                )
                .addGroup( // Linha 7, 8, 9 e divisão
                    this.alignComponents(layout.createSequentialGroup(), Arrays.asList(numbers[7], numbers[8], numbers[9], basicMath[1]), BUTTON_WIDTH)
                )
                .addGroup( // Linha com Clear, Clear Entry, Delete e multiplicação
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
    
    public void setVerticalGeneralGroup(GroupLayout layout){
    	final int BUTTON_HEIGHT = 45;
    		
    	layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(screen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup( // Linha com Memory Cleat, Memory Recall, Memory Add, Memory Subtract
		            	this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(systemFunc[3], systemFunc[4], systemFunc[5], systemFunc[6]), BUTTON_HEIGHT)
		            )       
					.addGroup( // Linha com porcentagem, raíz quadrada, potência quadrada, inverso
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(specialMath[0], specialMath[4], specialMath[1], specialMath[2]), BUTTON_HEIGHT)
              		)
              		.addGroup( // Linha com Clear, Clear Entry, divisão, e Delete
				            this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), Arrays.asList(systemFunc[0], systemFunc[1], basicMath[0], systemFunc[2]), BUTTON_HEIGHT)
              		)
              		.addGroup( // Linha com 8, multiplicação, 9 e 7
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
