package FeatureModel;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

public class PL_AOVgraph {
	Vector <String> filhos=new Vector <String>();
	Vector <String> pais=new Vector <String>();
	Vector <String> contribuicao=new Vector <String>();
	Hashtable<String, String> correlacao = new Hashtable<String, String>();
	Hashtable<String, String> property = new Hashtable<String, String>();
	Vector<String> composicao=new Vector<String>();
	Vector <String> pilha_pais=new Vector <String>();//controla os pais
	Vector <String> pilha_cardinalidade_grupo=new Vector <String>();//armazena a cardinalidade dos grupos
	String nome_saida;
	File file_xfm;
	
	public PL_AOVgraph(Vector<String> composicaovct, File xfm, String nome){
		composicao=composicaovct;
		file_xfm=xfm;
		nome_saida=nome;
	}
	
	public void gerarVetores(){
		String componente, max, min, value="", pai=null, group=null;
		int card_min, card_max;
		for(int i=0;i<composicao.size();i++){
			if(composicao.elementAt(i).equalsIgnoreCase("}") || composicao.elementAt(i).equalsIgnoreCase("/}")){
				if(value!="" && property.get(pai)==null)
					property.put(pai, value);
				value="";
				
				if(composicao.elementAt(i).equalsIgnoreCase("}"))//fechando uma feature
					pilha_pais.remove(pilha_pais.lastElement());//remove o pai da pilha, ja q fechou
				else//fechando um grupo
					pilha_cardinalidade_grupo.remove(pilha_cardinalidade_grupo.lastElement());//remove a cardinalidade da pilha
			}
			else if(composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("RootFeature")){
				componente=composicao.elementAt(i).substring(composicao.elementAt(i).indexOf("=")+1, composicao.elementAt(i).length());
				filhos.addElement(componente);
				pais.addElement("-");
				pilha_pais.addElement(componente);
				contribuicao.addElement(" ");
			}
			else if(composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("SolitaryFeature") || composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("SolitaryReference") || composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("GroupedFeature") || composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("GroupedReference")){
				if(composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("SolitaryReference") || composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("GroupedReference"))
					componente="task_ref "+composicao.elementAt(i).substring(composicao.elementAt(i).indexOf("=")+1, composicao.elementAt(i).length());
				else
					componente="task "+composicao.elementAt(i).substring(composicao.elementAt(i).indexOf("=")+1, composicao.elementAt(i).length());
				filhos.addElement(componente);
				pais.addElement(pilha_pais.elementAt(pilha_pais.size()-1));
				if(composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("GroupedFeature") || composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("GroupedReference")){//agrupamento
					max=pilha_cardinalidade_grupo.lastElement().substring(0,pilha_cardinalidade_grupo.lastElement().indexOf(" "));
					min=pilha_cardinalidade_grupo.lastElement().substring(pilha_cardinalidade_grupo.lastElement().indexOf(" ")+1,pilha_cardinalidade_grupo.lastElement().length());
					if(max.equals("-") && min.equals("-")){//grupo nao tem cardinalidade
						contribuicao.addElement("-");
						pai=pais.lastElement();
						value+="groupFeature=("+group+");";
						
						if(property.get(pai)==null)
							property.put(pai, value);
						value="";
					}
					else{
						card_min=Integer.parseInt(min);
						if(!max.equals("n")){//se a cardinalidade maxima nao for n, converte pra inteiro
							card_max=Integer.parseInt(max);
							if(card_max==1 && card_min==1)//xor
								contribuicao.addElement("xor");
							else{
								contribuicao.addElement("-");//a cardinalidade sera especificada no property
								pai=pais.lastElement();
								value+="groupFeature=("+group+");cardinalityGroupMin="+card_min+";"+"cardinalityGroupMax="+card_max+";";
								
								if(property.get(pai)==null)
									property.put(pai, value);
								value="";
							}
						}
						else
							if(card_min==1)//inc-or
								contribuicao.addElement("inc-or");
							else{
								contribuicao.addElement("-");//a cardinalidade sera especificada no property
								pai=pais.lastElement();
								value+="groupFeature=("+group+");cardinalityGroupMin="+card_min+";"+"cardinalityGroupMax="+max+";";
								
								if(property.get(pai)==null)
									property.put(pai, value);
								value="";
							}
					}
				}
				pilha_pais.addElement(componente);
			}
			else if(composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("Cardinality")){
				if(composicao.elementAt(i).charAt(composicao.elementAt(i).indexOf("=")+1)==' ')//nao tem cardinalidades 
					contribuicao.addElement("-");
				else{
					max=composicao.elementAt(i).substring(composicao.elementAt(i).indexOf("=")+1, composicao.elementAt(i).indexOf(" "));//converte pra inteiro
					min=composicao.elementAt(i).substring(composicao.elementAt(i).indexOf(" ")+1, composicao.elementAt(i).length()-1);//converte pra inteiro
					card_min=Integer.parseInt(min);
					if(!max.equals("n")){//se a cardinalidade maxima nao for n, converte pra inteiro
						card_max=Integer.parseInt(max);
						if(card_max==1 && card_min==1)//and
							contribuicao.addElement("and");
						else
							if(card_max==1 && card_min==0)//or
								contribuicao.addElement("or");
							else{
								contribuicao.addElement("-");//a cardinalidade sera especificada no property
								pai=pilha_pais.lastElement();
								value="cardinalityMin="+card_min+";"+"cardinalityMax="+card_max+";";
							}
					}
					else{
						contribuicao.addElement("-");//a cardinalidade sera especificada no property
						pai=pilha_pais.lastElement();
						value="cardinalityMin="+card_min+";"+"cardinalityMax="+max+";";
					}
				}
			}
			else if(composicao.elementAt(i).substring(0,composicao.elementAt(i).indexOf("=")).equals("FeatureGroup")){//abrindo um grupo
				i++;
				if(composicao.elementAt(i).charAt(composicao.elementAt(i).indexOf("=")+1)==' '){//o grupo nao tem cardinalidades 
					max="-";
					min="-";
				}
				else{
					max=composicao.elementAt(i).substring(composicao.elementAt(i).indexOf("=")+1, composicao.elementAt(i).indexOf(" "));
					min=composicao.elementAt(i).substring(composicao.elementAt(i).indexOf(" ")+1, composicao.elementAt(i).length()-1);
				}
				pilha_cardinalidade_grupo.addElement(max+" "+min);//empilha a cardinalidade do grupo
				group=group_property(i+1);
			}
			else if(composicao.elementAt(i).equalsIgnoreCase("Annotation=Annotation")){
				String key="task "+composicao.elementAt(i-1).substring(composicao.elementAt(i-1).indexOf("=")+1, composicao.elementAt(i-1).length());
				correlacao.put(key,composicao.elementAt(i+1).substring(composicao.elementAt(i+1).indexOf("=")+1, composicao.elementAt(i+1).length()-1));
				i+=2;
			}
		}
		/*System.out.println("filhos: "+filhos);
		System.out.println("pais: "+pais);
		System.out.println("contribuicao: "+contribuicao);
		System.out.println("correlacao: "+correlacao);
		System.out.println("property: "+property);*/
		TransformFeatureModel_AOV pl_aovgraph = new TransformFeatureModel_AOV(filhos, pais, contribuicao, correlacao, property);
		pl_aovgraph.gerarArquivo(nome_saida,file_xfm.getParent());
	}
	
