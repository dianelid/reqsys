package rastreability;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Matriz {
	
	Vector<String> incluiu = new Vector<String>();
	
public void criaMatrizArquivo(String coluna1){
	//System.out.println("MatrizTrans criaMatriztransArquivo");
 	String nomeArquivo = new String();
 	String Titulo = new String();
 	String Rodape = new String();
	if(coluna1.equals("afetados, afetam")){
		  nomeArquivo = "C://matriztransversalidade.html";
		  Titulo = "Matriz de Rastreamento de Relacionamentos Transversais";  
	}
	
	if(coluna1.equals("pais, filhos")){
		  nomeArquivo = "C://matrizcontribuicao.html";
		  Titulo = "Matriz de Rastreamento de Relacionamentos de Contribuição";
	}
	
	if(coluna1.equals("AOV-Graph, ModelER")){
		  nomeArquivo = "C://matrizaovmer.html";
		  Titulo = "Matriz de Rastreamento de Associações entre componentes AOV-Graph e elementos do MER";
		  Rodape = "Obs.: Os sinais '{' e '}' indicam que atributos e/ou entidades não aparecem no MER.";
	}
	
	String visaostr = new String(
				Titulo 
				+ "<br>"
 	    		+ "<table border=\""
 	    		+ "1" 
 	    		+ "\""
 	    		+"width="
 	    		+"\"30%\"" 
 	    		+ ">"
                + "<!--colunas-->"
 	    		+ "<tr>"
 	    		+ "<td>" + coluna1 + "</td>"
 	    		+ "<!--iniciodascolunas-->"
 	    		+ "</tr><!--fimdascolunas-->"
 	    		+ "<!--iniciolinhascelulas-->"
 	    		+ "<!--fimlinhascelulas-->"
 	    		+ "</table>"
 	    		+ Rodape
 	    
 	    );
  	    
  	    //System.out.println("Gerando MATRIZTRANS");
		
     	try {
            BufferedWriter out = new BufferedWriter(new FileWriter(nomeArquivo));
            out.write(visaostr); // " " = quebra de linha no  Windows 
            out.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
	
}

void organizaLinhas(String tipo, Vector<Linha> preenche){
	boolean remove=false;
	
 if(tipo.equals("aovmer")){

 for(int i=0; i<preenche.size();i++){
	 //System.out.println(preenche.elementAt(i).getLinhas());
	 //System.out.println(preenche.elementAt(i+1).getLinhas());
	for(int j=i+1; j<preenche.size();j++){
 if(preenche.elementAt(i).getLinhas().equals(preenche.elementAt(j).getLinhas())){
 //System.out.println(preenche.elementAt(i).getLinhas());
 //System.out.println(preenche.elementAt(j).getLinhas());	
 if(preenche.elementAt(i).getColuna().equals(preenche.elementAt(j).getColuna())){
 //System.out.println(preenche.elementAt(i).getColuna());	
 //System.out.println(preenche.elementAt(j).getColuna());
 
	for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
		preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));
		}
 
 preenche.remove(j);
}else{
	//System.out.println("ELSE");
	//System.out.println(preenche.elementAt(i).getColuna());	
	//System.out.println(preenche.elementAt(j).getColuna());
	 
	preenche.elementAt(i).setColunas(preenche.elementAt(i).getColuna());	
	preenche.elementAt(i).setColunas(preenche.elementAt(j).getColuna());	
	
	for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
		preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));
		}
	
	preenche.remove(j);
}
}
	}
}

