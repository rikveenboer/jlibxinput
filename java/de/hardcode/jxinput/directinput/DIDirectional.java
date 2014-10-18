//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 27. Dezember 2001, 23:40
//**********************************************************************************************
package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Directional;

/**
 *
 * @author  Herkules
 */
class DIDirectional implements Directional
{
    private final int mDeviceIdx;    
	private final int mIdx;
	
	/** 
	 * Creates a new instance of DIDirectional.
	 */
	DIDirectional( int devidx, int idx )	
	{
        mDeviceIdx = devidx;
		mIdx = idx;
	}
	
	/** Features may have a name provided e.g. by the driver.  */
	public String getName()
	{
		return DirectInputDriver.getDirectionalName( mDeviceIdx, mIdx );
	}
	
	/**
	 * Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	public boolean hasChanged()
	{
		return true;
	}

	
	public boolean isCentered()
	{
		return ( 0xffff == (DirectInputDriver.getDirection( mDeviceIdx, mIdx ) & 0xffff) ); 
	}
	
	public int getDirection()
	{
		return isCentered() ? 0 : DirectInputDriver.getDirection( mDeviceIdx, mIdx );
	}

	public double getValue()
	{
		if ( isCentered() )
			return 0.0;
		return 1.0;
	}

	/** 
	 * Inform about the resolution of the value returned by <code>getValue()</code>.
	 *
	 * @return resolution, e.g. 1.0 for coolie hats
	 */
	public double getResolution()
	{
		// DI POV always return 0.0 or 1.0, so the resolution is 1.0.
		return 1.0;
	}
	
	
}


