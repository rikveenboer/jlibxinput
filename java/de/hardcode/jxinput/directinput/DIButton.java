//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 27. Dezember 2001, 00:14
//**********************************************************************************************
package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Button;


/**
 *
 * @author  Herkules
 */
class DIButton implements Button
{
    private final int mDeviceIdx;    
	private final int mIdx;

	/** 
	 * Creates a new instance of DIButton.
	 */
	DIButton( int devidx, int idx )	
	{
        mDeviceIdx = devidx;
		mIdx = idx;
	}
	
	public String getName()
	{
		return DirectInputDriver.getButtonName( mDeviceIdx, mIdx );
	}

	/**
	 * Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	public boolean hasChanged()
	{
		return true;
	}
	
	public int getType()
	{
		return DirectInputDriver.getButtonType( mDeviceIdx, mIdx );
	}
	
	public boolean getState()
	{
		return DirectInputDriver.getButtonState( mDeviceIdx, mIdx );
	}
}