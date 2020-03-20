package mer;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ERModel {
	Vector<String> atributosvct = new Vector<String>();
	public Vector<String> reconheceFolhas(Vector<String> componentestpcvct, Hashtable<String,String> paishash, Hashtable<String,String> paisderefhash, boolean folha){
		//System.out.println("reconheceFolhas");
		//System.out.println(paishash);
		//System.out.println("PAIS DE REF: " + paisderefhash);
		Vector<String> NFolha = new Vector<String>();
		Vector<String> Folha = new Vector<String>();
		Vector<String> paisvct = new Vector<String>();
		Vector<String> paisderefvct = new Vector<String>();
		
		@SuppressWarnings("unused")
		boolean addFolha = true;
		
		Enumeration<String> enumeraPais = paishash.elements();
		Enumeration<String> enumeraPaisdeRef = paisderefhash.keys();
		@SuppressWarnings("unused")
		Enumeration<String> enumeraFilhosdeRef = paisderefhash.elements();
		while(enumeraPais.hasMoreElements()){
		    paisvct.addElement((String)enumeraPais.nextElement());
		    //System.out.println(valor);
		  }
		
		while(enumeraPaisdeRef.hasMoreElements()){
		    paisderefvct.addElement((String)enumeraPaisdeRef.nextElement());
		    //System.out.println(valor);
		  }
		//System.out.println("COMPONENTES: " + componentestpcvct);
		//System.out.println("PAISVCT: " + paisvct);
		
		for(int i = 0; i<componentestpcvct.size(); i++){
		
		for(int j = 0; j<paisvct.size(); j++){
		
		if(componentestpcvct.elementAt(i).equals(paisvct.elementAt(j))){
			
			if(!NFolha.contains(componentestpcvct.elementAt(i))){
			NFolha.addElement(componentestpcvct.elementAt(i));
			}
		
		}

		}
		
		
		 for(int j = 0; j<paisderefvct.size(); j++){
				
				if(componentestpcvct.elementAt(i).equals(paisderefvct.elementAt(j))){
					
					if(!NFolha.contains(componentestpcvct.elementAt(i))){
					NFolha.addElement(componentestpcvct.elementAt(i));
					}
				
				}

				}

		}
		
		for(int i = 0; i<componentestpcvct.size(); i++){
			
			if(!NFolha.contains(componentestpcvct.elementAt(i))){
				Folha.addElement(componentestpcvct.elementAt(i));	
			}
			
		}
		//System.out.println("NFOLHA: " + NFolha);
		//System.out.println("FOLHA: " + Folha);
	if(folha){
		return Folha;
	}else{
		return NFolha;
	}
	
	}
	
	public Vector<String> reconheceFilhos(String entidade, Hashtable<String,String> paishash, Hashtable<String,String> paisderefhash){
		Vector<String> atributos = new Vector<String>();
		Vector<String> paisvct = new Vector<String>();
		Vector<String> filhosvct = new Vector<String>();
		
		Enumeration<String> enumeraPais = paishash.elements();

		while(enumeraPais.hasMoreElements()){
			paisvct.addElement((String)enumeraPais.nextElement());
		    //System.out.println(valor);
		  }
		
		
		Enumeration<String> enumeraFilhos = paishash.keys();

		while(enumeraFilhos.hasMoreElements()){
			filhosvct.addElement((String)enumeraFilhos.nextElement());
		    //System.out.println(valor);
		  }
		
		for(int i = 0; i<filhosvct.size(); i++){
			if(paisvct.elementAt(i).equals(entidade)){
				atributos.addElement(filhosvct.elementAt(i));
			}
			}
		 //System.out.println("ATRIBUTOS EM RECONHECE FILHOS:" + atributos);
		
		 if(atributos.size()==0){
			 //System.out.println("ATRIBUTOS EM RECONHECE FILHOS:" + atributos);
			 //System.out.println("paisderefhash EM RECONHECE FILHOS:" + paisderefhash);
			 //System.out.println("entidade EM RECONHECE FILHOS:" + entidade);
			 atributos = reconheceReferencias(entidade, paisderefhash);	
		}
		//System.out.println("ATRIBUTOS: " + atributos);
		return atributos;
	}
	
	public Vector<String> reconheceReferencias(String entidade, Hashtable<String,String> paisderefhash){
		//System.out.println("entidade: "+ entidade);
		//System.out.println("paisderefhash: "+ paisderefhash);
		
		Vector<String> atributos = new Vector<String>();
		Vector<String> atributo = new Vector<String>();
		
		Enumeration<String> enumeraPais = paisderefhash.keys();
		Vector<String> paisvct = new Vector<String>();
		
		while(enumeraPais.hasMoreElements()){
			paisvct.addElement((String)enumeraPais.nextElement());
		    //System.out.println(valor);
		  }
		//System.out.println("paisvct"+ paisvct);
		
		
		Enumeration<String> enumeraFilhos = paisderefhash.elements();
		Vector<String> filhosvct = new Vector<String>();
		while(enumeraFilhos.hasMoreElements()){
			filhosvct.addElement((String)enumeraFilhos.nextElement());
		    //System.out.println(valor);
		  }
		
		for(int i = 0; i<paisvct.size(); i++){
			if(paisvct.elementAt(i).equals(entidade)){
				atributos.addElement(filhosvct.elementAt(i));
			}
			}
		//System.out.println("atributos.elementAt(0) EM REFERENCIAS: " + atributos.elementAt(0));
		//for(int i = 0; i<atributos.size(); i++){
			atributo = trataReferencia(atributos.elementAt(0));
			//atributos.addElement(atributo);
		//}
		//System.out.println("ATRIBUTO EM REFERENCIAS: " + atributo);
		return atributo;
	}
	
	public Vector<String> trataReferencia(String atributo){
		Vector<String> refnome = new Vector<String>();
		
		java.util.StringTokenizer atributosSt = new java.util.StringTokenizer(atributo, "\\)");
		java.util.Vector<String> atributovct = new java.util.Vector<String>();
			while (atributosSt.hasMoreTokens())   {
				atributovct.addElement(atributosSt.nextToken());   }
			//System.out.println("ATRIBUTOVCT EM TRATA " + atributovct);
		
			for(int i = 0; i<atributovct.size(); i++){
				String partes[] = atributovct.elementAt(i).split(";");
				
				java.util.StringTokenizer auxSt = new java.util.StringTokenizer(partes[0], "=");
				java.util.Vector<String> auxvct = new java.util.Vector<String>();
				//System.out.println("partes EM TRATA " + partes[0]);
				
				while (auxSt.hasMoreTokens())   {
					auxvct.addElement(auxSt.nextToken());   }
				//System.out.println("auxvct EM TRATA " + auxvct);
			    refnome.addElement(auxvct.elementAt(1).replaceFirst("\\(", " "));
			}
		
		//refnome = atributo ;
		
		//System.out.println("refnome EM REFERENCIAS: " + refnome);
		return refnome;
	}
	
	
	public Vector<String> trataEntidades(String entidade, Vector<String> atributos){
		//System.out.println("trataEntidades");
		//System.out.println("ENTRADA atributos: " + atributos);
		//System.out.println("ENTRADA entidade: " + entidade);
		for(int i=0; i<atributos.size(); i++){
		//atributos.remove(entidade);
			if(atributos.elementAt(i).equals(entidade)){
				//System.out.println("atributos ANTES: " + atributos);
				atributos.remove(i);
				//System.out.println("REMOVEU ");
				//System.out.println("atributos DEPOIS: " + atributos);
			}
		}
		
		for(int i=0; i<atributos.size(); i++){
			for(int j=i+1; j<atributos.size(); j++){
				//for(int k=0; k<atributos.size(); k++){
				if(atributos.elementAt(j).equals(atributos.elementAt(i))){
					//atributos.remove(i);
					//System.out.println("atributos.elementAt(j)"+atributos.elementAt(j));
					//System.out.println("atributos.elementAt(i)"+atributos.elementAt(i));
					atributos.remove(i);
				//	}	
				}
			}
				
			//System.out.println("S/ REPETIDOS atributos: " + atributos);
			
		}
		
		for(int i=0; i<atributos.size(); i++){
			//atributos.remove(entidade);
				if(atributos.elementAt(i).equals(entidade)){
					//System.out.println("atributos ANTES: " + atributos);
					atributos.remove(i);
					//System.out.println("REMOVEU ");
					//System.out.println("atributos DEPOIS: " + atributos);
				}
			}
		
		
		for(int i=0; i<atributos.size(); i++){
			for(int j=i+1; j<atributos.size(); j++){
				//for(int k=0; k<atributos.size(); k++){
				if(atributos.elementAt(j).equals(atributos.elementAt(i))){
					//atributos.remove(i);
					//System.out.println("atributos.elementAt(j)"+atributos.elementAt(j));
					//System.out.println("atributos.elementAt(i)"+atributos.elementAt(i));
					atributos.remove(i);
				//	}	
				}
			}
				
			//System.out.println("S/ REPETIDOS atributos: " + atributos);
			
		}
		//System.out.println("RETORNO atributos: " + atributos);
		return atributos;
	}
	
	public String declaraEntidades(String entidade, Vector<String> atributos){
		//System.out.println("trataEntidades");
		//System.out.println("ENTRADA atributos: " + atributos);
		//System.out.println("ENTRADA entidade: " + entidade);
		
		String resultado = new String();
		
		resultado = "entity " + entidade + "(" ;
		
		for(int i=0; i<atributos.size(); i++){
		resultado += atributos.elementAt(i);
		
		if(i+1 < atributos.size()){
			resultado += ", ";	
		}
		
		}
		
		resultado += " );";
		atributosvct = atributos;
		//System.out.println("RETORNO resultado;: " + resultado);
		return resultado;
	}
	
	public String declaraRelacionamentos(String entidade, Vector<String> atributos){
		//System.out.println("trataEntidades");
		//System.out.println("ENTRADA atributos: " + atributos);
		//System.out.println("ENTRADA entidade: " + entidade);
		
		String resultado = new String();
		
		resultado = entidade + "relationed to ";
		
		for(int i=0; i<atributos.size(); i++){
		resultado += atributos.elementAt(i);
		
		if(i+1 < atributos.size()){
			resultado += ", ";	
		}
		
		}
		
		resultado += " ;";
		atributosvct = atributos;
		//System.out.println("RETORNO resultado: " + resultado);
		return resultado;
	}
	
	public Vector<String> getAtributos(){
		
		return atributosvct;
	}
	
}
