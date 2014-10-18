//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 19. Dezember 2001, 22:44
//**********************************************************************************************
package de.hardcode.jxinput.directinput;

import java.lang.reflect.Array;

/**
 * DirectInputDriver: the connection to the Win32 joystick.
 * There is only one allowed, so the layout of this class is merely static.
 * 
 * History:
 * 
 * Changes since 0.1beta:
 *  - support of multiple devices addressed by the <code>dev</code> index
 * 
 *
 * @author Herkules
 * @version 0.2beta
 */
class DirectInputDriver
{
	private final static String NATIVE_LIB_NAME = "jxinput";
	
	/** Remember wether nativeinit() succeeded. */
	static boolean sIsOperational = false;
	
	//
	// Static arrays to hold the values.
	//
	private static double	[][] sAxisValues;
	private static boolean	[][] sButtonStates;
	private static int		[][] sDirectionalValues;

	/**
	 * Perform the static initialization.
	 */
	static 
	{
		try
		{
			// Load the native lib.
			System.loadLibrary( NATIVE_LIB_NAME );
			
            init();
		}
		catch( SecurityException e )
		{	
			Log.logger.warning("Native library jxinput not loaded due to a SecurityException.");
		}
		catch( UnsatisfiedLinkError e )
		{
			Log.logger.info("Native library jxinput not loaded due to an UnsatisfiedLinkError.");
		}
	}		
	
	
    private final static void init()
    {
        sIsOperational		= false;
        //
        // Initialize it.
        //
        if ( nativeinit() )
        {
            int devs			= getNumberOfDevices();
            sAxisValues			= new double	[ devs ][ DirectInputDriver.getMaxNumberOfAxes()			];
            sButtonStates		= new boolean	[ devs ][ DirectInputDriver.getMaxNumberOfButtons()			];
            sDirectionalValues	= new int		[ devs ][ DirectInputDriver.getMaxNumberOfDirectionals()	];

            // Bind the native lib to my variables.
            bind();

            // Remember I am fine.
            sIsOperational		= true;
        }
        
    }
    
    
	/** 
	 * Static ctor of DirectInputDriver.
	 * No object will be created due to the static layout.
	 */
	private DirectInputDriver()
	{
	}
		
	// Administration
	private static native   boolean     nativeinit();
	private static native   void        nativeexit();
	private static native   void        bind();

    static native           int         getNumberOfDevices();
    
	// Configuration
	static native           String      getName( int dev );
	static native           int         getNumberOfAxes( int dev );
	static native           int         getNumberOfButtons( int dev );
	static native           int         getNumberOfDirectionals( int dev );
	static native           int         getMaxNumberOfAxes();
	static native           int			getMaxNumberOfButtons();
	static native			int			getMaxNumberOfDirectionals();
		
	static native			boolean		isAxisAvailable( int dev, int idx );
	static native			String		getAxisName( int dev, int idx );
	static native			int			getAxisType( int dev, int idx );

	static native			boolean		isButtonAvailable( int dev, int idx );
	static native			String		getButtonName( int dev, int idx );
	static native			int			getButtonType( int dev, int idx );

	static native			boolean		isDirectionalAvailable( int dev, int idx );
	static native			String		getDirectionalName( int dev, int idx );
	
	// Operation
	static native			void		nativeupdate();

	
	public static boolean isAvailable()
	{
		return sIsOperational;
	}
	
	
    /**
     * Shutdown the device and free all Win32 resources.
     * It is not a good idea to access any joystick features after
     * <code>shutdown()</code>.
     */ 
	static void shutdown()
	{
		nativeexit();
        sAxisValues = null;
        sButtonStates = null;
        sDirectionalValues = null;
    }

    
    /**
     * Reset the device and free all Win32 resources.
     */ 
	static void reset()
	{
		shutdown();
        init();
    }
	
	static double getAxisValue( int dev, int idx )
	{
		return sAxisValues[ dev ][ idx ];
	}
	
	static boolean getButtonState( int dev, int idx )
	{
		return sButtonStates[ dev ][ idx ];
	}

	static int getDirection( int dev, int idx )
	{
		return sDirectionalValues[ dev ][ idx ];
	}

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) 
	{

		if ( ! sIsOperational )
			return;
		
		for( int i = 0; i < 5000; ++i )
			nativeupdate();

		shutdown();
    }	

}
