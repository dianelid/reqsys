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
public class ActionAovgraph implements IWorkbenchWindowActionDelegate {
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
	private Graph graph = new Graph();
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
	String atualizadot = new String();
	static boolean folha;
	/**
	 * The constructor.
	 */
	public ActionAovgraph() {
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
			"Atividade de VISUALIZAÇÃO de AOV-Graph selecionada");*/
		//visa = new Visao();
		//parser = visa.geraVerificacao();
		//visa.geraMer();
		
		Object[] options = {"Sim", "Usar arquivo anterior", "Cancelar"};
		Object frame = null;
		int n = JOptionPane.showOptionDialog(
			(Component) frame,
			"Deseja escolher um arquivo de entrada?",
			"VISUALIZAÇÃO - AOV-Graph",
			JOptionPane.YES_OPTION,
			JOptionPane.NO_OPTION,
			null,
			options,
			options[0]
		);

		if(n == JOptionPane.YES_OPTION){
			//System.out.println("Sim");
			dir = file.getInstance(true);
		}
		else
			if(n == JOptionPane.NO_OPTION){
				//System.out.println("Usar arquivo anterior");
				dir = file.getInstance();
				if(dir==null){
					Object[] options2 = {"Sim", "Não"};
					int n2 = JOptionPane.showOptionDialog(
						(Component) frame,
					    "Arquivo NULL. Deseja escolher um arquivo de entrada?",
					    "NULL",
					    JOptionPane.YES_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options2,
					    options[0]
					);
					if(n2==JOptionPane.YES_OPTION){
						dir = file.getInstance(true);
					}
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
						 System.out.println("OK! Arquivo de Entrada com Especificacao AOV-Graph Valido!");
						 //System.out.println("Arquivo de Visualização gerado com sucesso!");
						 //System.out.println("Arquivo de Composição gerado com sucesso!");
						 
						 composicaovct = aov.retornaComposicao();
						 advicevct = aov.retornaAdvice();
						 componentes = aov.retornaComponentes();
						 filhosvct = aov.retornaFilhos();
						 paishash = aov.retornaPais();
						 paisderefhash = aov.retornaPaisdeRef();
						 componentestpcvct = aov.retornaComponentecomTopico();			 
					
						 //System.out.println("paishash: " + paishash);
		   
						visa.geraAovgraph(nomedoarquivo);
						atualizadot = graph.declaraPais(graph.pais(paishash));
						atualizadot = atualizadot + graph.declaraContribuicoes(graph.pais(paishash));
						visa.atualizaAovgraph(atualizadot, nomedoarquivo);
						System.out.println("OK! Visualização de AOV-Graph concluida!");
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

        while (aux1.hasMoreTokens()){
               aux2.addElement(aux1.nextToken());
        }
       
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
