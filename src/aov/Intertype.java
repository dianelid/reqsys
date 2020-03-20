package aov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Intertype {
	static String arquivo;
	static String referencia;
	
	public boolean verificaIntertype(String intertype, Vector<String> componentes){
		//System.out.println("Intertype");
		
		//System.out.println("verificaIntertype!");
		boolean jaDeclarado = false;
		//System.out.println("intertype - " + intertype);
		//System.out.println("componentes - " + componentes);
		java.util.Vector<String> intertypeVerificado = new java.util.Vector<String>();
		//for(int i=0; i < intertype.size(); i++){
			for(int j=0; j < componentes.size(); j++){
				if((intertype.startsWith(componentes.elementAt(j)))){
					
					intertypeVerificado.addElement(componentes.elementAt(j));
					jaDeclarado = true;

				
				}
			}
		//}
		//System.out.println("intertypeVerificado - " + intertypeVerificado);
	    //System.out.println("jaDeclarado - " + jaDeclarado);
		return jaDeclarado;
	}
	
	public String adicionaReferencia(String jaDeclarado){
		//String componenteAdicionado = new String();
		java.util.StringTokenizer jaDeclaradotoken = new java.util.StringTokenizer(jaDeclarado, "(");
  		java.util.Vector<String> jaDeclaradovct = new java.util.Vector<String>();
  		String Referencia = new String();
  		
  		while (jaDeclaradotoken.hasMoreTokens())   {
  			jaDeclaradovct.addElement(jaDeclaradotoken.nextToken());   }
  		
		//System.out.println("jaDeclaradovct - " + jaDeclaradovct);	
  		
		if(jaDeclarado.startsWith("task")){
			
			String nomeComponente = jaDeclaradovct.elementAt(0).replaceFirst("task", " ");
			//String nomeComponente = partes[partes.length - 1];
			String descrComp[] = jaDeclaradovct.elementAt(1).split("\\{");
			String descrComponente = descrComp[0];
			String descrComponente2 = descrComponente.replaceFirst("\\}", "");
			//System.out.println("nomeComponente - " + nomeComponente);
			Referencia = "task_ref=(" + nomeComponente + ";" + descrComponente2 ;
		
		}
		
		if(jaDeclarado.startsWith("goal")){
			
			String partes[] = jaDeclaradovct.elementAt(0).split("goal");
			String nomeComponente = partes[partes.length - 1];
			String descrComponente = jaDeclaradovct.elementAt(1).replaceFirst("\\{", "");
			String descrComponente2 = descrComponente.replaceFirst("\\}", "");
			//System.out.println("nomeComponente - " + nomeComponente);
			Referencia = "goal_ref=(" + nomeComponente + ";" + descrComponente2 ;
		}
		
		if(jaDeclarado.startsWith("softgoal")){
			
			String partes[] = jaDeclaradovct.elementAt(0).split("softgoal");
			String nomeComponente = partes[partes.length - 1];
			String descrComponente = jaDeclaradovct.elementAt(1).replaceFirst("\\{", "");
			String descrComponente2 = descrComponente.replaceFirst("\\}", "");
			//System.out.println("nomeComponente - " + nomeComponente);
			Referencia = "softgoal_ref=(" + nomeComponente + ";" + descrComponente2 ;
			//System.out.println("softgoalReferencia - " + Referencia);
		}
		//System.out.println("Referencia: " + Referencia);
		return Referencia;
	}
	
	
	public void intertypeTokens(String intertypePointcutEComponentes, Vector<String> componentes, java.util.Hashtable<String, String> paishash, String arquivo){
	/*	System.out.println("INTERTYPE intertypeTokens");
		this.arquivo = arquivo;
		String origem = new String();
		
		java.util.StringTokenizer afetadoEadicionado = new java.util.StringTokenizer(intertypePointcutEComponentes, "/");
  		java.util.Vector<String> afetadoEadicionadovct = new java.util.Vector<String>();
  		while (afetadoEadicionado.hasMoreTokens())   {
  			afetadoEadicionadovct.addElement(afetadoEadicionado.nextToken());   }
  		//System.out.println("afetadoEadicionadovct: " +afetadoEadicionadovct);
  		
  		java.util.StringTokenizer pointcutsSeparados = new java.util.StringTokenizer(afetadoEadicionadovct.elementAt(0), ",");
  		java.util.Vector<String> pointcutsSeparadosvct = new java.util.Vector<String>();
  		while (pointcutsSeparados.hasMoreTokens())   {
  			pointcutsSeparadosvct.addElement(pointcutsSeparados.nextToken());   } 
  		//System.out.println("pointcutsSeparadosvct: " +pointcutsSeparadosvct);
	 	
  		java.util.StringTokenizer pointcut = new java.util.StringTokenizer(afetadoEadicionadovct.elementAt(1), "-");
	  	java.util.Vector<String> pointcutvct = new java.util.Vector<String>();
	  	while (pointcut.hasMoreTokens())   {
	    	pointcutvct.addElement(pointcut.nextToken());   }
	  	//System.out.println("pointcutvct: " +pointcutvct);
    	
	  	java.util.StringTokenizer pointcutand = new java.util.StringTokenizer(pointcutvct.elementAt(0), "+");
  		java.util.Vector<String> pointcutandvct = new java.util.Vector<String>();
  		while (pointcutand.hasMoreTokens())   {
  		pointcutandvct.addElement(pointcutand.nextToken());	}
  		
      	for(int j=0; j < pointcutandvct.size(); j++) {
      		if(pointcutvct.elementAt(0).contains(pointcutandvct.elementAt(j))){
      			//System.out.println("pointcutsSeparadosvct: " +pointcutsSeparadosvct.elementAt(j));
      			String pointcutAtual = pointcutandvct.elementAt(j); 
      			//System.out.println("pointcutAtual: " +pointcutAtual);  
      		  	
      			for(int k=0; k < pointcutsSeparadosvct.size(); k++) {
        			if(pointcutsSeparadosvct.elementAt(k).startsWith(pointcutAtual)){
      				//System.out.println("pointcutsSeparadosvct: " +pointcutsSeparadosvct.elementAt(j));
      				String componenteAfetado = pointcutsSeparadosvct.elementAt(k); 
      				//System.out.println("INTERTYPE componenteAfetado: " +componenteAfetado);	
      				
      				java.util.StringTokenizer afetado = new java.util.StringTokenizer(componenteAfetado, "-");
  	  				java.util.Vector<String> afetadovct = new java.util.Vector<String>();
     				while (afetado.hasMoreTokens())   {
    					  afetadovct.addElement(afetado.nextToken());}
    				//System.out.println("afetadovct - " + afetadovct);	  
      			    
    				java.util.StringTokenizer origemst = new java.util.StringTokenizer(afetadovct.elementAt(afetadovct.size()-1), "origem");
  	  				java.util.Vector<String> origemvct = new java.util.Vector<String>();
  	  				while (origemst.hasMoreTokens())   {
  	  					origemvct.addElement(origemst.nextToken());}
  	  				//System.out.println("origemvct - " + origemvct);
  	  				
      			    java.util.StringTokenizer afetados = new java.util.StringTokenizer(origemvct.elementAt(0), "*");
  	  				java.util.Vector<String> afetadosvct = new java.util.Vector<String>();
     				while (afetados.hasMoreTokens())   {
    					  afetadosvct.addElement(afetados.nextToken());}
    				//System.out.println("afetadoSvct - " + afetadosvct);
    			
     			//TESTE DO "OR"
     				for(int p=0; p<afetadosvct.size(); p++){
     					
     				java.util.Vector<String> afetadoDescror = new java.util.Vector<String>();
     				java.util.Vector<String> testaorvct = new java.util.Vector<String>();
     				java.util.Vector<String> orfinal = new java.util.Vector<String>();
     				java.util.Vector<String> orvct = new java.util.Vector<String>();
        				if(afetadosvct.elementAt(p).contains("+")){
        					//System.out.println("ENTROU NO OR");
        					String or[] = afetadosvct.elementAt(p).split("\\+");
        					for(int n=0; n<or.length; n++){
        						 for(int q=0; q<componentes.size();q++){
        		     					if(componentes.elementAt(q).contains(or[n]+";")){
        		     						orvct.addElement(componentes.elementAt(q));
        		     					}
        					}
        				}
        					System.out.println("orvct - " + orvct);
        			}
        		
        		if(orvct.size()>0){
        		for(int r=0; r<orvct.size();r++){
        			java.util.StringTokenizer orDescr = new java.util.StringTokenizer(orvct.elementAt(r), "(");
        			while (orDescr.hasMoreTokens())   {
        				   testaorvct.addElement(orDescr.nextToken());}	
        				}
        		//System.out.println("testaorvct - " + testaorvct);
        		
        				for(int r=0; r<testaorvct.size();r++){
        					if(paishash.containsKey(testaorvct.elementAt(r))){
        						orfinal.addElement(testaorvct.elementAt(r));
        						orfinal.addElement(paishash.get(testaorvct.elementAt(r)));
        					//System.out.println("orfinal - " + orfinal);
        						}
        				}
        				
        		if(orfinal.size()>0){	
        		for(int s=1; s<orfinal.size(); s=s+2){	
        			for(int t=s+2; t<orfinal.size(); t=t+2){
        			if(!(orfinal.elementAt(s).equals(orfinal.elementAt(t)))){
        				//System.out.println("orfinal.elementAt(t-1) - " + orfinal.elementAt(t-1));
        				//System.out.println("orfinal.elementAt(s-1) - " + orfinal.elementAt(s-1));
        				if(!afetadoDescror.contains(orfinal.elementAt(t-1)) && !afetadoDescror.contains(orfinal.elementAt(s-1))){
        				afetadoDescror.addElement(orfinal.elementAt(t-1));
        				afetadoDescror.addElement(orfinal.elementAt(s-1));}
        				}
        			  } 
        		}

        			}
        		
        		
        		
        		java.util.StringTokenizer pointcuts = new java.util.StringTokenizer(pointcutvct.elementAt(1), "}");
          		java.util.Vector<String> Separatorvct = new java.util.Vector<String>();
          		while (pointcuts.hasMoreTokens())   {
          			Separatorvct.addElement(pointcuts.nextToken());   }
          		
          		//System.out.println("SeparatorvctORRRR - " + Separatorvct);
        		
          		for(int d=0; d<Separatorvct.size(); d++){
          		//System.out.println("REFERENCIA!");
          			
          			referencia = adicionaReferencia(Separatorvct.elementAt(d));
          		
              		for(int i=0; i<afetadoDescror.size(); i++){
                		intertypeComposicao(afetadoDescror.elementAt(i),referencia);}}
          		}
          		
        		
          		//System.out.println("pointcutvct.elementAt(1)ORRRR - " + pointcutvct.elementAt(1));
        		
        		
        		//System.out.println("afetadoDescrvctORRRR - " + afetadoDescror);

        			//System.out.println("orfinal - " + orfinal);
        			 
     				
     			} //TESTE DO "OR"
    				
    			for(int m=0; m<afetadosvct.size(); m++){
      			    for(int l=0; l<componentes.size();l++){
     					if(componentes.elementAt(l).contains(afetadosvct.elementAt(m)+";")){
 							componenteAfetado=componentes.elementAt(l);
 							//System.out.println("componenteAfetado: " + componenteAfetado);
 							
 							java.util.StringTokenizer afetadoDescr = new java.util.StringTokenizer(componenteAfetado, "(");
 							java.util.Vector<String> afetadoDescrvct = new java.util.Vector<String>();
	  
 							while (afetadoDescr.hasMoreTokens())   {
 							afetadoDescrvct.addElement(afetadoDescr.nextToken());}
 							
 							//System.out.println("afetadoDescrvct:  " + afetadoDescrvct);
 							//System.out.println("componenteAdicionado:  " + pointcutvct.elementAt(1));
 							//System.out.println("componentes.elementAt(" + l + "):  " + componentes.elementAt(l));
 			        		java.util.StringTokenizer pointcuts = new java.util.StringTokenizer(pointcutvct.elementAt(1), "}");
 			          		java.util.Vector<String> Separatorvct = new java.util.Vector<String>();
 			          		while (pointcuts.hasMoreTokens())   {
 			          			Separatorvct.addElement(pointcuts.nextToken());   }
 			          		
 			          		//System.out.println("Separatorvct - " + Separatorvct);
 			        		
 			          		for(int d=0; d<Separatorvct.size(); d++){
 			          			//System.out.println("REFERENCIA!");
 			          			referencia = adicionaReferencia(Separatorvct.elementAt(d));
 			          			intertypeComposicao(afetadoDescrvct.elementAt(0),referencia);
 			          		
 			          		}
 			          		
 							
 							
 							} } }
    					//TRATAMENTO DA ORIGEM
				for(int cont=0; cont<componentes.size();cont++){	
    			if(componentes.elementAt(cont).contains(origemvct.elementAt(1)+";")){
							origem=componentes.elementAt(cont);
							//System.out.println("origem: " + origem);
    			} }		
							java.util.StringTokenizer afetadoDescr = new java.util.StringTokenizer(origem, "(");
							java.util.Vector<String> afetadoDescrvct = new java.util.Vector<String>();
  
							while (afetadoDescr.hasMoreTokens())   {
							afetadoDescrvct.addElement(afetadoDescr.nextToken());}
							
							//System.out.println("afetadoDescrvct:  " + afetadoDescrvct);
							//System.out.println("componenteAdicionado:  " + pointcutvct.elementAt(1));
							//System.out.println("componentes.elementAt(" + l + "):  " + componentes.elementAt(l));
			        		java.util.StringTokenizer pointcuts = new java.util.StringTokenizer(pointcutvct.elementAt(1), "}");
			          		java.util.Vector<String> Separatorvct = new java.util.Vector<String>();
			          		while (pointcuts.hasMoreTokens())   {
			          			Separatorvct.addElement(pointcuts.nextToken());   }
			          		
			          		//System.out.println("Separatorvct - " + Separatorvct);
			        		
			          	    if(Separatorvct.size()>1){
			          		for(int d=0; d<Separatorvct.size()-1; d++){
			          			if(verificaIntertype(Separatorvct.elementAt(d), componentes)){
			          			//System.out.println("REFERENCIA!");
			          			referencia = adicionaReferencia(Separatorvct.elementAt(d));
			          			intertypeComposicao(afetadoDescrvct.elementAt(0),referencia);
			          		}
			          		else if(!verificaIntertype(Separatorvct.elementAt(d), componentes)){
			          			//System.out.println("Separatorvct.elementAt(d): " + Separatorvct.elementAt(d));
			          			//System.out.println("S/ REFERENCIA!");
			          			intertypeComposicao(afetadoDescrvct.elementAt(0),pointcutvct.elementAt(1));
			          		}
			          		}
			          		}else{
			          			//System.out.println("Constraint!!");
			          			//System.out.println("S/ REFERENCIA!");
			          		intertypeComposicao(afetadoDescrvct.elementAt(0),pointcutvct.elementAt(1));	
			          		}
							
      				}	
        		}
      		}
      	}*/
	}
	
	public void intertypeComposicao(String afetado, String adicionado, String arquivo){
	//System.out.println("afetado: " + afetado);
	//System.out.println("adicionado: " + adicionado);
		//String componenteafetado = afetado;
		//String componenteadicionado = adicionado;
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
			  BufferedReader in = new BufferedReader(new FileReader("C:\\composicao.html"));                       
			  while (in.ready()) {                
				  str = str+in.readLine();           
				  }  
			  //System.out.println("CLASSE INTERTYPE: str - " + str);
		  } 
		      catch (IOException e) {    }
		      //System.out.println("CLASSE INTERTYPE: componente afetado - " + componenteafetado);
		      //System.out.println("CLASSE INTERTYPE: componente adicionado - " + componenteadicionado);
		      String partes[] = str.split(SubstituidoModificado5);
		      //System.out.println("CLASSE INTERTYPE: partes[0] - " + partes[0]);
		      String parteModificada = partes[partes.length - 1].replaceFirst("\\)\\{","){" + "\n" + adicionado);
		      //String parteModificada = partes[partes.length - 1].replaceFirst("\\) \\{","){" + adicionadovct.elementAt(adicionadovct.size()-1));
		      //System.out.println("CLASSE INTERTYPE: parte modificada - " + parteModificada);
		      
			  String composicaofinal = partes[0] + SubstituidoModificado5 + parteModificada;
			  //System.out.println("CLASSE INTERTYPE: composicaofinal - " + composicaofinal);
			  try {
		        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\composicao.html"));
		        out.write(composicaofinal); 
		        out.close();
		    }
		    catch(IOException e){
		        // possiveis erros são tratados aqui
		    	}
		}else{
			if(!adicionado.startsWith("{")){
				
				String parte0 = adicionado;
				if(!adicionado.startsWith("goal_ref") && !adicionado.startsWith("softgoal_ref") && !adicionado.startsWith("task_ref")){
				String partes[] = adicionado.split("\\(");
				parte0 = partes[0];
				}
			try {
  			  BufferedReader in = new BufferedReader(new FileReader("C:\\matriz.txt"));                       
  			  while (in.ready()) {                
  				  strvisao = strvisao+in.readLine();           
  				  }  
  			  
		        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\matriz.txt"));
		        out.write(strvisao + "line(" + afetado + "); column(" + parte0 + "); cell(intertype);" ); 
		        //System.out.println("afetado: "+ afetado);
		        //System.out.println("adicionado: " + adicionado);
		        //System.out.println("parte0: "+ parte0);
		        out.close();
		    }
		    catch(IOException e){
		        // possiveis erros são tratados aqui
		    	}
		}
			    }
		
	}
			  
	  }

		
