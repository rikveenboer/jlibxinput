//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 9. April 2002, 22:43
//**********************************************************************************************
package de.hardcode.jxinput.virtual;

import java.util.ArrayList;
import de.hardcode.jxinput.Axis;



/**
 * This is the main worker class for JXVirtualInputDevice.
 * 
 * @author  Herkules
 */
class VirtualDriver 
{	

	private final VirtualAxis[] mVAxes = new VirtualAxis[ Axis.NUMBER_OF_ID ];
	
	/**
	 * Creates a new instance of KeyboardDriver.
	 */
	VirtualDriver()
	{
	}

	
	/**
	 * Update features under my control.
	 */
	final void update( long deltaT )
	{
		//
		// Delegate the update call to the axes in use.
		//
		for ( int i = 0; i < mVAxes.length; i++ )
		{
			if ( null != mVAxes[ i ] )
				mVAxes[ i ].update( deltaT );
		}
	}

	
	/**
	 * How many axes are registered?
	 */
	final int getNumberOfAxes()
	{
		int ctr = 0;
		for ( int i = 0; i < mVAxes.length; i++ )
		{
			if ( null != mVAxes[ i ] )
				ctr++;
		}
		return ctr;
	}
	
	Axis getAxis(int idx)
	{
		return mVAxes[ idx ];
	}
	
	
	/** 
	 * Place a new axis under my observation.
	 */
	final void registerVirtualAxis( int id, VirtualAxis a )
	{
		mVAxes[ id ] = a;
	}
	
	
	/**
	 * Remove an axis from my control.
	 */
	final void unregisterVirtualAxis( VirtualAxis a )
	{
		for ( int i = 0; i < mVAxes.length; ++i )
		{
			if ( mVAxes[ i ] == a )
			{
				mVAxes[ i ] = null;
				break;
			}
		}
	}
	
	
}
