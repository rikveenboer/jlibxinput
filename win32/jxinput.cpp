//
// jxinput.cpp
//
#include "stdafx.h"
#include "jxinput.h"


//
// Globals
//
extern HINSTANCE g_hInst;


/**
 * Ctor: Connect with DI
 */
JXInput::JXInput( LPDIRECTINPUTDEVICE8 pJoystick, HWND hWnd ) :
	mpJoystick( pJoystick ),
	mSliderCount( 0 ),
	mPOVCount( 0 ),
	mButtonCount( 0 )
{ 
	initAxisConfig();
	initButtonsConfig();
	initDirectionalsConfig();

	if ( FAILED( InitDirectInput( hWnd ) ) )
	{
		FreeDirectInput();
	}
}



/**
 * Destructor:
 * Free DirectInput.
 */
JXInput::~JXInput()
{ 
	FreeDirectInput();
}


void JXInput::update()
{
	UpdateInputState();
}


TCHAR * const JXInput::getName() const
{
	return (TCHAR*)mdiDevInfo.tszInstanceName;
}



int JXInput::getNumberOfAxes() const
{
	return mdiDevCaps.dwAxes;
}

int JXInput::getNumberOfButtons() const
{
	return mButtonCount;
}

int JXInput::getNumberOfDirectionals() const
{
	return mPOVCount;
}


double JXInput::getAxisValueHelper( LONG val, int idx ) const
{
	const AxisConfig& cfg = mAxisConfig[ idx ];

	double span = (double)( cfg.mMaxValue - cfg.mMinValue );
	double ret = (double)(val - cfg.mMinValue) / span;
	
	if ( TYPE_SLIDER != cfg.mType )
		return ret*2.0 - 1.0;
	return ret;
}

double JXInput::getX() const
{
	return getAxisValueHelper( mJS.lX, ID_X );
}
double JXInput::getY() const
{
	return getAxisValueHelper( mJS.lY, ID_Y );
}
double JXInput::getZ() const
{
	return getAxisValueHelper( mJS.lZ, ID_Z );
}
double JXInput::getRotX() const
{
	return getAxisValueHelper( mJS.lRx, ID_ROTX );
}
double JXInput::getRotY() const
{
	return getAxisValueHelper( mJS.lRy, ID_ROTY );
}
double JXInput::getRotZ() const
{
	return getAxisValueHelper( mJS.lRz, ID_ROTZ );
}
double JXInput::getSlider0() const
{
	return getAxisValueHelper( mJS.rglSlider[ 0 ], ID_SLIDER0 );
}
double JXInput::getSlider1() const
{
	return getAxisValueHelper( mJS.rglSlider[ 1 ], ID_SLIDER1 );
}



bool JXInput::isAxisAvailable( int idx ) const
{
	assert( idx < JXINPUT_MAX_AXES );
	return mAxisConfig[ idx ].mIsAvailable;
}

TCHAR * const JXInput::getAxisName( int idx ) const
{
	assert( idx < JXINPUT_MAX_AXES );
	return (char*const)mAxisConfig[ idx ].mName;
}

int JXInput::getAxisType( int idx ) const
{
	assert( idx < JXINPUT_MAX_AXES );
	return mAxisConfig[ idx ].mType;
}

double JXInput::getAxisValue( int idx ) const
{
	assert( idx < JXINPUT_MAX_AXES );

	// Failsafe if called accidentally
	if ( ! mAxisConfig[ idx ].mIsAvailable )
		return 0.0;

	return (this->*mAxisConfig[ idx ].mGetValueMethod)();
}





bool JXInput::isButtonAvailable( int idx ) const
{
	assert( idx < JXINPUT_MAX_BUTTONS );
	return mButtonConfig[ idx ].mIsAvailable;
}

TCHAR * const JXInput::getButtonName( int idx ) const
{
	assert( idx < JXINPUT_MAX_BUTTONS );
	return (char*const)mButtonConfig[ idx ].mName;
}

int JXInput::getButtonType( int idx ) const
{
	assert( idx < JXINPUT_MAX_BUTTONS );
	return mButtonConfig[ idx ].mType;
}

bool JXInput::isButtonDown( int idx ) const
{
	assert( idx < JXINPUT_MAX_BUTTONS );
	return 0 != mJS.rgbButtons[ idx ] ;
}


bool JXInput::isDirectionalAvailable( int idx ) const
{
	assert( idx < JXINPUT_MAX_DIRECTIONALS );
	return mDirectionalConfig[ idx ].mIsAvailable;
}

TCHAR * const JXInput::getDirectionalName( int idx ) const
{
	assert( idx < JXINPUT_MAX_DIRECTIONALS );
	return (char*const)mDirectionalConfig[ idx ].mName;
}

