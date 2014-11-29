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
import de.hardcode.jxinput.event.JXInputAxisEventListener;
import de.hardcode.jxinput.event.JXInputAxisEvent;
import de.hardcode.jxinput.Axis;

/**
 * Example listener to an axis.
 *
 * @author Herkules
 */
public class AxisListener 
	implements JXInputAxisEventListener
{
	
	/**
	 * Creates a new instance of AxisListener.
	 */
	public AxisListener( Axis axis )
	{
		JXInputEventManager.addListener( this, axis, 0.1 );
	}
	
	
	public void changed( JXInputAxisEvent ev )
	{
		System.out.println( "Axis " + ev.getAxis().getName() + " changed : value=" + ev.getAxis().getValue() + ", event causing delta=" + ev.getDelta() );
	}
	
}
