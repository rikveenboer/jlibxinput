//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 31. Januar 2002, 23:33
//**********************************************************************************************
package de.hardcode.jxinput.event;

import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.Button;

/**
 * Represents event coming from a button.
 * @author Joerg Plewe
 */
public class JXInputButtonEvent
{
    final Button mButton;
	
	/** 
     * Creates a new instance of JXInputEvent.
	 */
	JXInputButtonEvent( Button button )
	{
		mButton = button;
	}
    
    /**
     * The feature that caused the event.
     */
    public final Button getButton()
    {
        return mButton;        
    }
    
}
