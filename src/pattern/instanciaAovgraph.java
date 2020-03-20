package pattern;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import analise.JavaCharStream;
import aov.aovgraph;
import aov.aovgraphTokenManager;

public class instanciaAovgraph {
	   private static aovgraph parser;
	   private static JavaCharStream char_stream;
	   private static aovgraphTokenManager token_manager;
	   
	   public instanciaAovgraph() {
	   }

	@SuppressWarnings("static-access")
	public aovgraph getInstance(String diretorio) {
		   Reader dstream=null;
		   if (parser == null){
			   try {
					dstream = new FileReader(diretorio);
					char_stream=new JavaCharStream(dstream);
					token_manager=new aovgraphTokenManager(char_stream);
				  	parser = new aovgraph(token_manager);
				} catch (FileNotFoundException e) {
					System.out.println("File not found. Exiting. Excessao: "+ e);
				}
		   }
		   else{
			   try {
					dstream = new FileReader(diretorio);
					char_stream.ReInit(dstream);
					token_manager.ReInit(char_stream);
				  	parser.ReInit(token_manager);
				} catch (FileNotFoundException e) {
					System.out.println("File not found. Exiting. Excessao: "+ e);
				}
		   }
		   /*if (parser == null){
	  		try{
				parser = new aovgraph(new aovgraphTokenManager
										(new JavaCharStream
										(new FileReader
										(new File(diretorio)))));
			}catch (FileNotFoundException e) {
				System.out.println("File not found. Exiting. Excessao: "+ e);
			}
	      }*/
			
	    /*else{
	    try {
			parser.ReInit(new aovgraphTokenManager
											(new JavaCharStream
											(new FileReader
											(new File(diretorio)))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	      }*/
	      return parser;
	   }	
}