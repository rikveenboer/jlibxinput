//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 31. Januar 2002, 23:42
//**********************************************************************************************
package de.hardcode.jxinput.event;

import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.JXInputDevice;

import java.util.ArrayList;
import de.hardcode.jxinput.Axis;
import java.util.Iterator;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.Directional;

/**
 * Handles all events and listeners.
 * <code>JXInputEventManager</code> is layed out a static singleton.
 * @author Herkules
 */
public class JXInputEventManager
{
	
    private final static ArrayList mAxisEventListeners			= new ArrayList();
    private final static ArrayList mButtonEventListeners		= new ArrayList();
    private final static ArrayList mDirectionalEventListeners	= new ArrayList();

	private static autotrigger mAutoTrigger = null;
	
    /**
     * Inner class combining a listener with its scheduling parameters.
     */
    private static class axislistener
    {
        final JXInputAxisEventListener mListener;
        final double mTreshold;
        final JXInputAxisEvent mEvent;
		double mLastValueFired = 0.0;

		axislistener( JXInputAxisEventListener l, Axis axis, double treshold )
        {
            mListener = l;
            mTreshold = treshold;
			mEvent = new JXInputAxisEvent( axis );
        }
		
		final void checkTrigger()
		{
			double curval = mEvent.getAxis().getValue();
			double delta = curval - mLastValueFired;

			if ( Math.abs( delta ) >= mTreshold )
			{
				mLastValueFired = curval;
				mEvent.mDelta = delta;
				mListener.changed( mEvent );
			}
		}	
	}
	
    /**
     * Inner class combining a listener with its scheduling parameters.
     */
    private static class buttonlistener
    {
        final JXInputButtonEventListener mListener;
        final JXInputButtonEvent mEvent;
		boolean mLastValueFired = false;

		buttonlistener( JXInputButtonEventListener l, Button button )
        {
            mListener = l;
			mEvent = new JXInputButtonEvent( button );
        }
		
		final void checkTrigger()
		{
			boolean curstate = mEvent.getButton().getState();
			if ( curstate != mLastValueFired )
			{
				mLastValueFired = curstate;
				mListener.changed( mEvent );
			}
		}	
	}
	

    private static class directionallistener
    {
        final JXInputDirectionalEventListener mListener;
        final double mValueTreshold;
        final JXInputDirectionalEvent mEvent;
		double mLastValueFired = 0.0;
		boolean mLastCenteredFired = true;
		int mLastDirectionFired = 0;
		
		directionallistener( JXInputDirectionalEventListener l, Directional directional, double valuetreshold )
        {
            mListener = l;
            mValueTreshold = valuetreshold;
			mEvent = new JXInputDirectionalEvent( directional );
        }
		
		final void checkTrigger()
		{
			double	curval				= mEvent.getDirectional().getValue();
			int		curdir				= mEvent.getDirectional().getDirection();
			boolean curctr				= mEvent.getDirectional().isCentered();
			
			double	delta				= curval - mLastValueFired;
			int		dirdelta			= curdir - mLastDirectionFired;
			boolean centeredchanged		= mLastCenteredFired != curctr;

			if ( Math.abs( delta ) >= mValueTreshold 
				|| Math.abs( dirdelta ) > 0 
				|| centeredchanged )
			{
				mLastValueFired = curval;
				mLastDirectionFired = curdir;
				mLastCenteredFired = curctr;
				
				mEvent.mValueDelta = delta;
				mEvent.mDirectionDelta = dirdelta;
				mListener.changed( mEvent );
			}
		}	
	}
	
	/** 
     * Creates a new instance of JXInputEventManager.
	 */
	private JXInputEventManager()
	{
	}
    
	
	/**
	 * Remove all listeners at once.
	 */
	public static void reset()
	{
		mAxisEventListeners.clear();
		mButtonEventListeners.clear();
		mDirectionalEventListeners.clear();
	}
	
	
    /**
     * Query devices and fire all occuring events.
     * <code>trigger()</code> is thought to be called by <code>JXInputManager#updateFeatures()</code>.
     */
    public static void trigger()
    {
		int n = mAxisEventListeners.size();
		for ( int i = 0; i < n; i++ )
		{
			axislistener l = (axislistener)mAxisEventListeners.get( i );
			l.checkTrigger();
		}

		n = mButtonEventListeners.size();
		for ( int i = 0; i < n; i++ )
		{
			buttonlistener l = (buttonlistener)mButtonEventListeners.get( i );
			l.checkTrigger();
		}

		n = mDirectionalEventListeners.size();
		for ( int i = 0; i < n; i++ )
		{
			directionallistener l = (directionallistener)mDirectionalEventListeners.get( i );
			l.checkTrigger();
		}
	}
	
	
	private final static class autotrigger extends Thread
	{
		boolean mFinish = false;
		final int mDelay;
		
		autotrigger( int delay )
		{
			mDelay = delay;
		}
		
		public void run()
		{
			while ( ! mFinish )
			{
				try
				{
					Thread.sleep( mDelay );
					JXInputManager.updateFeatures();
				}
				catch ( InterruptedException ex )
				{	
				}
			}
		}
	}
	
	
    /**
     * Set the intervall in ms that is used to check for new values of the features.
     * Set it to <= 0 to prohibit automatic triggering. Events will then only be fired
     * when somebody invokes <code>JXInputManager#updateFeatures()</code>.
     */
    public static void setTriggerIntervall( int ms )
    {
		//
		// Kill current thread, if any
		// 
		if ( null != mAutoTrigger )
		{
			mAutoTrigger.mFinish = true;
			try
			{
				mAutoTrigger.join();
			}
			catch ( InterruptedException ex )
			{	
			}
		}
	
		mAutoTrigger = null;
		
		if ( ms > 0 )
		{
			mAutoTrigger = new autotrigger( ms );
			mAutoTrigger.start();
		}

	}
    
    
    

    public static void addListener( JXInputAxisEventListener l, Axis axis, double treshold )
    {
        mAxisEventListeners.add( new JXInputEventManager.axislistener( l, axis, treshold ) );
    }

	public static void addListener( JXInputAxisEventListener l, Axis axis )
    {
        mAxisEventListeners.add( new JXInputEventManager.axislistener( l, axis, axis.getResolution() ) );
    }

	public static void removeListener( JXInputAxisEventListener l )
	{
		mAxisEventListeners.remove( l );
	}
	
	
	public static void addListener( JXInputButtonEventListener l, Button button )
    {
        mButtonEventListeners.add( new JXInputEventManager.buttonlistener( l, button ) );
    }
	
	public static void removeListener( JXInputButtonEventListener l )
	{
		mButtonEventListeners.remove( l );
	}

	public static void addListener( JXInputDirectionalEventListener l, Directional directional, double valuetreshold )
    {
        mDirectionalEventListeners.add( new JXInputEventManager.directionallistener( l, directional, valuetreshold ) );
    }
		
	public static void addListener( JXInputDirectionalEventListener l, Directional directional )
    {
        mDirectionalEventListeners.add( new JXInputEventManager.directionallistener( l, directional, directional.getResolution() ) );
    }

	public static void removeListener( JXInputDirectionalEventListener l )
	{
		mDirectionalEventListeners.remove( l );
	}

}
