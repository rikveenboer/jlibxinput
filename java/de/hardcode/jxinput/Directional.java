//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 27. Dezember 2001, 23:33
//**********************************************************************************************
package de.hardcode.jxinput;

/**
 *
 * @author  Herkules
 */
public interface Directional extends Feature
{
	/**
	 * If the Directional has a center position where it points to no direction, this
	 * flag is true when this position is reached.
	 */
	boolean isCentered();
	
	/**
	 * Retrieve the direction pointed to.
	 * Value is given in 1/100 degree, [0,36000]
	 */
	int getDirection();

	/**
	 * Retrieve the analog value pointing to the angle described by
	 * <code>getDirection()</code>.
	 * For coolie hats this will be either 1,0 for any direction or 0.0
	 * when <code>isCentered()==true</code>.
	 */
	double getValue();

	/**
	 * Inform about the resolution of the value returned by <code>getValue()</code>.
	 *
	 * @return resolution, e.g. 1.0 for coolie hats
	 */
	double getResolution();

}

