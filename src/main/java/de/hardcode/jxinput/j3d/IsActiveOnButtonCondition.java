//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 25. Februar 2002, 22:43
//**********************************************************************************************
package de.hardcode.jxinput.j3d;

import de.hardcode.jxinput.Button;

/**
 *
 * @author Herkules
 */
public class IsActiveOnButtonCondition implements IsActiveCondition
{	
	private final boolean mActiveState;
	private final Button mButton;
	
	/**
	 * Creates a new instance of IsAlwayActiveCondition.
	 */
	public IsActiveOnButtonCondition( Button button, boolean activestate )
	{
		mActiveState = activestate;
		mButton = button;
	}
	
	/** 
	 * Tell wether a certain thing is active.
	 */
	public boolean isActive()
	{
		return mButton.getState() == mActiveState;
	}
	
}
