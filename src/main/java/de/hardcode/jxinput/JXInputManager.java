//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 29. Dezember 2001, 02:17
//**********************************************************************************************
package de.hardcode.jxinput;

//
// Import driver stuff
//
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.keyboard.JXKeyboardInputDevice;
import de.hardcode.jxinput.virtual.JXVirtualInputDevice;


import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Component;


/**
 * Manages the available instances of JXInputDevice.
 * It holds the one central update method which synchronizes with the physical device.
 * @author Herkules
 */
public class JXInputManager
{
	
	/** Remember when the last update took place. */
	private static			long		mTimeOfLastUpdate;

	/** Maintain a list of devices. */
    private final static	ArrayList	mDevices		= new ArrayList();
	
	/** Maintain a list of direct input devices. */
    private final static	ArrayList	mDIDevices		= new ArrayList();

	/** Maintain a list of virtual devices. */
    private final static	ArrayList	mVirtualDevices	= new ArrayList();

	/** Maintain a list of keyboard devices. */
    private final static	ArrayList	mKBDevices		= new ArrayList();
	
	/**
	 * Statically retrieve all DirectInputDevices available.
	 */
    static
    {
		reset();
	}

	
    /**
	 * @directed 
	 */
    /*#JXInputDevice lnkJXInputDevice;*/

	/** 
	 * Creates a new instance of JXInputManager.
	 * This is prohibited - it only has static members.
	 */
	private JXInputManager()	
	{
	}
	

    /**
     * Retrieve the number of available input devices.
     */
    public static int getNumberOfDevices()
    {
        return mDevices.size();
    }
    
	/**
	 * Delivers the JXInputDevice with the desired index.
	 * <p>
     * Take care that <code>idx < getNumberOfDevices()</code>!
     */
	public static JXInputDevice getJXInputDevice( int idx )
	{
		//
		// Be well-behaved even if idx is out of range.
		//
		if ( idx >= mDevices.size() )
			return null;
        return (JXInputDevice)mDevices.get( idx );   
    }

	
	/**
	 * Master reset for all devices and events.
	 * After calling reset(), better forget all devices created or retrieved.
	 * They are no longer valid. 
	 * Event listeners will no longer be called and should be discarded.
	 */
	synchronized public static void reset()
	{
		JXInputEventManager.reset();
		
		mDevices.clear();

		mVirtualDevices.clear();
		mDIDevices.clear();

		DirectInputDevice.reset();
		
        for ( int i = 0; i < DirectInputDevice.getNumberOfDevices(); ++i )
        {
			DirectInputDevice dev = new DirectInputDevice( i );
            mDevices.add( dev );
            mDIDevices.add( dev );
        }
		
		// I have to call updateFeatures one time here during initialization 
		// bc. I experienced difficulties otherwise while running with the 
		// J3D sensoring stuff!
//		updateFeatures();
 		DirectInputDevice.update();
		
		int n = mKBDevices.size();
		for ( int i = 0; i < n; ++i )
			((JXKeyboardInputDevice)mKBDevices.get( i )).shutdown();
		mKBDevices.clear();
	}
	

	/** 
	 * Update the (shared) state of all features in one step. 
	 * This method asks the actual device for a consistant state. 
	 * After calling this method, all features may have new values. 
	 * updateFeatures() is meant to be called e.g. once per frame in a gaming environment.
	 */
	public static void updateFeatures()
    {
        // Get timing
		long now = System.currentTimeMillis();
		long deltaT = now - mTimeOfLastUpdate;
		
		// Update available driver
  		DirectInputDevice.update();
        
		// 
		// Update the virtual devices.
		//
		Iterator vdevices = mVirtualDevices.iterator();
		while ( vdevices.hasNext() )
		{
			((JXVirtualInputDevice)vdevices.next()).update( deltaT );
		}
				
        // Remember time
		mTimeOfLastUpdate = now;
        
		// Fire all events.
        JXInputEventManager.trigger();
    }


    
	/** 
	 * Get time when last update occurred.
	 * @return tickervalue in milliseconds
	 */
	public static long getLastUpdateTime()
    {
		return mTimeOfLastUpdate;
	}
    
	
	/** 
	 * Create a new pseudo-device.
	 */
	public static JXKeyboardInputDevice createKeyboardDevice()
	{
		JXKeyboardInputDevice d = new JXKeyboardInputDevice();
		mDevices.add( d );
		mKBDevices.add( d );
		return d;
	}
	
	
	/** 
	 * Create a new pseudo-device listening to a Swing component.
	 * Make sure that the component also has the keyboard focus!!
	 */
	public static JXKeyboardInputDevice createKeyboardDevice( Component comp )
	{
		JXKeyboardInputDevice d = new JXKeyboardInputDevice( comp );
		mDevices.add( d );
		mKBDevices.add( d );
		return d;
	}

	
	/**
	 * Delete a keyboard device again e.g. when the corresponding 
	 * JComponent gets deleted.
	 */
	public static void deleteKeyboardDevice( JXKeyboardInputDevice dev )
	{		
		mDevices.remove( dev );
		mKBDevices.remove( dev );
		((JXKeyboardInputDevice)dev).shutdown();
	}


	/** 
	 * Create a new pseudo-device.
	 */
	public static JXVirtualInputDevice createVirtualDevice()
	{
		JXVirtualInputDevice d = new JXVirtualInputDevice();
		mDevices.add( d );
		mVirtualDevices.add( d );
		return d;
	}

	
	/**
	 * Delete a virtual device again. 
	 */
	public static void deleteVirtualDevice( JXVirtualInputDevice dev )
	{		
		mDevices.remove( dev );
		mVirtualDevices.remove( dev );
	}

}
