/**
 * 
 */
package fr.ece.model;

/**
 * @author Simon
 *
 */
public class Coordinates {

	public double x;
	public double y;
	public double z;
	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Coordinates(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public String toString(){
		
		return x + "," + y + "," + z;
		
	}
	
}