	public String group_property(int index){
		String group="";
		int count=0;
		while(true){
			if(composicao.elementAt(index).equalsIgnoreCase("/}"))
				return group;
			if(!composicao.elementAt(index).equalsIgnoreCase("}")){
				if(composicao.elementAt(index).substring(0,composicao.elementAt(index).indexOf("=")).equals("FeatureGroup")){
					count++;
					index++;
					while(count!=0){
						if(composicao.elementAt(index).equalsIgnoreCase("/}"))
							count--;
						else if(!composicao.elementAt(index).equalsIgnoreCase("}")){
							if(composicao.elementAt(index).substring(0,composicao.elementAt(index).indexOf("=")).equals("FeatureGroup"))
								count++;
						}
						index++;
					}
				}
			}
			if(!composicao.elementAt(index).equalsIgnoreCase("}")){
				if(composicao.elementAt(index).substring(0,composicao.elementAt(index).indexOf("=")).equals("GroupedReference"))
					group+="task_ref "+composicao.elementAt(index).substring(composicao.elementAt(index).indexOf("=")+1, composicao.elementAt(index).length())+",";
				else if(composicao.elementAt(index).substring(0,composicao.elementAt(index).indexOf("=")).equals("GroupedFeature"))
					group+="task "+composicao.elementAt(index).substring(composicao.elementAt(index).indexOf("=")+1, composicao.elementAt(index).length())+",";
			}
			index++;
		}
	}
}
