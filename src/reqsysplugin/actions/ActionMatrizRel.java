package reqsysplugin.actions;

import rastreability.rastreabilityMatrix;

import java.awt.Component;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import pattern.instanciaAovgraph;
import pattern.instanciaArquivo;
import pattern.instanciaRastreability;
import rastreability.Matriz;
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
public class ActionMatrizRel implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private aovgraph aov;
	private instanciaAovgraph parser = new instanciaAovgraph();
	private boolean matrizfinalizada = false;
	@SuppressWarnings("unused")
	private aovgraphTokenManager tm;
	@SuppressWarnings("unused")
	private SimpleNode sn; 
	@SuppressWarnings("unused")
	private IAction action;
	private Visao visa;
	@SuppressWarnings("unused")
	private String arquivo = "visao";
	private String matrizRel = new String();
	private File dir;
	private instanciaArquivo file = new instanciaArquivo();
	static Matriz matriz = new Matriz();
	//static java.util.Vector<String> componentes = new java.util.Vector<String>();
	//static java.util.Vector<String> intertypedeclarationvct = new java.util.Vector<String>();
	//static java.util.Vector<String> advicevct = new java.util.Vector<String>();
	//static java.util.Vector<String> composicaovct = new java.util.Vector<String>();
	//static java.util.Vector<String> filhosvct = new java.util.Vector<String>();
	//static java.util.Hashtable<String, String> paishash = new java.util.Hashtable<String, String>();
	/**
	 * The constructor.
	 */
	public ActionMatrizRel() {
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
	rastreabilityMatrix rastreMatrix = null;
	@SuppressWarnings("unused")
	instanciaRastreability parserRastreab = new instanciaRastreability("contribuicao");
	//System.out.println("RUN ActionMatrizRel" );	
		/*MessageDialog.openInformation(
			window.getShell(),
			"ReqSys Plugin",
			"Atividade VISUALIZAÇÃO de MATRIZ DE RASTREABILIDADE selecionada");*/
		Object[] options = {"Sim",
                "Usar arquivo anterior",
                "Cancelar"};
Object frame = null;
int n = JOptionPane.showOptionDialog(
(Component) frame,
"Deseja escolher um arquivo de entrada?",
"VISUALIZAÇÃO - MATRIZ RELACIONAMENTO CONTRIBUIÇÃO",
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
		
	
	aov = parser.getInstance(dir.getPath());	
		visa = new Visao();
		//parser = visa.geraVerificacao();
		visa.geraMatrizRel();
		
		try {
			switch (aovgraph.aspect_oriented_model()){
			case 0:
			 
			if(!matrizfinalizada){
		     Hashtable<String,String> pais = aov.retornaPais();
		     Hashtable<String,String> contribui = aov.retornaContribui();
		     //System.out.println(contribui);
			 
		     matrizRel = visa.organizaMatrizRel(pais, contribui);
		     //System.out.println("matrizRel: " + matrizRel);
		     //System.out.println("pais: " + pais);
			}
		     System.out.println("OK! Arquivo de Entrada com Especificação em AOV-Graph V\u00e1lido!");
			 
		     //rastreMatrix = null;
		    
		     if(!matrizfinalizada){
		     rastreMatrix = instanciaRastreability.getInstance();
		     matriz.criaMatrizArquivo("pais, filhos");
		     
		     matrizfinalizada = visa.atualizaMatrizRel(matrizRel);
		     

                    try {
                      switch (rastreMatrix.rastreability_matrix("contribuicao")) {
                      case 0:
                        System.out.println("OK. Matriz de Rastreabilidade com Contribuições Gerada com Sucesso!");
                        
                        break;
                      case 1:
                        System.out.println("Goodbye.");
                        break;
                      default:
                        break;
                      }
                    } catch (Exception e) {
                      System.out.println("NOK.Erro ao Gerar Matriz de Rastreabilidade com Contribuições!");
                      System.out.println(e.getMessage());

                    } catch (Error e) {
                      System.out.println("Oops.");
                      System.out.println(e.getMessage());

                    }
		     }
		     //System.out.println("OK. Matriz de Rastreabilidade com Contribuições Gerada com Sucesso!");
             //rastreMatrix = null;
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