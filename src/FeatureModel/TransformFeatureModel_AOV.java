package FeatureModel;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class TransformFeatureModel_AOV {
	Vector<String> filhos = new Vector<String>();
	Vector<String> pais = new Vector<String>();
	Vector<String> contribuicao = new Vector<String>();
	Hashtable<String, String> correlacao = new Hashtable<String, String>();
	Hashtable<String, String> property = new Hashtable<String, String>();
	Hashtable<String, Integer> rotulos_referencias = new Hashtable<String, Integer>();
	Vector<Integer> indices_pointcuts = new Vector<Integer>();
	Vector<Integer> indices_advices = new Vector<Integer>();
	Hashtable<Integer, String> indices_cross = new Hashtable<Integer, String>();
	Vector<Integer> vector_pointcuts = new Vector<Integer>();
	Vector<String> pilha=new Vector<String>();
	StringBuffer arquivo;
	int rotulo=1;
	
	public TransformFeatureModel_AOV(Vector<String> vector_filhos, Vector<String> vector_pais, Vector<String> vector_contribuicao, Hashtable<String, String> correlation, Hashtable<String, String> propriedades){
		filhos=vector_filhos;
		pais=vector_pais;
		contribuicao=vector_contribuicao;
		correlacao=correlation;
		property=propriedades;
	}
	
	public void gerarArquivo(String nome_modelo, String diretorio){
		int j=1;
		arquivo=new StringBuffer("aspect_oriented_model{\n");
		ocultarReferencias();
		for(int i=0;i<pais.size();i++){
			if(pais.elementAt(i).equals("-")){//se é pai de um goal_model...
				arquivo.append("goal_model ("+filhos.elementAt(i)+"; GM"+j+"){\n");
				recursiva(i);
				correlation();
				crosscutting();
				arquivo.append("}\n");
				j++;
			}
		}
		arquivo.append("};");
		escrever(arquivo.toString(),diretorio,nome_modelo);
	}
	
	public void recursiva (int indice){
		Vector<Integer> index=new Vector<Integer>();
		index=busca(filhos.elementAt(indice));
		String propriedades, nome_referencia;
		boolean flag;
		for(int j=0;j<index.size();j++){
			indice=index.elementAt(j);
			propriedades=property.get(filhos.elementAt(indice));
			if(filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equals("task_ref")){//referencia
				flag=true;
				for(int k=0;k<vector_pointcuts.size();k++){
					if(indice==(int)vector_pointcuts.elementAt(k))
						flag=false;
				}
				if(flag==true){
					nome_referencia=filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length());
					arquivo.append("task_ref = ("+nome_referencia+"; T"+buscarRotuloReferencia("task "+nome_referencia)+";");
					
					if(contribuicao.elementAt(indice).equals("and"))
						arquivo.append(" and;)\n");
					else if(contribuicao.elementAt(indice).equals("or"))
						arquivo.append(" or;)\n");
					else if(contribuicao.elementAt(indice).equals("xor"))
						arquivo.append(" xor;)\n");
					else if(contribuicao.elementAt(indice).equals("inc-or"))
						arquivo.append(" inc-or;)\n");
					else if (propriedades!=null){//property
						arquivo.append(") property{"+propriedades+"}");
						propriedades=null;
					}
					else
						arquivo.append(")\n");
				}
			}
			else{
				arquivo.append(filhos.elementAt(indice)+" (");
				pilha.addElement("task");
				if(contribuicao.elementAt(indice).equals("and"))
					arquivo.append("and; T"+rotulo+";){");
				else if(contribuicao.elementAt(indice).equals("or"))
					arquivo.append("or; T"+rotulo+";){");
				else if(contribuicao.elementAt(indice).equals("xor"))
					arquivo.append("xor; T"+rotulo+";){");
				else if(contribuicao.elementAt(indice).equals("inc-or"))
					arquivo.append("inc-or; T"+rotulo+";){");
				else if (propriedades!=null){//property
					arquivo.append("T"+rotulo+";){property{"+propriedades+"}");
					propriedades=null;
				}
				else
					arquivo.append("T"+rotulo+";){");
					
				if(propriedades!=null)
					arquivo.append("property{"+propriedades+"}\n");
				else
					arquivo.append("\n");
				
				rotulos_referencias.put(filhos.elementAt(indice),rotulo);
				rotulo++;
				recursiva(indice);
			}
		}
		if(pilha.size()>0){
			pilha.remove(pilha.size()-1);//remove o ultimo elemento
			arquivo.append("}\n");//fecha a task
		}
	}
	
	public void correlation(){
		Set<String> chaves = new HashSet<String>();
		chaves.addAll(correlacao.keySet());//adiciona todas as chaves de correlacao
		Object[] keys=chaves.toArray();//transforma o chaves no Array keys
		for(int j=0;j<keys.length;j++){
			int indice_dois_pontos;
			String tipo_correlacao="", nome_source, nome_target, targets[]=correlacao.get(keys[j]).split ("[,\\s;]{2}",-1);
			for(int k=0;k<targets.length;k++){
				arquivo.append("correlation (");
				indice_dois_pontos=targets[k].indexOf(":");
				if(indice_dois_pontos>-1){
					tipo_correlacao=targets[k].substring(0,indice_dois_pontos);
					nome_target=targets[k].substring(indice_dois_pontos+2,targets[k].length());
				}
				else{
					nome_target=targets[k];
				}
				nome_source=keys[j].toString().substring(keys[j].toString().indexOf(" ")+1,keys[j].toString().length());
				arquivo.append(tipo_correlacao+"){\nsource = task_ref = ("+nome_source+"; "+buscarRotuloReferencia("task "+nome_source)+";)");
				arquivo.append("\ntarget = task_ref = ("+nome_target+"; "+buscarRotuloReferencia("task "+nome_target)+";)\n}\n");
			}
		}
	}
	
	public int buscarRotuloReferencia(String componente){
		if(rotulos_referencias.get(componente)!=null){
			return rotulos_referencias.get(componente);
		}
		return 0;
	}
	
	public void ocultarReferencias(){
		Set<Integer> chaves_cross = new HashSet<Integer>();
		Object[] keys;
		
		for(int i=0;i<filhos.size();i++){
			if(filhos.elementAt(i).substring(0,filhos.elementAt(i).indexOf(" ")).equals("task_ref")){//referencia
				String nome_referencia=filhos.elementAt(i).substring(filhos.elementAt(i).indexOf(" ")+1,filhos.elementAt(i).length());
				indices_pointcuts.addElement(i);//adiciona o indice de quem chama a referencia
				indices_advices.addElement(filhos.indexOf("task "+nome_referencia));//adiciona o indice da referencia original
			}
		}
		
		for(int i=0;i<indices_advices.size();i++){
			for(int j=i+1;j<indices_advices.size();j++){
				if(indices_advices.elementAt(i).equals(indices_advices.elementAt(j)))
					indices_cross.put(indices_advices.elementAt(i),"");//adiciona a indices_cross os elementos que se repetem
			}
		}
		
		chaves_cross.addAll(indices_cross.keySet());
		keys=chaves_cross.toArray();
		for(int i=0;i<indices_pointcuts.size();i++){
			for(int j=0;j<keys.length;j++){
				if(indices_advices.elementAt(i).equals(keys[j]))
					vector_pointcuts.addElement(indices_pointcuts.elementAt(i));
			}
		}//System.out.println("vector_pointcuts: "+vector_pointcuts);
	}
	
	public void crosscutting(){
		Hashtable<String, String> source = new Hashtable<String, String>(), pointcuts_advices = new Hashtable<String, String>();
		Set<Integer> chaves_cross = new HashSet<Integer>();
		Set<String> chaves_source = new HashSet<String>();
		Object[] keys, keys_;
		String nome, advices[], include_pointcut="";
		int indice, id=1;
		
		chaves_cross.addAll(indices_cross.keySet());
		keys=chaves_cross.toArray();
		for(int i=0;i<chaves_cross.size();i++){
			if(source.get(pais.elementAt((Integer)keys[i]))!=null)//se ja existe
				source.put(pais.elementAt((Integer)keys[i]),source.get(pais.elementAt((Integer)keys[i]))+";"+keys[i]);//concatena
			else
				source.put(pais.elementAt((Integer)keys[i]),keys[i].toString());
		}
		
		chaves_source.clear();
		chaves_source.addAll(source.keySet());//adiciona todas as chaves de source no set chaves
		keys=chaves_source.toArray();//transforma o set em Array
		for(int i=0;i<keys.length;i++){
			nome=keys[i].toString();
			arquivo.append("crosscutting (source = "+nome.substring(nome.indexOf(" ")+1,nome.length())+" (T"+buscarRotuloReferencia(keys[i].toString())+")){");
			
			nome=source.get(keys[i]);
			advices=nome.split("\\;");
			for(int j=0;j<advices.length;j++){
				indice=Integer.parseInt(advices[j]);
				for(int k=0;k<indices_advices.size();k++){
					if(indice==indices_advices.elementAt(k))
						include_pointcut+=pais.elementAt(indices_pointcuts.elementAt(k))+";";
				}
				if(pointcuts_advices.get(include_pointcut)!=null)
					pointcuts_advices.put(include_pointcut,pointcuts_advices.get(include_pointcut)+";"+filhos.elementAt(indice));
				else
					pointcuts_advices.put(include_pointcut,filhos.elementAt(indice));
				
				include_pointcut="";
			}
			
			//System.out.println("pointcuts_advices["+i+"]: "+pointcuts_advices);
			chaves_source.clear();
			chaves_source.addAll(pointcuts_advices.keySet());
			keys_=chaves_source.toArray();
			for(int j=0;j<keys_.length;j++){
				arquivo.append("\npointcut (PC"+id+"): include(");
				advices=keys_[j].toString().split("\\;");
				arquivo.append(advices[0].substring(advices[0].indexOf(" ")+1,advices[0].length())+"; "+buscarRotuloReferencia(advices[0])+";)");
				for(int k=1;k<advices.length;k++){
					arquivo.append(" and include("+advices[k].substring(advices[k].indexOf(" ")+1,advices[k].length())+"; "+buscarRotuloReferencia(advices[k])+";)");
				}
				id++;
			}
			id=1;
			for(int j=0;j<keys_.length;j++){
				arquivo.append("\nadvice (around): PC"+id+"{");
				advices=pointcuts_advices.get(keys_[j]).split("\\;");
				for(int k=0;k<advices.length;k++){
					arquivo.append("\ntask_ref = ("+advices[k].substring(advices[k].indexOf(" ")+1,advices[k].length())+"; "+buscarRotuloReferencia(advices[k])+";)");
				}
				arquivo.append("\n}");
				id++;
			}
			pointcuts_advices.clear();
			arquivo.append("\n}\n");
			id=1;
		}
	}
	
	public Vector<Integer> busca(String componente){
		Vector<Integer> index = new Vector<Integer>();
		for(int i=0;i<pais.size();i++){
			if(pais.elementAt(i).equalsIgnoreCase(componente)){
				index.addElement(i);
			}
		}
		return index;
	}
	
	public void escrever(String arquivo, String diretorio, String nome_modelo){
		FileOutputStream fos;
		int i=0;
		
		try{
			fos=new FileOutputStream(diretorio+"\\"+nome_modelo+".txt");
			while(i<arquivo.length())
			   fos.write((int)arquivo.charAt(i++));
			fos.flush();
			fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
