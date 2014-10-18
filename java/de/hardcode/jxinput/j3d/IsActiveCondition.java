//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 25. Februar 2002, 22:41
//**********************************************************************************************
package de.hardcode.jxinput.j3d;

/**
 * 
 * @author Herkules
 */
public interface IsActiveCondition
{
	public final static IsActiveCondition ALWAYS = IsAlwaysActiveCondition.ALWAYS;
	public final static IsActiveCondition NEVER = IsAlwaysActiveCondition.NEVER;

	/**
	 * Tell wether a certain thing is active.
	 */
	boolean isActive();
}

