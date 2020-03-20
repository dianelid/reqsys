package reqsysplugin.actions;

import java.awt.Component;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import mer.ERModel;
import mer.ComponenteER;
import mer.erm;
import mer.ermTokenManager;
//import mer.ermTokenManager;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import pattern.instanciaAovgraph;
import pattern.instanciaArquivo;

//import analise.JavaCharStream;
import mer.JavaCharStream;
import analise.ParseException;
import analise.SimpleNode;
import aov.*;
import rastreability.*;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ActionMer implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private aovgraph aov;
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
	//private String arquivo = "visao";
	private File dir;
	private instanciaArquivo file = new instanciaArquivo();
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
	static java.util.Vector<String> declaraRelacionamentos = new java.util.Vector<String>();
	static java.util.Vector<ComponenteER> entidades = new java.util.Vector<ComponenteER>();
	static java.util.Vector<String> atributos = new java.util.Vector<String>();
	static java.util.Vector<String> outrasEntidades = new java.util.Vector<String>();
	static java.util.Vector<String> Entidadevct = new java.util.Vector<String>();
	public java.util.Hashtable<String, String> paishash = new java.util.Hashtable<String, String>();
	public java.util.Hashtable<String, String> paisderefhash = new java.util.Hashtable<String, String>();
	static boolean folha;
	/**
	 * The constructor.
	 */
	public ActionMer() {
	//System.out.println("CONSTRUTOR ActionMer" );
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public void run(IAction action) {
	//System.out.println("RUN ActionMatrizTrans" );	
		/*MessageDialog.openInformation(
			window.getShell(),
			"ReqSys Plugin",
			"Atividade de VISUALIZAÇÃO de MER selecionada");*/
		//visa = new Visao();
		//parser = visa.geraVerificacao();
		//visa.geraMer();
		
		Object[] options = {"Sim",
                "Usar arquivo anterior",
                "Cancelar"};
Object frame = null;
int n = JOptionPane.showOptionDialog(
(Component) frame,
"Deseja escolher um arquivo de entrada?",
"VISUALIZAÇÃO - MER",
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
dir=null;
}
	
		
		
	//System.out.println("DAYANNE");	
	aov = parser.getInstance(dir.getPath());
	
	
	String nomedoarquivo = new String(getDiretorio(dir.getPath()));
	
		
		try {
			switch (aov.aspect_oriented_model()){
			case 0:
			 System.out.println("OK! Arquivo de Entrada com Especificação AOV-Graph V\u00e1lido!");
			 //System.out.println("Arquivo de Visualização gerado com sucesso!");
			 //System.out.println("Arquivo de Composição gerado com sucesso!");
			 
			 composicaovct = aov.retornaComposicao();
			 advicevct = aov.retornaAdvice();
			 componentes = aov.retornaComponentes();
			 filhosvct = aov.retornaFilhos();
			 paishash = aov.retornaPais();
			 paisderefhash = aov.retornaPaisdeRef();
			 componentestpcvct = aov.retornaComponentecomTopico();			 
			 Vector<String> nomeEnt = new Vector<String>();
        	 Vector<String> nomeAtr = new Vector<String>();
        	 Vector<String> nomeoutrasEnt = new Vector<String>();
        	 Vector<String> nomeoutrasEnt2 = new Vector<String>();
        	 Vector<String> nomeAtr2 = new Vector<String>();
				//System.out.println("COMPON COM TOPICOS" + componentestpcvct);
				//System.out.println(paishash);
             //visa.finalizaMatrizTrans();
			 folha = false;
			 model = new ERModel();
			 NFolha = model.reconheceFolhas(componentestpcvct, paishash, paisderefhash, folha);
             //System.out.println(NFolha);
             
             for(int i = 0 ; i<NFolha.size(); i++){
            	 entidades.addElement(new ComponenteER(NFolha.elementAt(i)));
            	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
             }

             for(int i = 0 ; i<entidades.size(); i++){
            	 entidades.elementAt(i).setAssociados(model.reconheceFilhos(entidades.elementAt(i).getComponenteOrigem(), paishash, paisderefhash));
             }
             

           
             
             for(int i = 0 ; i<entidades.size(); i++){
            	 Entidadevct = entidades.elementAt(i).extraiTopico();
            	//System.out.println("ENTIDADES:" + Entidadevct);
            	//System.out.println(entidades.elementAt(i).getComponenteOrigem());
            	
             	associados = entidades.elementAt(i).getAssociados();
             	 
             }
             
             folha = true;
             Folha = model.reconheceFolhas(componentestpcvct, paishash, paisderefhash, folha);
             //System.out.println("FOLHA: " + Folha);
             
           for(int i = 0 ; i<entidades.size(); i++){
            	//System.out.println(entidades.elementAt(i).getComponenteOrigem());
        	     Vector<String> entidadeatual = new Vector<String>();
            	 Vector<String> auxFolha = new Vector<String>();
            	 Vector<String> auxFolha2 = new Vector<String>();
            	 String declEntidades = new String();
            	 String declRelacionamentos = new String();
            	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
            	 //System.out.println("associados: " + entidades.elementAt(i).getAssociados());
            	 
            	 
                 
                	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
                	 outrasEntidades = entidades.elementAt(i).encontraOutrasEntidades(entidades.elementAt(i).getAssociados(), NFolha);
                	 //System.out.println("outrasentidades: " + outrasentidades);
                  
            	 
            	 
            	 if(paisderefhash.containsKey(entidades.elementAt(i).getComponenteOrigem())){
            	 auxFolha = model.reconheceReferencias(entidades.elementAt(i).getComponenteOrigem(), paisderefhash);
            	 //System.out.println("auxFolha:" + auxFolha);
            	 }
            	 
            	 
            	 if(!auxFolha.isEmpty()){
            	 for(int j = 0 ; j<auxFolha.size(); j++){
            	 Folha.addElement(auxFolha.elementAt(j));
            	 } }
            	 
            	 atributos = entidades.elementAt(i).encontraAtributos(entidades.elementAt(i).getAssociados(), Folha);
            	 
            	 
                 for(int j = i+1 ; j<entidades.size(); j++){
                	 //System.out.println(entidades.elementAt(i).getComponenteOrigem());
            		 //System.out.println(entidades.elementAt(j).getComponenteOrigem());
                	 if(entidades.elementAt(i).extraiTopico().equals(entidades.elementAt(j).extraiTopico())){
                    	 if(paisderefhash.containsKey(entidades.elementAt(j).getComponenteOrigem())){
                        	 auxFolha2 = model.reconheceReferencias(entidades.elementAt(j).getComponenteOrigem(), paisderefhash);
                        	 //System.out.println(entidades.elementAt(j).getComponenteOrigem());
                        	 }
                		 
                    	 if(!auxFolha2.isEmpty()){
                        	 for(int l = 0 ; l<auxFolha2.size(); l++){
                        	 Folha.addElement(auxFolha2.elementAt(l)); } }
                    	 
                		 Vector<String> auxiliar = new Vector<String>();
                		 nomeAtr2 = entidades.elementAt(i).geraAtributosEntidades(entidades.elementAt(j).encontraAtributos(entidades.elementAt(j).getAssociados(), Folha));
                		 entidadeatual=entidades.elementAt(j).extraiTopico();
                		 entidades.remove(j);
                		 j--;
                		 //System.out.println(nomeAtr); 
                	 }
                }   
            	 
            	 if(!atributos.isEmpty()){
            	 
            	 nomeEnt = entidades.elementAt(i).extraiTopico();
            	 //System.out.println("entidade: " + nomeEnt);
            	 nomeAtr = entidades.elementAt(i).geraAtributosEntidades(atributos);          	 
            	 if(nomeEnt.equals(entidadeatual)){
        		 for(int k =0; k<nomeAtr2.size(); k++){
        			 nomeAtr.addElement(nomeAtr2.elementAt(k));
        		 }
            	 }
            	 //System.out.println("ENTIDADES:" + nomeEnt);
        		 //System.out.println("atributos: " + nomeAtr);   
            	 
            for(int j=0; j<nomeEnt.size();j++){
            		 nomeAtr = model.trataEntidades(nomeEnt.elementAt(j), nomeAtr);
            	if(nomeAtr.size()>0){
            		declEntidades = model.declaraEntidades(nomeEnt.elementAt(j),nomeAtr);
            		declaraEntidades.addElement(declEntidades);}
            	 }
            	 }
            	 
            	 
            	 if(!outrasEntidades.isEmpty()){
                	 
            		 nomeoutrasEnt = entidades.elementAt(i).geraAtributosEntidades(outrasEntidades);
                	   
                	 
                	 for(int j=0; j<nomeEnt.size();j++){
                		 nomeoutrasEnt  = model.trataEntidades(nomeEnt.elementAt(j), nomeoutrasEnt);
                	if(nomeoutrasEnt .size()>0){
                		declRelacionamentos = model.declaraRelacionamentos(nomeEnt.elementAt(j),nomeoutrasEnt);
                		declaraRelacionamentos.addElement(declRelacionamentos);}
                	 }
                	 }
             }
           
             visa.geraMer(nomedoarquivo);
             
             String matrizRel = new String();
             
        	 for(int j=0; j<declaraEntidades.size(); j++){
        	 matrizRel += declaraEntidades.elementAt(j);}
        	 
        	 for(int j=0; j<declaraRelacionamentos.size(); j++){
            	 matrizRel += declaraRelacionamentos.elementAt(j);}
        	 
			 visa.atualizaMer(matrizRel, nomedoarquivo );
             @SuppressWarnings("unused")
			erm parser = null;
             
                         try{
            parser = new erm(new ermTokenManager(new JavaCharStream
                                                         (new FileReader
                                                         (new File(nomedoarquivo + "ermodel.txt")))));

                 }catch (FileNotFoundException e) {
                         System.out.println("File not found. Exiting. Excessao: "+ e);
                 }

               try {
                 switch (erm.entity_relationship_model(nomedoarquivo)) {
                 case 0:
                   System.out.println("OK! Modelo Entidade-Relacionamento gerado com Sucesso!");
                   break;
                 case 1:
                   System.out.println("Goodbye.");
                   break;
                 default:
                   break;
                 }
               } catch (Exception e) {
                 System.out.println("NOK! Erro ao Gerar Modelo Entidade Relacionamento ");
                 System.out.println(e.getMessage());

               } catch (Error e) {
                 System.out.println("Oops.");
                 System.out.println(e.getMessage());

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
	public String getDiretorio(String getpath) {
		String diretorio = new String();
		Vector<String> aux2 = new Vector<String>();
 	    StringTokenizer aux1 = new java.util.StringTokenizer(getpath, "\\");

       while (aux1.hasMoreTokens())   {
               aux2.addElement(aux1.nextToken());   }
       
       for(int i = 0; i<aux2.size(); i ++){
    	if(i!=aux2.size()-1){   
    	diretorio = diretorio + aux2.elementAt(i) + "\\\\";  
    	}
       }
       
		//System.out.println("diretorio: " + diretorio);
		return diretorio;
	}
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		//System.out.println("ActionMer selectionChanged");
		this.action = action; 
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
		//System.out.println("ActionMer dispose");
		//System.out.println("dispose");
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		//System.out.println("ActionMer init");

		this.window = window;
	}
}