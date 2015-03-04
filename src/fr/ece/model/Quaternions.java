/**
 * 
 */
package fr.ece.model;

/**
 * @author Simon
 *
 */
public class Quaternions {

	private double Q0;
	private double Q1;
	private double Q2;
	private double Q3;
	
	
	
	
	/**
	 * @param q0
	 * @param q1
	 * @param q2
	 * @param q3
	 */
	public Quaternions(double q0, double q1, double q2, double q3) {
		super();
		Q0 = q0;
		Q1 = q1;
		Q2 = q2;
		Q3 = q3;
		
	}
	/**
	 * @return the q0
	 */
	public double getQ0() {
		return Q0;
	}
	/**
	 * @param q0 the q0 to set
	 */
	public void setQ0(double q0) {
		Q0 = q0;
	}
	/**
	 * @return the q1
	 */
	public double getQ1() {
		return Q1;
	}
	/**
	 * @param q1 the q1 to set
	 */
	public void setQ1(double q1) {
		Q1 = q1;
	}
	/**
	 * @return the q2
	 */
	public double getQ2() {
		return Q2;
	}
	/**
	 * @param q2 the q2 to set
	 */
	public void setQ2(double q2) {
		Q2 = q2;
	}
	/**
	 * @return the q3
	 */
	public double getQ3() {
		return Q3;
	}
	/**
	 * @param q3 the q3 to set
	 */
	public void setQ3(double q3) {
		Q3 = q3;
	}

	
	
	
}
