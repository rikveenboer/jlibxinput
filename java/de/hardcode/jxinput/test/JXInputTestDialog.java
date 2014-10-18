//**********************************************************************************************
// Dipl. Phys. Joerg Plewe, HARDCODE Development
// Created on 27. Dezember 2001, 01:15
//**********************************************************************************************

package de.hardcode.jxinput.test;

import de.hardcode.jxinput.*;
import de.hardcode.jxinput.event.*;
import de.hardcode.jxinput.keyboard.JXKeyboardInputDevice;
import de.hardcode.jxinput.virtual.JXVirtualInputDevice;
import de.hardcode.jxinput.virtual.VirtualAxis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/**
 * Test dialog showing some features of JXInput.
 * @author Herkules
 */
public class JXInputTestDialog extends javax.swing.JDialog 
	implements ActionListener
{
	
	private JXKeyboardInputDevice   mKeyboardDevice = null;
	private JXVirtualInputDevice    mVirtualDevice  = null;
	
	Button mButtonUp;
	Button mButtonDown;
	Button mButtonLeft;
	Button mButtonRight;
	Button mButtonFire;
	Button mButtonSpace;
	
	/** Creates new form JXInputTestDialog */
	public JXInputTestDialog(java.awt.Frame parent, boolean modal)
	{
		super(parent, modal);
		initComponents();
		configureKeyboardInputDevice();
		configureVirtualInputDevice();
		initDevicePanels();
		pack();
	
		// Request the focus so that the keyboarddevice can work
		mMainPanel.requestFocus();

		new Timer( 50, this ).start();
		
		// Uncomment this line as an alternative to the Timer above.
		// Don't use both!!
		//JXInputEventManager.setTriggerIntervall( 50 );
	}
	
	
    /**
     * Implement ActionListener#actionPerformed().
	 * This is called by the Timer.
     */
	public void actionPerformed( ActionEvent e )
	{
        JXInputManager.updateFeatures();
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					for ( int i = 0; i < mDevicesTabbedPane.getComponentCount(); ++i )
					{
						((JXInputDevicePanel)mDevicesTabbedPane.getComponent( i )).update();
					}
				}
			}
		);
	}

	
	/** 
	 * Configure a test JXKeyboardInputdevice.
	 */
	void configureKeyboardInputDevice()
	{
		mKeyboardDevice = JXInputManager.createKeyboardDevice();
		
		mKeyboardDevice.createButton( KeyEvent.VK_ESCAPE );

		mKeyboardDevice.createButton( KeyEvent.VK_F1 );
		mKeyboardDevice.createButton( KeyEvent.VK_F2 );
		mKeyboardDevice.createButton( KeyEvent.VK_F3 );
		mKeyboardDevice.createButton( KeyEvent.VK_F4 );
		
		mKeyboardDevice.createButton( KeyEvent.VK_LEFT );
		mKeyboardDevice.createButton( KeyEvent.VK_RIGHT );
		mKeyboardDevice.createButton( KeyEvent.VK_UP );
		mKeyboardDevice.createButton( KeyEvent.VK_DOWN );

		mKeyboardDevice.createButton( KeyEvent.VK_PAGE_UP );
		mKeyboardDevice.createButton( KeyEvent.VK_PAGE_DOWN );

		mButtonSpace	= mKeyboardDevice.createButton( KeyEvent.VK_SPACE );
		mButtonLeft		= mKeyboardDevice.createButton( KeyEvent.VK_A );
		mButtonRight	= mKeyboardDevice.createButton( KeyEvent.VK_D );
		mButtonDown		= mKeyboardDevice.createButton( KeyEvent.VK_S );
		mButtonUp		= mKeyboardDevice.createButton( KeyEvent.VK_W );

		// Configure it to make it listen to the main panel.
		// I try to keep the kbd focus on it.
		mKeyboardDevice.listenTo( mMainPanel );
	}

	
	/** 
	 * Configure a test JXVirtualInputdevice.
	 */
	void configureVirtualInputDevice()
	{
		mVirtualDevice  = JXInputManager.createVirtualDevice();

		Button firebutton;
		//
		// Remember 'fire' button of first device for use
		// in the virtual device. 
		// For we ran configureKeyboardInputDevice() before,
		// getJXInputDevice( 0 ) should not return null
		//
		firebutton = JXInputManager.getJXInputDevice( 0 ).getButton( 0 );

		VirtualAxis x = mVirtualDevice.createAxis( Axis.ID_X );
		x.setButtons( mButtonRight, mButtonLeft );
		x.setName( "x: A-D" );
		
		VirtualAxis y = mVirtualDevice.createAxis( Axis.ID_Y );
		y.setButtons( mButtonUp, mButtonDown );
		y.setSpringSpeed( 0.0 );
		y.setName( "y: S|W" );

		VirtualAxis slider = mVirtualDevice.createAxis( Axis.ID_SLIDER0 );
		slider.setIncreaseButton( mButtonSpace );
		slider.setTimeFor0To1( 2000 );
		slider.setName( "<space>" );
		slider.setType( Axis.SLIDER );
        
		if ( null != firebutton )
		{
			slider = mVirtualDevice.createAxis( Axis.ID_SLIDER1 );
			slider.setIncreaseButton( firebutton );
			slider.setTimeFor0To1( 2000 );
			slider.setName( "JoyButton 0" );
		}

		
	}
				
	
	/**
	 * Initialize one panel for each device available.
	 */
	void initDevicePanels()
    {
        int cnt = JXInputManager.getNumberOfDevices();
        
        mLabelNoDevice.setVisible( cnt == 0 );
        mDevicesTabbedPane.setVisible( cnt != 0 );
        
        for ( int i = 0; i < cnt; ++i )
        {
			JXInputDevice dev = JXInputManager.getJXInputDevice( i );
			if ( null != dev )
			{
				//
				// Setup an own panel for each device.
				//
				JPanel panel = new JXInputDevicePanel( dev );
				mDevicesTabbedPane.addTab( dev.getName(), panel );
			}
		}
    }
    
	
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        mMainPanel = new javax.swing.JPanel();
        mLabelNoDevice = new javax.swing.JLabel();
        mDevicesTabbedPane = new javax.swing.JTabbedPane();
        mButtonReset = new javax.swing.JButton();

        setTitle("JXInput (C) 2001-2006 HARDCODE Dev.");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        mMainPanel.setLayout(new java.awt.BorderLayout(10, 0));

        mLabelNoDevice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mLabelNoDevice.setText("No JXInputDevice available!");
        mLabelNoDevice.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mMainPanel.add(mLabelNoDevice, java.awt.BorderLayout.NORTH);

        mDevicesTabbedPane.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                mDevicesTabbedPaneFocusGained(evt);
            }
        });

        mMainPanel.add(mDevicesTabbedPane, java.awt.BorderLayout.CENTER);

        mButtonReset.setText("Reset ");
        mButtonReset.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mButtonResetActionPerformed(evt);
            }
        });

        mMainPanel.add(mButtonReset, java.awt.BorderLayout.SOUTH);

        getContentPane().add(mMainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mButtonResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mButtonResetActionPerformed
    {//GEN-HEADEREND:event_mButtonResetActionPerformed

        while ( this.mDevicesTabbedPane.getTabCount() > 0 )
            this.mDevicesTabbedPane.removeTabAt( 0 );
        
        JXInputManager.reset();
		configureKeyboardInputDevice();
		configureVirtualInputDevice();
		initDevicePanels();
		pack();
	
		// Request the focus so that the keyboarddevice can work
		mMainPanel.requestFocus();
        
    }//GEN-LAST:event_mButtonResetActionPerformed

	private void mDevicesTabbedPaneFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_mDevicesTabbedPaneFocusGained
	{//GEN-HEADEREND:event_mDevicesTabbedPaneFocusGained
		// Switch focus back to main panel!
		this.mMainPanel.requestFocus();
	}//GEN-LAST:event_mDevicesTabbedPaneFocusGained
	
	/** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
		setVisible(false);
		dispose();
		System.exit( 0 );
    }//GEN-LAST:event_closeDialog
	
	/**
	 * Allow the dialog to run standalone.
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		new JXInputTestDialog(new javax.swing.JFrame(), true).setVisible(true);
	}
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton mButtonReset;
    private javax.swing.JTabbedPane mDevicesTabbedPane;
    private javax.swing.JLabel mLabelNoDevice;
    private javax.swing.JPanel mMainPanel;
    // End of variables declaration//GEN-END:variables
	
}
