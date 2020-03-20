package aov;

import java.util.*;
import analise.TokenMgrError;

public class VerificacaoSemantica {
	//System.out.println("VerificacaoSemantica");
	private TokenMgrError erro= new TokenMgrError();
	
	public void validaSubstitute(Vector<String> substitutevct, Vector<String> intertypePointcutsvct)  throws Exception {
		//System.out.println("substitutevct - intertypePointcutsvct : " + substitutevct + " - "+ intertypePointcutsvct);
		for(int i=0; i < substitutevct.size(); i++){
			for(int j=0; j < intertypePointcutsvct.size(); j++){	
				if(intertypePointcutsvct.elementAt(j).contains(substitutevct.elementAt(i))){
					erro.atualizaArquivoErro("\nWARNING! Primitiva 'susbstitute' associada a um 'intertype declaration' no pointcut " + substitutevct.elementAt(i) + "!");	
					try{
						throw new Exception();
					}catch(Exception e){		
						System.out.println("\nWARNING! Primitiva 'susbstitute' associada a um 'intertype declaration' no pointcut " + substitutevct.elementAt(i) + "!");
					}
				}//else{System.out.println("TUDO OK");}
			}
		}
	}
	
	public void validaComponente(Vector<String> componentes) throws Exception{
		for(int i=0; i < componentes.size(); i++){
			for(int j=i+1; j < componentes.size(); j++){
				if(componentes.elementAt(i).equals(componentes.elementAt(j))){
					erro.atualizaArquivoErro("\nWARNING!" + " Nome de componente " + componentes.elementAt(i) + " ja eh utilizado! ");	
					try{
						throw new Exception();
					}catch(Exception e){
						//if(!(componentes.elementAt(i).equals(componentes.elementAt(j))))	
						System.out.println("\nWARNING!" + " Nome de componente " + componentes.elementAt(i) + " ja eh utilizado! ");
					}
				}
			}
		}	
	}
	
	public void validaReferencia(Vector<String> componentes, Vector<String> referencias) throws Exception{
		java.util.Vector<String> nomeComponentevct = new java.util.Vector<String>();	
		
		for(int j=0;j<componentes.size();j++){
			java.util.StringTokenizer nomeComponente = new java.util.StringTokenizer(componentes.elementAt(j), "(");
			while (nomeComponente.hasMoreTokens()) {
				nomeComponentevct.addElement(nomeComponente.nextToken());
			    break;
			}
		}
		//System.out.println("NOME COMPONENTE: " + nomeComponentevct);
		//System.out.println("REFERENCIAS: " + referencias);
		for(int i=0; i < referencias.size(); i++){
			if(!nomeComponentevct.contains(referencias.elementAt(i))){
				erro.atualizaArquivoErro("\nWARNING!" + " Referencia " + referencias.elementAt(i) + " a componente nao declarado! ");
				try{
					throw new Exception();
				}catch(Exception e){
					//if(!(componentes.elementAt(i).equals(componentes.elementAt(j))))
					System.out.println("\nWARNING!" + " Referencia " + referencias.elementAt(i) + " a componente nao declarado! ");
				}
			}
		}
	}
} 