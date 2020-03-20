/* Recebe o Vector composicaovct e através dele cria os Vectors com todos os filhos, pais, contribuições e correlações.
 * 
 * Para criar o Vector filhos: depois de cada <br> armazena em uma string o tipo do componente e concatena com o nome do componente (que pode estar no indice seguinte, depois de um "(" ou depois de um "=(").
 * Se o índice seguinte for "(" passar para o próximo índice e se for "=" passar 2 índices a frente.
 * Para criar o Vector pais: Para cada "{" empilha o nome do componente e para cada "}" desempilha.
 * O pai será o elemento do topo da pilha naquele momento. Como no início de cada goal_model o tamanho da pilha é 0, o pai será "-".
 */

package FeatureModel;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class FeatureModel {
	Vector <String> composicao=new Vector <String>();
	Vector <String> filhos=new Vector <String>();
	Vector <String> pais=new Vector <String>();
	Vector <String> contribuicao=new Vector <String>();
	Vector <String> pilha=new Vector <String>();
	Hashtable<String, String> correlacao = new Hashtable<String, String>();
	Hashtable<String, String> property = new Hashtable<String, String>();
	File file_xfmm, file_aov;
	String nome_modelo, nome_saida;
	
	public FeatureModel(Vector <String> composicaovct, File xfmm, File aov, String nome, String saida){
		composicao=composicaovct;
		file_xfmm=xfmm;
		file_aov=aov;
		nome_modelo=nome;
		nome_saida=saida;
	}	

	public FeatureModel(){}
	
	public void gerarTabela(){
		String componente="-", value="";
		for(int i=1;i<composicao.size();i++){
			if(composicao.elementAt(i).equalsIgnoreCase("property")){
				value="";
				i+=2;
				while(composicao.elementAt(i)!="}"){
					value+=composicao.elementAt(i);
					i++;
				}
				property.put(componente, value);
			}
			else
				if(composicao.elementAt(i).equalsIgnoreCase("crosscutting")){
					i=crosscutting(i,value);
				}
				else
					if(composicao.elementAt(i).equalsIgnoreCase("correlation")){
						i=correlation(i,value);
					}
					else
						if(composicao.elementAt(i).length()>4){
							if(composicao.elementAt(i).substring(0, 4).equalsIgnoreCase("<br>")||composicao.elementAt(i).equalsIgnoreCase("task_ref")||composicao.elementAt(i).equalsIgnoreCase("goal_ref")||composicao.elementAt(i).equalsIgnoreCase("softgoal_ref")){
							//if(composicao.elementAt(i).substring(0, 4).equalsIgnoreCase("<br>")||((composicao.elementAt(i-1).equalsIgnoreCase("{")||composicao.elementAt(i-1).equalsIgnoreCase("}"))&&(composicao.elementAt(i).equalsIgnoreCase("task_ref")||composicao.elementAt(i).equalsIgnoreCase("goal_ref")||composicao.elementAt(i).equalsIgnoreCase("softgoal_ref")))){
								if(composicao.elementAt(i).substring(0, 4).equalsIgnoreCase("<br>")){
									componente=composicao.elementAt(i).substring(4, composicao.elementAt(i).length()-1);
									i++;
									if(composicao.elementAt(i).equalsIgnoreCase("("))//goal_model
										i++;
								}
								else{//referencia
									componente=composicao.elementAt(i);
									i+=3;
								}
								componente+=" "+composicao.elementAt(i);
								filhos.addElement(componente);
								pais.addElement(pilha.elementAt(pilha.size()-1));
								i++;
								if(composicao.elementAt(i).equalsIgnoreCase("("))
									i++;
								else{
									while(composicao.elementAt(i)!=")"){
										i++;
									}
									i-=2;
								}
								//if((composicao.elementAt(i-1).equalsIgnoreCase("(")||composicao.elementAt(i-1).equalsIgnoreCase(";"))&&(composicao.elementAt(i).equalsIgnoreCase("and")||composicao.elementAt(i).equalsIgnoreCase("or")||composicao.elementAt(i).equalsIgnoreCase("xor")||composicao.elementAt(i).equalsIgnoreCase("inc-or")))
								if(composicao.elementAt(i).equalsIgnoreCase("and")||composicao.elementAt(i).equalsIgnoreCase("or")||composicao.elementAt(i).equalsIgnoreCase("xor")||composicao.elementAt(i).equalsIgnoreCase("inc-or"))
									contribuicao.addElement(composicao.elementAt(i));
								else
									contribuicao.addElement("-");
							}
						}
						else
							if(composicao.elementAt(i).equalsIgnoreCase("{"))
								pilha.addElement(componente);
							else
								if(composicao.elementAt(i).equalsIgnoreCase("}"))
									pilha.remove(pilha.size()-1);
		}
		/*System.out.println("filhos: "+filhos);
		System.out.println("pais: "+pais);
		System.out.println("contribuicao: "+contribuicao);
		System.out.println("property: "+property);
		System.out.println("correlacao: "+correlacao);*/
		TransformAOV_FeatureModel feature_model=new TransformAOV_FeatureModel(filhos, pais, contribuicao, correlacao, property);	
		feature_model.gerarArquivo(nome_modelo, file_xfmm.getPath(), file_aov.getParent(), nome_saida);
	}
	
	public int crosscutting(int i, String value){
		String key, nome_componente, tipo_componente;
		Hashtable<String, String> pointcuts=new Hashtable<String, String>();
		Hashtable<String, String> advices=new Hashtable<String, String>();
		i+=10;
		while(composicao.elementAt(i).equalsIgnoreCase("pointcut")){
			i+=2;
			key=composicao.elementAt(i);
			i+=3;
			while(composicao.elementAt(i).equalsIgnoreCase("include")){
				i+=2;
				if(pointcuts.get(key)!=null)//o id do poitcut ja esta na hashtable
					pointcuts.put(key,pointcuts.get(key)+"-"+composicao.elementAt(i));//concatena
				else
					pointcuts.put(key,composicao.elementAt(i));
				i+=5;
				if(composicao.elementAt(i).equalsIgnoreCase("and "))
					i++;
			}
		}
		while(composicao.elementAt(i).equalsIgnoreCase("advice")){
			i+=5;
			key=composicao.elementAt(i);
			i+=2;
			while(composicao.elementAt(i).equalsIgnoreCase("task_ref")||composicao.elementAt(i).equalsIgnoreCase("goal_ref")||composicao.elementAt(i).equalsIgnoreCase("softgoal_ref")){
				value=composicao.elementAt(i);
				tipo_componente=composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("_"));
				i+=3;
				value+=" "+composicao.elementAt(i);
				nome_componente=composicao.elementAt(i);
				i+=4;
				if(composicao.elementAt(i).equals(")")){
					value+=contribuicao_referencia(nome_componente, tipo_componente);
					i++;
				}
				else{
					value+=";"+composicao.elementAt(i);
					i+=3;
				}
				if(advices.get(key)!=null)
					advices.put(key,advices.get(key)+"-"+value);
				else
					advices.put(key,value);
			}
			i++;
		}
		//System.out.println("pointcuts: "+pointcuts);System.out.println("advices: "+advices);
		adicionar_aos_vetores(pointcuts, advices);
		
		return i;
	}
	
	public String contribuicao_referencia(String nome_componente, String tipo_componente){
		for(int i=0;i<composicao.size();i++){
			if(composicao.elementAt(i).equals("<br>"+tipo_componente+" ") && composicao.elementAt(i+1).equals(nome_componente)){
				i+=3;
				return ";"+composicao.elementAt(i);
			}
		}
		return "";
	}
	
	public void adicionar_aos_vetores(Hashtable<String, String> pointcuts, Hashtable<String, String> advices){
		Set<String> chaves = new HashSet<String>();
		chaves.addAll(pointcuts.keySet());//adiciona todas as chaves de pointcuts ao Set chaves_pointcuts
		Object[] keys=chaves.toArray();//transforma os Sets em Arrays
		String pai_cross, filhos_cross;
		String pointcut_pai[], advice_filho[], tipo[];
		
		for(int i=0;i<chaves.size();i++){
			pai_cross=pointcuts.get(keys[i]);
			pointcut_pai=pai_cross.split("\\-");//divide a string value por "-"
			filhos_cross=advices.get(keys[i]);
			advice_filho=filhos_cross.split("\\-");//divide a string value por "-"
			
			tipo=new String[pointcut_pai.length];
			for(int l=0;l<pointcut_pai.length;l++){
				tipo[l]=buscarTipo(pointcut_pai[l]);
			}
			
			for(int j=0;j<advice_filho.length;j++){
				for(int k=0;k<pointcut_pai.length;k++){
					pais.addElement(tipo[k]+pointcut_pai[k]);
					contribuicao.addElement(advice_filho[j].substring(advice_filho[j].indexOf(";")+1,advice_filho[j].length()));
					filhos.addElement(advice_filho[j].substring(0,advice_filho[j].indexOf(";")));//adiciona o advice ao vetor de filhos como uma referencia
				}
			}
		}
	}
	
	public String buscarTipo(String componente){
		for(int i=1;i<composicao.size();i++){
			if(composicao.elementAt(i).equals(componente))
				if(composicao.elementAt(i-1).equals("<br>task ")||composicao.elementAt(i-1).equals("<br>goal ")||composicao.elementAt(i-1).equals("<br>softgoal "))
					return composicao.elementAt(i-1).substring(4, composicao.elementAt(i-1).length());					 
		}
		return "";
	}
	
	public int correlation(int i, String value){
		String tipo_correlation, key;
		int indice_tipo;
		i+=2;
		tipo_correlation=composicao.elementAt(i);
		i+=5;
		key=composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf('_'))+" ";
		i+=3;
		key+=composicao.elementAt(i);
		i+=10;
		value+=composicao.elementAt(i);
		if(correlacao.get(key)!=null){//o componente ja esta na hashtable
			indice_tipo=correlacao.get(key).indexOf(tipo_correlation);
			if(indice_tipo>=0 && indice_tipo<correlacao.get(key).length())
				correlacao.put(key, correlacao.get(key)+", "+value);//concatena
			else
				correlacao.put(key, correlacao.get(key)+"; "+tipo_correlation+": "+value);//concatena
		}
		else
			correlacao.put(key,tipo_correlation+": "+value);
		i+=5;
		return i;
	}
}