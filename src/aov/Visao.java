package aov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import analise.JavaCharStream;

public class Visao {
	static String arquivo = "visao";
	
	public aovgraph geraVerificacao(){
		aovgraph parser = null;
		//System.out.println("VISAO geraVerificacao");
			try{
		 parser = new aovgraph(new aovgraphTokenManager
								(new JavaCharStream
								(new FileReader
								(new File("C://aovgraph.txt")))));
		 //System.out.println("VISAO - Arquivo de entrada gerado ");
			}catch (FileNotFoundException e) {
				System.out.println("VISAO - File not found. Exiting. Excessao: "+ e);
			}
		return parser;
		
	}
	
	public void geraMatrizTrans(){
		
		
  	    String visaostr = new String(" rastreability_matrix(crosscutting; default){");
  	    File matriz = new File("C://matriz.txt");
  	    if(matriz.exists()){
  	    	matriz.delete();	
  	    }
  	    //System.out.println("Gerando MATRIZTRANS");
		
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
            out.write(visaostr); // " " = quebra de linha no  Windows 
            out.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	}
	
	public void geraMatrizMer(){
		
  	    String visaostr = new String(" rastreability_matrix(ERmodel; default){");
  	    File matriz = new File("C://matriz.txt");

  	    //System.out.println("Gerando MATRIZTRANS");
		
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
            out.write(visaostr); // " " = quebra de linha no  Windows 
            out.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	}
	
	public void geraMer(String nomedoarquivo){
  	    String visaostr = new String("entity_relationship_model(default){");
  	    
  	    //System.out.println("Gerando MATRIZTRANS");
		
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter(nomedoarquivo + "ermodel.txt"));
            out.write(visaostr); // " " = quebra de linha no  Windows 
            out.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	}
	
	public void geraAovgraph(String nomedoarquivo){
  	    String visaostr = new String("digraph aov{");
  	    
  	    //System.out.println("Gerando MATRIZTRANS");
		
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter(nomedoarquivo + "aovgraph.dot"));
            out.write(visaostr); // " " = quebra de linha no  Windows 
            out.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	}
	
