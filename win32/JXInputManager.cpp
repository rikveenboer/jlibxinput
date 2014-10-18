
#include "stdafx.h"
#include "JXInputManager.h"
#include "JXInput.h"

//
// Globals
//
extern HINSTANCE g_hInst;


JXInputManager::JXInputManager( HWND hWnd ) :
mhWnd( hWnd ),
mDeviceCounter( 0 )
{

	for ( int i = 0; i < MAX_JXINPUTS; ++i )
	{
		mDevices[ i ] = NULL;
	}


	if ( FAILED( InitDirectInput( hWnd ) ) )
	{
		FreeDirectInput();
	}

}

JXInputManager::~JXInputManager()
{
	for ( int i = 0; i < getNumberOfJXInputs(); ++i )
	{
		delete mDevices[ i ];
		mDevices[ i ] = NULL;
	}

	FreeDirectInput();
}

int JXInputManager::getNumberOfJXInputs() const
{
	return mDeviceCounter;
}

JXInput& JXInputManager::getJXInput( int idx ) const
{
	assert( idx < mDeviceCounter );
	return * mDevices[ idx ];
}


int JXInputManager::getMaxNumberOfAxes() const
{
	return JXINPUT_MAX_AXES;
}

int JXInputManager::getMaxNumberOfButtons() const
{
	return JXINPUT_MAX_BUTTONS;
}

int JXInputManager::getMaxNumberOfDirectionals() const
{
	return JXINPUT_MAX_DIRECTIONALS;
}



//-----------------------------------------------------------------------------
// Name: InitDirectInput()
// Desc: Initialize the DirectInput variables.
//-----------------------------------------------------------------------------
HRESULT JXInputManager::InitDirectInput( HWND hWnd )
{
    HRESULT hr;

    // Register with the DirectInput subsystem and get a pointer
    // to a IDirectInput interface we can use.
    // Create a DInput object
    if( FAILED( hr = DirectInput8Create( g_hInst, DIRECTINPUT_VERSION, 
                                         IID_IDirectInput8, (VOID**)&mpDI, NULL ) ) )
        return hr;

    // Look for a simple joystick we can use for this sample program.
    if( FAILED( hr = mpDI->EnumDevices( DI8DEVCLASS_GAMECTRL, 
                                         EnumJoysticksCallback,
                                         (VOID*)this, DIEDFL_ALLDEVICES /*| DIEDFL_INCLUDEPHANTOMS*/ ) ) )
        return hr;

	// Look for a other devices
    if( FAILED( hr = mpDI->EnumDevices( DI8DEVCLASS_DEVICE, 
                                         EnumJoysticksCallback,
                                         (VOID*)this, DIEDFL_ALLDEVICES /*| DIEDFL_INCLUDEPHANTOMS*/ ) ) )
        return hr;

    return S_OK;
}


//-----------------------------------------------------------------------------
// Name: FreeDirectInput()
// Desc: Initialize the DirectInput variables.
//-----------------------------------------------------------------------------
HRESULT JXInputManager::FreeDirectInput()
{

	if ( NULL != mpDI )
		mpDI->Release();
	mpDI = NULL;
    return S_OK;
}


//-----------------------------------------------------------------------------
// Name: EnumJoysticksCallback()
// Desc: Called once for each enumerated joystick. If we find one, create a
//       device interface on it so we can play with it.
//-----------------------------------------------------------------------------
BOOL CALLBACK JXInputManager::EnumJoysticksCallback( const DIDEVICEINSTANCE* pdidInstance,
                                     VOID* pContext )
{
    HRESULT hr;
	LPDIRECTINPUTDEVICE8 pJoystick;

	JXInputManager* pThis  = (JXInputManager*)pContext;

	//
	// if the maximum number of devices is already registered,
	// issue a warning and stop enumeration.
	//
	if( MAX_JXINPUTS == pThis->mDeviceCounter )
	{
		OutputDebugString( "Max. number of devices exceeded!" );
		return DIENUM_STOP;
	}



    // Obtain an interface to the enumerated joystick.
	hr = pThis->mpDI->CreateDevice( pdidInstance->guidInstance, &pJoystick, NULL );

    // If it failed, then we can't use this joystick. (Maybe the user unplugged
    // it while we were in the middle of enumerating it.)
    if( FAILED(hr) ) 
        return DIENUM_CONTINUE;

	JXInput* pJ = new JXInput( pJoystick, pThis->mhWnd );

	//
	// only register useful devices
	//
	if( pJ->getNumberOfAxes() + pJ->getNumberOfButtons() + pJ->getNumberOfDirectionals() > 0 )
	{
		pThis->addJXInput( pJ );
	}
	else
	{
		delete pJ;
	}

    return DIENUM_CONTINUE;
}


/**
 * Register a JXInput device.
 */
void JXInputManager::addJXInput( JXInput* pJ )
{
	assert( mDeviceCounter < MAX_JXINPUTS );

	if( mDeviceCounter < MAX_JXINPUTS )
		mDevices[ mDeviceCounter++ ] = pJ; 
}
