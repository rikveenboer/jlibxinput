//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
// Created on 23. Dezember 2002, 19:21
//**********************************************************************************************
package de.hardcode.jxinput.util;

import de.hardcode.jxinput.Button;

/**
 *
 * @author  Herkules
 */
public class OrButton implements Button
{
    private final   Button  mButton1;
    private final   Button  mButton2;
    
    
    /**
     * Creates a new instance of OrButton.
     */
    public OrButton( Button b1, Button b2 )
    {
        mButton1 = b1;
        mButton2 = b2;
    }
    
    public String getName()
    {
        return mButton1.getName();
    }
    
    public boolean getState()
    {
        return mButton1.getState() ||  mButton2.getState();
    }
    
    public int getType()
    {
        return mButton1.getType();
    }
    
    public boolean hasChanged()
    {
        return mButton1.hasChanged() ||  mButton2.hasChanged();
    }
    
}
