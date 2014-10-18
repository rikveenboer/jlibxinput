
/*
 *	@(#)HelloUniverse.java 1.15 02/02/07 14:48:36
 *
 * Copyright (c) 1996-2002 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed,licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package de.hardcode.jxinput.j3d.test;


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import de.hardcode.jxinput.j3d.DeviceConfiguration;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.j3d.IsActiveCondition;
import de.hardcode.jxinput.j3d.J3DInputDevice;
import de.hardcode.jxinput.j3d.IsActiveOnButtonCondition;


public class HelloUniverse extends Applet
{
	
	private SimpleUniverse u = null;
	TransformGroup objTrans;
	
	public BranchGroup createSceneGraph()
	{
		BranchGroup objRoot = new BranchGroup();
		objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRoot.addChild(objTrans);
		objTrans.addChild(new ColorCube(0.4));
		
//		Transform3D yAxis = new Transform3D();
//		Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
//											0, 0,
//											4000, 0, 0,
//											0, 0, 0);
//		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans, yAxis,
//																0.0f, (float) Math.PI*2.0f);
//		BoundingSphere bounds =	new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
//		rotator.setSchedulingBounds(bounds);
//		objTrans.addChild(rotator);
		return objRoot;
	}
	
	
	public HelloUniverse()
	{
		
	}
	
	public void init()
	{
		// These are the string arguments given to the VirtualInputDevice
		// constructor.  These are settable parameters.  Look in the
		// VirtualInputDevice constructor for a complete list.
		String[] args = new String[10];
		args[0] = "printvalues";
		args[1] = "true";
		args[2] = "yscreeninitloc";
		args[3] = "50";
		args[4] = null;
		
		
		// now create the HelloUniverse Canvas
		setLayout(new BorderLayout());
		GraphicsConfiguration config =	SimpleUniverse.getPreferredConfiguration();
		
		Canvas3D c = new Canvas3D(config);
		add("Center", c);
		
		// Create a simple scene and attach it to the virtual universe
		BranchGroup scene = createSceneGraph();
		u = new SimpleUniverse(c);
		
		//
		// Use the inputdevice
		//
		InputDevice device = createInputDevice();
			
		// Register the VirtualInputDevice with Java 3D
		u.getViewer().getPhysicalEnvironment().addInputDevice( device );
		
//		TransformGroup viewTrans = u.getViewingPlatform().getViewPlatformTransform();

		// Put the behavoir to teh object
		SensorBehavior s = new SensorBehavior( objTrans, device.getSensor(0) );
		s.setSchedulingBounds( new BoundingSphere( new Point3d(0.0,0.0,0.0), Float.MAX_VALUE ) );
		objTrans.addChild( s );
		
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);
	}
	
	public void destroy()
	{
		u.removeAllLocales();
	}
	

	/**
	 * Setup an input device.
	 */
	private InputDevice createInputDevice()
	{
		IsActiveCondition button1down = new IsActiveOnButtonCondition(JXInputManager.getJXInputDevice( 0 ).getButton( 0 ), true );
		IsActiveCondition button1up = new IsActiveOnButtonCondition(JXInputManager.getJXInputDevice( 0 ).getButton( 0 ), false );
		
		Axis xaxis = JXInputManager.getJXInputDevice( 0 ).getAxis( Axis.ID_X );
		Axis yaxis = JXInputManager.getJXInputDevice( 0 ).getAxis( Axis.ID_Y );
		
		DeviceConfiguration cnf = new DeviceConfiguration();
		
		//
		// Setup the configuration to use joysticks x/y for rotation is not button is pressed
		// and for translation if button1 is pressed.
		//
		cnf.setRotational( 
					DeviceConfiguration.AXIS_Y, 
					xaxis, 
					button1up, 
					IsActiveCondition.NEVER, 
					0.0, Math.PI 
					);

		cnf.setRotational( 
					DeviceConfiguration.AXIS_X, 
					yaxis, 
					button1up, 
					IsActiveCondition.NEVER, 
					0.0, Math.PI 
					);

		cnf.setTranslational( 
					DeviceConfiguration.AXIS_Z, 
					yaxis, 
					button1down, 
					IsActiveCondition.NEVER, 
					-5.0, 4.0 
					);
		cnf.setTranslational( 
					DeviceConfiguration.AXIS_X, 
					xaxis, 
					button1down, 
					IsActiveCondition.NEVER, 
					0.0, 4.0 
					);
		
		// We have the config, create the device...
		J3DInputDevice d = new J3DInputDevice( cnf );

		// The InputDevice must be initialized before registering it
		// with the PhysicalEnvironment object.
		d.initialize();

		return d;
	}
	
	
	public static void main(String[] args)
	{
		new MainFrame(new HelloUniverse(), 350, 350);
	}
}
