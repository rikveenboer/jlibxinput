//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 11. April 2002, 23:40
//**********************************************************************************************
package de.hardcode.jxinput.virtual;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import java.security.InvalidParameterException;

/**
 *
 * @author  Jörg Plewe
 */
public class VirtualAxis
	implements Axis
{
	
	private int mType = Axis.TRANSLATION;
	private final int mID;
	private String mName = "VirtualAxis";
	private double mCurrentValue = 0;
	
	private Button mButtonIncrease = null;
	private Button mButtonDecrease = null;
	private double mSpeed = 1.0 / 500.0;
	private double mSpringSpeed = 1.0 / 500.0;
	
	/**
	 * Creates a new instance of VirtualAxis.
	 */
	public VirtualAxis( int id )
	{
		mID = id;
	}
	
	
    /**
     * Set the type of this axis to be either <code>Axis.ROTATION</code>,
     * <code>Axis.TRANSLATION</code> or <code>Axis.SLIDER</code>.
     */
    public void setType( int type )
    {
		if (    Axis.ROTATION != type
            &&  Axis.TRANSLATION != type 
            &&  Axis.SLIDER != type 
            )
			throw new InvalidParameterException( "Invalid type for axis!" );
        
        mType = type;
    }
    
	/**
	 * Update features under my control.
	 */
	final void update( long deltaT )
	{
		double change = mSpeed * deltaT;
		double springchange = mSpringSpeed * deltaT;
		boolean doincrease = ( null != mButtonIncrease  &&  mButtonIncrease.getState() );
		boolean dodecrease = ( null != mButtonDecrease  &&  mButtonDecrease.getState() );
		boolean iscontrolled =	doincrease || dodecrease;

		double controlledchange = 0.0;
		if ( doincrease )
			controlledchange += change;
		if ( dodecrease )
			controlledchange -= change;
		
		mCurrentValue += controlledchange;
		
		if ( mCurrentValue > 0.0  &&  ! doincrease )
		{
			springchange = Math.min( mCurrentValue, springchange );
			mCurrentValue -= springchange;
		}
		if ( mCurrentValue < 0.0  &&  ! dodecrease )
		{
			springchange = Math.min( -mCurrentValue, springchange );
			mCurrentValue += springchange;
		}
		
		// 
		// Hold value within range
		//
		if ( mCurrentValue > 1.0 )
			mCurrentValue = 1.0;
		double lowerlimit = Axis.SLIDER == mType ?  0.0 : -1.0;
		if ( mCurrentValue < lowerlimit )
			mCurrentValue = lowerlimit;
	}

	
    /** 
     * Set the button to increase the axis for a single button axis.
     */
	public final void setIncreaseButton( Button b )
	{
		if ( null == b )
			throw new InvalidParameterException( "Button may not be null!" );
		
		mButtonIncrease = b;
	}

    
    /** 
     * Set the buttons to increase and descrease the axis.
     */
	public final void setButtons( Button increase, Button decrease )
	{
		if ( null == increase  ||  null == decrease )
			throw new InvalidParameterException( "Buttons may not be null!" );
			
		mButtonIncrease = increase;
		mButtonDecrease = decrease;
	}

	
	public final void setSpeed( double speed )
	{
		mSpeed = speed;
	}

	public final void setSpringSpeed( double springspeed )
	{
		mSpringSpeed = springspeed;
	}

	
	public final void setTimeFor0To1( int ms )
	{
		if ( 0 >= ms )
			mSpeed = 0.0;
		else
			mSpeed = 1.0/ ms;
	}
	public final void setTimeFor1To0( int ms )
	{
		if ( 0 >= ms )
			mSpringSpeed = 0.0;
		else
			mSpringSpeed = 1.0/ ms;
	}
	
	
	public final void setName( String name )
	{
		mName = name;
	}
	
	//*********************************************************************************************
	//
	// Implement Axis
	//
	//*********************************************************************************************
	
	/**
	 * Features may have a name provided e.g. by the driver.
	 */
	public String getName()
	{
		return mName;
	}
	
	/** 
	 * Inform about the resolution of the axis.
	 *
	 * @return resolution, e.g. 2^-16
	 */
	public double getResolution()
	{
		return 1.0/65536.0;
	}
	
	
	/** 
	 * Retrieve the type of the axis.
	 * @return [ TRANSLATION | ROTATION | SLIDER ]
	 */
	public int getType()
	{
		return mType;
	}
	
	/** Returns the current value of the axis.
	 * The range of the result depends on the axis type.
	 *
	 * @return value [-1.0,1.0] or [0.0,1.0]
	 */
	public double getValue()
	{
		return mCurrentValue;
	}
	
	/** Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	public boolean hasChanged()
	{
		return true;
	}
	
}
