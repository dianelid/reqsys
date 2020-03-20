package reqsysplugin.actions;

import rastreability.rastreabilityMatrix;
import rastreability.rastreabilityMatrixTokenManager;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import mer.ERModel;
import mer.ComponenteER;
//import mer.JavaCharStream;
import mer.erm;
import mer.ermTokenManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import pattern.instanciaAovgraph;
import pattern.instanciaArquivo;
import pattern.instanciaRastreability;
import rastreability.Matriz;
import rastreability.JavaCharStream;
import analise.ParseException;
import analise.SimpleNode;
import aov.Visao;
import aov.aovgraph;
import aov.aovgraphTokenManager;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ActionMatrizMer implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private aovgraph aov;
	private boolean matrizfinalizada = false;
	@SuppressWarnings("unused")
	private rastreabilityMatrix rastreMatrix;
	@SuppressWarnings("unused")
	private aovgraphTokenManager tm;
	@SuppressWarnings("unused")
	private SimpleNode sn; 
	@SuppressWarnings("unused")
	private IAction action;
	@SuppressWarnings("unused")
	private Visao visa = new Visao();
	private ERModel model;
	private instanciaAovgraph parser = new instanciaAovgraph();
	private File dir;
	private instanciaArquivo file = new instanciaArquivo();
	static Matriz matriz = new Matriz();
	private String matrizmer = new String();
	//private String arquivo = "visao";
	
	//static MatrizTrans matriz = new MatrizTrans();
	static java.util.Vector<String> componentes = new java.util.Vector<String>();
	static java.util.Vector<String> intertypedeclarationvct = new java.util.Vector<String>();
	static java.util.Vector<String> advicevct = new java.util.Vector<String>();
	static java.util.Vector<String> composicaovct = new java.util.Vector<String>();
	static java.util.Vector<String> filhosvct = new java.util.Vector<String>();
	public java.util.Vector<String> componentestpcvct = new java.util.Vector<String>();
	static java.util.Vector<String> NFolha = new java.util.Vector<String>();
	static java.util.Vector<String> Folha = new java.util.Vector<String>();
	static java.util.Vector<String> associados = new java.util.Vector<String>();
	static java.util.Vector<String> declaraEntidades = new java.util.Vector<String>();
	static java.util.Vector<ComponenteER> ComponenteAOV = new java.util.Vector<ComponenteER>();
	static java.util.Vector<ComponenteER> atributo = new java.util.Vector<ComponenteER>();
	static java.util.Vector<String> atributos = new java.util.Vector<String>();
	static java.util.Vector<String> colunas = new java.util.Vector<String>();
	static java.util.Vector<String> Entidadevct = new java.util.Vector<String>();
	public java.util.Hashtable<String, String> paishash = new java.util.Hashtable<String, String>();
	public java.util.Hashtable<String, String> paisderefhash = new java.util.Hashtable<String, String>();
	static boolean folha;
	public Vector<String> controledeentidades = new Vector<String>();
	/**
	 * The constructor.
	 */
	public ActionMatrizMer() {
	//System.out.println("CONSTRUTOR ActionMatrizRel" );
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void run(IAction action) {
		//System.out.println("RUN ActionMatrizTrans" );	
		/*MessageDialog.openInformation(
			window.getShell(),
			"ReqSys Plugin",
			"Atividade de VISUALIZAÇÃO de MER selecionada");*/

		
		instanciaRastreability parserRastreab = new instanciaRastreability("aovmer");
		/*File arquivo = null;
		
        final JFileChooser fc = new JFileChooser();
        int res = fc.showOpenDialog(null);
        
        if(res == JFileChooser.APPROVE_OPTION){
          arquivo = fc.getSelectedFile();  
          //System.out.println("Voce escolheu o arquivo: " + arquivo.getName() + " no diretório  " + arquivo.getPath());
        }
        else{
          //System.out.println("Voce nao selecionou nenhum arquivo.");
        }*/
		Object[] options = {"Sim",
                "Usar arquivo anterior",
                "Cancelar"};
Object frame = null;
int n = JOptionPane.showOptionDialog(
(Component) frame,
"Deseja escolher um arquivo de entrada?",
"VISUALIZAÇÃO - MATRIZ AOV-GRAPH x MER",
JOptionPane.YES_OPTION,
JOptionPane.NO_OPTION,
null,
options,
options[0]);

if(n == JOptionPane.YES_OPTION){
//System.out.println("Sim");
dir = file.getInstance(true);
}
else if(n == JOptionPane.NO_OPTION){
//System.out.println("Usar arquivo anterior");
dir = file.getInstance();
if(dir==null){
	
		Object[] options2 = {"Sim",
                "Não"};
		
		int n2 = JOptionPane.showOptionDialog(
				(Component) frame,
			    "Arquivo NULL. Deseja escolher um arquivo de entrada?",
			    "NULL",
			    JOptionPane.YES_OPTION,
			    JOptionPane.WARNING_MESSAGE,
			    null,
			    options2,
			    options[0]);
		
		if(n2==JOptionPane.YES_OPTION){
	dir = file.getInstance(true);}else{}
	
}
}else{
//System.out.println("Cancelar");	
//dir=null;
}
		
		
	//System.out.println("DAYANNE");	
	aov = parser.getInstance(dir.getPath());		
		
		try {
			switch (aov.aspect_oriented_model()){
			case 0:
			 System.out.println("OK! Arquivo de Entrada com Especificação AOV-Graph V\u00e1lido!");
			 //System.out.println("Arquivo de Visualização gerado com sucesso!");
			 //System.out.println("Arquivo de Composição gerado com sucesso!");
			 if(!matrizfinalizada){
			 composicaovct = aov.retornaComposicao();
			 advicevct = aov.retornaAdvice();
			 componentes = aov.retornaComponentes();
			 filhosvct = aov.retornaFilhos();
			 paishash = aov.retornaPais();
			 paisderefhash = aov.retornaPaisdeRef();
			 componentestpcvct = aov.retornaComponentecomTopico();			 

				//System.out.println("COMPON COM TOPICOS" + componentestpcvct);
				//System.out.println(paishash);
             //visa.finalizaMatrizTrans();
			 folha = false;
			 model = new ERModel();
			 NFolha = model.reconheceFolhas(componentestpcvct, paishash, paisderefhash, folha);
             //System.out.println(NFolha);
             
             for(int i = 0 ; i<NFolha.size(); i++){
            	 ComponenteAOV.addElement(new ComponenteER(NFolha.elementAt(i)));
            	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
             }
             
             for(int i = 0 ; i<ComponenteAOV.size(); i++){
            	//System.out.println(entidades.elementAt(i).getComponenteOrigem());
            	 ComponenteAOV.elementAt(i).setAssociados(model.reconheceFilhos(ComponenteAOV.elementAt(i).getComponenteOrigem(), paishash, paisderefhash));
            	//System.out.println(entidades.elementAt(i).getComponenteOrigem());
            	//System.out.println(entidades.elementAt(i).getAssociados());
             }
           
             
             for(int i = 0 ; i<ComponenteAOV.size(); i++){
            	 Entidadevct = ComponenteAOV.elementAt(i).extraiTopico();
            	//System.out.println("ENTIDADES:" + Entidadevct);
            	//System.out.println(ComponenteAOV.elementAt(i).getComponenteOrigem());
            	associados = ComponenteAOV.elementAt(i).getAssociados();
            	//System.out.println("associados: " + associados);
             }
             folha = true;
             Folha = model.reconheceFolhas(componentestpcvct, paishash, paisderefhash, folha);
             //System.out.println("FOLHA: " + Folha);
             
             for(int i = 0 ; i<ComponenteAOV.size(); i++){
            	 Vector<String> auxFolha = new Vector<String>();
            	 String declEntidades = new String();
            	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
            	 //System.out.println(entidades.elementAt(i).getAssociados());
            	 
            	 if(paisderefhash.containsKey(ComponenteAOV.elementAt(i).getComponenteOrigem())){
            	 auxFolha = model.reconheceReferencias(ComponenteAOV.elementAt(i).getComponenteOrigem(), paisderefhash);
            	 //System.out.println("auxFolha:" + auxFolha);
            	 }
            	 if(!auxFolha.isEmpty()){
            	 for(int j = 0 ; j<auxFolha.size(); j++){
            	 Folha.addElement(auxFolha.elementAt(j));
            	 } }
            	 
            	 atributos = ComponenteAOV.elementAt(i).encontraAtributos(ComponenteAOV.elementAt(i).getAssociados(), Folha);
            	 //System.out.println("Folha:" + Folha);
            	 //System.out.println("entidades.elementAt(i).getComponenteOrigem():" + entidades.elementAt(i).getComponenteOrigem());
            	 //System.out.println("entidades.elementAt(i).getAssociados():" + entidades.elementAt(i).getAssociados());
            	 if(!atributos.isEmpty()){
            	 
            	 Vector<String> nomeEnt = new Vector<String>();
            	 Vector<String> nomeAtr = new Vector<String>();
            	 
            	 nomeEnt = ComponenteAOV.elementAt(i).extraiTopico();
            	 //System.out.println("ENTIDADES:" + nomeEnt);
            	 
            	 nomeAtr = ComponenteAOV.elementAt(i).geraAtributosEntidades(atributos);
            	 
            	 //System.out.println("ENTIDADES:" + nomeEnt);
            	   
            	 
            	 for(int j=0; j<nomeEnt.size();j++){
            		 nomeAtr = model.trataEntidades(nomeEnt.elementAt(j), nomeAtr);
            		 //System.out.println("nomeAtr:" + nomeAtr);
            	if(nomeAtr.size()>0){
            		declEntidades = model.declaraEntidades(nomeEnt.elementAt(j),nomeAtr);
            		declaraEntidades.addElement(declEntidades);}
            	 }
            	 //System.out.println("declaraEntidades: " + declaraEntidades);
            	 //for(int j=0; j<nomeEnt.size(); j++){
            	 //model.trataEntidades(nomeEnt.elementAt(j), atributos);
            	 //}
            	 }
             }
             
             Vector<String> atribs = new Vector<String>();
             atribs=model.getAtributos();
             
             
             /*String matrizRel = new String();
             
        	 for(int j=0; j<declaraEntidades.size(); j++){
        	 matrizRel += declaraEntidades.elementAt(j); //+ System.getProperty("line.Separator");
        	 }*/
        	 
        	 
             for(int j=0; j<ComponenteAOV.size();j++){
            //System.out.println("entidades.elementAt(j).getComponenteOrigem():" + entidades.elementAt(j).getComponenteOrigem()); 
            //System.out.println("entidades.elementAt(j).getComponenteOrigem():" + entidades.elementAt(j).extraiTopico()); 
            //System.out.println("entidades.elementAt(j).getAtributos():" + entidades.elementAt(j).getAtributos()); 
             }
             
             //System.out.println("Folha:" + Folha);
             
             for(int i = 0 ; i<Folha.size(); i++){
            	 atributo.addElement(new ComponenteER(Folha.elementAt(i)));
            	 //atributo.elementAt(i).setPai(paishash, Folha.elementAt(i));
            	 //System.out.println(Folha.elementAt(i) + "-" + atributo.elementAt(i).getAssociados());
            	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
             }

             Vector<String> controledetopicos = new Vector<String>();
             String paiatual = new String();
             String paianterior = new String();
             for(int j=0; j<atributo.size();j++){
                 //System.out.println("atributo.elementAt(j).getComponenteOrigem():" + atributo.elementAt(j).getComponenteOrigem()); 
                 //System.out.println("atributo.elementAt(j).extraiTopico():" + atributo.elementAt(j).extraiTopico()); 

                 
               
                 folha = true;
                 Folha = model.reconheceFolhas(componentestpcvct, paishash, paisderefhash, folha);
                 //System.out.println("FOLHA: " + Folha);
                 Vector<String> topicoentidadesfilhasFinal = new Vector<String>();

                 //for(int i = 0 ; i<NFolha.size(); i++){
                	 //ComponenteAOV.addElement(new ComponenteER(NFolha.elementAt(i)));
                	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
                 //}
            	 
                 
                 for(int i=0; i<atributo.elementAt(j).extraiTopico().size(); i++){
                	 //System.out.println("atributo.elementAt(j): " + atributo.elementAt(j).getComponenteOrigem());
                	 
                	 if(!colunas.contains(atributo.elementAt(j).extraiTopico().elementAt(i))){
                		 colunas.addElement(atributo.elementAt(j).extraiTopico().elementAt(i));
                	 }
                	 

                	 atributo.elementAt(j).setPai(paishash, atributo.elementAt(j).getComponenteOrigem());	 
                	 paiatual = atributo.elementAt(j).getPai();
                	 
                	 Vector<String> topico = atributo.elementAt(j).extraiTopico(atributo.elementAt(j).getPai());
                	 Vector<String> entidadesfilhas = new Vector<String>();
                	 Vector<String> folhasfilhas = new Vector<String>();
                	 Vector<String> topicoentidadesfilhas = new Vector<String>();
                	 
                	 String topicoent = new String();
                	 Vector<String> topicofolhasfilhas = new Vector<String>();
                	 
                	 
                	 boolean flag = true;
                	 //System.out.println("topico: " + topico);
                	 entidadesfilhas = geraFilhos(atributo.elementAt(j).getPai(), paishash, NFolha, Folha, flag);
                	 //System.out.println("entidadesfilhas: " + entidadesfilhas);
                	 if(entidadesfilhas.size()>0){
                	 for(int h=0;h<entidadesfilhas.size();h++){
                	 topicoentidadesfilhas = (atributo.elementAt(j).extraiTopico(entidadesfilhas.elementAt(h)));
                	 
                	 for(int k=0;k<topicoentidadesfilhas.size();k++){
                    	 topicoentidadesfilhasFinal.addElement(topicoentidadesfilhas.elementAt(k));
                    	 }
                	 
                	 }

            		 }
                	 //System.out.println("topicoentidadesfilhasFinal: " + topicoentidadesfilhasFinal);
                	 flag = false;
                	 
                	 //System.out.println("topico: " + topico);
                	 folhasfilhas = geraFilhos(atributo.elementAt(j).getPai(), paishash, NFolha, Folha, flag);
            		 
                	 if(folhasfilhas.size()>0){
                	 for(int h=0;h<folhasfilhas.size();h++){
                	 topicofolhasfilhas = (atributo.elementAt(j).extraiTopico(folhasfilhas.elementAt(h)));
            		 }
            		 }
                	 
                	 //System.out.println("entidadesfilhas: " + entidadesfilhas);
                	 if(!topico.contains(atributo.elementAt(j).extraiTopico().elementAt(i))
                		&& !topicoentidadesfilhasFinal.contains(atributo.elementAt(j).extraiTopico().elementAt(i))
                		//&& !topicofolhasfilhas.contains(atributo.elementAt(j).extraiTopico().elementAt(i))
                		&& !controledetopicos.contains(atributo.elementAt(j).extraiTopico().elementAt(i))
                		
                		){
                		//System.out.println("atributo.elementAt(j).extraiTopico().elementAt(i): " + atributo.elementAt(j).extraiTopico().elementAt(i));
                		 
                		 matrizmer = matrizmer + visa.organizaMatrizMer(atributo.elementAt(j).getComponenteOrigem(), atributo.elementAt(j).extraiTopico().elementAt(i), "atributo");
                	     paianterior = paiatual;
                    	 if(!controledetopicos.contains(atributo.elementAt(j).extraiTopico().elementAt(i))){
                        	 controledetopicos.addElement(atributo.elementAt(j).extraiTopico().elementAt(i));
                        }
                	 }else{
                		 //System.out.println("paiatual: " + paiatual);
                		 //System.out.println("paianterior: " + paianterior);
                		 //if(paiatual.equals(paianterior)){
                		 //System.out.println("{} atributo.elementAt(j).extraiTopico().elementAt(i): " + atributo.elementAt(j).extraiTopico().elementAt(i));
                		 
                		 //entidadesfilhas = geraFilhos(atributo.elementAt(j).getPai(), paishash, NFolha);
                		 //System.out.println("entidadesfilhas: " + entidadesfilhas);
                		 matrizmer = matrizmer + visa.organizaMatrizMer(atributo.elementAt(j).getComponenteOrigem(), atributo.elementAt(j).extraiTopico().elementAt(i), "{atributo}");	 
                		 //}else{
                		//	 matrizmer = matrizmer + visa.organizaMatrizMer(atributo.elementAt(j).getComponenteOrigem(), atributo.elementAt(j).extraiTopico().elementAt(i), "atributo");	 
                		// }
                	}
                	
                  }
                 
                  }
             
             for(int j=0; j<ComponenteAOV.size();j++){
            	 
            	 ComponenteAOV.elementAt(j).setPai(paishash, ComponenteAOV.elementAt(j).getComponenteOrigem());
            	 
            	 Vector<String> topico = ComponenteAOV.elementAt(j).extraiTopico(ComponenteAOV.elementAt(j).getPai());
                 
            	 //System.out.println("entidades.elementAt(j).getComponenteOrigem():" + entidades.elementAt(j).getComponenteOrigem()); 
                 //System.out.println("entidades.elementAt(j).getComponenteOrigem():" + entidades.elementAt(j).extraiTopico()); 
                 
                 for(int i=0; i<ComponenteAOV.elementAt(j).extraiTopico().size(); i++){
                	 if(!colunas.contains(ComponenteAOV.elementAt(j).extraiTopico().elementAt(i))){
                		 colunas.addElement(ComponenteAOV.elementAt(j).extraiTopico().elementAt(i));
                	 }
                	// String topico = ComponenteAOV.elementAt(j).extraiTopico().elementAt(i);
                	 
                	 if(!topico.contains(ComponenteAOV.elementAt(j).extraiTopico().elementAt(i))
                	 && !controledeentidades.contains(ComponenteAOV.elementAt(j).extraiTopico().elementAt(i))
                	 ){
                		 
                	 matrizmer  = matrizmer + visa.organizaMatrizMer(ComponenteAOV.elementAt(j).getComponenteOrigem(), ComponenteAOV.elementAt(j).extraiTopico().elementAt(i), "entidade");
                	 
                	 if(!controledeentidades.contains(ComponenteAOV.elementAt(j).extraiTopico().elementAt(i))){
                		 controledeentidades.addElement(ComponenteAOV.elementAt(j).extraiTopico().elementAt(i));
                      }
                	 
                	 }else{
                	 matrizmer  = matrizmer + visa.organizaMatrizMer(ComponenteAOV.elementAt(j).getComponenteOrigem(), ComponenteAOV.elementAt(j).extraiTopico().elementAt(i), "{entidade}");	 
                	 }
                 
                 }
                 
                 //System.out.println("colunas: " + colunas); 
                  }
             //System.out.println("colunas: " + colunas); 
        	 
             //System.out.println("matrizRel: " + matrizRel);
			 }
			 
			 if(!matrizfinalizada){
        	 visa.geraMatrizMer();
        	 matrizfinalizada = visa.atualizaMatrizMer(matrizmer);
			
			 
             rastreMatrix = parserRastreab.getInstance();
             
             matriz.criaMatrizArquivo("AOV-Graph, ModelER");
			 
         /*                try{
            parser = new rastreabilityMatrix(new rastreabilityMatrixTokenManager
                                                         (new JavaCharStream
                                                         (new FileReader
                                                         (new File("C://matriztransversalidade.txt")))));

                 }catch (FileNotFoundException e) {
                         System.out.println("File not found. Exiting. Excessao: "+ e);
                 } */
			 
               try {
              switch (rastreMatrix.rastreability_matrix("aovmer")) {
                 case 0:
                   System.out.println("OK! Matriz de Rastreabilidade AOV-Graph x MER Gerada com Sucesso!");
                   break;
                 case 1:
                   System.out.println("Goodbye.");
                   break;
                 default:
                   break;
                 }
               } catch (Exception e) {
                 System.out.println("NOK! Erro ao Gerar Matriz de Rastreabilidade AOV-Graph x MER ");
                 System.out.println(e.getMessage());

               } catch (Error e) {
                 System.out.println("Oops.");
                 System.out.println(e.getMessage());

               }
			 
			 }else{
				 System.out.println("OK! Matriz de Rastreabilidade AOV-Graph x MER Gerada com Sucesso!");	 
			 }
			 break;

case 1:
  System.out.println("Oops! Arquivo de entrada em branco...");
break;

default:
  System.out.println("Oops! You did it again...");
break;
			             }
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}

	public Vector<String> geraFilhos(String pai, Hashtable<String, String> pais, Vector<String> NFolha, Vector<String> Folha, boolean folha) {
		//System.out.println("ActionMatrizRel init");
		Vector<String> filhos = new Vector<String>();
		
		
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
	    
	    for(int i=0;i<paisvct.size();i++){
	    	if(paisvct.elementAt(i).equals(pai)){
	    		if(folha){
	    		if(NFolha.contains(filhosvct.elementAt(i))){
	    		filhos.addElement(filhosvct.elementAt(i));
	    		//System.out.println("filho: " + componente);
	    		//System.out.println("pai: " + pai);
	    		}
	    		}else{
		    	if(Folha.contains(filhosvct.elementAt(i))){
			    		filhos.addElement(filhosvct.elementAt(i));
			    		//System.out.println("filho: " + componente);
			    		//System.out.println("pai: " + pai);
			    }	
	    		}
	    	}
	    }
		
		
		return filhos;
	}
	
	
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		//System.out.println("ActionMatrizRel selectionChanged");
		this.action = action; 
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
		//System.out.println("ActionMatrizRel dispose");
		//System.out.println("dispose");
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		//System.out.println("ActionMatrizRel init");

		this.window = window;
	}
}