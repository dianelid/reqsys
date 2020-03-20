package reqsysplugin.actions;

import java.awt.Component;
import java.io.File;
import javax.swing.JOptionPane;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import pattern.instanciaAovgraph;
import pattern.instanciaArquivo;
import analise.ParseException;
import analise.SimpleNode;
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
public class ActionVerificacao implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private aovgraph aov;
	@SuppressWarnings("unused")
	private aovgraphTokenManager tm;
	@SuppressWarnings("unused")
	private SimpleNode sn; 
	@SuppressWarnings("unused")
	private boolean verificacao = false;
	@SuppressWarnings("unused")
	private IAction action; 
	private instanciaAovgraph parser = new instanciaAovgraph();
	private instanciaArquivo file = new instanciaArquivo();
	private File dir;
	
	/**
	 * The constructor.
	 */
	public ActionVerificacao() {
		//System.out.println("construtor ActionVerificacao");
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@SuppressWarnings("static-access")
	public void run(IAction action) {
		Object[] options = {"Sim","Usar arquivo anterior","Cancelar"};
		Object frame = null;
		int n = JOptionPane.showOptionDialog(
		(Component) frame,
		"Deseja escolher um arquivo de entrada?",
		"VERIFICAÇÃO",
		JOptionPane.YES_OPTION,
		JOptionPane.NO_OPTION,
		null,
		options,
		options[0]);

		if(n == JOptionPane.YES_OPTION){
			dir = file.getInstance(true);
		}
		else if(n == JOptionPane.NO_OPTION){
			//System.out.println("Usar arquivo anterior");
			dir = file.getInstance();
			if(dir==null){
				Object[] options2 = {"Sim","Não"};
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
					dir = file.getInstance(true);
				}
			}
		}else{
			dir=null;
		}
		
		aov = parser.getInstance(dir.getPath());	
			
	    try{
			switch (aov.aspect_oriented_model()){
			 	case 0:
			 		System.out.println("OK! Arquivo de Entrada com Especificacao em AOV-Graph Valido!");
			 		break;
			 	case 1:
			 		System.out.println("Oops! Arquivo de entrada em branco...");
			 		break;
			 	default:
			 		System.out.println("Oops! You did it again...");
			 	break;
		    }
	    }catch(ParseException e){
	    	System.out.println(e.getMessage());
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
		//System.out.println("selectionChanged Verificacao");
		this.action = action; 
		//System.out.println("getId Verificacao: " + action.getId());
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
		//System.out.println("dispose");
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		//System.out.println("init ActionVerificacao");
		this.window = window;
	}
}