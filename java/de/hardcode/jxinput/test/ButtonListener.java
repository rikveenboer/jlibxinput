//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 20. Februar 2002, 22:19
//**********************************************************************************************
package de.hardcode.jxinput.test;

import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.event.JXInputButtonEventListener;
import de.hardcode.jxinput.event.JXInputButtonEvent;
import de.hardcode.jxinput.Button;

/**
 * Sample button listener.
 *
 * @author Herkules
 */
public class ButtonListener implements JXInputButtonEventListener
{
	
	/**
	 * Creates a new instance of AxisListener.
	 */
	public ButtonListener( Button button )
	{
		JXInputEventManager.addListener( this, button );
	}
	
	
	public void changed( JXInputButtonEvent ev )
	{
		System.out.println( "Button " + ev.getButton().getName() + " changed : state=" + ev.getButton().getState() );
	}
	
}
