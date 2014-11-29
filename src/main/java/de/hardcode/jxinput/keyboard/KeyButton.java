//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 9. April 2002, 22:51
//**********************************************************************************************
package de.hardcode.jxinput.keyboard;

import de.hardcode.jxinput.Button;
import java.awt.event.KeyEvent;


/**
 * Associates a keycode with a Button and handles the current state of that button.
 *
 * @author  Herkules
 */
class KeyButton 
	implements Button
{
	private final int mKeyCode;
	private boolean mIsPressed;
	private boolean mHasChanged;
	
	/**
	 * Creates a new instance of KeyButton.
	 */
	public KeyButton( int keycode )
	{
		mKeyCode = keycode;
	}
	
	
	/**
	 * Return the keycode assigned with this button.
	 */
	public final int getKeyCode()
	{
		return mKeyCode;
	}

	final void setIsPressed( boolean flag )
	{
		mIsPressed = flag;
	}
	
	//*********************************************************************************************
	//
	// Implement Button
	//
	//*********************************************************************************************
	

	/**
	 * Features may have a name provided e.g. by the driver.
	 */
	public String getName()
	{
		return KeyEvent.getKeyText( mKeyCode );
	}
	
	/** 
	 * Tells the state of the button at last update.
	 */
	public boolean getState()
	{
		return mIsPressed;
	}
	
	/** 
	 * Retrieve the type of the button.
	 * Pushbutton will deliver <code>true==getState()</code> as long as they are pressed down.
	 * Togglebuttons will change their state once they are pressed and keep that state
	 * until they are pressed again.
	 * @return [ PUSHBUTTON | TOGGLEBUTTON ]
	 */
	public int getType()
	{
		return Button.PUSHBUTTON;
	}

	
	/** 
	 * Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	public boolean hasChanged()
	{
		return true;
	}
	
}