for(int i=0; i<preenche.size();i++){
	for(int j=i+1; j<preenche.size();j++){
if(preenche.elementAt(i).getLinhas().equals(preenche.elementAt(j).getLinhas())){
//System.out.println(preenche.elementAt(j).getLinhas());	
if(preenche.elementAt(i).getColuna().equals(preenche.elementAt(j).getColuna())){
	preenche.remove(j);
}else{
	//System.out.println("ELSE");
	//System.out.println(preenche.elementAt(i).getColuna());	
	//System.out.println(preenche.elementAt(j).getColuna());
	 
	preenche.elementAt(i).setColunas(preenche.elementAt(i).getColuna());	
	preenche.elementAt(i).setColunas(preenche.elementAt(j).getColuna());	
	
	for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
		preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));
		}
	
	preenche.remove(j);
}
}
	}
}

}
 
 if(tipo.equals("contribuicao")){

	 for(int i=0; i<preenche.size();i++){
		for(int j=i+1; j<preenche.size();j++){
	 if(preenche.elementAt(i).getLinhas().equals(preenche.elementAt(j).getLinhas())&&preenche.elementAt(i).getColuna().equals(preenche.elementAt(j).getColuna())){
	 //System.out.println(preenche.elementAt(i).getLinhas());
	 //System.out.println(preenche.elementAt(j).getLinhas());	
	 if(preenche.elementAt(i).getColuna().equals(preenche.elementAt(j).getColuna())){
	 //System.out.println(preenche.elementAt(i).getColuna());	
	 //System.out.println(preenche.elementAt(j).getColuna());
			for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
				preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));
				}
	 preenche.remove(j);
	}
	}
		}
	}

	for(int i=0; i<preenche.size();i++){
		
		for(int j=i+1; j<preenche.size();j++){
	if(preenche.elementAt(i).getLinhas().equals(preenche.elementAt(j).getLinhas())){
	//System.out.println(preenche.elementAt(j).getLinhas());	
	if(preenche.elementAt(i).getColuna().equals(preenche.elementAt(j).getColuna())){
		for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
			preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));
			}
		preenche.remove(j);
	}
	}
		}
	}
	}

if(tipo.equals("transversalidade")){
	for(int i=0; i<preenche.size();i++){
		for(int j=i+1; j<preenche.size();j++){
	if(preenche.elementAt(i).getLinhas().equals(preenche.elementAt(j).getLinhas())){
	//System.out.println("Linha: " + preenche.elementAt(i).getLinhas());
	//System.out.println("Linha: " + preenche.elementAt(j).getLinhas());	
	if(!preenche.elementAt(i).getColunas().equals(preenche.elementAt(j).getColunas())){
	//System.out.println("Coluna: " + preenche.elementAt(i).getColunas());	
	//System.out.println("Coluna: " + preenche.elementAt(j).getColunas());
	for(int k=0; k<preenche.elementAt(j).getColunas().size();k++){
	if(!preenche.elementAt(i).getColunas().contains(preenche.elementAt(j).getColunas().elementAt(k))){
		preenche.elementAt(i).getColunas().addElement(preenche.elementAt(j).getColunas().elementAt(k));
		//preenche.elementAt(i).contaCelulas(preenche.elementAt(i).getCelula().size());
		for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
		preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));}
		remove = true;
		}
	}
	if(remove){ preenche.remove(j); j--;}
	}
	}
		}
		
	}	
	
	
	for(int i=0; i<preenche.size();i++){
		for(int j=i+1; j<preenche.size();j++){
	if(preenche.elementAt(i).getLinhas().equals(preenche.elementAt(j).getLinhas())){
	//System.out.println("Linha: " + preenche.elementAt(i).getLinhas());
	//System.out.println("Linha: " + preenche.elementAt(j).getLinhas());	
	if(!preenche.elementAt(i).getColunas().equals(preenche.elementAt(j).getColunas())){
	//System.out.println("Coluna: " + preenche.elementAt(i).getColunas());	
	//System.out.println("Coluna: " + preenche.elementAt(j).getColunas());
	for(int k=0; k<preenche.elementAt(j).getColunas().size();k++){
	if(!preenche.elementAt(i).getColunas().contains(preenche.elementAt(j).getColunas().elementAt(k))){
		preenche.elementAt(i).getColunas().addElement(preenche.elementAt(j).getColunas().elementAt(k));
		//preenche.elementAt(i).contaCelulas(preenche.elementAt(i).getCelula().size());
		for(int cel=0; cel<preenche.elementAt(j).getCelula().size();cel++){
			preenche.elementAt(i).getCelula().addElement(preenche.elementAt(j).getCelula().elementAt(cel));
			}
		//preenche.elementAt(i).contaCelulas(preenche.elementAt(j).getCelula().size());
		
		//System.out.println("Linha: " + preenche.elementAt(i).getLinhas());
		//System.out.println("Colunas novas: " + preenche.elementAt(i).getColunas());	
		remove = true;
		}
	}
	if(remove) preenche.remove(j); j--;
	}
	}
		}
		
	}	
	
}

}

