//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 26. Dezember 2001, 00:40
//**********************************************************************************************
package de.hardcode.jxinput.directinput;

import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.Button;

/**
 *
 * @author  Herkules
 */
public class DirectInputDevice implements JXInputDevice
{		
    int mDeviceIdx;
    
	private	DIAxis[]			mAxes;
	private	DIButton[]			mButtons;
	private	DIDirectional[]		mDirectionals;
	
	/**
	 * The number of DirectInput devices available with the driver.
	 */
    public static int getNumberOfDevices()
	{
		if ( DirectInputDriver.isAvailable() )
			return DirectInputDriver.getNumberOfDevices();
		return 0;
	}
	
	
	/**
	 * Update the state of all devices.
	 */
	public static void update()
	{
		if ( DirectInputDriver.isAvailable() )
			DirectInputDriver.nativeupdate();
    }

    
    
	
	/** 
	 * Creates a new instance of DirectInputDevice.
	 */
	public DirectInputDevice( int devidx )
	{	
        mDeviceIdx = devidx;

		init();
	}

    /**
     * Reset the DirectInput connection.
     */
    public static void reset()
    {
		if ( DirectInputDriver.isAvailable() )
            DirectInputDriver.reset();
    }

	
	/**
	 * Initialisation of fields.
	 */
	private final void init()
	{
        //
        // Allocate arrays for max. number of features
        //
        mAxes			= new DIAxis		[ getMaxNumberOfAxes()			];
        mButtons		= new DIButton		[ getMaxNumberOfButtons()		];
        mDirectionals	= new DIDirectional	[ getMaxNumberOfDirectionals()	];
		
        //
        // Fill arrays due to the state of the driver.
        //
		for ( int i = 0; i < mAxes.length; ++i )
		{
			if ( DirectInputDriver.isAxisAvailable( mDeviceIdx, i ) )
				mAxes[ i ] = new DIAxis( mDeviceIdx, i );
		}
		
		for ( int i = 0; i < mButtons.length; ++i )
		{
			if ( DirectInputDriver.isButtonAvailable( mDeviceIdx, i ) )
				mButtons[ i ] = new DIButton( mDeviceIdx, i );
		}

		for ( int i = 0; i < mDirectionals.length; ++i )
		{
			if ( DirectInputDriver.isDirectionalAvailable( mDeviceIdx, i ) )
				mDirectionals[ i ] = new DIDirectional( mDeviceIdx, i );
		}		
	}
	
    /** Devices may have a name.  */
	public String getName()
	{
		String name = DirectInputDriver.getName( mDeviceIdx );
		if ( null == name )
			return "Win32 DirectInput Joystick";
		return name;
	}

	
	/** Actual number of available buttons.  */
	public int getNumberOfButtons()
	{
		return DirectInputDriver.getNumberOfButtons( mDeviceIdx );
	}

	/** Actual number of available axes.  */
	public int getNumberOfAxes()
	{
		return DirectInputDriver.getNumberOfAxes( mDeviceIdx );
	}

	/** Actual number of available directional features.  */
	public int getNumberOfDirectionals()
	{
		return DirectInputDriver.getNumberOfDirectionals( mDeviceIdx );
	}
	
	/** Maximum number of buttons as an upper bound for index values.  */
	public int getMaxNumberOfButtons()
	{	
		return DirectInputDriver.getMaxNumberOfButtons();
	}

	/** Maximum number of axes as an upper bound for index values.  */
	public int getMaxNumberOfAxes()
	{
		return DirectInputDriver.getMaxNumberOfAxes();
	}
	
	/** Maximum number of available directional features.  */
	public int getMaxNumberOfDirectionals()
	{
		return DirectInputDriver.getMaxNumberOfDirectionals();
	}

    
	public Axis getAxis(int idx)
	{
		return mAxes[ idx ];
	}

	public Button getButton(int idx)
	{
		return mButtons[ idx ];
	}

	public Directional getDirectional(int idx)
	{
		return mDirectionals[ idx ];
	}
    
}



