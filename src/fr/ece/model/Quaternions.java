/**
 * 
 */
package fr.ece.model;

import org.apache.commons.math3.geometry.euclidean.threed.*;

/**
 * @author Simon
 *
 */
public class Quaternions extends Rotation {

	/**
	 * @param q0
	 * @param q1
	 * @param q2
	 * @param q3
	 */
	public Quaternions(double q0, double q1, double q2, double q3) {
		super(q0, q1, q2, q3, true);
	}

	/**
	 * @return the q0
	 */

	public Coordinates rotate(Coordinates vector) {
		return new Coordinates(this.applyTo(vector), vector.getTime());
	}

	public Coordinates rotateInverse(Coordinates vector) {
		return new Coordinates(this.applyInverseTo(vector), vector.getTime());
	}

}
