package reqsysplugin.actions;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import pattern.instanciaAovgraph;
import pattern.instanciaArquivo;
import FeatureModel.FeatureModel;
import analise.ParseException;
import aov.aovgraph;

public class ActionFeatureModel implements IWorkbenchWindowActionDelegate{
	private IWorkbenchWindow window;
	private IAction action;
	private File file_aov, file_xfmm, file_xdm;
	String nome_modelo, nome_saida;
	private aovgraph aov;
	private instanciaAovgraph parser= new instanciaAovgraph();;
	static Vector<String> composicaovct= new Vector<String>();
	
	@Override
	public void run(IAction action) {
		composicaovct.clear();
		file_aov=selectFile("Deseja escolher um arquivo de entrada?",0);
		aov = parser.getInstance(file_aov.getPath());
		try {
			switch (aov.aspect_oriented_model()){
				case 0:
					System.out.println("Especificacao AOV-Graph valida!");
					nome_modelo=JOptionPane.showInputDialog(null, "Digite o nome do Modelo de Features");
					file_xfmm = selectFile("Deseja escolher o arquivo de gramatica?",1);
					file_xdm = selectFile("Deseja escolher o arquivo de visualizacao?",2);
					nome_saida=JOptionPane.showInputDialog(null, "Digite o nome do arquivo de saida");
					
					File arquivoDestino = new File(file_aov.getParent()+"\\"+nome_saida+".xdm");
					if(!arquivoDestino.exists()){//se o arquivo xdm nao existe no diretorio de origem com o mesmo nome do arquivo de saida
						File arquivoOrigem = new File(file_xdm.getPath());
						FileChannel in=null;
						try {
							in = new FileInputStream(arquivoOrigem).getChannel();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}  
						FileChannel out=null;
						try {
							out = new FileOutputStream(arquivoDestino).getChannel();
						} catch (FileNotFoundException e) {
							System.out.println(e.getMessage());
						}  
						try {
							in.transferTo(0, in.size(), out);//copia o arquivo xdm para o local onde o arquivo xfm sera gerado, com o mesmo nome do xfm
							in.close();  
							out.close();  
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}  
					}
					
					composicaovct = aov.retornaComposicao();//System.out.println("composicaovct: "+composicaovct);
					FeatureModel fm = new FeatureModel(composicaovct,file_xfmm,file_aov,nome_modelo,nome_saida);
					fm.gerarTabela();
					System.out.println("Modelo de Features gerado com sucesso!");
					break;
				case 1:
				    System.out.println("Oops! Arquivo de entrada em branco...");
				    break;
				default:
					System.out.println("Oops! You did it again...");
					break;
			}
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private File selectFile(String msg, int flag){
		instanciaArquivo instance_file = new instanciaArquivo();
		File file;
		Object[] options = {"Sim", "Usar arquivo anterior", "Cancelar"};
		Object frame = null;
		int n = JOptionPane.showOptionDialog((Component) frame, msg, "Modelo de Features",
			JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options, options[0]);

		if(n == JOptionPane.YES_OPTION)//usar arquivo de entrada
			file = instance_file.getInstance(true, flag);
		else{
			if(n == JOptionPane.NO_OPTION){//usar arquivo anterior
				file = instance_file.getInstance(flag);
				if(file==null){
					Object[] options2 = {"Sim", "Não"};
					int n2 = JOptionPane.showOptionDialog((Component) frame, "Arquivo NULL. "+msg, "NULL",
					    JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, options2, options[0]);
					if(n2==JOptionPane.YES_OPTION)
						file = instance_file.getInstance(true, flag);
				}
			}else
				file=null;//cancelar
		}
		return file;
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
