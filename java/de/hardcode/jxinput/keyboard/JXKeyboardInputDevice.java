//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 9. April 2002, 22:40
//**********************************************************************************************
package de.hardcode.jxinput.keyboard;


import de.hardcode.jxinput.*;
import java.awt.Component;


/**
 * Virtual input device treating a AWT keyboard as a source for Buttons.
 *
 * @author  Herkules
 */
public class JXKeyboardInputDevice
	implements JXInputDevice
{
	private static final String DEVICENAME = "Swing Keyboard";

	/** The driver doing all the real work. */
	private final KeyboardDriver mDriver = new KeyboardDriver();

	/** The Component I am listening to. */
	private Component mComponent = null;
	
	/** Hold the biggest keycode for which a button has been created. */
	private int mMaxIdxCreated = 0;
	
	
	/**
	 * Creates a new instance of JXKeyboardInputDevice.
	 */
	public JXKeyboardInputDevice()
	{
	}

	
	/**
	 * Creates a new instance of JXKeyboardInputDevice 
	 * immediately listening to a JComponent.
	 */
	public JXKeyboardInputDevice( Component comp )
	{
		listenTo( comp );
	}
	
	/** 
	 * Makes this device listen to a certain JComponent.
	 */
	public final void listenTo( Component comp )
	{
		shutdown();
		mComponent = comp;
		mComponent.addKeyListener( mDriver );
	}
	
	/**
	 * Shut down. No longer listen to my JComponent.
	 */
	public final void shutdown()
	{
		if ( null != mComponent )
			mComponent.removeKeyListener( mDriver );
	}
	
	
	/**
	 * Create a button object for a certain keycode.
	 */
	public Button createButton( int keycode )
	{	
		if ( 0 > keycode  ||  0x100 < keycode )
			throw new InvalidKeyCodeException();

		KeyButton b;
		if ( null == (b = mDriver.getButton( keycode ) ) )
		{
			b = new KeyButton( keycode );
			mDriver.registerKeyButton( b );
			if ( keycode > mMaxIdxCreated )
				mMaxIdxCreated = keycode;
		}
		return b;
	}
	

	public void removeButton( Button b )
	{
		mDriver.unregisterKeyButton( (KeyButton) b ); 
	}
	
	
	
	//*********************************************************************************************
	//
	// Implement JXInputDevice
	//
	//*********************************************************************************************
	
	public Axis getAxis(int idx)
	{
		// No axes on keyboard.
		return null;
	}
	
	
	public Button getButton(int idx)
	{
		// idx is interpreted as the keycode
		return mDriver.getButton( idx );
	}
	
	public Directional getDirectional(int idx)
	{
		// No directionals on keyboard.
		return null;
	}
	
	/** Maximum number of axes as an upper bound for index values.  */
	public int getMaxNumberOfAxes()
	{
		// No axes on keyboard.
		return 0;
	}
	
	/** Maximum number of buttons as an upper bound for index values.  */
	public int getMaxNumberOfButtons()
	{
		// Return biggest keycode (inclusive).
		return mMaxIdxCreated + 1;
	}
	
	/** Maximum number of directional features as an upper bound for index values.  */
	public int getMaxNumberOfDirectionals()
	{
		// No directionals on keyboard.
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
		return 0;
	}
	
	/** Actual number of available buttons.  */
	public int getNumberOfButtons()
	{
		return mDriver.getNumberOfButtons();
	}
	
	/** Actual number of available directional features.  */
	public int getNumberOfDirectionals()
	{
		// No directionals on keyboard.
		return 0;
	}
	
}
