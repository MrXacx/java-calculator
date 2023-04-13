package com.calculator.service;

public class Mathematics{
	private String[] portion;
	private String operatorID;
	
	public Mathematics(){
		this.portion = new String[2];
	}
	
	public void setPortion(double number, int id){
		this.portion[id] = Double.valueOf(number).toString();
	}
	
	public boolean isset(int id){
		return portion[id].isBlank();
	}
	
	public void setOperatorID(String operatorID){
		this.operatorID = operatorID;
	}
	
	public double getResult(){
		// TODO: Código que retorna determinado cálculo de acordo com operatorID, USAR SWITCH-CASE
		return 0.0;
	}
	
	public void close(){
		this.portion[0] = "";
		this.portion[1] = "";;
		this.operatorID = "";;
	}
}
