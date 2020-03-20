package mer;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ComponenteER {
	private Vector<String> associados = new Vector<String>();
	private Vector<String> outrasentidades = new Vector<String>();	
	private String componenteOrigem = new String();
	private String nomeEntidade = new String();
	private String pai = new String();
	private String rotulodot = new String();
	private Vector<String> atributos = new Vector<String>();
public ComponenteER(String id){
	componenteOrigem = id;
}	
	
public ComponenteER(String entidade, Vector<String> atributos2) {
	nomeEntidade = entidade;
	atributos = atributos2;
}

public void setAssociados(Vector<String> atrib){
 for(int i=0; i<atrib.size(); i++){
 associados.addElement(atrib.elementAt(i));}
 //System.out.println(atributos);
}

public void setAtributos(Vector<String> atrib){
	 for(int i=0; i<atrib.size(); i++){
	 atributos.addElement(atrib.elementAt(i));}
	 //System.out.println(atributos);
	}

public void setAssociados(String atrib){
	
	 associados.addElement(atrib);}
	 //System.out.println(atributos);
	

public void setPai(Hashtable<String, String> pais, String componente){
	
	
	Enumeration<String> enumeraPais = pais.elements();
	Vector<String> paisvct = new Vector<String>();
	
	Enumeration<String> enumeraFilhos = pais.keys();
	Vector<String> filhosvct = new Vector<String>();
	
	while(enumeraPais.hasMoreElements()){
		paisvct.addElement((String)enumeraPais.nextElement());  
      }
	
    while(enumeraFilhos.hasMoreElements()){
    	filhosvct.addElement((String)enumeraFilhos.nextElement());
      }
    
    for(int i=0;i<filhosvct.size();i++){
    	if(filhosvct.elementAt(i).equals(componente)){
    		
    		pai=paisvct.elementAt(i);
    		//System.out.println("filho: " + componente);
    		//System.out.println("pai: " + pai);
    		
    	}
    }
    
	}

public String getPai(){
		
return pai;
    
	}

public String getNomeEntidade(){
	
	return nomeEntidade;
	    
		}

public Vector<String> getAssociados(){
	 for(int i=0; i<associados.size(); i++){
		 if(!associados.elementAt(i).contains("[")){
			//System.out.println("ATRIBUTO EXCLUIDO: "+atributos.elementAt(i));
			 associados.remove(i);
		 } }
	return associados;
}

public String getComponenteOrigem(){
	return componenteOrigem;
}

public Vector<String> extraiTopico(String componente){
	String Origem = new String();
	Origem = componente;
	java.util.StringTokenizer Origemst = new java.util.StringTokenizer(Origem, "]");
	java.util.Vector<String> Origemvct = new java.util.Vector<String>();
	
	java.util.Vector<String> Entidadevct = new java.util.Vector<String>();
	while (Origemst.hasMoreTokens())   {
			Origemvct.addElement(Origemst.nextToken());}
	
	
	for(int i = 0; i<Origemvct.size(); i++){
	if(Origemvct.elementAt(i).contains("[")){
		java.util.StringTokenizer Entidadest = new java.util.StringTokenizer(Origemvct.elementAt(i), "[");
		java.util.Vector<String> extraiEntidadevct = new java.util.Vector<String>();
		while (Entidadest.hasMoreTokens())   {
			extraiEntidadevct.addElement(Entidadest.nextToken());}
		
		Entidadevct.addElement(extraiEntidadevct.elementAt(1));
		}
	
	}
	//System.out.println("Origemvct: " + Origemvct);
	//System.out.println("Entidadevct: " + Entidadevct);
	
	return Entidadevct;
	//System.out.println(Entidadevct);
	}

public Vector<String> extraiTopico(){
	String Origem = new String();
	Origem = getComponenteOrigem();
	java.util.StringTokenizer Origemst = new java.util.StringTokenizer(Origem, "]");
	java.util.Vector<String> Origemvct = new java.util.Vector<String>();
	
	java.util.Vector<String> Entidadevct = new java.util.Vector<String>();
	while (Origemst.hasMoreTokens())   {
			Origemvct.addElement(Origemst.nextToken());}
	
	
	for(int i = 0; i<Origemvct.size(); i++){
	if(Origemvct.elementAt(i).contains("[")){
		java.util.StringTokenizer Entidadest = new java.util.StringTokenizer(Origemvct.elementAt(i), "[");
		java.util.Vector<String> extraiEntidadevct = new java.util.Vector<String>();
		while (Entidadest.hasMoreTokens())   {
			extraiEntidadevct.addElement(Entidadest.nextToken());}
		
		Entidadevct.addElement(extraiEntidadevct.elementAt(1));
		}
	
	}
	//System.out.println("Origemvct: " + Origemvct);
	//System.out.println("Entidadevct: " + Entidadevct);
	
	return Entidadevct;
	//System.out.println(Entidadevct);
	}

public Vector<String> geraAtributosEntidades(Vector<String> atributos){
java.util.Vector<String> finalvct = new java.util.Vector<String>();
@SuppressWarnings("unused")
String str = new String();	
for(int i = 0; i<atributos.size(); i++){
	
	java.util.StringTokenizer aux1st = new java.util.StringTokenizer(atributos.elementAt(i), "\\]");
	java.util.Vector<String> aux1vct = new java.util.Vector<String>();
	
	while (aux1st.hasMoreTokens())   {
		aux1vct.addElement(aux1st.nextToken());}
	
	//System.out.println("aux1vct:" + aux1vct);
	
	for(int j = 0; j<aux1vct.size(); j++){
	if(aux1vct.elementAt(j).contains("[")){
	java.util.StringTokenizer aux2st = new java.util.StringTokenizer(aux1vct.elementAt(j), "\\[");
	java.util.Vector<String> aux2vct = new java.util.Vector<String>();
	while (aux2st.hasMoreTokens())   {
		aux2vct.addElement(aux2st.nextToken());}
	finalvct.addElement(aux2vct.elementAt(1));
	}
		
	      //String partes[] = aux1vct.elementAt(j).split("\\[");
	      //System.out.println("CLASSE INTERTYPE: partes[0] - " + partes[0]);
	      //String parteModificada = partes[0]+afetado+"{"+adicionado+partes[partes.length - 1];
	      //finalvct.addElement(partes[1]);
	}
	
	}
	//System.out.println("finalvct:" + finalvct);
	return finalvct;
	//System.out.println(Entidadevct);
	}


public Vector<String> encontraAtributos(Vector<String> associados, Vector<String> folhas){
	
	
	//System.out.println("associados:" + associados);
	
	for(int i = 0; i<associados.size(); i++){
		if(folhas.contains(associados.elementAt(i))){
			atributos.addElement(associados.elementAt(i));
		}
		
}
	//System.out.println("atributos:" + atributos);
	return atributos;
	
	}

public Vector<String> encontraOutrasEntidades(Vector<String> associados, Vector<String> Nfolhas){
	
	
	//System.out.println("associados:" + associados);
	
	for(int i = 0; i<associados.size(); i++){
		if(Nfolhas.contains(associados.elementAt(i))){
			outrasentidades.addElement(associados.elementAt(i));
		}
		
}
	//System.out.println("atributos:" + atributos);
	return outrasentidades;
	
	}

public Vector<String> getOutrasEntidades(){
	
	return outrasentidades;
	
	}

public Vector<String> getAtributos(){
	//System.out.println("atributos:" + atributos);
	return atributos;
	}

public void setRotulodot(String rotulo){
	
	rotulodot = rotulo;
	//System.out.println("atributos:" + atributos);
	
	}

public void setEntidades(Vector<String> entidades){
	
	outrasentidades = entidades;
	//System.out.println("atributos:" + atributos);
	
	}

public String getRotulodot(){
	
	return rotulodot;	//System.out.println("atributos:" + atributos);
	
	}

public Vector<String> geraAssociados(){
	String Origem = new String();
	Origem = getComponenteOrigem();
	java.util.StringTokenizer Origemst = new java.util.StringTokenizer(Origem, "]");
	java.util.Vector<String> Origemvct = new java.util.Vector<String>();
	java.util.Vector<String> extraiEntidadevct = new java.util.Vector<String>();
	java.util.Vector<String> Entidadevct = new java.util.Vector<String>();
	while (Origemst.hasMoreTokens())   {
			Origemvct.addElement(Origemst.nextToken());}
	
	for(int i = 0; i<Origemvct.size(); i++){
	if(Origemvct.elementAt(i).contains("[")){
		java.util.StringTokenizer Entidadest = new java.util.StringTokenizer(Origemvct.elementAt(i), "[");
		while (Entidadest.hasMoreTokens())   {
			extraiEntidadevct.addElement(Entidadest.nextToken());}
		Entidadevct.addElement(extraiEntidadevct.elementAt(1));
		}
	
	}
	return Entidadevct;
	//System.out.println(Entidadevct);
	}

}
