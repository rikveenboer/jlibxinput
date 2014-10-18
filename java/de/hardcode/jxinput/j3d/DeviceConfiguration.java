//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 23. Februar 2002, 14:05
//**********************************************************************************************
package de.hardcode.jxinput.j3d;

import de.hardcode.jxinput.Axis;


/**
 * Connects JXInput with J3DInputDevice.
 *
 * @author Herkules
 */
public class DeviceConfiguration
{
	public final static int AXIS_X = 0;
	public final static int AXIS_Y = 1;
	public final static int AXIS_Z = 2;
	
	private final static class axisvalue
	{
		private final Axis mAxis;
		private final IsActiveCondition mIsActive;
		private final IsActiveCondition mIsIncremental;
		private final double mScale;
		private final double mOffset;
		private double mValue;
		
		axisvalue( Axis axis, IsActiveCondition active, IsActiveCondition incremental, double offset, double scale )
		{
			mAxis = axis;
			mIsActive = active;
			mIsIncremental = incremental;
			mValue = mOffset = offset;
			mScale = scale;
		}
		
		double value()
		{
			if ( mIsActive.isActive() )
			{
				double newval = mAxis.getValue() * mScale;
				
				if ( mIsIncremental.isActive() )
					mValue += newval;
				else
					mValue = newval + mOffset;
			}
			return mValue;
		}
	}

	DeviceConfiguration.axisvalue [] mAxisTrans = new DeviceConfiguration.axisvalue[ 3 ];
	DeviceConfiguration.axisvalue [] mAxisRot = new DeviceConfiguration.axisvalue[ 3 ];
	
	/**
	 * Creates a new instance of DeviceConfiguration.
	 */
	public DeviceConfiguration()
	{
	}
	
	
	double getTranslational( int axisid )
	{
		DeviceConfiguration.axisvalue val = mAxisTrans[ axisid ];
		return null == val ? 0.0 : val.value();
	}
	
	double getRotational( int axisid )
	{
		DeviceConfiguration.axisvalue val = mAxisRot[ axisid ];
		return null == val ? 0.0 : val.value();
	}

	public void setTranslational( int axisid, Axis axis, IsActiveCondition active, IsActiveCondition incremental, double offset, double scale )
	{
		if ( axisid < 0  ||  axisid > AXIS_Z )
			throw new IllegalArgumentException();
		mAxisTrans[ axisid ] = new DeviceConfiguration.axisvalue( axis, active, incremental, offset, scale );
	}

	public void setRotational( int axisid, Axis axis, IsActiveCondition active, IsActiveCondition incremental, double offset, double scale )
	{
		if ( axisid < 0  ||  axisid > AXIS_Z )
			throw new IllegalArgumentException();
		mAxisRot[ axisid ] = new DeviceConfiguration.axisvalue( axis, active, incremental, offset, scale );
	}
	
}