void atualizaMatriz(String tipo, Vector<Linha> preenche, int qtde){
	
	String visaostr= new String();
	String matrizHtml = new String();
	String matrizHtmlcolunas = new String();
	String nomeArquivo = new String();
	Vector<String> topico = new Vector<String>();
	//boolean imprimiu = true;
	//System.out.println("CLASSE Matriz: qtdeAdicionados - " + qtdeAdicionados);
	//nomeArquivo = "C://matriz.txt";
	
	organizaLinhas(tipo, preenche);
	
	
	if(tipo.equals("transversalidade")){
		  nomeArquivo = "C://matriztransversalidade.html";
	}

	if(tipo.equals("contribuicao")){
		  nomeArquivo = "C://matrizcontribuicao.html";
	}

	if(tipo.equals("aovmer")){
		  nomeArquivo = "C://matrizaovmer.html";
	}
	
	
	  //if(tipoMatriz.equals("aovmer")){
		  //System.out.println("colunasaovmer: " + colunasaovmer);
		  //if(colunaaovmer.size()>0){
			  for(int i=0; i<preenche.size(); i++){
				  //colunaaovmer.addElement(new Coluna(colunasaovmer.elementAt(i)));
				  //System.out.println("colunas.elementAt(i).getColuna(): " + preenche.elementAt(i).getColuna());
				  
				  for(int j=0; j<preenche.size(); j++){
					
					  if(tipo.equals("aovmer")){
						  for(int conti=0;conti<preenche.elementAt(i).getColunas().size();conti++){
								if(!topico.contains(preenche.elementAt(i).getColunas().elementAt(conti))){
									topico.addElement(preenche.elementAt(i).getColunas().elementAt(conti));
								}  
							  }  
					
					  }
					  
					  
					  if(tipo.equals("transversalidade")){
						  for(int conti=0;conti<preenche.elementAt(i).getColunas().size();conti++){
							if(!topico.contains(preenche.elementAt(i).getColunas().elementAt(conti))){
								topico.addElement(preenche.elementAt(i).getColunas().elementAt(conti));
							}  
						  }
							  }
					  
					  
					  if(tipo.equals("contribuicao")){
						  for(int conti=0;conti<preenche.elementAt(i).getColunas().size();conti++){
							if(!topico.contains(preenche.elementAt(i).getColunas().elementAt(conti))){
								topico.addElement(preenche.elementAt(i).getColunas().elementAt(conti));
							}  
						  }
							  }
					  
					  
					  
				  }
				  
				  
				 // System.out.println("colunas.elementAt(i).getLinhas(): " + preenche.elementAt(i).getLinhas());
				 // System.out.println("colunas.elementAt(i).getCelula(): " + preenche.elementAt(i).getCelula());
				  
			  }
			  
			  //System.out.println("topico: " + topico);
		//}
		  
		  
		  //}
	
		 String parte1 = new String();
		 
		 try {        
			  
			  BufferedReader in = new BufferedReader(new FileReader(nomeArquivo));                       
			  
			  
			  while (in.ready()) {                
				  visaostr = visaostr+in.readLine();           
				  } 
			  //System.out.println(visaostr);
			  String partes[] = visaostr.split("<!--iniciolinhascelulas--><");
			  matrizHtmlcolunas = partes[0] + "<!--iniciolinhascelulas-->"; 
			  parte1 = partes[partes.length-1];
			  
		} 
		    catch (IOException e) { 
		  	  //System.out.println("Excecao!" );
		    }  
	    
		    
		    if(tipo.equals("transversalidade")){
		    	boolean imprimiulinha = true;
		    	boolean imprimiucelula = true;
		    	Vector<String> linhas = new Vector<String>();
		    	for(int i=0; i<preenche.size(); i++){
					// if(preenche.elementAt(i).getColuna().equals(topico.elementAt(k))){
					if(!linhas.contains(preenche.elementAt(i).getLinhas())){		
		    		linhas.addElement(preenche.elementAt(i).getLinhas());
					}
		    		//	}   
					
					 }
			 
				  
				 for(int aux=0; aux<linhas.size(); aux++){
					 imprimiulinha = true;
					 int cont=0;
					 //System.out.println("linha." + aux + ": " + linhas.elementAt(aux));
					 matrizHtmlcolunas = matrizHtmlcolunas  + "<tr><td>"+ linhas.elementAt(aux) + "</td>";
					  
					 for(int k=0; k<topico.size(); k++){ 
				     imprimiucelula = true;
				     
					 for(int conta=0; conta<preenche.size();conta++){
						 
						//System.out.println(linhas.elementAt(aux));
					 if(preenche.elementAt(conta).getLinhas().equals(linhas.elementAt(aux))){
						 //System.out.println(linhas.elementAt(aux));
						//for(int r=0; r<preenche.elementAt(conta).getColunas().size(); r++){ 
						 if(preenche.elementAt(conta).getColunas().contains(topico.elementAt(k))){
						 //System.out.println(preenche.elementAt(conta).getColuna());
						 //System.out.println(topico.elementAt(k));
							 if(imprimiulinha){
								 for(int q=0;q<k;q++){
									 //if(imprimiu){
									 matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>";   
									 //}
									 
									 }
								 imprimiulinha=false;
								 }
							 if(imprimiucelula){
						   //System.out.println("conta: " + conta); 
						   //System.out.println(preenche.elementAt(conta).getLinhas()); 
						   //System.out.println(preenche.elementAt(conta).getColunas()); 
						   //System.out.println("size: " + preenche.elementAt(conta).getCelula().size());
						   //System.out.println(preenche.elementAt(conta).getCelula().elementAt(cont)); 
						   //System.out.println("cont: " + cont); 
							 matrizHtmlcolunas = matrizHtmlcolunas + "<td>" + preenche.elementAt(conta).getCelula().elementAt(cont) + "</td>" ;
							 imprimiucelula=false;
							 if(cont<preenche.elementAt(conta).getCelula().size()-1){
							 cont++;}
							 
							 }
						 }else if(!imprimiulinha){
							 
							 matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>"; 
							 
						 }
					 //}
						 	
					 }
					 }
					
				 
			  }
				 
					 matrizHtmlcolunas = matrizHtmlcolunas + "</tr>";
				 
				 //System.out.println("linhas: " + linhas);
				  }	
		    }
		    
		    
		    
		    
	    if(tipo.equals("aovmer")){	
	    	boolean imprimiulinha = true;
	    	boolean imprimiucelula = true;
	    	Vector<String> linhas = new Vector<String>();
	    	for(int i=0; i<preenche.size(); i++){
				// if(preenche.elementAt(i).getColuna().equals(topico.elementAt(k))){
				if(!linhas.contains(preenche.elementAt(i).getLinhas())){		
	    		linhas.addElement(preenche.elementAt(i).getLinhas());
				}
	    		//	}   
				
				 }
		 
			  
			 for(int aux=0; aux<linhas.size(); aux++){
				 imprimiulinha = true;
				 int cont=0;
				 matrizHtmlcolunas = matrizHtmlcolunas  + "<tr><td>"+ linhas.elementAt(aux) + "</td>";
				  
				 for(int k=0; k<topico.size(); k++){ 
			     imprimiucelula = true;
			     
				 for(int conta=0; conta<preenche.size();conta++){
					 
					//System.out.println(linhas.elementAt(aux));
				 if(preenche.elementAt(conta).getLinhas().equals(linhas.elementAt(aux))){
					 //System.out.println(linhas.elementAt(aux));
					//for(int r=0; r<preenche.elementAt(conta).getColunas().size(); r++){ 
					 if(preenche.elementAt(conta).getColunas().contains(topico.elementAt(k))){
					 //System.out.println(preenche.elementAt(conta).getColuna());
					 //System.out.println(topico.elementAt(k));
						 if(imprimiulinha){
							 for(int q=0;q<k;q++){
								 //if(imprimiu){
								 matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>";   
								 //}
								 
								 }
							 imprimiulinha=false;
							 }
						 if(imprimiucelula){
					   //System.out.println("conta: " + conta); 
					   //System.out.println(preenche.elementAt(conta).getLinhas()); 
					   //System.out.println(preenche.elementAt(conta).getColunas()); 
					   //System.out.println("size: " + preenche.elementAt(conta).getCelula().size());
					   //System.out.println(preenche.elementAt(conta).getCelula().elementAt(cont)); 
					   //System.out.println("cont: " + cont); 
						matrizHtmlcolunas = matrizHtmlcolunas + "<td>" + preenche.elementAt(conta).getCelula().elementAt(cont) + "</td>" ;
						 imprimiucelula=false;
						 if(cont<preenche.elementAt(conta).getCelula().size()){
						 cont++;}
						 
						 }
					 }else if(!imprimiulinha){
						 
						 matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>"; 
						 
					 }
				 //}
					 	
				 }
				 }
				
			 
		  }
			 
				 matrizHtmlcolunas = matrizHtmlcolunas + "</tr>";
			 
			 //System.out.println("linhas: " + linhas);
			  }
         }
	    
	    
	    
	    if(tipo.equals("contribuicao")){
	    	int coluna =0;
	    	int cell = 0;
			  //for(int k=0; k<topico.size(); k++){ 
				  Vector<String> linhas = new Vector<String>();
				 
				  for(int i=0; i<preenche.size(); i++){
					 //if(preenche.elementAt(i).getColuna().equals(topico.elementAt(k))){
							linhas.addElement(preenche.elementAt(i).getLinhas());
					//	}   
					
					 }
					  
				 
				 for(int aux=0; aux<linhas.size(); aux++){
				 
					 matrizHtmlcolunas = matrizHtmlcolunas  + "<tr><td>"+ linhas.elementAt(aux) + "</td>";

										 
					  //for(int cont=0;cont<linhas.size();cont++){
					  
					 for(int conta=0; conta<preenche.size();conta++){
					cell = 0;
						//System.out.println(linhas.elementAt(aux));
					 if(preenche.elementAt(conta).getLinhas().equals(linhas.elementAt(aux))){
						 //System.out.println(linhas.elementAt(aux));
						 //if(preenche.elementAt(conta).getColuna().equals(topico.elementAt(k))){
						 //System.out.println(preenche.elementAt(conta).getColuna());
						 //System.out.println(topico.elementAt(k));
						 if(coluna>0){
						  for(int q=0;q<coluna;q++){
							 matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>";   
						  }
						  }
						 
						 for(int p=0; p<preenche.elementAt(conta).getColunas().size();p++){
							 
							 matrizHtmlcolunas = matrizHtmlcolunas + "<td>" + preenche.elementAt(conta).getCelula().elementAt(cell) + "</td>" ;
							 
						 coluna++;
						 if(!(cell==(preenche.elementAt(conta).getCelula().size())-1)){
						 cell++;} 
						 //}
						 }	
					 }
					 }
				for(int contador=coluna;contador<topico.size();contador++){
					  matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>";  
				 } 
					 //}
						 //if(k<topico.size()){
							 // for(int contador=k;contador<topico.size();contador++){
								  //matrizHtmlcolunas = matrizHtmlcolunas + "<td> - </td>";  
							 // }  
							 // }
					 
				
					  matrizHtmlcolunas = matrizHtmlcolunas + "</tr>";
					  
					  
					  //System.out.println("CLASSE Matriztrans: matrizHtml - " + matrizHtml);
				 
			  }
				 

				 
				 //System.out.println("linhas: " + linhas);
				  //}
	         }
	    
	    
	    
		//System.out.println("parte1: " + parte1);
		matrizHtmlcolunas =   matrizHtmlcolunas + "<" + parte1;
	    
			try {
		        BufferedWriter out = new BufferedWriter(new FileWriter(nomeArquivo));
		        out.write(matrizHtmlcolunas); // " " = quebra de linha no  Windows 
		        out.close();
		    }
		    catch(IOException e){
		        System.out.println(e.getMessage());
		    } 

	    
	    
		/**/
	     
		    atualizaColunas(tipo, topico);
	    
	}



