package com.calculator.service;


/**
 * @author Ariel Santos
 * @version 1.0
 * @since 2.0
 */

public class Mathematics{
	private String[] portion;
	private String operatorID;
	
	public Mathematics(){
		/**
		 * @version 1.0
		 * @since 1.0
	 	 */
		
		this.portion = new String[2];
	}
	
	public void setPortion(double number, int id){
		/**
		 * @param valor a ser inserido no array para cálculo
		 * @param posição do array que o valor deve ocupar
	     * @version 1.0
	     * @since 1.0
		 */
		
		this.portion[id] = Double.valueOf(number).toString();
	}
	
	public boolean isset(int id){
		/**
		 * @param posição da parcela no array
		 * @return a existência de algum valor diferente de espaços na posição determinada
		 * @version 1.0
		 * @since 1.0
		 */
		
		return !portion[id].isBlank();
	}
	
	public void setOperatorID(String operatorID){
		/**
		 * @param Operador matemático a ser utilizado
		 * @version 1.0
		 * @since 1.0
		 */
		
		this.operatorID = operatorID;
	}
	
	public double getResult(){
		/**
		 * @return Cálculo realizado com base do operador
		 * @version 1.0
		 * @since 1.0
		 */
		
		// TODO: Código que retorna determinado cálculo de acordo com operatorID, USAR SWITCH-CASE
		return 0.0;
	}
	
	public void close(){
		/**
		 * @version 1.0
		 * @since 1.0
		 */
		
		// Limpa todos os atributos da classe
		this.portion[0] = "";
		this.portion[1] = "";;
		this.operatorID = "";;
	}
}
