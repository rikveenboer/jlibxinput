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
 *
 * @author  Herkules
 */
public interface Button extends Feature
{
	// Enumeration of button types
	final static int PUSHBUTTON = 0;	
	final static int TOGGLEBUTTON = 1;
	
	/**
	 * Retrieve the type of the button. 
	 * Pushbutton will deliver <code>true==getState()</code> as long as they are pressed down.
	 * Togglebuttons will change their state once they are pressed and keep that state
	 * until they are pressed again. 
	 * @return [ PUSHBUTTON | TOGGLEBUTTON ]
	 */
	int getType();
	
	/**
	 * Tells the state of the button at last update.
	 */ 
	boolean getState();
}