void atualizaColunas(String tipo, Vector<String> topico){
String 	matrizHtml = new String();
String 	visaostr = new String();
String nomeArquivo = new String();	
if(tipo.equals("transversalidade")){
	  nomeArquivo = "C://matriztransversalidade.html";
}

if(tipo.equals("contribuicao")){
	  nomeArquivo = "C://matrizcontribuicao.html";
}

if(tipo.equals("aovmer")){
	  nomeArquivo = "C://matrizaovmer.html";
}



	try {   
		
		  BufferedReader in = new BufferedReader(new FileReader(nomeArquivo));                       
		  while (in.ready()) {                
			  visaostr = visaostr+in.readLine();           
			  }  
		  String partes[] = visaostr.split("</tr><!--fimdascolunas-->");
		  matrizHtml = partes[0];
		  //System.out.println(partes[0]);
		  //System.out.println(partes[1]);
		  for(int cont=0;cont<topico.size();cont++){
			  matrizHtml = matrizHtml + "<td>" + topico.elementAt(cont) + "</td>";
		  }
		  
		  matrizHtml = matrizHtml + "</tr><!--fimdascolunas-->" + partes[1];
		  
		  //System.out.println("CLASSE Matriztrans: matrizHtml - " + matrizHtml);
		  

	} 
	    catch (IOException e) { 
	  	  //System.out.println("Excecao!" );
	    }

		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(nomeArquivo));
	        out.write(matrizHtml); // " " = quebra de linha no  Windows 
	        out.close();
	    }
	    catch(IOException e){
	        System.out.println(e.getMessage());
	    }  
	
	
	
}

}//FECHA CLASSE
