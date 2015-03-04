package fr.ece.view;

import fr.ece.model.Coordinates;
import fr.ece.model.Data;
import processing.core.*;
import processing.event.MouseEvent;


/**
 * @author Simon
 *
 */
public class Processing extends PApplet {

	public float scale=1;
	
	public Data data;	  
	
	public String PATH = "/Users/Simon/Desktop/test1.txt";
	
	public int height = 800;
	
	public int width = 800;
	public int boxSize = height/2;
	
	public float scalePos = 1;
	
	
	
	public void setup() {
		
		data = new Data();
		
	    size(height, width, OPENGL);
	    background(0, 0, 0);

	  }
	
	  public void draw() {
		  background(0, 0, 0);
		  //translate repère au centre de la fenêtre
		  translate(width/2, height/2);
		  //rotate
		  rotateY(map(mouseX, 0, width, -PI, PI));
		  rotateX(map(mouseY, 0, height, -PI, PI));
		  //image
		  scale(scale);
		  
		  pushMatrix(); // mémorise l'état du repère
		  
		  translate(-boxSize/2, -boxSize/2, -boxSize/2);
		  stroke(128, 128, 128);
		  noFill();
		  box(boxSize);
		  
		  popMatrix(); // restaure l'état du repère

	      noFill();
	      beginShape();
		  
		  for(Coordinates c : data.pos){
			  stroke(255,0,0);
			  
		      vertex((float)c.x*scalePos, (float)c.y*scalePos, (float)c.z*scalePos);

		  }
		  
		  endShape();
	  }
	  
	  
	  public void mouseWheel(MouseEvent event) {
		  float e=event.getAmount();
		  scale+=e*0.05;
		}

}
