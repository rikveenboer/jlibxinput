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
import de.hardcode.jxinput.event.JXInputDirectionalEventListener;
import de.hardcode.jxinput.event.JXInputDirectionalEvent;
import de.hardcode.jxinput.Directional;

/**
 * Sample directional listener.
 *
 * @author Herkules
 */
public class DirectionalListener implements JXInputDirectionalEventListener
{
	/**
	 * Creates a new instance of AxisListener.
	 */
	public DirectionalListener( Directional directional )
	{
		JXInputEventManager.addListener( this, directional, 1.0 );
	}
	
	
	public void changed( JXInputDirectionalEvent ev )
	{
		System.out.println( "Directional " + ev.getDirectional().getName() + " changed : direction=" + ev.getDirectional().getDirection() + ", value=" + ev.getDirectional().getValue() + ", event causing delta=" + ev.getDirectionDelta() );
	}
	
}
