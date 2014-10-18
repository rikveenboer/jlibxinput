/*
 * JXInputDevicePanel.java
 *
 * Created on 23. Januar 2002, 22:19
 */
package de.hardcode.jxinput.test;

import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.Button;

import javax.swing.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Dictionary;
import java.util.Enumeration;

/**
 *
 * @author  Herkules
 */
public class JXInputDevicePanel extends javax.swing.JPanel 
{
    private static final Font AXIS_SLIDER_FONT = new Font( "Verdana", Font.PLAIN, 9 );
    
   	private final   JXInputDevice mDev;
    private final 	ArrayList mAxisSliders          = new ArrayList();
	private final   ArrayList mButtonCheckboxes     = new ArrayList();
	private final   ArrayList mDirectionalLabels    = new ArrayList();

    
    /** Creates new form JXInputDevicePanel */
    public JXInputDevicePanel( JXInputDevice dev ) 
    {
		mDev = dev;
        initComponents();
        initFromDevice();
    }

	/**
	 * Helper class connecting a JSlider with an Axis.
	 */
	private class AxisSlider extends JSlider
	{
		Axis mAxis;
		AxisSlider( Axis axis )
		{
			super( ( Axis.SLIDER == axis.getType() ? 0 : -100 ), 100 );
            this.setMajorTickSpacing( Axis.SLIDER == axis.getType() ? 25 : 50 );
            this.setMinorTickSpacing( 5 );
            this.setPaintTicks( true );
            this.setPaintLabels( true );
			this.setEnabled( false );
            
            Dictionary labeldict = this.getLabelTable();
            Enumeration labels = labeldict.elements();
            while ( labels.hasMoreElements() )
            {
                JLabel label = (JLabel)labels.nextElement();
                label.setFont( AXIS_SLIDER_FONT );
                label.setSize( 32, 12 );
                label.setHorizontalAlignment( SwingConstants.LEFT );
            }
            
			mAxis = axis;
		}
		
		void update()
		{
			int ax = (int)(mAxis.getValue() * 100.0);

			//
			// Only if value really changes
			//
			if ( ax != this.getValue() )
			{
				this.setValue( ax );
				this.setToolTipText( mAxis.getName() + ": " + Double.toString( mAxis.getValue() ) );
			}
		}
		
	}

	
	private class ButtonCheckbox extends JCheckBox
	{
		Button mButton;
		ButtonCheckbox( Button button )
		{
			super( button.getName() );
			this.setEnabled( false );
			mButton = button;
		}
		
		void update()
		{
			boolean state = mButton.getState();

			//
			// Only if value really changes
			//
			if ( state != this.isSelected() )
			{
				this.setSelected( state );
			}
		}		
	}

	
	private class DirectionalLabel extends JLabel
	{
		Directional mDirectional;
		int mCurrent = 0;

		DirectionalLabel( Directional directional )
		{
			super( directional.getName() );
			mDirectional = directional;
		}
		
