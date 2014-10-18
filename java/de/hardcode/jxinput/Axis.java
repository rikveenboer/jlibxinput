//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 19. Dezember 2001, 21:58
//**********************************************************************************************
package de.hardcode.jxinput;

/**
 * The Axis interface describes the most common feature of a joystick or other input devices.
 *
 * @author  Herkules
 */
public interface Axis extends Feature
{
	// Enumeration of axes.
	final static int ID_X = 0;
	final static int ID_Y = 1;
	final static int ID_Z = 2;
	final static int ID_ROTX = 3;
	final static int ID_ROTY = 4;
	final static int ID_ROTZ = 5;
	final static int ID_SLIDER0 = 6;
	final static int ID_SLIDER1 = 7;
	final static int NUMBER_OF_ID = 8;
	
	// Enumeration of axis types
	final static int TRANSLATION = 0;	
	final static int ROTATION = 1;
	final static int SLIDER = 2;

	/**
	 * Retrieve the type of the axis.
	 * The type is describes the meaning and the range of values of the axis.
	 * <p>
	 * <code>TRANSLATION</code> typed axes denote a translational deviation from a center
	 * position. This can be e.g. the common, basic joystick axes.
	 * The range of <code>getValue()</code> is <code>[-1.0,1.0]</code>. 
	 * <p>
	 * <code>ROTATION</code> typed axes denote a rotational deviation from a center
	 * position. Something on the stick is turned or twisted. 
	 * The range of <code>getValue()</code> is <code>[-1.0,1.0]</code>. 
	 * <p>
	 * <code>SLIDER</code> typed axes denote a shifting device without a center position.
	 * A good sample is a throttle control. 
	 * The range of <code>getValue()</code> is <code>[0.0,1.0]</code>. 
	 *
	 * @return [ TRANSLATION | ROTATION | SLIDER ]
	 */
	int getType();

	/**
	 * Returns the current value of the axis. 
	 * The range of the result depends on the axis type.
	 * 
	 * @return value [-1.0,1.0] or [0.0,1.0]
	 */
	double getValue();

    
    /**
     * Inform about the resolution of the axis.
     *
     * @return resolution, e.g. 2^-16
     */
    double getResolution();
    
}


