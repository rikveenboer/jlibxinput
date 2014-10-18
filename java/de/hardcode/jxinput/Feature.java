//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 27. Dezember 2001, 00:19
//**********************************************************************************************
package de.hardcode.jxinput;

/**
 * An input device offers a set of features (otherwise it would be pretty useless).
 * Features in this sense can be axes, buttons and a feature callede <e>directional</e> here.
 * Coolie hats are typical directionals because they control a direction (to look at e.g.).
 * <p>
 * There are no concrete classes directly derived from <code>Feature</code> - it only 
 * provides a basis for other interfaces.
 *
 * @see Axis
 * @see Button
 * @see Directional
 * 
 * @author Herkules
 */
public abstract interface Feature
{
	/** 
     * Features may have a name provided e.g. by the driver. 
     */
	String getName();
	
	/**
	 * Denote wether this feature has changed beyond it's resolution since it got last
	 * updated.
	 */
	boolean hasChanged();
}

