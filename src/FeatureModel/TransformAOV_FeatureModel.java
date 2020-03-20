package FeatureModel;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

public class TransformAOV_FeatureModel {
	Vector<String> filhos = new Vector<String>();
	Vector<String> pais = new Vector<String>();
	Vector<String> contribuicao = new Vector<String>();
	Hashtable<String, String> correlacao = new Hashtable<String, String>();
	Hashtable<String, String> property = new Hashtable<String, String>();
	StringBuffer arquivo;
	Vector<String> pilha=new Vector<String>();
	Vector<String> grupo=new Vector<String>(), visitado=new Vector<String>();
	String removido, cardinalidade_min, cardinalidade_max, cardinalityGroup[]=new String[2];
	
	public TransformAOV_FeatureModel(Vector<String> vector_filhos, Vector<String> vector_pais, Vector<String> vector_contribuicao, Hashtable<String, String> vector_correlacao, Hashtable<String, String> propriedades){
		filhos=vector_filhos;
		pais=vector_pais;
		contribuicao=vector_contribuicao;
		correlacao=vector_correlacao;
		property=propriedades;
	}
	
	public void gerarArquivo(String nome_modelo, String path, String diretorio, String nome_saida){
		arquivo=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		arquivo.append("<fm:FeatureModel fm:value=\""+nome_modelo+"\" xmlns:fm=\"http://www.pnp-software.com/XFeature/fmm\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.pnp-software.com/XFeature/fmm "+path+"\">\n");
		for(int i=0;i<pais.size();i++){
			if(pais.elementAt(i).equals("-")){//se é pai de um goal_model...
				arquivo.append("<fm:RootFeature fm:value=\""+filhos.elementAt(i).substring(filhos.elementAt(i).indexOf(" ")+1, filhos.elementAt(i).length())+"\">\n");
				recursiva(i);
				arquivo.append("</fm:RootFeature>\n");
			}
		}
		arquivo.append("</fm:FeatureModel>");
		escrever(arquivo.toString(),diretorio, nome_saida);
	}
	
	public void recursiva (int indice){
			int flag; 
			String cardinalidade[]=new String[2];
			Vector<Integer> index=new Vector<Integer>();
			index=busca(filhos.elementAt(indice));//procura por um componente de filho em pai
			for(int j=0;j<index.size();j++){
				flag=0;
				indice=index.elementAt(j);
				
				if(visitado!=null)
					for(int k=0;k<visitado.size();k++){
						if(filhos.elementAt(indice).equalsIgnoreCase(visitado.elementAt(k))){
							flag++;//não vizita esse elemento, pq já foi vizitado
							break;
						}
					}
								
				if(flag==0){
					if(!filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equals("task_ref"))
						visitado.addElement(filhos.elementAt(indice));//adiciona o elemento atual ao vector de visitados
					if(isFeature(filhos.elementAt(indice))==true){//se esse elemento deve ser uma feature...
						cardinalidade=hasCardinality(filhos.elementAt(indice));
						if(cardinalidade[0]==null && cardinalidade[1]==null){//não tem a propriedade cardinality
							if(contribuicao.elementAt(indice).equals("xor")){
								agrupamento(pais.elementAt(indice),"1","1","xor");//insere um grupo de features
								if(pilha.size()>0){
									removido=pilha.remove(pilha.size()-1);
									fecha_tag(removido);//System.out.println("removido: "+removido);
								}
							}
							else
								if(contribuicao.elementAt(indice).equals("inc-or")){
									agrupamento(pais.elementAt(indice),"1","n","inc-or");//insere um grupo de features
									if(pilha.size()>0){
										removido=pilha.remove(pilha.size()-1);
										fecha_tag(removido);//System.out.println("removido: "+removido);
									}
								}
								else{
									solitary(indice);
									if(contribuicao.elementAt(indice).equals("and"))
										arquivo.append("<fm:Cardinality fm:cardMax=\"1\" fm:cardMin=\"1\"/>\n");
									else
										if(contribuicao.elementAt(indice).equals("or"))
											arquivo.append("<fm:Cardinality fm:cardMax=\"1\" fm:cardMin=\"0\"/>\n");
										else
											arquivo.append("<fm:Cardinality fm:cardMax=\"\" fm:cardMin=\"\"/>\n");
									verificarAgrupamento(indice);
									recursiva(indice);
								}
						}
						else{//tem a propriedade cardinality
							solitary(indice);//insere uma feature
							cardinalidade_min=cardinalidade[0];
							cardinalidade_max=cardinalidade[1];
							arquivo.append("<fm:Cardinality fm:cardMax=\""+cardinalidade_max+"\" fm:cardMin=\""+cardinalidade_min+"\"/>\n");
							
							verificarAgrupamento(indice);
							recursiva(indice);
						}
						//recursiva(indice);
					}
				}
			}//System.out.println("pilha: "+pilha);
			if(pilha.size()>0){
				removido=pilha.remove(pilha.size()-1);
				fecha_tag(removido);//System.out.println("removido: "+removido);
			}
	}
	
