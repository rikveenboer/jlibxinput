//**********************************************************************************************
//		(C) Copyright 2002 by Dipl. Phys. Joerg Plewe, HARDCODE Development
//		All rights reserved. Copying, modification,
//		distribution or publication without the prior written
//		consent of the author is prohibited.
//
//	Created on 16. April 2002, 23:31
//**********************************************************************************************
package de.hardcode.jxinput.keyboard;

/**
 * Exeception to be thrown if keycode is not in then range [0,255].
 *
 * @author Herkules
 */
public class InvalidKeyCodeException
	extends IllegalArgumentException
{
	
	/**
	 * Creates a new instance of InvalidKeyCodeException.
	 */
	public InvalidKeyCodeException()
	{
	}
	

	/**
	 * Creates a new instance of InvalidKeyCodeException.
	 */
	public InvalidKeyCodeException( String s )
	{
		super( s );
	}
}
