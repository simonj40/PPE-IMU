/**
 * 
 */
package fr.ece.view;

import javax.swing.JFrame;
import fr.ece.model.Data;

/**
 * @author Simon
 *
 */

public class DisplayFrame extends JFrame {
	

	public DisplayFrame(){
        this.setSize(600, 600); //The window Dimensions
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.JPanel panel = new javax.swing.JPanel();
        //panel.setBounds(20, 20, 600, 600);
        
        
        Processing sketch = new Processing();
        //Processing2D sketch = new Processing2D();
        
        panel.add(sketch);
        this.add(panel);
        sketch.init(); //this is the function used to start the execution of the sketch
		
        /*
        Sketch2D sketch = new Sketch2D();
        this.add(sketch);
        */
        
        
        this.setVisible(true);
    }
	
}