int JXInput::getDirection( int idx ) const
{
	assert( idx < JXINPUT_MAX_DIRECTIONALS );
	return mJS.rgdwPOV[ idx ] ;
}


/**
 * Initialize axis configuration array.
 */
void JXInput::initAxisConfig()
{
	mAxisConfig[ ID_X ].mIsAvailable = false;
	mAxisConfig[ ID_X ].mType = TYPE_TRANSLATION;
	mAxisConfig[ ID_X ].mGetValueMethod = &JXInput::getX;

	mAxisConfig[ ID_Y ].mIsAvailable = false;
	mAxisConfig[ ID_Y ].mType = TYPE_TRANSLATION;
	mAxisConfig[ ID_Y ].mGetValueMethod = &JXInput::getY;

	mAxisConfig[ ID_Z ].mIsAvailable = false;
	mAxisConfig[ ID_Z ].mType = TYPE_TRANSLATION;
	mAxisConfig[ ID_Z ].mGetValueMethod = &JXInput::getZ;

	mAxisConfig[ ID_ROTX ].mIsAvailable = false;
	mAxisConfig[ ID_ROTX ].mType = TYPE_ROTATION;
	mAxisConfig[ ID_ROTX ].mGetValueMethod = &JXInput::getRotX;

	mAxisConfig[ ID_ROTY ].mIsAvailable = false;
	mAxisConfig[ ID_ROTY ].mType = TYPE_ROTATION;
	mAxisConfig[ ID_ROTY ].mGetValueMethod = &JXInput::getRotY;

	mAxisConfig[ ID_ROTZ ].mIsAvailable = false;
	mAxisConfig[ ID_ROTZ ].mType = TYPE_ROTATION;
	mAxisConfig[ ID_ROTZ ].mGetValueMethod = &JXInput::getRotZ;
	
	mAxisConfig[ ID_SLIDER0 ].mIsAvailable = false;
	mAxisConfig[ ID_SLIDER0 ].mType = TYPE_SLIDER;
	mAxisConfig[ ID_SLIDER0 ].mGetValueMethod = &JXInput::getSlider0;

	mAxisConfig[ ID_SLIDER1 ].mIsAvailable = false;
	mAxisConfig[ ID_SLIDER1 ].mType = TYPE_SLIDER;
	mAxisConfig[ ID_SLIDER1 ].mGetValueMethod = &JXInput::getSlider1;
}


/**
 * Initialize buttons configuration array.
 */
void JXInput::initButtonsConfig()
{
	for ( int i = 0; i < JXINPUT_MAX_BUTTONS; ++i )
	{
		mButtonConfig[ i ].mIsAvailable = false;
		mButtonConfig[ i ].mName[ 0 ] = '\0';
		mButtonConfig[ i ].mType = TYPE_PUSHBUTTON;
	}

}


/**
 * Initialize directionals configuration array.
 */
void JXInput::initDirectionalsConfig()
{
	for ( int i = 0; i < JXINPUT_MAX_DIRECTIONALS; ++i )
	{
		mDirectionalConfig[ i ].mIsAvailable = false;
		mDirectionalConfig[ i ].mName[ 0 ] = '\0';
	}

}



//-----------------------------------------------------------------------------
// Name: EnumAxesCallback()
// Desc: Callback function for enumerating the axes on a joystick
//-----------------------------------------------------------------------------
BOOL CALLBACK JXInput::EnumAxesCallback( const DIDEVICEOBJECTINSTANCE* pdidoi,
                                VOID* pContext )
{
  	JXInput* pThis  = (JXInput*)pContext;
  
	AxisConfig* pAxCfg = NULL;

    // Set the UI to reflect what objects the joystick supports
	// Code derived from M$ samples, really sucks, eh?
    if (pdidoi->guidType == GUID_XAxis)
    {
		pAxCfg = & pThis->mAxisConfig[ ID_X ];
    }
    if (pdidoi->guidType == GUID_YAxis)
    {
		pAxCfg = & pThis->mAxisConfig[ ID_Y ];
    }
    if (pdidoi->guidType == GUID_ZAxis)
    {
		pAxCfg = & pThis->mAxisConfig[ ID_Z ];
    }
    if (pdidoi->guidType == GUID_RxAxis)
    {
		pAxCfg = & pThis->mAxisConfig[ ID_ROTX ];
    }
    if (pdidoi->guidType == GUID_RyAxis)
    {
		pAxCfg = & pThis->mAxisConfig[ ID_ROTY ];
    }
    if (pdidoi->guidType == GUID_RzAxis)
    {
		pAxCfg = & pThis->mAxisConfig[ ID_ROTZ ];
	}
    if (pdidoi->guidType == GUID_Slider)
    {
        switch( pThis->mSliderCount++ )
        {
            case 0 :
				pAxCfg = & pThis->mAxisConfig[ ID_SLIDER0 ];
                break;

            case 1 :
				pAxCfg = & pThis->mAxisConfig[ ID_SLIDER1 ];
                break;
        }
    }

    // fail-safe
    if( NULL == pAxCfg )   // e.g. GUID_Unknown    
      return DIENUM_CONTINUE;


	//
	// Perform config.
	//

    DIPROPRANGE diprg; 
    diprg.diph.dwSize       = sizeof(DIPROPRANGE); 
    diprg.diph.dwHeaderSize = sizeof(DIPROPHEADER); 
	diprg.diph.dwHow        = DIPH_BYID; 
    diprg.diph.dwObj        = pdidoi->dwType; // Specify the enumerated axis

    // Get the range for the axis
    if( FAILED( pThis->mpJoystick->GetProperty( DIPROP_RANGE, &diprg.diph ) ) )
        return DIENUM_CONTINUE;

	pAxCfg->mMinValue = diprg.lMin;
	pAxCfg->mMaxValue = diprg.lMax;

	strcpy( (char*)pAxCfg->mName, (char*)pdidoi->tszName );
	pAxCfg->mIsAvailable = true;

    return DIENUM_CONTINUE;
}



