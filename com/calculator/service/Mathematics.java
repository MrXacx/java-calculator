package com.calculator.service;


/**
 * @author Ariel Santos
 * @author Pedro Santos
 * @version 1.0
 * @since 3.0
 */

public class Mathematics{

	private Double[] portion; // Números do cálculo
	private String operatorID; // Operador a ser utilizado
	
	public Mathematics(){
		this.portion = new Double[2]; // Instancia array de duas posições
	}
	
	public void setPortion(Double number, int id){
		/**
		 * @param número a ser calculado
		 * @param posição do número do cálculo
		 * @since 1.0
		 * @version 1.0
		 */
		this.portion[id] = number;
	}
	
	
	public void setOperatorID(String operatorID){
		/**
		 * @param operador do cálculo
		 * @since 1.0
		 * @version 1.0
		 */
		this.operatorID = operatorID;
	}
	
	public double getResult() throws Exception{
		/**
		 * @return Resultado da operação definida
		 * @since 1.0
		 * @version 1.0 
		 * @throws Exception
		 */

		switch (this.operatorID) {
            case "+":
				// Caso soma tenha sido selecionada
                return portion[0] + portion[1];

            case "-":
				// Caso subtração tenha sido selecionada
				return portion[0] - portion[1];

            case "x":
				// Caso multiplicação tenha sido selecionada
				return portion[0] * portion[1];

            case "÷":
				// Caso divisão tenha sido selecionada
				return portion[1].equals(0) ? Double.NaN : portion[0] / portion[1];
			default:
				// Caso valor inesperado esteja em this.operatorID
				throw new Exception("Erro ao obter resultado matemático em Mathematics::getResult(). Operador utilizado: " + this.operatorID); // Emite exceção
        }
	}
}
