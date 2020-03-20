package aov;

import java.util.Vector;

public class Componente {
private String Id = new String();
private String Nome = new String();
private String DescrCompleta = new String();
private String PrimitivaOperador = new String();
private String tipo = new String();
private Vector<String> filhos = new Vector<String>();	



public Componente (String identificador){
	setIdentificador(identificador);	
}	

public void setIdentificador(String identificador){
	Id = identificador;	
	setTipo(Id);
}	

public void setNome(String descr){
	Nome = descr;	
}

public void setTipo(String componente){
	
	if(componente.startsWith("task")){
		tipo = "task";
		Id = componente.replaceFirst("task"," "); }
	
	if(componente.startsWith("goal")){
		tipo = "goal";
		Id = componente.replaceFirst("goal"," ");}
	
	if(componente.startsWith("softgoal")){
		tipo = "softgoal";
		Id = componente.replaceFirst("softgoal"," ");}
	
}

public void setFilhos(String filho){
	
	/*if(filho.startsWith("task")){
		filhos.addElement(filho.replaceFirst("task"," ")); //}
	
	//if(filho.startsWith("goal")){
		filhos.addElement(filho.replaceFirst("goal"," ")); //}
	
	if(filho.startsWith("softgoal")){
		filhos.addElement(filho.replaceFirst("softgoal"," ")); }*/

	filhos.addElement(filho);	
}

public void setCompleto(String completo){
	DescrCompleta = completo;	
}

public void setPrimitivaOperador(String primitiva){
	PrimitivaOperador = primitiva;
}

public Vector<String> getFilhos(){
	return filhos;	
}

public String getTipo(){
	return tipo;	
}

public String getCompleto(){
	return DescrCompleta;	
}

public String getIdentificador() {
	return Id;
}

public String getNome() {
	return Nome;
}

public String getPrimitiva() {
	return PrimitivaOperador ;
}
	
}