	public void verificarAgrupamento(int indice){
		String array_grupo[];
		Vector retorno_grupo;
		array_grupo=hasGroup(filhos.elementAt(indice));
		if(array_grupo!=null){//se o elemento atual é pai de um grupo...
			retorno_grupo=new Vector(Arrays.asList(array_grupo));//transforma o array num vector
			arquivo.append("<fm:FeatureGroup fm:value=\"FeatureGroup\">\n<fm:Cardinality fm:cardMax=\""+cardinalityGroup[1]+"\" fm:cardMin=\""+cardinalityGroup[0]+"\"/>\n");
			pilha.addElement("FeatureGroup");//abre o grupo
			grupo.addAll(retorno_grupo);//grupo com todas as features do agrupamento
			visitado.addAll(retorno_grupo);//retorno_grupo não for null insere as features do grupo em visitados
			agrupamentoProperty(filhos.elementAt(indice));
		}
	}
	
	public String[] hasGroup(String componente){
		String grupo,group_features[]=null;
		String value=property.get(componente);
		int flag=0;
		
		if(value==null){//nao tem property
			return group_features;
		}
		else{//tem property
			String property_values[]=value.split("\\;");//divide a string value por ";"
			for(int j=0;j<property_values.length;j++){
				if(property_values[j].substring(0,property_values[j].indexOf("=")).equalsIgnoreCase("groupFeature")){
					grupo=property_values[j].substring(property_values[j].indexOf("=")+2,property_values[j].length()-1);
					group_features=grupo.split("\\,");
					flag++;
				}
				else{
					if(property_values[j].substring(0,property_values[j].indexOf("=")).equalsIgnoreCase("cardinalityGroupMin")){
						cardinalityGroup[0]=property_values[j].substring(property_values[j].indexOf("=")+1,property_values[j].length());
						flag++;
					}
					else
						if(property_values[j].substring(0,property_values[j].indexOf("=")).equalsIgnoreCase("cardinalityGroupMax")){
							cardinalityGroup[1]=property_values[j].substring(property_values[j].indexOf("=")+1,property_values[j].length());
							flag++;
						}
				}
				if(flag==3)
					return group_features;
			}
			return group_features;//se possui property, mas não possui groupFeature, retorna o valor default (null)
		}
	}
	