//-----------------------------------------------------------------------------
// Name: EnumButtonsCallback()
// Desc: Callback function for enumerating the axes on a joystick
//-----------------------------------------------------------------------------
BOOL CALLBACK JXInput::EnumButtonsCallback( const DIDEVICEOBJECTINSTANCE* pdidoi,
                                VOID* pContext )
{
  	JXInput* pThis  = (JXInput*)pContext;

	//
	// if the maximum number of buttons is already registered,
	// issue a warning and stop enumeration.
	//
	if( JXINPUT_MAX_BUTTONS == pThis->mButtonCount )
	{
		OutputDebugString( "Max. number of buttons exceeded!" );
		return DIENUM_STOP;
	}


	ButtonConfig* pBtCfg = NULL;

    if ( pdidoi->guidType == GUID_Button )
    {
		assert( JXINPUT_MAX_BUTTONS > pThis->mButtonCount );
		pBtCfg = & pThis->mButtonConfig[ pThis->mButtonCount++ ];
	}

    
    // fail-safe
    if( NULL == pBtCfg )   // e.g. unknown stuff
      return DIENUM_CONTINUE;
	  assert( NULL != pBtCfg );

	//
	// Perform config.
	//

	strcpy( (char*)pBtCfg->mName, (char*)pdidoi->tszName );
	pBtCfg->mIsAvailable = true;

    return DIENUM_CONTINUE;
}


//-----------------------------------------------------------------------------
// Name: EnumPOVsCallback()
// Desc: Callback function for enumerating the axes on a joystick
//-----------------------------------------------------------------------------
BOOL CALLBACK JXInput::EnumPOVsCallback( const DIDEVICEOBJECTINSTANCE* pdidoi,
                                VOID* pContext )
{
  	JXInput* pThis  = (JXInput*)pContext;

	//
	// if the maximum number of buttons is already registered,
	// issue a warning and stop enumeration.
	//
	if( JXINPUT_MAX_DIRECTIONALS == pThis->mPOVCount )
	{
		OutputDebugString( "Max. number of POVs exceeded!" );
		return DIENUM_STOP;
	}

	DirectionalConfig* pDirCfg = NULL;
  

    if (pdidoi->guidType == GUID_POV)
    {
		assert( JXINPUT_MAX_DIRECTIONALS > pThis->mPOVCount );
		pDirCfg = & pThis->mDirectionalConfig[ pThis->mPOVCount++ ];
    }

    // fail-safe
    if( NULL == pDirCfg )   // e.g. unknown stuff
      return DIENUM_CONTINUE;
  	assert( NULL != pDirCfg );

	//
	// Perform config.
	//

	strcpy( (char*)pDirCfg->mName, (char*)pdidoi->tszName );
	pDirCfg->mIsAvailable = true;

    return DIENUM_CONTINUE;
}



//-----------------------------------------------------------------------------
// Name: EnumEffectsCallback()
// Desc: Callback function for enumerating the effects of a joystick
//-----------------------------------------------------------------------------
BOOL CALLBACK JXInput::EnumEffectsCallback( const DIEFFECTINFO* pdidoi,
							VOID* pContext )
{
 	JXInput* pThis  = (JXInput*)pContext;

	//
	// Work on that!!
	//

    return DIENUM_CONTINUE;
}



