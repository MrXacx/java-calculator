package com.calculator.design;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import com.calculator.design.LayoutFactory;
/**
 * @author Ariel Santos
 * @author Italo Cerqueira
 * @author Maria França
 * @author Pedro Santos
 * @version 3.0
 * @since 1.0
 */
public class Panel extends JPanel {
	private LayoutFactory layout;
	private JPanel screen;
    public Panel() {
        this.initComponents();
        this.initLayout();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
    	screen = new JPanel(); 	       
    	layout = new LayoutFactory();
    	layout.createComponents(screen);
	}
    
    @SuppressWarnings("unchecked")
    private void initLayout(){
    
        GroupLayout screenLayout = new GroupLayout(screen); // Determina alinhamento do painel de cálculos
		GroupLayout containerLayout = new GroupLayout(this); // Cria layout geral

		screen.setLayout(screenLayout); // Define o layout que o painel deve utilizar
        layout.setHorizontalScreenLayout(screenLayout); // Define layout horizontal do painel
        layout.setVerticalScreenLayout(screenLayout); // Define layout vertical
     
        this.setLayout(containerLayout); // Define o layout que o painel deve utilizar
        layout.setHorizontalGeneralGroup(containerLayout); // Define layout horizontal do painel
        layout.setVerticalGeneralGroup(containerLayout); // Define layout vertical

    }
}
