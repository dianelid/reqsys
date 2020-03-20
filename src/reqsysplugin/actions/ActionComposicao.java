package reqsysplugin.actions;


import java.awt.Component;
import java.io.File;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import pattern.instanciaAovgraph;
import pattern.instanciaArquivo;

import analise.ParseException;
import analise.SimpleNode;
import aov.*;



/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */

public class ActionComposicao implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	private instanciaAovgraph parser = new instanciaAovgraph();
	
	@SuppressWarnings("unused")
	private aovgraphTokenManager tm;
	private instanciaArquivo file = new instanciaArquivo();
	private File dir;
	@SuppressWarnings("unused")
	private SimpleNode sn; 
	
	@SuppressWarnings("unused")
	private IAction action;
	static java.util.Vector<String> componentes = new java.util.Vector<String>();
	static java.util.Vector<String> intertypedeclarationvct = new java.util.Vector<String>();
	static java.util.Vector<String> advicevct = new java.util.Vector<String>();
	static java.util.Vector<String> composicaovct = new java.util.Vector<String>();
	static java.util.Vector<String> filhosvct = new java.util.Vector<String>();
	static java.util.Vector<String> operadores = new java.util.Vector<String>();
	java.util.Vector<String> componentecompleto = new java.util.Vector<String>();
	static java.util.Vector<Pointcut> pointcutsvct = new java.util.Vector<Pointcut>();
	static java.util.Vector<Componente> afetadosvct = new java.util.Vector<Componente>();
	static java.util.Hashtable<String, String> paishash = new java.util.Hashtable<String, String>();
	/**
	 * The constructor.
	 */
	public ActionComposicao() {
	//System.out.println("Construtor ActionComposicao");
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public void run(IAction action) {
		//System.out.println("run ActionComposicao");
		Vector<Componente> afetadoOr = new Vector<Componente>();
		String descricao= new String();
		boolean repetido= false;
		String arquivo= new String("composicao");
		//Console console = null;
		
		/*MessageDialog.openInformation(
			window.getShell(),
			"ReqSys Plugin",
			"Atividade de COMPOSIÇÃO selecionada");*/
		
		//console.writer();
		
		//parser = compos.criaParser();
		//aov = parser.getInstance();	
		Object[] options = {"Sim",
                "Usar arquivo anterior",
                "Cancelar"};
Object frame = null;
int n = JOptionPane.showOptionDialog(
(Component) frame,
"Deseja escolher um arquivo de entrada?",
"COMPOSIÇÃO",
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
	aovgraph aov = parser.getInstance(dir.getPath());
	
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
			 intertypedeclarationvct = aov.retornaIntertype();
			 pointcutsvct = aov.retornaPointcuts();
			 componentecompleto = aov.retornaComponenteCompleto();
			//System.out.println("componentecompleto: " + componentecompleto);
			//System.out.println("paishash: " + paishash );
			 
			//System.out.println("composicaovct: " + composicaovct );
			 Composicao compos = new Composicao(dir.getParent());
			 compos.geraArquivoComposicao(composicaovct);
			 if(pointcutsvct.size()>0){
	        	 	//System.out.println("advicevct: " + advicevct );
                 for(int j=0; j<pointcutsvct.size();j++){
                      
 			 	afetadosvct = pointcutsvct.elementAt(j).getAfetadovector();	
 			 	
                         if(afetadosvct.size()>0){
                        	 	 
                        	 for(int k=0; k<afetadosvct.size();k++){
                        		 if(componentes.size()>0){
                        			 for(int l = 0; l<componentes.size(); l++){
                        			 	if(componentes.elementAt(l).contains(afetadosvct.elementAt(k).getIdentificador()+";")){
                        			 		
                        			 		String partes[] = componentes.elementAt(l).split("\\(");
                        			 		descricao = partes[0];
                        			 		
                        			 		afetadosvct.elementAt(k).setNome(descricao);

                        			 	} 
                        			 	
                        			 } 
                        		 }
                        		 
                        		 
                        		 if(componentecompleto.size()>0){
                        			 for(int l = 0; l<componentecompleto.size(); l++){
                        			 	if(componentecompleto.elementAt(l).startsWith(afetadosvct.elementAt(k).getNome())){
                        			 		
                        			 		//System.out.println("COMPLETO: " + afetadosvct.elementAt(k).getCompleto());
                        			 		
                        			 		afetadosvct.elementAt(k).setCompleto(componentecompleto.elementAt(l));
                        			 		//System.out.println("COMPONENTE: " + afetadosvct.elementAt(k).getNome());
                        			 		//System.out.println("COMPLETO: " + afetadosvct.elementAt(k).getCompleto());
                        			 	} 
                        			 	
                        			 } 
                        		 }

    			 		}           			 	
                        	  

                         }
                         

                     	 if(pointcutsvct.elementAt(j).getOperadores().size()>0 &&
     			 				pointcutsvct.elementAt(j).getOperadores().contains("or")){
     			 			 	
     			 			 	afetadoOr = pointcutsvct.elementAt(j).verificaOr(paishash, afetadosvct, pointcutsvct.elementAt(j).getOperadores());
     			 			 	
     			 			 	for(int i = 0; i<afetadoOr.size(); i++){
     			 			 		
     		     			 		if(pointcutsvct.elementAt(j).possuiAdvicearound()){
     		     			 		  
     		     			 			//System.out.println(pointcutsvct.elementAt(j).getIdentificador());
     		     			 			//System.out.println(afetadoOr.elementAt(i).getPrimitiva());
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("substitute")){
     		     			 				//System.out.println("getcompleto: " + afetadoOr.elementAt(i).getCompleto());
     	   		     			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
     		     			 			compos.geraAdvicearound(afetadoOr.elementAt(i).getCompleto(), pointcutsvct.elementAt(j).getAdvicearound(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 			}
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("include")){
     		     			 			compos.geraAdvicearound(afetadoOr.elementAt(i).getNome(), pointcutsvct.elementAt(j).getAdvicearound(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 			}
     		     			 		}
     		     			 		
     		     			 		if(pointcutsvct.elementAt(j).possuiAdvicebefore()){
       		     			 		  
     		     			 			//System.out.println(pointcutsvct.elementAt(j).getIdentificador());
     		     			 			//System.out.println(afetadoOr.elementAt(i).getPrimitiva());
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("substitute")){
     		     			 				//System.out.println("getcompleto: " + afetadoOr.elementAt(i).getCompleto());
     	   		     			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
     		     			 			compos.geraAdvicebefore(afetadoOr.elementAt(i).getCompleto(), pointcutsvct.elementAt(j).getAdvicebefore(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 			}
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("include")){
     		     			 			compos.geraAdvicebefore(afetadoOr.elementAt(i).getNome(), pointcutsvct.elementAt(j).getAdvicebefore(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 			}
     		     			 		}
     		     			 		
     		     			 		if(pointcutsvct.elementAt(j).possuiAdviceafter()){
         		     			 		  
     		     			 			//System.out.println(pointcutsvct.elementAt(j).getIdentificador());
     		     			 			//System.out.println(afetadoOr.elementAt(i).getPrimitiva());
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("substitute")){
     		     			 				//System.out.println("getcompleto: " + afetadoOr.elementAt(i).getCompleto());
     	   		     			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
     		     			 			compos.geraAdviceafter(afetadoOr.elementAt(i).getCompleto(), pointcutsvct.elementAt(j).getAdviceafter(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 			}
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("include")){
     		     			 			compos.geraAdviceafter(afetadoOr.elementAt(i).getCompleto(), pointcutsvct.elementAt(j).getAdviceafter(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 			}
     		     			 		}
     		     			 		
     		     			 		
     		     			 		if(pointcutsvct.elementAt(j).possuiIntertype()){
       		     			 		  
     		     			 			//System.out.println(pointcutsvct.elementAt(j).getIdentificador());
     		     			 			//System.out.println(afetadoOr.elementAt(i).getPrimitiva());
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("substitute")){
     		     			 				//System.out.println("getcompleto: " + afetadoOr.elementAt(i).getCompleto());
     	   		     			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
     		     			 			//System.out.println("1: " + pointcutsvct.elementAt(j).getIdentificador());
     		     			 			Vector<String> intervct = pointcutsvct.elementAt(j).getIntertype();	
     		     			 			String interst = new String();
     		     			 			for(int m=0;m<intervct.size();m++){
     		     			 			repetido = compos.verificaIntertype(intervct.elementAt(m), componentes);
     		     			 			
     		     			 			if(repetido){
     		     			 				interst = compos.adicionaReferencia(intervct.elementAt(m));
     		     			 				compos.geraIntertype(afetadoOr.elementAt(i).getCompleto(), interst, arquivo, afetadoOr.elementAt(i).getPrimitiva());
     		     			 			}else{
     		     			 			   compos.geraIntertype(afetadoOr.elementAt(i).getCompleto(), intervct.elementAt(m), arquivo, afetadoOr.elementAt(i).getPrimitiva());			
     		     			 			}
     		     			 			
     		     			 			}
     		     			 				
     		     			 			
     		     			 			}
     		     			 			
     		     			 			if(afetadoOr.elementAt(i).getPrimitiva().equals("include")){
     		     			 			//System.out.println("2: " +pointcutsvct.elementAt(j).getIdentificador());
     		     			 			Vector<String> intervct = pointcutsvct.elementAt(j).getIntertype();	
     		     			 			String interst = new String();
     		     			 			for(int m=0;m<intervct.size();m++){
     		     			 			repetido = compos.verificaIntertype(intervct.elementAt(m), componentes);
     		     			 			
     		     			 			if(repetido){
     		     			 				interst = compos.adicionaReferencia(intervct.elementAt(m));
     		     			 				compos.geraIntertype(afetadoOr.elementAt(i).getCompleto(), interst, arquivo, afetadoOr.elementAt(i).getPrimitiva());			
     		     			 			}else{
     		     			 			   compos.geraIntertype(afetadoOr.elementAt(i).getCompleto(), intervct.elementAt(m), arquivo, afetadoOr.elementAt(i).getPrimitiva());			
     		     			 			}
     		     			 			
     		     			 			}		
     		     			 				
     		     			 			//compos.geraIntertype(afetadoOr.elementAt(i).getCompleto(), pointcutsvct.elementAt(j).getIntertype(), arquivo, afetadoOr.elementAt(i).getPrimitiva());		
     		     			 				
     		     			 			}
     		     			 		}
     			 			 	
     			 			 	}
     			 			 	
      			 		}
                     	 else { 
                     	if(afetadosvct.size()>0){
                     		
                          	 for(int k=0; k<afetadosvct.size();k++){
                 			 	
              			 		if(pointcutsvct.elementAt(j).possuiIntertype()){
              			 		    //System.out.println(pointcutsvct.elementAt(j).getIdentificador());
              			 			//System.out.println(afetadosvct.elementAt(k).getPrimitiva());
              			 			//System.out.println("getcompleto: " + afetadosvct.elementAt(k).getCompleto());
             			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
              			 			
              			 		if(afetadosvct.elementAt(k).getPrimitiva().equals("substitute")){
              			 			//System.out.println("getcompleto SUBSTITUTE: " + afetadosvct.elementAt(k).getCompleto());
             			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
             			 		    //System.out.println("primitica: " + afetadosvct.elementAt(k).getPrimitiva());
              			 		//System.out.println("3: " + pointcutsvct.elementAt(j).getIdentificador());
              			 			//repetido = compos.verificaIntertype(pointcutsvct.elementAt(j).getIntertype(), componentes);
              			 			
              			 			Vector<String> intervct = pointcutsvct.elementAt(j).getIntertype();	
 		     			 			String interst = new String();
 		     			 			for(int m=0;m<intervct.size();m++){
 		     			 			repetido = compos.verificaIntertype(intervct.elementAt(m), componentes);
 		     			 			
 		     			 			if(repetido){
 		     			 				interst = compos.adicionaReferencia(intervct.elementAt(m));
 		     			 				compos.geraIntertype(afetadosvct.elementAt(k).getNome(), interst, arquivo, afetadosvct.elementAt(k).getPrimitiva());	
 		     			 			}else{
 		     			 				compos.geraIntertype(afetadosvct.elementAt(k).getNome(), intervct.elementAt(m), arquivo, afetadosvct.elementAt(k).getPrimitiva());		
 		     			 			}
 		     			 			}
              			 			
              			 			//compos.geraIntertype(afetadosvct.elementAt(k).getCompleto(), pointcutsvct.elementAt(j).getIntertype(), arquivo, afetadosvct.elementAt(k).getPrimitiva());
              			 		}else{
              			 		//System.out.println("4: " +pointcutsvct.elementAt(j).getIdentificador());
              			 		//repetido = compos.verificaIntertype(pointcutsvct.elementAt(j).getIntertype(), componentes);
              			 		
              			 		
              		 			Vector<String> intervct = pointcutsvct.elementAt(j).getIntertype();	
		     			 		String interst =  new String();	
		     			 			for(int m=0;m<intervct.size();m++){
		     			 			repetido = compos.verificaIntertype(intervct.elementAt(m), componentes);
		     			 			
		     			 			if(repetido){
 		     			 				interst = compos.adicionaReferencia(intervct.elementAt(m));
 		     			 				compos.geraIntertype(afetadosvct.elementAt(k).getNome(), interst, arquivo, afetadosvct.elementAt(k).getPrimitiva());	
		     			 			}else{
 		     			 				compos.geraIntertype(afetadosvct.elementAt(k).getNome(), intervct.elementAt(m), arquivo, afetadosvct.elementAt(k).getPrimitiva());		
 		     			 			}
		     			 			}
              			 		
              			 		//System.out.println("4: " +afetadosvct.elementAt(k).getNome());	
              			 			//compos.geraIntertype(afetadosvct.elementAt(k).getNome(), pointcutsvct.elementAt(j).getIntertype(), arquivo, afetadosvct.elementAt(k).getPrimitiva());	
              			 		}
              			 			}//INTERTYPE
                                	 }//FOR k	
                     		
                     		
                     		
                     		
                       	
                       	 for(int k=0; k<afetadosvct.size();k++){
            			 	
     			 		if(pointcutsvct.elementAt(j).possuiAdvicearound()){
     			 		    //System.out.println(pointcutsvct.elementAt(j).getIdentificador());
     			 			//System.out.println(afetadosvct.elementAt(k).getPrimitiva());
     			 			//System.out.println("getcompleto: " + afetadosvct.elementAt(k).getCompleto());
    			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
     			 			
     			 		if(afetadosvct.elementAt(k).getPrimitiva().equals("substitute")){
     			 			//System.out.println("getcompleto SUBSTITUTE: " + afetadosvct.elementAt(k).getCompleto());
    			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
    			 		    //System.out.println("primitica: " + afetadosvct.elementAt(k).getPrimitiva());
     			 			compos.geraAdvicearound(afetadosvct.elementAt(k).getCompleto(), pointcutsvct.elementAt(j).getAdvicearound(), arquivo, afetadosvct.elementAt(k).getPrimitiva());
     			 		}else{
     			 			compos.geraAdvicearound(afetadosvct.elementAt(k).getNome(), pointcutsvct.elementAt(j).getAdvicearound(), arquivo, afetadosvct.elementAt(k).getPrimitiva());	
     			 		}
     			 			}//AROUND
                       	 }//FOR k
                       	 
                       	 
                       	 for(int k=0; k<afetadosvct.size();k++){
             			 	
          			 		if(pointcutsvct.elementAt(j).possuiAdvicebefore()){
          			 		    //System.out.println(pointcutsvct.elementAt(j).getIdentificador());
          			 			//System.out.println(afetadosvct.elementAt(k).getPrimitiva());
          			 			//System.out.println("getcompleto: " + afetadosvct.elementAt(k).getCompleto());
         			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
          			 			
          			 		if(afetadosvct.elementAt(k).getPrimitiva().equals("substitute")){
          			 			//System.out.println("getcompleto SUBSTITUTE: " + afetadosvct.elementAt(k).getCompleto());
         			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
         			 		    //System.out.println("primitica: " + afetadosvct.elementAt(k).getPrimitiva());
          			 			compos.geraAdvicebefore(afetadosvct.elementAt(k).getCompleto(), pointcutsvct.elementAt(j).getAdvicebefore(), arquivo, afetadosvct.elementAt(k).getPrimitiva());
          			 		}else{
          			 			compos.geraAdvicebefore(afetadosvct.elementAt(k).getNome(), pointcutsvct.elementAt(j).getAdvicebefore(), arquivo, afetadosvct.elementAt(k).getPrimitiva());	
          			 		}
          			 			}//BEFORE
                            	 }//FOR k
                       	 
                       	 
                       	 
                       	 for(int k=0; k<afetadosvct.size();k++){
              			 	
           			 		if(pointcutsvct.elementAt(j).possuiAdviceafter()){
           			 		    //System.out.println(pointcutsvct.elementAt(j).getIdentificador());
           			 			//System.out.println(afetadosvct.elementAt(k).getPrimitiva());
           			 			//System.out.println("getcompleto: " + afetadosvct.elementAt(k).getCompleto());
          			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
           			 			
           			 		if(afetadosvct.elementAt(k).getPrimitiva().equals("substitute")){
           			 			//System.out.println("getcompleto SUBSTITUTE: " + afetadosvct.elementAt(k).getCompleto());
          			 		    //System.out.println("around: " + pointcutsvct.elementAt(j).getAdvicearound());
          			 		    //System.out.println("primitica: " + afetadosvct.elementAt(k).getPrimitiva());
           			 			compos.geraAdviceafter(afetadosvct.elementAt(k).getCompleto(), pointcutsvct.elementAt(j).getAdviceafter(), arquivo, afetadosvct.elementAt(k).getPrimitiva());
           			 		}else{
           			 			compos.geraAdviceafter(afetadosvct.elementAt(k).getCompleto(), pointcutsvct.elementAt(j).getAdviceafter(), arquivo, afetadosvct.elementAt(k).getPrimitiva());	
           			 		}
           			 			}//AFTER
                             	 }//FOR k
                       	 
                       	 
                       	 
                     	}
                     	
                     	 }
                 }
          }
			 
			 
			 //System.out.println("advicevct: " + advicevct);
			 
			 
	       /*  if(advicevct.size()>0){
	        	 	//System.out.println("advicevct: " + advicevct );
                    for(int j=0; j<advicevct.size();j++){
                            compos.geraAdvice(advicevct.elementAt(j), componentes, filhosvct, paishash);
                    }
             }
			 
             if(intertypedeclarationvct.size()>0){
                 for(int i=0; i<intertypedeclarationvct.size();i++){
                         compos.geraIntertype(intertypedeclarationvct.elementAt(i), componentes, paishash);
                 }
          }*/
	        System.out.println("Composição realizada com sucesso!"); 
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
		//System.out.println("selectionChanged ActionComposicao");
		this.action = action; 
		//System.out.println("getId Composicao: " + action.getId());
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
		//System.out.println("init ActionComposicao");
		//System.out.println("getId Composicao: " + action.getId());
		//if(action.getId().equals("aovgraphPlugin.actions.ActionComposicao")){
		//compos = new Composicao();
		//parser = compos.criaParser();
		//}
		this.window = window;
		
		//System.out.println("init ActionComposicao RETORNO");
	}
}