package aov;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Pointcut {
	private String Nome = new String();
	private String identificador = new String();
	private Vector<Componente> Afetados= new Vector<Componente>();
	private Vector<String> Operadores= new Vector<String>();
	private String advicearound = new String("vazia");
	private String adviceafter = new String("vazia");
	private String advicebefore = new String("vazia");
	private Vector<String> intertypedeclaration = new Vector<String>();
	String aux = new String();
	
	public Pointcut(String nome, String id){
		setNomeId(nome,id);	
	}

	public Vector<Componente> verificaOr(Hashtable<String, String> parentes, Vector<Componente> afetadosvct, Vector<String> operadoresvct){
	Vector<Componente> retorna = new Vector<Componente>();
	Vector<String> paisvct = new Vector<String>();
	Vector<String> filhosvct = new Vector<String>();
	@SuppressWarnings("unused")
	int contador;
	
	String operando1 = new String();
	String operando2 = new String();
	String pai1 = new String();
	String pai2 = new String();
	//System.out.println("parentes: "+ parentes);
	
	//for(int i =0; i<afetadosvct.size(); i++){
	//System.out.println("AFETADO."+i+ ".: "+ afetadosvct.elementAt(i).getNome());
	//}
	
	Enumeration<String> enumeraPais = parentes.elements();
	Enumeration<String> enumeraFilhos = parentes.keys();
	while(enumeraPais.hasMoreElements()){
	    paisvct.addElement((String)enumeraPais.nextElement());
	  }
	
	while(enumeraFilhos.hasMoreElements()){
	    filhosvct.addElement((String)enumeraFilhos.nextElement());
	  }
	
	for(int i =0; i<operadoresvct.size(); i++){
	if(operadoresvct.elementAt(i).equals("or")){
		operando1 = afetadosvct.elementAt(i).getNome();
		operando2 = afetadosvct.elementAt(i+1).getNome();
		//System.out.println("operando1: " + operando1);
		//System.out.println("operando2: " + operando2);
		for(int j =0; j<filhosvct.size(); j++){
		if(filhosvct.elementAt(j).equals(operando1)){	
		pai1 = paisvct.elementAt(j);	
		}
		if(filhosvct.elementAt(j).equals(operando2)){	
			pai2 = paisvct.elementAt(j);	
		}
		}
		//System.out.println("pai1: " + pai1);
		//System.out.println("pai2: " + pai2);
		
		if(pai1.equals(pai2)){
			if(!retorna.contains(operando1)){
			retorna.addElement(afetadosvct.elementAt(i+1));}	
		}
		if(!pai1.equals(pai2)){
			
			if(!retorna.contains(afetadosvct.elementAt(i))){
			retorna.addElement(afetadosvct.elementAt(i));
			//System.out.println("operando1: " + operando1);
			//System.out.println("retorna: " + afetadosvct.elementAt(i).getNome());
			}
			
			if(!retorna.contains(afetadosvct.elementAt(i+1))){
			retorna.addElement(afetadosvct.elementAt(i+1));
			//System.out.println("operando2: " + operando2);
			//System.out.println("retorna: " + afetadosvct.elementAt(i+1).getNome());
			}
		}
	}else{
		if(!retorna.contains(afetadosvct.elementAt(i))){
		retorna.addElement(afetadosvct.elementAt(i));
		//System.out.println("else: " + afetadosvct.elementAt(i).getNome());
		}
	}
	//System.out.println("retorna: " + retorna);
	}
//	if(retorna.size()==0){
//		retorna.addElement(operando1);	
	//}
	//System.out.println("retorna: " + retorna);
	return retorna;
	}
	
	public void setNomeId(String nome, String id){
		Nome = nome;
		identificador = id;	
		}

	public void setOperadores(String op){
	 Operadores.addElement(op);	
		}

	public void setIDafetado(String afetado, String primitiva){
		//System.out.println("setAfetado - afetado : " +  afetado);
		
		Afetados.addElement(new Componente(afetado));
		
		for(int i=0; i < Afetados.size(); i++){
			if(Afetados.elementAt(i).getIdentificador().equals(afetado)){
				Afetados.elementAt(i).setPrimitivaOperador(primitiva);
			}
			
		}
			
	}
	
	public void setAfetadonome(String id, String descr){
		
		System.out.println("setAfetadonome");
		
		if(Afetados.size()>0){
		for(int i=0; Afetados.size()<0; i++){
		if(Afetados.elementAt(i).getIdentificador().equals(id)){
			Afetados.elementAt(i).setNome(descr);	
		}	
		}	
		}
		
		
		
	}

	public void setAdvicearound(String around){
		advicearound = around;
	}

	public void setAdviceafter(String after){
		adviceafter= after;
	}

	public void setAdvicebefore(String before){
		advicebefore = before;
	}

	public void setIntertype(String intertype){
		
		//aux = aux + intertype;
		intertypedeclaration.addElement(intertype);
	}
	
	public String getNome(){
		return Nome;	
		}

	public String getIdentificador(){
		return identificador;	
		}

	public Vector<Componente> getAfetadovector(){
			return Afetados;
		}
	
	public String getAfetadonome(String id){
		String nome = new String();
		
		if(Afetados.size()>0){
		for(int i=0; Afetados.size()<0; i++){
		if(Afetados.elementAt(i).getIdentificador().equals(id)){
			nome = Afetados.elementAt(i).getNome();	
		}	
		}	
		}
		
		return nome;
	}
		
	public Vector<String> getOperadores(){
		return Operadores;
			}


	public String getAdvicearound(){
		return advicearound;
	}

	public String getAdviceafter(){
		return adviceafter;
	}

	public String getAdvicebefore(){
		return advicebefore;
	}
	
	public Vector<String> getIntertype(){
		return intertypedeclaration;
	}
	
	public boolean possuiIntertype(){
	boolean retorno;
	
		if(!(intertypedeclaration.size()==0)){
			retorno = true;
			}else{
		retorno = false;
			}
	return retorno;
	}
	
	public boolean possuiAdvicearound(){
		boolean retorno;
		
			if(!advicearound.equals("vazia")){
				retorno = true;
				}else{
			retorno = false;
				}
		return retorno;
		}
	
	public boolean possuiAdvicebefore(){
		boolean retorno;
		
			if(!advicebefore.equals("vazia")){
				retorno = true;
				}else{
			retorno = false;
				}
		return retorno;
		}
	
	public boolean possuiAdviceafter(){
		boolean retorno;
		
			if(!adviceafter.equals("vazia")){
				retorno = true;
				}else{
			retorno = false;
				}
		return retorno;
		}
	
	
}
