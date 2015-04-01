/**
 * 
 */
package fr.ece.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * @author Simon
 *
 */
public class Data {

	public String PATHOUT = "dataOUT.txt";
	public String PATHIN = "DATA.txt";

	public int aExtLimit = 50;

	public Vector3D aExt;

	public List<Coordinates> accel;
	public List<Quaternions> quaternion;

	public List<Coordinates> angle;
	public List<Coordinates> pos;
	public List<Coordinates> speed;

	public double g = 9.81;

	public double RATE = 1 / 0.008;

	/**
	 * @param accel
	 * @param gyro
	 * @param angle
	 */
	public Data() {
		super();
		this.accel = new ArrayList<Coordinates>();
		this.quaternion = new ArrayList<Quaternions>();
		this.speed = new ArrayList<Coordinates>();
		this.pos = new ArrayList<Coordinates>();

		this.extractData(PATHIN);
		calculateAExt();
		this.correctA();
		this.generatePos();
		this.dataToFile();
	}

	public void extractData(String path) {

		try {
			FileReader fr = new FileReader(path);
			BufferedReader dataReader = new BufferedReader(fr);

			while (dataReader.ready()) {
				addData(dataReader.readLine());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addData(String line) {
		// System.out.println(line);

		String[] lineArray = line.split(" ");
		if (lineArray.length == 7) {
			accel.add(extractAccel(lineArray));
			quaternion.add(extractQuaternion(lineArray));
		} else {
			System.out.println("incorrect data file format ! : \n" + line);
		}
	}

	public Quaternions extractQuaternion(String[] lineArray) {

		Quaternions quat = new Quaternions(Double.parseDouble(lineArray[3]),
				Double.parseDouble(lineArray[4]),
				Double.parseDouble(lineArray[5]),
				Double.parseDouble(lineArray[6]));

		return quat;
	}

	public Coordinates extractAccel(String[] lineArray) {

		Coordinates coor = new Coordinates((Double.parseDouble(lineArray[0])),
				(Double.parseDouble(lineArray[1])),
				(Double.parseDouble(lineArray[2])));

		return coor;
	}

	public Coordinates extractGyro(String[] lineArray) {

		Coordinates coor = new Coordinates(Double.parseDouble(lineArray[3]),
				Double.parseDouble(lineArray[4]),
				Double.parseDouble(lineArray[5]));

		return coor;
	}

	public Coordinates extractAngle(String[] lineArray) {

		Coordinates coor = new Coordinates(Double.parseDouble(lineArray[6]),
				Double.parseDouble(lineArray[7]),
				Double.parseDouble(lineArray[8]));

		return coor;
	}

	public void generatePos() {

		speed.add(new Coordinates(0, 0, 0));

		pos.add(new Coordinates(0, 0, 0));

		for (int i = 1; i < accel.size(); i++) {

			Coordinates c = new Coordinates(speed.get(i - 1).getX()
					+ accel.get(i).getX() / RATE, speed.get(i - 1).getY()
					+ accel.get(i).getY() / RATE, speed.get(i - 1).getZ()
					+ accel.get(i).getZ() / RATE);
			speed.add(c);
		}

		for (int i = 1; i < speed.size(); i++) {

			Coordinates c = new Coordinates(pos.get(i - 1).getX()
					+ speed.get(i).getX() / RATE, pos.get(i - 1).getY()
					+ speed.get(i).getY() / RATE, pos.get(i - 1).getZ()
					+ speed.get(i).getZ() / RATE);
			pos.add(c);
		}
	}

	/**
	 * 
	 */
	/*
	 * public void correctA() {
	 * 
	 * for(int i=0 ; i<accel.size() ; i++){
	 * 
	 * double Ax = accel.get(i).getX(); double Ay = accel.get(i).getY(); double
	 * Az = accel.get(i).getZ();
	 * 
	 * double Q0 = quaternion.get(i).getQ0(); double Q1 =
	 * quaternion.get(i).getQ1(); double Q2 = quaternion.get(i).getQ2(); double
	 * Q3 = quaternion.get(i).getQ3();
	 * 
	 * //put A vector in fix referential accel.get(i).x = (1 - 2*Math.pow(Q2, 2)
	 * - 2*Math.pow(Q3, 2))*Ax + 2*(Q1*Q2 + Q0*Q3)*Ay + 2*(Q1*Q3 - Q0*Q2)*Az;
	 * accel.get(i).y = 2*(Q1*Q2 - Q0*Q3)*Ax + (1-2*Math.pow(Q1,
	 * 2)-2*Math.pow(Q3, 2))*Ay + 2*(Q2*Q3 + Q0*Q1)*Az; accel.get(i).z =
	 * 2*(Q1*Q3 + Q0*Q2)*Ax + 2*(Q2*Q3 -Q0*Q1)*Ay + (1-2*Math.pow(Q1,
	 * 2)-2*Math.pow(Q2, 2))*Az; //add g coefficient accel.get(i).x =
	 * accel.get(i).x*g; accel.get(i).y = accel.get(i).y*g; accel.get(i).z =
	 * accel.get(i).z*g;
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	public void correctA() {
		
		List<Coordinates> newList = new ArrayList<>();

		for (int i = 0; i < accel.size(); i++) {
			Coordinates a = accel.get(i);
			Quaternions q = quaternion.get(i);
			// Rotate A to put it back in Fix referential
			Vector3D vector = q.rotateInverse(a);
			// Remove External Accel from it (gravitational acceleration,
			// centripetal acceleration, coriolis acceleration)
			vector = vector.subtract(aExt);
			
			double x = vector.getX();
			BigDecimal bd = new BigDecimal(x);
			bd = bd.setScale(4, BigDecimal.ROUND_HALF_DOWN);
			x = bd.doubleValue();
			double y = vector.getY();
			bd = new BigDecimal(y);
			bd = bd.setScale(4, BigDecimal.ROUND_HALF_DOWN);
			y = bd.doubleValue();
			double z = vector.getZ();
			bd = new BigDecimal(z);
			bd = bd.setScale(4, BigDecimal.ROUND_HALF_DOWN);
			z = bd.doubleValue();	
			
			vector = new Vector3D(x,y,z);
			
			vector = vector.scalarMultiply(g);
			// Add it to the new list
			newList.add(new Coordinates(vector, a.getTime()));
		}

		this.accel = newList;

	}

	private void calculateAExt() {

		Vector3D vector = new Vector3D(0,0,0);
		
		double x = 0;
		double y = 0;
		double z = 0;

		for (int i = 0; i < aExtLimit; i++) {
			Quaternions q = quaternion.get(i);
			vector = q.rotateInverse(accel.get(i));
			x += vector.getX();
			y += vector.getY();
			z += vector.getZ();
		}
		
		x = x/(double)aExtLimit;
		y = y/(double)aExtLimit;
		z = z/(double)aExtLimit;
		
		this.aExt = new Vector3D(x,y,z);
		
		System.out.println("Vector Aext = "+vector.getX()+" "+vector.getY()+" "+vector.getZ() );

	}

	/**
	 * 
	 */
	/*
	 * public void removeG() {
	 * 
	 * for(int i=0 ; i<accel.size() ; i++){
	 * 
	 * double Q0 = quaternion.get(i).getQ0(); double Q1 =
	 * quaternion.get(i).getQ1(); double Q2 = quaternion.get(i).getQ2(); double
	 * Q3 = quaternion.get(i).getQ3(); //remove the effect of gravity on
	 * acceleration accel.get(i).x = accel.get(i).x + 2*(Q1*Q3 - Q0*Q2);
	 * accel.get(i).y = accel.get(i).y + 2*(Q2*Q3 + Q0*Q1); accel.get(i).z =
	 * accel.get(i).z + (1-2*Math.pow(Q1, 2)-2*Math.pow(Q2, 2)); }
	 * 
	 * }
	 */

	public void dataToFile() {

		try {
			FileWriter writer = new FileWriter(PATHOUT);

			writer.write("\n\n******************************ACCELLERATION******************************\n\n");
			for (Coordinates c : accel) {
				String line = (float) c.getX() + "\t\t" + (float) c.getY()
						+ "\t\t" + (float) c.getZ() + "\n";
				writer.write(line);
			}

			writer.write("\n\n******************************POSITIONS******************************\n\n");
			for (Coordinates c : pos) {
				String line = (float) c.getX() + "\t\t" + (float) c.getY()
						+ "\t\t" + (float) c.getZ() + "\n";
				writer.write(line);
			}
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
