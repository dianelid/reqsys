package pattern;

import rastreability.rastreabilityMatrix;
import rastreability.rastreabilityMatrixTokenManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;

import rastreability.JavaCharStream;


public class instanciaRastreability {
	private static rastreabilityMatrix parser = null;
	
	private static String nomeArquivo = new String();
	
	   public instanciaRastreability(String tipo) {
		   
		   nomeArquivo = "C://matriz.txt";
		/*	 if(tipo.equals("transversalidade")){
				  nomeArquivo = "C://matriztransversalidade.txt";
				  System.out.println(nomeArquivo);
			 
			 }
			 
			 if(tipo.equals("contribuicao"))
			{
				  nomeArquivo = "C://matrizcontribuicao.txt";
				  System.out.println(nomeArquivo);
			  }*/
	   }

	   @SuppressWarnings("static-access")
	public static rastreabilityMatrix getInstance() {

		   //if (parser == null){
			   //System.out.println("PARSER NULL");
	  		try{
				parser = new rastreabilityMatrix
					(new rastreabilityMatrixTokenManager
                    (new JavaCharStream
                    (new FileReader
                    ("C://matriz.txt"))));
					}catch (FileNotFoundException e) {
	       System.out.println("File not found. Exiting. Excessao: "+ e);
					}
	      //}
		 /*else{
	     try {
	    	Reader reader = new FileReader("C://matriz.txt");
			parser.ReInit(reader);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}	  
	    	  
	      }*/

	      return parser;
	   }
}
