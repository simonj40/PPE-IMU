/**
 * 
 */
package fr.ece.model;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * @author Simon
 *
 */
public class Coordinates extends Vector3D {

	private double time;

	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param time
	 */
	public Coordinates(double x, double y, double z, double time) {
		super(x,y,z);
		this.time = time;
	}
	
	public Coordinates(Vector3D vector, double time) {
		super(1, vector);
		this.time = time;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param time
	 */
	public Coordinates(double x, double y, double z) {
		super(x,y,z);
	}
	
	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}
	
}
