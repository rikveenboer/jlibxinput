//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 19. Dezember 2001, 21:47
//**********************************************************************************************
package de.hardcode.jxinput;

/** 
 * The <code>JXInputDevise</code> is the main entrypoint to the jxinput package.
 * <p>
 * A JXInputDevice represents one physical device like a joystick, a gamepad or
 * even some emulation (e.g. using keyboard) that implements the interface.
 * <p>
 * The basis task of a <code>JXInputDevise</code> is to maintain a consistent state of all its features.
 * <br>
 * It is save to distribute the <code>Feature</code> objects into application without worrying 
 * about someone else performing an <code>update</code> method and thereby destructing the consistent state.
 * <p>
 * An additional task is to provide basic device features information like number of axes, buttons 
 * and directional features.
 *
 * @see Feature
 * @see JXInputManager
 *
 * @author Herkules
 * @version 0.2beta
 */
public interface JXInputDevice
{
	/**
	 * @directed 
	 */
    /*#Features lnkFeatures;*/

	/**
	 *@link aggregationByValue
	 */
    /*#Feature lnkFeature;*/

	/** 
	 * Devices may have a name. 
	 * This name might be provided by a system dependant driver. 
	 */
	String getName();
		
	/** Actual number of available axes. */
	int getNumberOfAxes();

	/** Actual number of available buttons. */
	int getNumberOfButtons();

	/** Actual number of available directional features. */
	int getNumberOfDirectionals();
	
	/** Maximum number of axes as an upper bound for index values. */
	int getMaxNumberOfAxes();

	/** Maximum number of buttons as an upper bound for index values. */
	int getMaxNumberOfButtons();

	/** Maximum number of directional features as an upper bound for index values. */
	int getMaxNumberOfDirectionals();
	
	Axis getAxis( int idx );
	Button getButton( int idx );	
	Directional getDirectional( int idx );	
}