	public void agrupamentoProperty(String componente){
		Vector<Integer> index=busca(componente);
		int indice;
		for(int i=0;i<grupo.size();i++){
			for(int j=0;j<index.size();j++){
				indice=index.elementAt(j);
				if(filhos.elementAt(indice).equalsIgnoreCase(grupo.elementAt(i))){
					if(isFeature(filhos.elementAt(indice))==true){//se esse elemento deve ser uma feature...
						if(filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("task_ref")||filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("goal_ref")||filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("softgoal_ref")){//se é referencia
							arquivo.append("<fm:GroupedReference fm:value=\""+filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length())+"\">\n");//retira a substring "task_ref" ou "goal_ref" ou "softgoal_ref" que vem antes do nome das referencias
							pilha.addElement("GroupedReference");
						}
						else{
							arquivo.append("<fm:GroupedFeature fm:value=\""+filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length())+"\">\n");//retira a substring "task" ou "goal" ou "softgoal" que vem antes do nome das tarefas em filhos
							pilha.addElement("GroupedFeature");
						}
						verificarAnotacao(indice);
						verificarAgrupamento(indice);
							
						recursiva(indice);
					}
					break;
				}
			}
		}
		
		removido=pilha.remove(pilha.size()-1);
		fecha_tag(removido);//fecha o grupo anterior
	}
	
	public void agrupamento(String pai, String cardinalidade_min, String cardinalidade_max, String relacionamento){
		Vector<Integer> index=busca(pai);
		int indice, flag;
		String cardinalidade[]=new String[2];
		
		arquivo.append("<fm:FeatureGroup fm:value=\"FeatureGroup\">\n<fm:Cardinality fm:cardMax=\""+cardinalidade_max+"\" fm:cardMin=\""+cardinalidade_min+"\"/>\n");
		pilha.addElement("FeatureGroup");
		
		for(int j=0;j<index.size();j++){
			flag=0;
			indice=index.elementAt(j);
			if(contribuicao.elementAt(indice).equalsIgnoreCase(relacionamento)){
				cardinalidade=hasCardinality(filhos.elementAt(indice));
				if(cardinalidade[0]==null && cardinalidade[1]==null){//se nao tem cardinalidade
					if(grupo!=null){
						for(int k=0;k<grupo.size();k++){
							if(filhos.elementAt(indice).equalsIgnoreCase(grupo.elementAt(k))){
								flag++;//não vizita esse elemento, pq já foi vizitado
								break;
							}
						}
					}
					if(flag==0){//se nao foi vizitado ainda
						if(filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("task_ref")||filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("goal_ref")||filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("softgoal_ref")){//se é referencia
							arquivo.append("<fm:GroupedReference fm:value=\""+filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length())+"\">\n");//retira a substring "task_ref" ou "goal_ref" ou "softgoal_ref" que vem antes do nome das referencias
							pilha.addElement("GroupedReference");
						}
						else{
							arquivo.append("<fm:GroupedFeature fm:value=\""+filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length())+"\">\n");//retira a substring "task" ou "goal" ou "softgoal" que vem antes do nome das tarefas em filhos
							pilha.addElement("GroupedFeature");
						}
						verificarAnotacao(indice);
						verificarAgrupamento(indice);
						
						recursiva(indice);
						visitado.addElement(filhos.elementAt(indice));
					}
				}
			}
		}
	}
	
	public void solitary(int indice){
		if(filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("task_ref")||filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("goal_ref")||filhos.elementAt(indice).substring(0,filhos.elementAt(indice).indexOf(" ")).equalsIgnoreCase("softgoal_ref")){//se é referencia
			arquivo.append("<fm:SolitaryReference fm:value=\""+filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length())+"\">\n");
			pilha.addElement("SolitaryReference");
		}
		else{
			arquivo.append("<fm:SolitaryFeature fm:value=\""+filhos.elementAt(indice).substring(filhos.elementAt(indice).indexOf(" ")+1,filhos.elementAt(indice).length())+"\">\n");
			pilha.addElement("SolitaryFeature");
		}
		
		verificarAnotacao(indice);
	}
	
	public void verificarAnotacao(int indice){
		if(correlacao.get(filhos.elementAt(indice))!=null){
			arquivo.append("<fm:Annotation fm:value=\"Annotation\">\n<fm:Description fm:value=\"");
			arquivo.append(correlacao.get(filhos.elementAt(indice)));
			arquivo.append("\"/>\n</fm:Annotation>\n");
		}
	}
	
	public boolean isFeature(String componente){
		String value=property.get(componente);
		if(value==null){//nao tem property
			return true;
		}
		else{//tem property
			String property_values[]=value.split("\\;");//divide a string value por ";"
			for(int j=0;j<property_values.length;j++){
				if(property_values[j].substring(0,property_values[j].indexOf("=")).equalsIgnoreCase("isFeature")){
					if(property_values[j].substring(property_values[j].indexOf("=")+1,property_values[j].length()).equalsIgnoreCase("yes"))
						return true;
					else
						return false;
				}
			}
			return true;//se possui property, mas não possui isFeature, retorna o valor default (true)
		}
	}

	public String[] hasCardinality(String componente){
		String cardinalidade[]=new String[2];
		int flag=0;
		String value=property.get(componente);
		if(value==null){//nao tem property
			return cardinalidade;
		}
		else{//tem property
			String property_values[]=value.split("\\;");//divide a string value por ";"
			for(int j=0;j<property_values.length;j++){
				if(property_values[j].substring(0,property_values[j].indexOf("=")).equalsIgnoreCase("cardinalityMin")){
					cardinalidade[0]=property_values[j].substring(property_values[j].indexOf("=")+1,property_values[j].length());
					flag++;
				}
				else
					if(property_values[j].substring(0,property_values[j].indexOf("=")).equalsIgnoreCase("cardinalityMax")){
						cardinalidade[1]=property_values[j].substring(property_values[j].indexOf("=")+1,property_values[j].length());
						flag++;
					}
				if(flag==2)	
					return cardinalidade;
				
			}
			return cardinalidade;//se possui property, mas não possui cardinality, retorna o valor default (null)
		}
	}

	public void fecha_tag(String flag){
		if(flag.equalsIgnoreCase("SolitaryFeature"))
			arquivo.append("</fm:SolitaryFeature>\n");
		else if(flag.equalsIgnoreCase("SolitaryReference"))
			arquivo.append("</fm:SolitaryReference>\n");
		else if(flag.equalsIgnoreCase("FeatureGroup"))
			arquivo.append("</fm:FeatureGroup>\n");
		else if(flag.equalsIgnoreCase("GroupedReference"))
			arquivo.append("</fm:GroupedReference>\n");
		else
			arquivo.append("</fm:GroupedFeature>\n");
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
	
	public void escrever(String arquivo, String diretorio, String nome_saida){
		FileOutputStream fos;
		int i=0;
		
		try{
			fos=new FileOutputStream(diretorio+"\\"+nome_saida+".xfm");
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
