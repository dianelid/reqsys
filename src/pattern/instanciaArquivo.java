package pattern;

import java.io.File;
import javax.swing.JFileChooser;

public class instanciaArquivo {
	   private static File file, file_aov, file_xfmm, file_xdm;

	   public instanciaArquivo() {}
	   
	   public File getInstance(int flag){
		   if(flag==0)
			   return file_aov;
           else if(flag==1)
        	   return file_xfmm;
           else
        	   return file_xdm;
	   }
	   
	   public File getInstance(boolean teste, int flag) {
		  if (teste){
	          final JFileChooser fc = new JFileChooser();
	          int res = fc.showOpenDialog(null);
	          
	          if(res == JFileChooser.APPROVE_OPTION){
	            if(flag==0){
	            	file_aov = fc.getSelectedFile();
	            	file=file_aov;
	            }
	            else if(flag==1){
	            	file_xfmm = fc.getSelectedFile();
	            	file=file_xfmm;
	            }
	            else{
	            	file_xdm = fc.getSelectedFile();
	            	file=file_xdm;
	            }
	          }
	          else
	        	  System.out.println("Voce nao selecionou nenhum arquivo.");//diretorio = null;
	      }
	      return file;
	   }
	   
	   public File getInstance(){
		   return file;
	   }
	   
	   public File getInstance(boolean teste) {
		  if (teste){
	          final JFileChooser fc = new JFileChooser();
	          int res = fc.showOpenDialog(null);
	          
	          if(res == JFileChooser.APPROVE_OPTION)
	            file = fc.getSelectedFile();  
	          else
	        	  System.out.println("Voce nao selecionou nenhum arquivo.");//diretorio = null;
	      }
	      return file;
	   }
}
