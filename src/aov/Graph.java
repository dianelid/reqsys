package aov;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Graph {

public Vector<Componente> pais(Hashtable<String, String> paishash){
//System.out.println("pais");
Vector<Componente> pais = new Vector<Componente>();

Vector<String> paisvct = new Vector<String>();
Vector<String> filhosvct = new Vector<String>();
Enumeration<String> enumeraPais = paishash.elements();
Enumeration<String> enumeraFilhos = paishash.keys();

while(enumeraPais.hasMoreElements()){
    paisvct.addElement((String)enumeraPais.nextElement());
  }

for(int i=0;i<paisvct.size();i++){
	pais.addElement(new Componente(paisvct.elementAt(i)));
}



//System.out.println(paisvct);
while(enumeraFilhos.hasMoreElements()){
    filhosvct.addElement((String)enumeraFilhos.nextElement());
  }
//System.out.println(filhosvct);

for(int i=0;i<pais.size();i++){
	//if(){
	pais.elementAt(i).setFilhos(filhosvct.elementAt(i));
	//System.out.println("componentes: " + pais.elementAt(i).getIdentificador());
	//}
}

for(int i=0;i<pais.size();i++){
	for(int j=i+1;j<pais.size();j++){
	if(pais.elementAt(i).getIdentificador().equals(pais.elementAt(j).getIdentificador())){
	
	
	for(int k=0;k<pais.elementAt(j).getFilhos().size();k++){
		pais.elementAt(i).setFilhos(pais.elementAt(j).getFilhos().elementAt(k));	
	}
	
	pais.remove(j);
	j--;
	
	}
	}
}
for(int i=0;i<pais.size();i++){
//System.out.println("componentes: " + pais.elementAt(i).getIdentificador());
//System.out.println("seu tipo: " + pais.elementAt(i).getTipo());
//System.out.println("seu filho: " + pais.elementAt(i).getFilhos());
}
return pais;	
	
}

public String declaraPais(Vector<Componente> pais) {
String declarapais = new String();
String box = new String();
for(int i=0; i<pais.size();i++){
	
	if(pais.elementAt(i).getTipo().equals("task")){
		box="hexagon";
	}
	
	if(pais.elementAt(i).getTipo().equals("goal")){
		box="octagon";
	}
	
	if(pais.elementAt(i).getTipo().equals("softgoal")){
		box="egg";
	}
	
	
	declarapais = declarapais 
	+ "node [shape = "
	+ box 
	+ "]{ node [ label = \" "
	+ pais.elementAt(i).getIdentificador()
	+ " \" ]componente"
	+ i + "; }" ;
}



/*for(int i=0; i<pais.size();i++){
	declarapais = declarapais 
	+ "node [shape = box]{ node [ label = \" "
	+ pais.elementAt(i).getIdentificador()
	+ " \" ]entidade"
	+ i + "; }" ;
}*/


return declarapais;	
}

public String declaraContribuicoes(Vector<Componente> pais) {
	String declaracontribuicoes = new String();
	String box = new String();
	for(int i=0; i<pais.size();i++){
		
		for (int j=0; j<pais.elementAt(i).getFilhos().size(); j++){
             System.out.println(pais.elementAt(i).getFilhos().elementAt(j));
			if(pais.elementAt(i).getFilhos().elementAt(j).startsWith("task")){
				box="hexagon";	
				pais.elementAt(i).getFilhos().elementAt(j).replaceFirst("task"," ");
			}
			
			if(pais.elementAt(i).getFilhos().elementAt(j).startsWith("goal")){
				box="octagon";	
				pais.elementAt(i).getFilhos().elementAt(j).replaceFirst("goal"," ");
			}
			
			if(pais.elementAt(i).getFilhos().elementAt(j).startsWith("softgoal")){
				box="egg";	
				pais.elementAt(i).getFilhos().elementAt(j).replaceFirst("softgoal"," ");
			}
					
		
		declaracontribuicoes = declaracontribuicoes 
		+ "node [shape = "
		+ box 
		+ "]{ node [ label = \" "
		+ pais.elementAt(i).getFilhos().elementAt(j)
		+ " \" ]componente"
		+ i + + j + "; }" ;
	}


	}
	
	for(int i=0; i<pais.size();i++){
		
		for (int j=0; j<pais.elementAt(i).getFilhos().size(); j++){


					
		
		declaracontribuicoes = declaracontribuicoes 
		+ "componente" + i + j
		+ "-> [arrowhead=normal];"
		+ "componente" + i 
		+ ";" ;
	}


	}
	/*for(int i=0; i<pais.size();i++){
		declarapais = declarapais 
		+ "node [shape = box]{ node [ label = \" "
		+ pais.elementAt(i).getIdentificador()
		+ " \" ]entidade"
		+ i + "; }" ;
	}*/


	return declaracontribuicoes;	
	}
	
	
}
