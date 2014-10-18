//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 17. April 2002, 23:24
//**********************************************************************************************
package de.hardcode.jxinput.util;

import de.hardcode.jxinput.Axis;

/**
 *
 * @author  Herkules
 */
public class LatestChangedValueAxis implements Axis
{
	private final Axis mAxis1;
	private final Axis mAxis2;
	private       Axis mAxisInUse;

    private double mSaved1;
    private double mSaved2;
	
	/**
	 * Creates a new instance of MeanValueAxis.
	 */
	public LatestChangedValueAxis(Axis a1, Axis a2)
	{
		mAxis1      = a1;
		mAxis2      = a2;
        mAxisInUse  = a1;
        
        mSaved1 = a1.getValue();
        mSaved2 = a2.getValue();
	}
	
	/**
	 * Features may have a name provided e.g. by the driver.
	 */
	public String getName()
	{
		return mAxis1.getName();
	}
	
	/** Inform about the resolution of the axis.
	 *
	 * @return resolution, e.g. 2^-16
	 */
	public double getResolution()
	{
		return mAxis1.getResolution();
	}
	
	/** 
	 * Retrieve the type of the axis.
	 *
	 * @return [ TRANSLATION | ROTATION | SLIDER ]
	 */
	public int getType()
	{
		return mAxis1.getType();
	}
	
	/** Returns the current value of the axis.
	 * The range of the result depends on the axis type.
	 *s
	 * @return value [-1.0,1.0] or [0.0,1.0]
	 */
	public double getValue()
	{
		double v1 = mAxis1.getValue();
		double v2 = mAxis2.getValue();

        if ( Math.abs( v2 - mSaved2 ) > 0.2 )
        {
            mAxisInUse = mAxis2;
            mSaved2 = v2;
        }
        if ( Math.abs( v1 - mSaved1 ) > 0.2 )
        {
            mAxisInUse = mAxis1;
            mSaved1 = v1;
        }
        
        return mAxisInUse.getValue();
	}
	
	/** Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	public boolean hasChanged()
	{
		return true;
	}
	
}
