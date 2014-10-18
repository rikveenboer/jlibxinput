//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 22. Februar 2002, 13:21
//**********************************************************************************************
package de.hardcode.jxinput.j3d;

import javax.media.j3d.InputDevice;
import javax.media.j3d.Sensor;
import javax.media.j3d.SensorRead;
import javax.vecmath.Vector3d;
import javax.media.j3d.Transform3D;
import de.hardcode.jxinput.JXInputManager;


/**
 * Implementation of Java3D's InputDevice
 *
 * @author Herkules
 */
public class J3DInputDevice 
	implements InputDevice
{
    private Vector3d mPosition = new Vector3d();
    private Transform3D mNewTransform = new Transform3D();

	private Transform3D mRotTransX = new Transform3D();
    private Transform3D mRotTransY = new Transform3D();
    private Transform3D mRotTransZ = new Transform3D();

    private Vector3d mInitPos = new Vector3d( 0.0, 0.0, 0.0 );

	private Sensor mSensor = new Sensor( this );
	private SensorRead mSensorRead = new SensorRead();
	
	private DeviceConfiguration mConfig;
	
	/**
	 * Creates a new instance of J3DInputDevice.
	 */
	public J3DInputDevice( DeviceConfiguration config )
	{
		mConfig = config;
		setNominalPositionAndOrientation();
	}
	
	
	public void close()
	{
		// Intentionally empty
	}
	
	
	/**
	 * Retrieve processing mode.
	 * For this device, it always is NON_BLOCKING.
	 */
	public int getProcessingMode()
	{
		return InputDevice.NON_BLOCKING;
	}
	
	
	/**
	 * Don't care for the index, I only support one sensor.
	 * And I deliver that.
	 */
	public Sensor getSensor( int param )
	{
		return mSensor;
	}
	

	/**
	 * Tell the world about the only one sensor I support.
	 */
	public int getSensorCount()
	{
		return 1;
	}
	

	/**
	 * Well - initialize!
	 * Nothing to do here.
	 */
	public boolean initialize()
	{
		return true;
	}
	
	
	/**
	 * The main update method.
	 */
	public void pollAndProcessInput()
	{
		JXInputManager.updateFeatures();		
		
        mSensorRead.setTime( JXInputManager.getLastUpdateTime() );

	    mRotTransX.rotX( mConfig.getRotational( DeviceConfiguration.AXIS_X ) );
        mRotTransY.rotY( mConfig.getRotational( DeviceConfiguration.AXIS_Y ) );
        mRotTransZ.rotZ( mConfig.getRotational( DeviceConfiguration.AXIS_Z ) );

        mPosition.set( 
					mConfig.getTranslational( DeviceConfiguration.AXIS_X ),
					mConfig.getTranslational( DeviceConfiguration.AXIS_Y ),
					mConfig.getTranslational( DeviceConfiguration.AXIS_Z )
					);

		mNewTransform.set( mPosition );
		
        mNewTransform.mul( mRotTransX );  
        mNewTransform.mul( mRotTransY );
        mNewTransform.mul( mRotTransZ );

		mSensorRead.set( mNewTransform );
        mSensor.setNextSensorRead( mSensorRead );
	}
	
	
	/**
	 * Not called by current j3d implementation.
	 */
	public void processStreamInput()
	{
		// Intentionally empty
	}
	
	
	/**
	 * Reset state.
	 */
	public void setNominalPositionAndOrientation()
	{
        mSensorRead.setTime( JXInputManager.getLastUpdateTime() );
 
        mRotTransX.rotX( 0.0 );
        mRotTransY.rotY( 0.0 );
        mRotTransZ.rotZ( 0.0 );
 
        mPosition.set( mInitPos );
        
        mNewTransform.set( mPosition );
 
        mNewTransform.mul( mRotTransX );  
        mNewTransform.mul( mRotTransY );
        mNewTransform.mul( mRotTransZ );
 
        mSensorRead.set( mNewTransform );
        mSensor.setNextSensorRead( mSensorRead );
		
	}
	
	
	/**
	 * Set the processing mode.
	 * Only NON_BLOCKING is allowed!
	 */
	public void setProcessingMode(int param)
	{
        if ( param != InputDevice.NON_BLOCKING )
			throw new IllegalArgumentException("Processing mode must be NON_BLOCKING");
			
	}
	
}
