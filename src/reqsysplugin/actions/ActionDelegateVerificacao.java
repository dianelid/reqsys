package reqsysplugin.actions;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import pattern.instanciaArquivo;

public class ActionDelegateVerificacao implements
		IWorkbenchWindowActionDelegate, IObjectActionDelegate, IEditorActionDelegate{
	private IWorkbenchWindow window;
	private IAction action; 
	private IWorkbenchPart targetPart;
	private instanciaArquivo file = new instanciaArquivo();
	private File dir;
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		//System.out.println("init Verificacao");
		this.window = window;	
	}

	public void run(IAction action) {
		//System.out.println("run Verificacao");
		//dir = file.getInstance(true);
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		//System.out.println("selection Verificacao");
		this.action = action; 
		Object object = selection.getClass();		
		//System.out.println(object.toString());
	}
	
	public void setActivePart(IAction action, IWorkbenchPart part) {
		//System.out.println("setactive Verificacao");
		this.targetPart = part;
		//System.out.println("part: " + part.toString());
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart editor) {
		this.targetPart = editor;	
	}
}

