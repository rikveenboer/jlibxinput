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
import de.hardcode.jxinput.Axis;

/**
 * Represents an event coming from an axis.
 * @author Joerg Plewe
 */
public class JXInputAxisEvent
{
    private final Axis mAxis;
	double mDelta;
	
	/** 
     * Creates a new instance of JXInputEvent.
	 */
	JXInputAxisEvent( Axis axis )
	{
        mAxis = axis;
	}
    
    /**
     * The feature that caused the event.
     */
    public final Axis getAxis()
    {
        return mAxis;        
    }
    
    
    /**
     * Return the change in value that caused the event.
     */
    public double getDelta()
    {
        return mDelta;
    }
    
}
