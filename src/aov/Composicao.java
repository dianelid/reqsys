package aov;

import java.io.*;
import java.util.*;

import analise.JavaCharStream;


public class Composicao {
	static java.util.Vector<String> componentes = new java.util.Vector<String>();
	static java.util.Vector<String> intertypedeclarationvct = new java.util.Vector<String>();
	static java.util.Vector<String> advicevct = new java.util.Vector<String>();
	static java.util.Vector<String> composicaovct = new java.util.Vector<String>();
	static java.util.Vector<String> filhosvct = new java.util.Vector<String>();
	static java.util.Hashtable<String, String> paishash = new java.util.Hashtable<String, String>();
	static String arquivo = "composicao", diretorio;
	
	public Composicao(String dir){
		diretorio=dir;
	}
	
	public Composicao(){
	}
	
	public aovgraph criaParser(){
		aovgraph parser = null;
		//System.out.println("Composicao chamaVerificacao");
			try{
		 parser = new aovgraph(new aovgraphTokenManager
								(new JavaCharStream
								(new FileReader
								(new File("C://aovgraph.txt")))));
		 //System.out.println("COMPOSICAO - Arquivo de entrada gerado ");
			}catch (FileNotFoundException e) {
				System.out.println("COMPOSICAO - File not found. Exiting. Excessao: "+ e);
			}
		return parser;
	}
	
	public void geraArquivoComposicao(Vector<String> composicaovct){
  	    String composicaostr = new String();
  	    
  	    //System.out.println("Gerando composiçao");
		for (int i = 0; i < composicaovct.size(); i++) {
  		     composicaostr += composicaovct.elementAt(i);}
		//System.out.println("composicaostr: "+ composicaostr);
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter(diretorio+"\\composicao.html"));
            out.write(composicaostr); 
            out.close();

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	}
	
	public void geraAdvicearound(String afetado, String adicionado, String Arquivo, String primitiva)
	{
		@SuppressWarnings("unused")
		Vector<String> compDescr = componentes;
		@SuppressWarnings("unused")
		Vector<String> compFilhosvct = filhosvct;
		@SuppressWarnings("unused")
		Hashtable<String, String> parentesHsh = paishash;
		
		Advice adviceclass = new Advice();
	 	//adviceclass.adviceTokens(advice, compDescr, compFilhosvct, parentesHsh, arquivo);
		if(primitiva.equals("include"))
		adviceclass.advicearoundArquivo(afetado, adicionado, Arquivo);
		
		if(primitiva.equals("substitute"))
		adviceclass.advicesubstituteArquivo(afetado, adicionado, Arquivo, "advicebefore");
	}
	
	public void geraAdvicebefore(String afetado, String adicionado, String Arquivo, String primitiva)
	{
		@SuppressWarnings("unused")
		Vector<String> compDescr = componentes;
		@SuppressWarnings("unused")
		Vector<String> compFilhosvct = filhosvct;
		@SuppressWarnings("unused")
		Hashtable<String, String> parentesHsh = paishash;
		
		Advice adviceclass = new Advice();
	 	//adviceclass.adviceTokens(advice, compDescr, compFilhosvct, parentesHsh, arquivo);
		if(primitiva.equals("include"))
		adviceclass.advicebeforeArquivo(afetado, adicionado, Arquivo);
		
		if(primitiva.equals("substitute"))
		adviceclass.advicesubstituteArquivo(afetado, adicionado, Arquivo, "advicebefore");
	}
	
	public void geraAdviceafter(String afetado, String adicionado, String Arquivo, String primitiva)
	{
		@SuppressWarnings("unused")
		Vector<String> compDescr = componentes;
		@SuppressWarnings("unused")
		Vector<String> compFilhosvct = filhosvct;
		@SuppressWarnings("unused")
		Hashtable<String, String> parentesHsh = paishash;
		
		Advice adviceclass = new Advice();
	 	//adviceclass.adviceTokens(advice, compDescr, compFilhosvct, parentesHsh, arquivo);
		if(primitiva.equals("include"))
		adviceclass.adviceafterArquivo(afetado, adicionado, Arquivo);
		
		if(primitiva.equals("substitute"))
		adviceclass.advicesubstituteArquivo(afetado, adicionado, Arquivo, "adviceafter");
	}
	
	public void geraIntertype(String afetado, String intertype, String arquivo, String primitiva){
		//String intertype = intertypei;
		Vector<String> compDescr = componentes;
		Hashtable<String, String> parentesHsh = paishash;
		
		Intertype intertypeclass = new Intertype();
		intertypeclass.intertypeComposicao(afetado, intertype, arquivo);
	  }
	
	
	public String adicionaReferencia(String adicionado){
		String referencia = new String();
		
		Intertype intertypeclass = new Intertype();
		referencia = intertypeclass.adicionaReferencia(adicionado);
		
		return referencia;
	  }

	
	public boolean verificaIntertype(String intertype, Vector<String> componentes){
		//System.out.println("Intertype: " + intertype);
		//System.out.println("Componentes: " + componentes);
		//System.out.println("verificaIntertype!");
		boolean jaDeclarado = false;
		//System.out.println("intertype - " + intertype);
		//System.out.println("componentes - " + componentes);
		java.util.Vector<String> intertypeVerificado = new java.util.Vector<String>();
		//for(int i=0; i < intertype.size(); i++){
		//for(int i=0; i<intertype.size(); i++){
			for(int j=0; j < componentes.size(); j++){
				if((intertype.startsWith(componentes.elementAt(j)))){
					
					intertypeVerificado.addElement(componentes.elementAt(j));
					//System.out.println("Já declarado");
					jaDeclarado = true;

				
				}
			}
		//}
		//}
		//System.out.println("intertypeVerificado - " + intertypeVerificado);
	    //System.out.println("jaDeclarado - " + jaDeclarado);
		return jaDeclarado;
	}
	
	
}