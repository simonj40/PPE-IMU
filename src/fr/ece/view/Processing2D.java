/**
 * 
 */
package fr.ece.view;

import java.awt.*;
import java.util.Random;

import javax.swing.JPanel;

import fr.ece.model.Coordinates;
import fr.ece.model.Data;
import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * @author Simon
 *
 */
public class Processing2D extends PApplet {

	
public float scale=1;

	public Data data;	  
	
	public String PATH = "/Users/Simon/Desktop/data.txt";
	
	public int height = 800;
	
	public int width = 800;
	public int boxSize = height/2;
	
	public void setup() {
		
		data = new Data();
		data.extractData(PATH);
    	data.generatePos();
    	
    	data.dataToFile();
		
	    size(height, width);
	    background(255, 255, 255);

	  }
	
	
	  public void draw() {
		  background(255, 255, 255);
		  //translate repère au centre de la fenêtre
		  translate(width/2, height/2);
		  //rotate
		  rotateY(map(mouseX, 0, width, -PI, PI));
		  rotateX(map(mouseY, 0, height, -PI, PI));
		  //image
		  scale(scale);
		  //drawGrid();
	      noFill();
	      beginShape();
		  
		  for(Coordinates c : data.pos){
			  stroke(0,0,0);
			  vertex((float)c.x, (float)c.y);
		  }
		  
		  endShape();
	  }
	  
	  
	  public void drawGrid(){
		  
		  int grid = 20; // change this number to 20 or 50, etc., if you want fewer grid lines
		  size (height,width);
		  
		  
		  pushMatrix(); // mémorise l'état du repère 
		  translate(-width/2, -height/2);
		  for (int i = 0; i < width; i+=grid) {
		    line (i, 0, i, height);
		  }
		  for (int i = 0; i < height; i+=grid) {
		    line (0, i, width, i);
		  }
	
		  popMatrix(); // restaure l'état du repère  
	  }
	  
	  
	  public void mouseWheel(MouseEvent event) {
		  float e=event.getAmount();
		  scale+=e*0.05;
		}


}
