package reqsysplugin.views;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ReqSysView extends ViewPart { 
	private Text text;
	

	 public void createPartControl(Composite parent) {  
		 text = new Text(parent, SWT.READ_ONLY | SWT.MULTI);  
		 OutputStream out = new OutputStream() {   
			 @Override   
			 public void write(int b) throws IOException {    
				 if (text.isDisposed()) return;    
				 text.append(String.valueOf((char) b));   }  };  
				 
				 final PrintStream oldOut = System.out;  
				 System.setOut(new PrintStream(out));  
				 text.addDisposeListener(new DisposeListener() {   
					 public void widgetDisposed(DisposeEvent e) {    
						 System.setOut(oldOut);   }  }); }

	 public void setFocus() {  text.setFocus(); }}