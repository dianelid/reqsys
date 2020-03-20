package aov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Advice {
	
	
	public String arquivo = new String();
	static java.util.Vector<String> componentesAtualizados = new java.util.Vector<String>();
	BufferedReader in;
	InputStream input;
	URL url;
	public void adviceTokens(String advicePointcutEComponentes, Vector<String> componentes, Vector<String> filhosvct, java.util.Hashtable<String, String> paishash, String arquivo){
	
    //System.out.println("ADVICE adviceTokens");
	
	this.arquivo = arquivo;
		//System.out.println("advicePointcutEComponentes - " + advicePointcutEComponentes);
		//System.out.println("componentes - " + componentes);
		//System.out.println("arquivo - " + arquivo);
		//System.out.println("filhosvct - " + filhosvct);
		//java.util.Vector<String> afetadoDescrvct = new java.util.Vector<String>();

      	}
	
	
	public void advicesubstituteArquivo(String substituido, String substitui, String arquivo, String advinter) {
		//System.out.println("substituido: " + substituido);	
		//System.out.println("substitui: " + substitui);
		
		String str = new String();
		String strvisao = new String();
		String composicaofinal = new String();
		String SubstituidoModificado = substituido.replace("(","\\(");
		String SubstituidoModificado1 = SubstituidoModificado.replace(")","\\)");
	    String SubstituidoModificado2 = SubstituidoModificado1.replace("{","\\{");
	    String SubstituidoModificado3 = SubstituidoModificado2.replace("}","\\}");
	    String SubstituidoModificado4 = SubstituidoModificado3.replace("[","\\[");
	    String SubstituidoModificado5 = SubstituidoModificado4.replace("]","\\]");
		
	    if(arquivo.contains("composicao")){
	    try {        
			  BufferedReader in = new BufferedReader(new FileReader("C://composicao.html"));                       
			  while (in.ready()) {                
				  str = str+in.readLine(); //+System.getProperty ("line.separator");           
				  }  
			  //System.out.println("str: " + str);

			  
			  String partes[] = str.split(SubstituidoModificado5);
			  composicaofinal = partes[0] + substitui + partes[partes.length - 1];
			  //System.out.println("partes: " + partes);
			  //System.out.println("partes[partes.length - 1]: " + partes[partes.length - 1]);
	    } 
		      catch (IOException e) { 
		    	  //System.out.println("Excecao!" );
		      }
		      
			  try {
		        BufferedWriter out = new BufferedWriter(new FileWriter("C://composicao.html"));
		        out.write(composicaofinal); 
		        out.close();
		    }
		    catch(IOException e){
		        // possiveis erros são tratados aqui
		    	}
		
	    }else{
	    	
    		try {
    			//System.out.println("CLASSE ADVICE: substituido - " + substituido);
    			String partes[] = substituido.split("\\(");
    			//System.out.println("CLASSE ADVICE: partes - " + partes[0]);
    			
    			BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
    			  while (in.ready()) {                
    				  strvisao +=in.readLine();       }  
    			  
    			  //BufferedReader inmatriz = new BufferedReader(new FileReader("matriztrans.txt")); 
    			  
    			  //BufferedWriter outmatriz = new BufferedWriter(new FileWriter("matriztrans.txt"));
    			  
    			  //outmatriz.write("rastreability_matrix(relationship; <default>){ \n" ); 
    			  
    			  //while (inmatriz.ready()) {                
    				//  strmatriz = strmatriz+inmatriz.readLine()+"\n";       }  
    			  
		        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
		        //out.write(strvisao + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
		        out.write(strvisao + "line( " + partes[0] + "); " + "column(" + substitui + ");" + "cell(" + advinter + ");" );
		        out.close();
		        
		        
		        //outmatriz.write(strmatriz + "COMPONENTE " + substituido + " SUBSTITUIDO POR " + substitui + ".\n" ); 
		        //outmatriz.close();
		    }
		    catch(IOException e){
		        // possiveis erros são tratados aqui
		    	}
    		//System.out.println("VISAAAOOOOO - advicearoundComposicao!!!!!");
    		}	
	    	
	    }
		
	public void advicearoundArquivo(String afetadoDescr,  String pointcut, String Arquivo){
	arquivo = Arquivo;
	String str = new String();
	String strvisao = new String();
	//System.out.println("afetadoDescr: " + afetadoDescr);	
	//System.out.println("pointcut: " + pointcut);
	String SubstituidoModificado = afetadoDescr.replace("[","\\[");
    String SubstituidoModificado1 = SubstituidoModificado.replace("]","\\]");
	if(arquivo.equals("composicao")){
		try {        
			  in = new BufferedReader(new FileReader("C:\\composicao.html"));                       
			  
			  while (in.ready()) {                
				  str += in.readLine(); //+"<\\" + "\br>";
				  }  
			  //System.out.println("CLASSE INTERTYPE: str - " + str);
		  } 
		      catch (IOException e) {    }
		      
		    
		     //System.out.println("str: " + str); 
		      
		      String partes[] = str.split(SubstituidoModificado1);
		    
		    
		    String parteModificada = partes[partes.length - 1].replaceFirst("\\)\\{","){" + pointcut);
		    String composicaofinal = partes[0] + SubstituidoModificado1 + parteModificada;
	
		    
			  try {
			        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\composicao.html"));
			        out.write(composicaofinal); 
			        out.close();
			    }
			    catch(IOException e){
			        // possiveis erros são tratados aqui
			    	} 
	}else{
		
		try {
			//System.out.println("CLASSE ADVICE: arquivo - " + arquivo);	
			  BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
		  while (in.ready()) {                
			  strvisao = strvisao+in.readLine();           
			  }  
		  
		    //String partes[] = SubstituidoModificado5.split("\\(");
	        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
	        out.write(strvisao + "line(" + afetadoDescr + "); " + "column(" + pointcut + "); " + "cell(advicearound); " ); 
	        out.close();
	        //out.write(strvisao + "COMPONENTE " + afetado + " afetado por " + adicionado + ".\n" ); 
	        //out.close();
	    }
	    catch(IOException e){
	        // possiveis erros são tratados aqui
	    	}	
		
		
	}
	
}
	
	public void advicebeforeArquivo(String afetadoDescr,  String pointcut, String Arquivo){
		//System.out.println("afetadoDescr: " + afetadoDescr);	
		//System.out.println("pointcut: " + pointcut);
	
		String afetado = afetadoDescr;
		String adicionado = pointcut;
		String str = new String();
		String strvisao = new String();
		String SubstituidoModificado = afetado.replace("[","\\[");
	      String SubstituidoModificado1 = SubstituidoModificado.replace("]","\\]");
		if(arquivo.equals("composicao")){
		try {        

			  BufferedReader in = new BufferedReader(new FileReader("C://composicao.html"));                       
			  while (in.ready()) {                
				  str = str+in.readLine()+System.getProperty ("line.separator");           
				  }  
			  //System.out.println("CLASSE INTERTYPE: str - " + str);
		  } 
		      catch (IOException e) {    }
		      //System.out.println("CLASSE INTERTYPE: componente afetado - " + componenteafetado);
		      

		      String partes[] = str.split(SubstituidoModificado1);
		      //System.out.println("CLASSE INTERTYPE: partes[0] - " + partes[0]);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{",") {" + "\n" + adicionado);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{",") {" + "\n" + adicionado);
		     //String parteModificada = partes[0].replaceFirst("\\) \\{",") {" + "\n" + adicionado);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{","){" + adicionadovct.elementAt(adicionadovct.size()-1));
		      //System.out.println("CLASSE INTERTYPE: parte modificada - " + parteModificada);
		      
			  String composicaofinal = partes[0] + adicionado + afetado + partes[partes.length - 1];
			  //System.out.println("CLASSE INTERTYPE: composicaofinal - " + composicaofinal);
			  try {
		        BufferedWriter out = new BufferedWriter(new FileWriter("C://composicao.html"));
		        out.write(composicaofinal); 
		        out.close();
		    }
		    catch(IOException e){
		        // possiveis erros são tratados aqui
		    	}	   
		     } else{
		    		
		    		try {
		    			//System.out.println("CLASSE ADVICE: arquivo - " + arquivo);	
		  			  BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
					  while (in.ready()) {                
						  strvisao = strvisao+in.readLine();           
						  }  
					  
		    			
				        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
				        out.write(strvisao + "line(" + afetado + "); " + "column(" + adicionado + "); " + "cell(advicebefore); " ); 
				        out.close();
				        //out.write(strvisao + "COMPONENTE " + afetado + " afetado por " + adicionado + ".\n" ); 
				        //out.close();
				    }
				    catch(IOException e){
				        // possiveis erros são tratados aqui
				    	}
		    		
		    	}
	}
	
	public void adviceafterArquivo(String afetadoDescr,  String pointcut, String arquivo){
		//System.out.println("afetadoDescr: " + afetadoDescr);	
		//System.out.println("pointcut: " + pointcut);
		
		String afetado = afetadoDescr;
		String adicionado = pointcut;
		String str = new String();
		String strvisao = new String();
		
		String SubstituidoModificado = afetado.replace("(","\\(");
		String SubstituidoModificado1 = SubstituidoModificado.replace(")","\\)");
	    String SubstituidoModificado2 = SubstituidoModificado1.replace("{","\\{");
	    String SubstituidoModificado3 = SubstituidoModificado2.replace("}","\\}");
	    String SubstituidoModificado4 = SubstituidoModificado3.replace("[","\\[");
	    String SubstituidoModificado5 = SubstituidoModificado4.replace("]","\\]");
		
		if(arquivo.equals("composicao")){
		try {        
			  BufferedReader in = new BufferedReader(new FileReader("C://composicao.html"));                       
			  while (in.ready()) {                
				  str = str+in.readLine();           
				  }  
			  //System.out.println("CLASSE INTERTYPE: str - " + str);
		  } 
		      catch (IOException e) {    }
		      //System.out.println("componente afetado - " + afetado);
		      //System.out.println("componente adicionado - " + adicionado);
		      String partes[] = str.split(SubstituidoModificado5);
		      //System.out.println("CLASSE INTERTYPE: partes[0] - " + partes[0]);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{",") {" + "\n" + adicionado);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{",") {" + "\n" + adicionado);
		     //String parteModificada = partes[0].replaceFirst("\\) \\{",") {" + "\n" + adicionado);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{","){" + adicionadovct.elementAt(adicionadovct.size()-1));
		      //System.out.println("CLASSE INTERTYPE: parte modificada - " + parteModificada);
		      //String afetadoParametro =afetado.replace("\\","");
			  String composicaofinal = partes[0] + afetadoDescr + adicionado + partes[partes.length - 1];
			  //System.out.println("CLASSE INTERTYPE: composicaofinal - " + composicaofinal);
			  try {
		        BufferedWriter out = new BufferedWriter(new FileWriter("C://composicao.html"));
		        out.write(composicaofinal); 
		        out.close();
		    }
		    catch(IOException e){
		        // possiveis erros são tratados aqui
		    	}}else{	
		    		
		    		try {
		    			//System.out.println("CLASSE ADVICE: arquivo - " + arquivo);	
			  			  BufferedReader in = new BufferedReader(new FileReader("C://matriz.txt"));                       
						  while (in.ready()) {                
							  strvisao = strvisao+in.readLine();           
							  }  
						  	
						  String partes[] = afetadoDescr.split("\\(");	
			        BufferedWriter out = new BufferedWriter(new FileWriter("C://matriz.txt"));
			        out.write(strvisao + "line(" + partes[0] + "); " + "column(" + adicionado + "); " + "cell(adviceafter); " ); 
			        out.close();
			        //out.write("COMPONENTE " + afetado + " afetado por " + adicionado + ".\n" ); 
			        //out.close();
			    }
			    catch(IOException e){
			        // possiveis erros são tratados aqui
			    	}}
	}
}
