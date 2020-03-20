package rastreability;

import java.util.Vector;

public class Linha {
	private Vector<String> colunas = new Vector<String>();
	private Vector<String> celula = new Vector<String>();
	private int contacels[];
	private String IDlinha = new String();
	private String coluna = new String();
	
	public Linha(String linha){
		//System.out.println("linha: " + linha);
		//IDentificador = id;	
		//linhas.addElement(linha);
		IDlinha = linha;
		//celula = cell;
	}
	
	public Linha(String linhaatual, String col) {
		IDlinha = linhaatual;
		coluna = col;
		colunas.addElement(col);
		//System.out.println("linhaatual: " + linhaatual);
		//System.out.println("col: " + col);
	}
	
	public Linha(String linhaatual, Vector<String> col) {
		IDlinha = linhaatual;
		colunas = col;
		//System.out.println("linhaatual: " + linhaatual);
		//System.out.println("col: " + col);
	}

	public void setColunas(Vector<String> coluna){
		//System.out.println("coluna: " + coluna);
		colunas = coluna;
	}
	
	public void addinColunas(String coll){
		//System.out.println("ADD");
		//System.out.println(coll);
		
		if(!colunas.contains(coll)){
		colunas.addElement(coll);}
	}
	
	public void setColunas(String coll){
		coluna = coll;
		if(!colunas.contains(coll)){
			colunas.addElement(coll);}
	}
	
	public void setLinha(String linha){
		IDlinha = linha;
	}
	
	public Vector<String> getColunas(){
		return colunas;
	}
	
	public String getColuna(){
		//colunas.addElement(coluna);
		return coluna;
	}
	
	public Vector<String> getCelula(){
		return celula;
	}
	
	public String getLinhas(){
		return IDlinha;
	}

	public void setCelula(String celula2) {
		celula.addElement(celula2);
	}
	
	
	
	public void contaCelulas(int contacel) {
		
		//int i = contacels.length;
		//System.out.println(i);
		//if(!(contacels.length>0)){
		contacels[0]=contacel;
		//i++;
		//}//else{
		//	contacels[i]=contacel;	
		//}
	}
	
	public int[] getqtdeCels() {
		return contacels;
	}

}
