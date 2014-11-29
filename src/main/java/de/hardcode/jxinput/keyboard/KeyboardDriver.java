//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 9. April 2002, 22:43
//**********************************************************************************************
package de.hardcode.jxinput.keyboard;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.security.InvalidParameterException;



/**
 * Listen to a JComponent handle handle all associated button objects.
 * This is the main worker class for JXKeyboardInputDevice.
 * 
 * @author  Herkules
 */
class KeyboardDriver implements KeyListener
{
//	HashMap mKeysToObserveMap = new HashMap();
	
	int mNumberOfKeysObserved = 0;
	KeyButton [] mKeysObserved = new KeyButton [ 0x100 ];
	
	
	/**
	 * Creates a new instance of KeyboardDriver.
	 */
	public KeyboardDriver()
	{
	}

	
	/**
	 * How many buttons are registered?
	 */
	final int getNumberOfButtons()
	{
		return mNumberOfKeysObserved;
//		return mKeysToObserveMap.size();
	}
	
	
	/** 
	 * Place a new button under my observation.
	 */
	final boolean registerKeyButton( KeyButton b )
	{
		final int keycode = b.getKeyCode();

		if ( 0 > keycode  ||  0x100 < keycode )
			throw new InvalidKeyCodeException();
		
		if ( null == mKeysObserved[ keycode ] )
		{
			mKeysObserved[ keycode ] = b;
			mNumberOfKeysObserved++;
			return true;
		}
		else
		{
			return false;
		}
		
//		Integer code = new Integer( b.getKeyCode() );
//		if ( ! mKeysToObserveMap.containsKey( code ) )
//		{
//			mKeysToObserveMap.put( code, b );
//			return true;
//		}
//		else
//		{
//			return false;
//		}
	}
	
	final void unregisterKeyButton( KeyButton b )
	{
		final int keycode = b.getKeyCode();
		
		if ( 0 > keycode  ||  0x100 < keycode )
			throw new InvalidKeyCodeException();

		if ( null != mKeysObserved[ b.getKeyCode() ] )
		{
			mKeysObserved[ keycode ] = null;
			mNumberOfKeysObserved--;
		}
		
//		Integer code = new Integer( b.getKeyCode() );
//		mKeysToObserveMap.remove( code );
	}
	
	
	/**
	 * Retrieve the button from its keycode.
	 */
	final KeyButton getButton( int keycode )
	{
		if ( 0 > keycode  ||  0x100 < keycode )
			throw new InvalidKeyCodeException();

		return mKeysObserved[ keycode ];

//		Integer code = new Integer( keycode );
//		return (KeyButton)mKeysToObserveMap.get( code );
	}
	
	
	//*********************************************************************************************
	//
	// Implement KeyListener
	//
	//*********************************************************************************************
	
	public void keyPressed( KeyEvent keyEvent )
	{
		KeyButton b = getButton( keyEvent.getKeyCode() );
		if ( null != b )
			b.setIsPressed( true );
	}
	
	public void keyReleased( KeyEvent keyEvent )
	{
		KeyButton b = getButton( keyEvent.getKeyCode() );
		if ( null != b )
			b.setIsPressed( false );
	}
	
	public void keyTyped( KeyEvent keyEvent )
	{
		// Intentionally empty.
	}
	
}
