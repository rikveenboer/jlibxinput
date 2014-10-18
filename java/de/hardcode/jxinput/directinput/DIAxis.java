//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 27. Dezember 2001, 00:14
//**********************************************************************************************
package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.Axis;

/**
 *
 * @author  Herkules
 */
class DIAxis implements Axis
{
    private final int mDeviceIdx;
	private final int mIdx;

	/** 
	 * Creates a new instance of DIAxis.
	 */
	DIAxis( int devidx, int idx )	
	{
        mDeviceIdx = devidx;
		mIdx = idx;
	}

	public String getName()
	{
		return DirectInputDriver.getAxisName( mDeviceIdx, mIdx );
	}
	
	
	/**
	 * Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	public boolean hasChanged()
	{
		return true;
	}

	public double getValue()
	{
		return DirectInputDriver.getAxisValue( mDeviceIdx, mIdx );
	}
	
	public int getType()
	{
		return DirectInputDriver.getAxisType( mDeviceIdx, mIdx );
	}

	/** 
	 * Inform about the resolution of the axis.
	 *
	 * @return resolution, e.g. 2^-16
	 */
	public double getResolution()
	{
		// extend the driver here!!
		// Here I assume typical 16 bit resolution
		return ( getType() == Axis.SLIDER ? 1.0/65536.0 : 2.0/65536.0 ) ; 
	}
    
}


