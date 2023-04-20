package com.calculator.service;


/**
 * @author Ariel Santos
 * @author Maria França
 * @author Pedro Santos
 * @version 2.0
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
	
	public Double getResult() throws Exception{
	    /**
	     * @return Resultado da operação definida
	     * @since 1.0
	     * @version 1.0 
	     * @throws Exception
	     */
		
		double result;
	    switch (this.operatorID) {
	        case "+": // Caso soma tenha sido selecionada
	            result = portion[0] + portion[1];
	            break;
	        case "-": // Caso subtração tenha sido selecionada
	            result = portion[0] - portion[1];
				break;
	        case "x": // Caso multiplicação tenha sido selecionada
	            result = portion[0] * portion[1];
				break;
	        case "÷": // Caso divisão tenha sido selecionada		           
	            result = portion[1].equals(0) ? Double.NaN : portion[0] / portion[1];
				break;
	        default: // Caso valor inesperado esteja em this.operatorID
	            throw new Exception("Erro ao obter resultado matemático em Mathematics::getResult(). Operador utilizado: " + this.operatorID); // Emite exceção
	    }
	    
	    return Double.valueOf(result);
	}
	
	public Double getSubresult(double value, String subOperator) throws Exception{
		/**
		* @param fracionário a ser calculado
		* @param operador da subexpressão
		* @return resultado da subexpressão
		* @since 2.0
		* @version 3.0
		*/		
	
		double subresult;
		
		switch (subOperator) {
            case "%": // Caso porcentagem tenha sido selecionada         
                 subresult = portion[0] * value/100;
                 break;    
            case "√": // Caso radiciação tenha sido selecionada
                subresult = Math.sqrt(value);
 				break;
            case "x²": // Caso potenciação tenha sido selecionada      
                subresult = (value * value);
                break;
            case "1/x": // Caso o inverso do número tenha sido selecionado
                subresult =  1/value;
                break;      
            case "±": // Caso a alteração de sinal tenha sido selecionada             
                subresult = value * (-1); 
                break; 
            default: // Caso valor inesperado esteja em this.operatorID
                
                throw new Exception("Erro ao obter resultado matemático em Mathematics::getSubresult(). Operador utilizado: " + subOperator); // Emite exceção
        }
        
        return Double.valueOf(subresult);
	}
}
