//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 9. April 2002, 22:40
//**********************************************************************************************
package de.hardcode.jxinput.virtual;


import de.hardcode.jxinput.*;


/**
 * Virtual input device.
 *
 * @author  Herkules
 */
public class JXVirtualInputDevice implements JXInputDevice
{
	private static final String DEVICENAME = "Virtual Device";

	/** The driver doing all the real work. */
	private final VirtualDriver mDriver = new VirtualDriver();
		
	
	/**
	 * Creates a new instance of JXKeyboardInputDevice.
	 */
	public JXVirtualInputDevice()
	{
	}

	
	/** 
	 * The virtual input device needs to be updated regularly.
	 */
	public final void update( long deltaT )
	{
		//
		// Delegate the update call to the driver.
		//
		mDriver.update( deltaT );
	}
	
	/**
	 * Create a virtual axis object with a certain ID, e.g. Axis.ID_X.
	 */
	public VirtualAxis createAxis( int id )
	{	
		VirtualAxis a;
		a = new VirtualAxis( id );
		mDriver.registerVirtualAxis( id, a );
		return a;
	}
	

	public void removeAxis( VirtualAxis a )
	{
		mDriver.unregisterVirtualAxis( a ); 
	}
	
	
	
	//*********************************************************************************************
	//
	// Implement JXInputDevice
	//
	//*********************************************************************************************
	
	public Axis getAxis(int idx)
	{
		return mDriver.getAxis( idx );
	}
	
	
	public Button getButton(int idx)
	{
		// No virtual buttons.
		return null;
	}
	
	
	public Directional getDirectional(int idx)
	{
		// No virtual directionals.
		return null;
	}
	
	/** Maximum number of axes as an upper bound for index values.  */
	public int getMaxNumberOfAxes()
	{
		return Axis.NUMBER_OF_ID;
	}
	
	/** Maximum number of buttons as an upper bound for index values.  */
	public int getMaxNumberOfButtons()
	{
		// No virtual buttons.
		return 0;
	}
	
	/** Maximum number of directional features as an upper bound for index values.  */
	public int getMaxNumberOfDirectionals()
	{
		// No virtual directionals.
		return 0;
	}
	
	/**
	 * Devices may have a name.
	 * This name might be provided by a system dependant driver.
	 */
	public String getName()
	{
		return DEVICENAME;
	}
	
	/** Actual number of available axes.  */
	public int getNumberOfAxes()
	{
		// No axes on keyboard.
		return mDriver.getNumberOfAxes();
	}
	
	/** Actual number of available buttons.  */
	public int getNumberOfButtons()
	{
		return 0;
	}
	
	/** Actual number of available directional features.  */
	public int getNumberOfDirectionals()
	{
		// No directionals on keyboard.
		return 0;
	}
	
}