		void update()
		{
			int dir = mDirectional.getDirection();

			//
			// Only if value really changes
			//
			if ( dir != mCurrent )
			{
				this.setText( mDirectional.getName() + ":  " + ( mDirectional.isCentered() ? "-" : Integer.toString( dir ) ) );
				mCurrent = dir;
			}
		}		
	}

    
	/**
	 * Setup the dialogs content from the JXInputDevice.
	 */
	void initFromDevice()
	{		
		if ( null != mDev )
		{						
			((GridLayout)mAxesPanel.getLayout()).setRows( mDev.getNumberOfAxes() );
			
			for ( int i = 0; i < mDev.getMaxNumberOfAxes(); ++i )
			{
				if ( null != mDev.getAxis( i ) )
				{
					AxisSlider slider = new AxisSlider( mDev.getAxis( i ) );
                                     
					JLabel name = new JLabel( mDev.getAxis( i ).getName() );
                    name.setVerticalAlignment( SwingConstants.TOP );
                    name.setHorizontalAlignment( SwingConstants.CENTER );
					name.setPreferredSize( new java.awt.Dimension( 90, 0 ) );	
                                     
                    JPanel p = new JPanel();
                    p.setLayout( new BorderLayout() );
                    
                    p.add( name, BorderLayout.WEST );
					p.add( slider, BorderLayout.CENTER );

                    mAxesPanel.add( p );

                    // Add to list of all AxisSlider controls
                    mAxisSliders.add( slider );
					
					// Add an event listener:
					new AxisListener( mDev.getAxis( i ) );
				}
			}

			
			((GridLayout)mButtonsPanel.getLayout()).setRows( mDev.getNumberOfButtons() );
			for ( int i = 0; i < mDev.getMaxNumberOfButtons(); ++i )
			{
				if ( null != mDev.getButton( i ) )
				{
					ButtonCheckbox chk = new ButtonCheckbox( mDev.getButton( i ) );
					mButtonCheckboxes.add( chk );
					mButtonsPanel.add( chk );

					// Add an event listener:
					new ButtonListener( mDev.getButton( i ) );
				}
			}
			
			((GridLayout)mDirectionalPanel.getLayout()).setRows( mDev.getNumberOfDirectionals() / 2 );
			for ( int i = 0; i < mDev.getMaxNumberOfDirectionals(); ++i )
			{
				if ( null != mDev.getDirectional( i ) )
				{
					DirectionalLabel lbl = new DirectionalLabel( mDev.getDirectional( i ) );
					mDirectionalLabels.add( lbl );
					mDirectionalPanel.add( lbl );

					// Add an event listener:
					new DirectionalListener( mDev.getDirectional( i ) );
				}
			}
		}
	}
    
    
	public void update()
	{
        Iterator it = mAxisSliders.iterator();
		while ( it.hasNext() )
		{
			((AxisSlider)it.next()).update();
		}

		it = mButtonCheckboxes.iterator();
		while ( it.hasNext() )
		{
			((ButtonCheckbox)it.next()).update();
		}

		it = mDirectionalLabels.iterator();
		while ( it.hasNext() )
		{
			((DirectionalLabel)it.next()).update();
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
        mAxesPanelContainer = new javax.swing.JPanel();
        mAxesPanel = new javax.swing.JPanel();
        mDirectionalPanel = new javax.swing.JPanel();
        mButtonScrollPane = new javax.swing.JScrollPane();
        mButtonsPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout(2, 2));

        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                OnShow(evt);
            }
        });

        mAxesPanelContainer.setLayout(new java.awt.BorderLayout());

        mAxesPanelContainer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mAxesPanel.setLayout(new java.awt.GridLayout(1, 1, 0, 20));

        mAxesPanelContainer.add(mAxesPanel, java.awt.BorderLayout.NORTH);

        add(mAxesPanelContainer, java.awt.BorderLayout.CENTER);

        mDirectionalPanel.setLayout(new java.awt.GridLayout(1, 1));

        mDirectionalPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(mDirectionalPanel, java.awt.BorderLayout.SOUTH);

        mButtonsPanel.setLayout(new java.awt.GridLayout(1, 1));

        mButtonsPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mButtonScrollPane.setViewportView(mButtonsPanel);

        add(mButtonScrollPane, java.awt.BorderLayout.EAST);

    }// </editor-fold>//GEN-END:initComponents

	private void OnShow(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_OnShow
	{//GEN-HEADEREND:event_OnShow
		// Commented: the focus is held by a parent component
//		System.out.println("OnShow");
//		this.requestFocus();
	}//GEN-LAST:event_OnShow
 
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mAxesPanel;
    private javax.swing.JPanel mAxesPanelContainer;
    private javax.swing.JScrollPane mButtonScrollPane;
    private javax.swing.JPanel mButtonsPanel;
    private javax.swing.JPanel mDirectionalPanel;
    // End of variables declaration//GEN-END:variables

}
