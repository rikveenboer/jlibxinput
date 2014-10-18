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
import de.hardcode.jxinput.Directional;

/**
 * Represents an event coming from an axis.
 * @author Joerg Plewe
 */
public class JXInputDirectionalEvent
{
    private final Directional mDirectional;
	double mValueDelta;
	int mDirectionDelta;
	
	/** 
     * Creates a new instance of JXInputEvent.
	 */
	JXInputDirectionalEvent( Directional directional )
	{
		mDirectional = directional;
	}
    
    /**
     * The feature that caused the event.
     */
    public final Directional getDirectional()
    {
        return mDirectional;        
    }
    
    /**
     * Return the change in value that caused the event.
     */
    public double getValueDelta()
    {
        return mValueDelta;
    }

	/**
     * Return the change in direction that caused the event.
     */
    public int getDirectionDelta()
    {
        return mDirectionDelta;
    }
    
}