public boolean atualizaAovgraph(String aovdot, String nomedoarquivo){
		
		try {
			
			String strvisao = new String();
			
			//System.out.println("CLASSE ADVICE: partes - " + partes[0]);
			
			BufferedReader in = new BufferedReader(new FileReader(nomedoarquivo + "aovgraph.dot"));                       
			  while (in.ready()) {                
				  strvisao = strvisao+in.readLine();       }  
			 
			 
	        BufferedWriter out = new BufferedWriter(new FileWriter(nomedoarquivo + "aovgraph.dot"));
	        //out.write(strvisao + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
	        out.write(strvisao+aovdot+"}"); 
	        out.close();

	    }
	    catch(IOException e){
	        // possiveis erros são tratados aqui
	    	}
	    
	    return true;
	}
	
	public void geraAdvice(String advice, Vector<String> componentes, Vector<String> filhosvct, Hashtable<String, String> paishash, String arquivo){
		//System.out.println("VISAO geraAdvice" );
		Advice adviceclass = new Advice();
		//System.out.println("advice: " + advice);
		//System.out.println("componentes: " + componentes);
		//System.out.println("filhosvct: " + filhosvct);
		//System.out.println("paishash: " + paishash);
	 	adviceclass.adviceTokens(advice, componentes, filhosvct, paishash, arquivo);
	}
	
	public void geraIntertype(String advice, Vector<String> componentes, Hashtable<String, String> paishash){
		//System.out.println("VISAO geraIntertype" );
		Intertype intertypeclass = new Intertype();
	 	intertypeclass.intertypeTokens(advice, componentes, paishash, arquivo);
	}

	public boolean finalizaMatrizTrans() {
		//System.out.println("VISAO finalizaMatrizTrans");
		String strvisao = new String();
		try {
		
			  BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
			  while (in.ready()) {                
				  strvisao = strvisao+in.readLine();       }    
			  
	        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
	        out.write(strvisao+"}"); 
	        out.close();
	        
	        
	        //outmatriz.write(strmatriz + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
	        //outmatriz.close();
	    }
	    catch(IOException e){
	        // possiveis erros são tratados aqui
	    	}
	    
	    try {
			RandomAccessFile file = new RandomAccessFile("C://matriz.txt", "rw");
			file.seek(0);
	    
	    } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return true;
	}
	
	public void geraMatrizRel(){
    String visaostr = new String(" rastreability_matrix(contribution; default){");
    @SuppressWarnings("unused")
	File matriz = new File("C://matriz.txt");    
  	    //System.out.println("Gerando MATRIZCONTR");
		
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
            out.write(visaostr); // " " = quebra de linha no  Windows 
            out.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	}
	
	public String organizaMatrizMer(String linha, String coluna, String celula) {
		//System.out.println("organiza");
		String matrizRel = new String();
		
	//for(int i=0; i<paisvct.size(); i++){
		//if(!paisrepetidos.contains(paisvct.elementAt(i))){
		matrizRel = matrizRel + "line(" 
		+ linha  
		+ ");" 
		+ "column("
		+ coluna
		+ ");"
		

		+ "cell("
		+ celula + ");"; 
		//}
	//}
	
	//System.out.println("matrizRel: "+ matrizRel);
	
	return matrizRel;
	
	}//FECHA METODO
	
	
	
	
	public String organizaMatrizRel(Hashtable<String, String> pais, Hashtable<String, String> contribui) {
		//System.out.println("organiza");
		String matrizRel = new String();
		@SuppressWarnings("unused")
		String linha = new String();
		Enumeration<String> enumeraPais = pais.elements();
		Enumeration<String> enumeraTipocontribui = contribui.elements();
		Enumeration<String> enumeraFilhoscontribui = contribui.keys();
		Vector<String> paisvct = new Vector<String>();
		Vector<String> contribuifilhosvct = new Vector<String>();
		Vector<String> contribuitipovct = new Vector<String>();
		@SuppressWarnings("unused")
		Vector<String> colunasvct = new Vector<String>();
		Vector<String> filhosvct = new Vector<String>();
		Vector<String> paisrepetidos = new Vector<String>();
		String cell = new String();
	    while(enumeraFilhoscontribui.hasMoreElements()){
	    	contribuifilhosvct.addElement((String)enumeraFilhoscontribui.nextElement());
	        //System.out.println("contribuifilhosvct: " + contribuifilhosvct);
	      }
	    
	    while(enumeraTipocontribui.hasMoreElements()){
	    	contribuitipovct.addElement((String)enumeraTipocontribui.nextElement());
	        //System.out.println(valor);
	      }
		
	    while(enumeraPais.hasMoreElements()){
	        paisvct.addElement((String)enumeraPais.nextElement());
	        //System.out.println(valor);
	      }
	    
	    //System.out.println("paisvctsize: "+ paisvct.size());
	    


	//System.out.println("PAIS:" + paisvct);

	Enumeration<String> enumeraFilho = pais.keys();

	while(enumeraFilho.hasMoreElements()){
	    filhosvct.addElement((String)enumeraFilho.nextElement());
	    //System.out.println(valor);
	  }

	//System.out.println("FILHOS:" + filhosvct);

	for(int i=0; i<filhosvct.size(); i++){
	  linha=pais.get(filhosvct.elementAt(i));
	  //System.out.println("linha: " + linha);
	}

	for(int i=0; i<paisvct.size(); i++){
		if(pais.containsValue(paisvct.elementAt(i))){
			
		}
		  //linha=pais.get(filhosvct.elementAt(i));
		  //System.out.println("linha: " + linha);
		}
	//System.out.println("filhosvctsize: "+ filhosvct.size());

	for(int i=0; i<paisvct.size(); i++){
		if(!paisrepetidos.contains(paisvct.elementAt(i))){
		matrizRel = matrizRel + "line(" 
		+ paisvct.elementAt(i) 
		+ ");" 
		+ "column("
		+ filhosvct.elementAt(i);
		
		for(int j=i+1; j<paisvct.size(); j++){
			if(paisvct.elementAt(i).equals(paisvct.elementAt(j))){
				//System.out.println(paisvct.elementAt(i) + " IGUAL " + paisvct.elementAt(j));
				//System.out.println("FILHO: " + filhosvct.elementAt(j));
				
				//paisrepetidos.addElement(paisvct.elementAt(i));
				
				matrizRel = matrizRel   
				+ ","
				+ filhosvct.elementAt(j);
			}	
		}
		
		matrizRel = matrizRel + ");" ;
		
		for(int k=0; k<contribuifilhosvct.size(); k++){
			
			if(filhosvct.elementAt(i).contains(contribuifilhosvct.elementAt(k))){
				//System.out.println("contem");
				//System.out.println(paisvct.elementAt(i) + " IGUAL " + paisvct.elementAt(j));
				//System.out.println("FILHO: " + filhosvct.elementAt(j));
				
				cell = contribuitipovct.elementAt(k);
				//paisrepetidos.addElement(paisvct.elementAt(i));
				//System.out.println("cell: " + cell);
				matrizRel = matrizRel + "cell(" + cell + ");" ;	
			}else{
			//System.out.println("Ñ contem");
			//System.out.println("filhosvct.elementAt(i): " + filhosvct.elementAt(i));
			//System.out.println("contribuifilhosvct.elementAt(k)" + contribuifilhosvct.elementAt(k));
			}	
		}
		
		
	}
	
	//System.out.println("matrizRel: "+ matrizRel);
	
	}
	
	//matrizRel = matrizRel + "}" ;
	
	return matrizRel;
	
	}//FECHA METODO
	
	public boolean atualizaMatrizRel(String matrizRel){
		
		try {
			
			String strvisao = new String();
			
			//System.out.println("CLASSE ADVICE: partes - " + partes[0]);
			
			BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
			  while (in.ready()) {                
				  strvisao = strvisao+in.readLine();       }  
			 
			 
	        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
	        //out.write(strvisao + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
	        out.write(strvisao+matrizRel+"}"); 
	        out.close();

	    }
	    catch(IOException e){
	        // possiveis erros são tratados aqui
	    	}
	    return true;
	}
	
	
	public boolean atualizaMatrizMer(String matrizRel){
		
		try {
			
			String strvisao = new String();
			
			//System.out.println("CLASSE ADVICE: partes - " + partes[0]);
			
			BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
			  while (in.ready()) {                
				  strvisao = strvisao+in.readLine();       }  
			 
			 
	        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
	        //out.write(strvisao + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
	        out.write(strvisao+matrizRel+"}"); 
	        out.close();

	    }
	    catch(IOException e){
	        // possiveis erros são tratados aqui
	    	}
	    
	    return true;
	}
	
	
	public void atualizaMer(String matrizRel, String nomedoarquivo){
		
		try {
			
			String strvisao = new String();
			
			//System.out.println("CLASSE ADVICE: partes - " + partes[0]);
			
			BufferedReader in = new BufferedReader(new FileReader(nomedoarquivo + "ermodel.txt"));                       
			  while (in.ready()) {                
				  strvisao = strvisao+in.readLine();       }  
			 
			 
	        BufferedWriter out = new BufferedWriter(new FileWriter(nomedoarquivo + "ermodel.txt"));
	        //out.write(strvisao + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
	        out.write(strvisao+matrizRel+"}"); 
	        out.close();

	    }
	    catch(IOException e){
	        // possiveis erros são tratados aqui
	    	}
	}
	
	
}
