package mer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ERDiagram {
public void geraDiagrama(String dot, String nomedoarquivo){
	
	String der = new String();
    der = "graph default {" + dot ;
 	try {
        BufferedWriter out = new BufferedWriter(new FileWriter(nomedoarquivo + "der.dot"));
        der = der + "}";
        out.write(der); // " " = quebra de linha no  Windows 
        out.close();
    }
    catch(IOException e){
        System.out.println(e.getMessage());
    	
}
}

}
