//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 25. Februar 2002, 22:43
//**********************************************************************************************
package de.hardcode.jxinput.j3d;

/**
 *
 * @author Herkules
 */
final class IsAlwaysActiveCondition implements IsActiveCondition
{
	private final boolean mIsActive;
	
	public final static IsActiveCondition ALWAYS = new IsAlwaysActiveCondition( true );
	public final static IsActiveCondition NEVER = new IsAlwaysActiveCondition( false );
	
	/**
	 * Creates a new instance of IsAlwayActiveCondition.
	 */
	private IsAlwaysActiveCondition(boolean isactive)
	{
		mIsActive = isactive;
	}
	
	/** 
	 * Tell wether a certain thing is active.
	 */
	public boolean isActive()
	{
		return mIsActive;
	}
	
}
