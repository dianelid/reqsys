package reqsysplugin.actions;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import javax.swing.JOptionPane;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import FeatureModel.PL_AOVgraph;
import pattern.instanciaArquivo;

public class ActionPL_AOVgraph implements IWorkbenchWindowActionDelegate{
	private IWorkbenchWindow window;
	private IAction action;
	Vector<String> composicao=new Vector<String>();
	private File file_xfm;
	String nome_saida;
	String arquivo;
	
	public void run(IAction action) {
		composicao.clear();
		arquivo=null;
		file_xfm=selectFile("Deseja escolher um arquivo de entrada?");
		EntradadeDados(file_xfm);
		nome_saida=JOptionPane.showInputDialog(null, "Digite o nome do arquivo de saida");
		GerarComposicao();//System.out.println(composicao);
		//long time = System.currentTimeMillis();
		//System.out.println("Tempo de excecução ate GerarComposicao: " + (System.currentTimeMillis() - time ) + "ms." );
		PL_AOVgraph aov=new PL_AOVgraph(composicao,file_xfm,nome_saida);
		aov.gerarVetores();
		System.out.println("Especificacao gerada com sucesso!");
	}
	
	public void GerarComposicao(){
		StringBuffer acumular=new StringBuffer("");
		arquivo=arquivo.replace("\n","");
		String tags[]=arquivo.split(">");
		for(int i=2;i<tags.length-1;i++){
			int j=0;
			while(tags[i].charAt(j)!='<')
				j++;
			j+=4;
			if(tags[i].charAt(j)!=':'){
				int k=j;
				while(tags[i].charAt(k)!=' '){
					acumular.append(tags[i].charAt(k));
					k++;
				}
				acumular.append("=");
				if(!tags[i].substring(j,k).equals("Cardinality"))
					acumular.append(tags[i].substring(tags[i].indexOf("\"")+1,tags[i].length()-1));
				else{
					j=tags[i].indexOf("\"");
					while(tags[i].charAt(j)!='/'){
						if(tags[i].charAt(j)=='\"'){
							j++;
							while(tags[i].charAt(j)!='\"'){
								acumular.append(tags[i].charAt(j));
								j++;
							}
							acumular.append(" ");
						}
						j++;
					}
				}
				composicao.addElement(acumular.toString());
				acumular.replace(0,acumular.length(),"");
				//acumular="";
			}
			else{//fechando uma tag
				if(tags[i].charAt(j+1)=='F')
					composicao.addElement("/}");//fechando um grupo
				else
					composicao.addElement("}");//fechando uma feature
			}
		}
	}
	
	private File selectFile(String msg){
		instanciaArquivo instance_file = new instanciaArquivo();
		File file;
		Object[] options = {"Sim", "Usar arquivo anterior", "Cancelar"};
		Object frame = null;
		int n = JOptionPane.showOptionDialog((Component) frame, msg, "PL-AOVgraph",
			JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options, options[0]);

		if(n == JOptionPane.YES_OPTION)//usar arquivo de entrada
			file = instance_file.getInstance(true);
		else{
			if(n == JOptionPane.NO_OPTION){//usar arquivo anterior
				file = instance_file.getInstance();
				if(file==null){
					Object[] options2 = {"Sim", "Não"};
					int n2 = JOptionPane.showOptionDialog((Component) frame, "Arquivo NULL. "+msg, "NULL",
					    JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, options2, options[0]);
					if(n2==JOptionPane.YES_OPTION)
						file = instance_file.getInstance(true);
				}
			}else
				file=null;//cancelar
		}//System.out.println("caminho: "+file.getPath()+"\n");
		return file;
	}
	
	private void EntradadeDados(File file){
		FileInputStream fis;
		try{
			fis=new FileInputStream(file);
			int i=fis.read();
			while(i!=-1){
			   if(arquivo==null)
				   arquivo=(char)i+"";
			   else
				   arquivo+=(char)i;
			   i=fis.read();	
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.action = action;
	}
}