//-----------------------------------------------------------------------------
// Name: InitDirectInput()
// Desc: Initialize the DirectInput variables.
//-----------------------------------------------------------------------------
HRESULT JXInput::InitDirectInput( HWND hWnd )
{
    HRESULT hr;

    // Make sure we got a joystick
    if( NULL == mpJoystick )
    {
        return E_FAIL;
    }

	
	//
	// Ask the device for some useful information.
	//
	mdiDevInfo.dwSize = sizeof( DIDEVICEINSTANCE );
	hr = mpJoystick->GetDeviceInfo( &mdiDevInfo );
    if( FAILED(hr) ) 
        return hr;

    // Set the data format to "simple joystick" - a predefined data format 
    //
    // A data format specifies which controls on a device we are interested in,
    // and how they should be reported. This tells DInput that we will be
    // passing a DIJOYSTATE structure to IDirectInputDevice::GetDeviceState().
    hr = mpJoystick->SetDataFormat( &c_dfDIJoystick2 );
    if( FAILED(hr) ) 
        return hr;

    // Set the cooperative level to let DInput know how this device should
    // interact with the system and with other DInput applications.
//    hr = g_pJoystick->SetCooperativeLevel( hDlg, DISCL_EXCLUSIVE|DISCL_FOREGROUND );
 	DWORD mode = ( NULL == hWnd  ?  DISCL_NONEXCLUSIVE|DISCL_BACKGROUND  :  DISCL_EXCLUSIVE|DISCL_BACKGROUND );
	hr = mpJoystick->SetCooperativeLevel( hWnd, mode );
	if( FAILED(hr) ) 
		return hr;

    // Determine how many axis the joystick has (so we don't error out setting
    // properties for unavailable axis)
    mdiDevCaps.dwSize = sizeof(DIDEVCAPS);
    hr = mpJoystick->GetCapabilities(&mdiDevCaps);
    if ( FAILED(hr) ) 
        return hr;


    // Enumerate the axes of the joyctick and set the range of each axis. Note:
    // we could just use the defaults, but we're just trying to show an example
    // of enumerating device objects (axes, buttons, etc.).
    mpJoystick->EnumObjects( EnumAxesCallback,		(VOID*)this, DIDFT_AXIS		);
    mpJoystick->EnumObjects( EnumButtonsCallback,	(VOID*)this, DIDFT_BUTTON	);
    mpJoystick->EnumObjects( EnumPOVsCallback,		(VOID*)this, DIDFT_POV		);

	mpJoystick->EnumEffects( EnumEffectsCallback,	(VOID*)this, DIEFT_ALL		);

	// For FF sticks, switch on autocenter as long as we do not use real FF
	SwitchAutoCenter( true );

    return S_OK;
}





//-----------------------------------------------------------------------------
// Name: UpdateInputState()
// Desc: Get the input device's state and display it.
//-----------------------------------------------------------------------------
HRESULT JXInput::UpdateInputState()
{
    HRESULT     hr;

    if( mpJoystick ) 
    {

		// Poll the device to read the current state
		hr = mpJoystick->Poll(); 
		if( FAILED(hr) )  
		{
			// DInput is telling us that the input stream has been
			// interrupted. We aren't tracking any state between polls, so
			// we don't have any special reset that needs to be done. We
			// just re-acquire and try again.
			hr = mpJoystick->Acquire();
			while( hr == DIERR_INPUTLOST ) 
				hr = mpJoystick->Acquire();

			// hr may be DIERR_OTHERAPPHASPRIO or other errors.  This
			// may occur when the app is minimized or in the process of 
			// switching, so just try again later 
			return S_OK; 
		}

		// Get the input's device state
		if( FAILED( hr = mpJoystick->GetDeviceState( sizeof(DIJOYSTATE2), &mJS ) ) )
			return hr; // The device should have been acquired during the Poll()

    } 

    return S_OK;
}




//-----------------------------------------------------------------------------
// Name: FreeDirectInput()
// Desc: Initialize the DirectInput variables.
//-----------------------------------------------------------------------------
HRESULT JXInput::FreeDirectInput()
{
    // Unacquire and release any DirectInputDevice objects.
    if( NULL != mpJoystick ) 
    {
        // Unacquire the device one last time just in case 
        // the app tried to exit while the device is still acquired.
        mpJoystick->Unacquire();

		mpJoystick->Release();
		mpJoystick = NULL;
    }

    return S_OK;
}



HRESULT JXInput::SwitchAutoCenter( bool onoff )
{
    HRESULT hr;

	DIPROPDWORD DIPropAutoCenter;
 
	DIPropAutoCenter.diph.dwSize = sizeof(DIPropAutoCenter);
	DIPropAutoCenter.diph.dwHeaderSize = sizeof(DIPROPHEADER);
	DIPropAutoCenter.diph.dwObj = 0;
	DIPropAutoCenter.diph.dwHow = DIPH_DEVICE;
	DIPropAutoCenter.dwData = ( onoff ? DIPROPAUTOCENTER_ON : DIPROPAUTOCENTER_OFF );

	hr = mpJoystick->SetProperty( DIPROP_AUTOCENTER, &DIPropAutoCenter.diph );
	return hr;
}
