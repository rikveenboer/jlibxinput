#include "stdafx.h"
	
#include "de_hardcode_jxinput_directinput_DirectInputDriver.h"
#include "jxinput.h"
#include "JXInputManager.h"


//
// Globals
//
extern HINSTANCE g_hInst;

static JXInputManager*	pJXInputManager = NULL;
static JXInput*			apJXInput[ MAX_JXINPUTS ];
static HWND				hWndJava;

//
// IDs of the static Java arrays.
//
static jfieldID			sAxesFieldID;
static jfieldID			sButtonsFieldID;
static jfieldID			sDirectionsFieldID;



/**
 * Remove all resources allocated by the Java binding.
 */
void shutdownJavaResources()
{
	if ( NULL != pJXInputManager )
		delete pJXInputManager;

	if ( NULL != hWndJava )
		DestroyWindow( hWndJava );

	pJXInputManager = NULL;

	for( int i = 0; i < MAX_JXINPUTS; ++i )
		apJXInput[ i ] = NULL;

	hWndJava = NULL;
}



JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
{
	return JNI_VERSION_1_2;
}


JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved)
{
	shutdownJavaResources();
}


JNIEXPORT jboolean JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_nativeinit
  (JNIEnv * penv, jclass pClazz )
{

	//
	// Create a non-visible window as 'owner' of the DI device.
	//
	hWndJava =  CreateWindowEx(
	  0/*WS_EX_APPWINDOW*/,			// DWORD dwExStyle,      // extended window style
	  "STATIC",						// LPCTSTR lpClassName,  // pointer to registered class name
	  NULL,							// LPCTSTR lpWindowName, // pointer to window name
	  0/*WS_CAPTION*/,				// DWORD dwStyle,        // window style
	  0,							// int x,                // horizontal position of window
	  0,							// int y,                // vertical position of window
	  0,							// int nWidth,           // window width
	  0,							// int nHeight,          // window height
	  NULL,							// HWND hWndParent,      // handle to parent or owner window
	  NULL,							// HMENU hMenu,          // handle to menu, or child-window identifier
	  g_hInst,						// HINSTANCE hInstance,  // handle to application instance
	  NULL							// LPVOID lpParam        // pointer to window-creation data
	);
	
	
	if ( NULL == pJXInputManager )
	{	
		pJXInputManager = new JXInputManager( hWndJava );

		for( int i = 0; i < MAX_JXINPUTS; ++i )
			apJXInput[ i ] = NULL;

		for ( int i = 0; i < pJXInputManager->getNumberOfJXInputs(); ++i  )
		{
			apJXInput[ i ] = & pJXInputManager->getJXInput( i );
		}
	}

	return true;
}



JNIEXPORT void JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_nativeexit
  (JNIEnv *, jclass )
{
	shutdownJavaResources();
}


/**
 * Bind my field IDs to the Java variables.
 */
JNIEXPORT void JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_bind
  (JNIEnv * penv, jclass pClazz)
{
	//
	// All fields are static.
	// 
	sAxesFieldID		= penv->GetStaticFieldID( pClazz, "sAxisValues",		"[[D" );
	sButtonsFieldID		= penv->GetStaticFieldID( pClazz, "sButtonStates",		"[[Z" );
	sDirectionsFieldID	= penv->GetStaticFieldID( pClazz, "sDirectionalValues",	"[[I" );
}


JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getNumberOfDevices
  (JNIEnv *penv, jclass)
{
	return pJXInputManager->getNumberOfJXInputs();	
}


JNIEXPORT jstring JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getName
  (JNIEnv *penv, jclass, jint dev)
{
	return penv->NewStringUTF( apJXInput[ dev ]->getName() );
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getNumberOfAxes
  (JNIEnv *, jclass, jint dev)
{
	return apJXInput[ dev ]->getNumberOfAxes();
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getNumberOfButtons
  (JNIEnv *, jclass, jint dev)
{
	return apJXInput[ dev ]->getNumberOfButtons();
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getNumberOfDirectionals
  (JNIEnv *, jclass, jint dev)
{
	return apJXInput[ dev ]->getNumberOfDirectionals();
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getMaxNumberOfAxes
  (JNIEnv *, jclass)
{
	return pJXInputManager->getMaxNumberOfAxes();
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getMaxNumberOfButtons
  (JNIEnv *, jclass)
{
	return pJXInputManager->getMaxNumberOfButtons();
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getMaxNumberOfDirectionals
  (JNIEnv *, jclass)
{
	return pJXInputManager->getMaxNumberOfDirectionals();
}

JNIEXPORT jboolean JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_isAxisAvailable
  (JNIEnv *, jclass, jint dev, jint idx )
{
	return apJXInput[ dev ]->isAxisAvailable( idx );
}

JNIEXPORT jstring JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getAxisName
  (JNIEnv *penv, jclass, jint dev, jint idx )
{
	return penv->NewStringUTF( apJXInput[ dev ]->getAxisName( idx ) );
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getAxisType
  (JNIEnv *, jclass, jint dev, jint idx )
{
	return apJXInput[ dev ]->getAxisType( idx );
}


JNIEXPORT jboolean JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_isButtonAvailable
  (JNIEnv *, jclass, jint dev, jint idx )
{
	return apJXInput[ dev ]->isButtonAvailable( idx );
}

JNIEXPORT jstring JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getButtonName
  (JNIEnv *penv, jclass, jint dev, jint idx )
{
	return penv->NewStringUTF( apJXInput[ dev ]->getButtonName( idx ) );
}

JNIEXPORT jint JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getButtonType
  (JNIEnv *, jclass, jint dev, jint idx )
{
	return apJXInput[ dev ]->getButtonType( idx );
}

JNIEXPORT jboolean JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_isDirectionalAvailable
  (JNIEnv *, jclass, jint dev, jint idx )
{
	return apJXInput[ dev ]->isDirectionalAvailable( idx );
}

JNIEXPORT jstring JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_getDirectionalName
  (JNIEnv *penv, jclass, jint dev, jint idx )
{
	return penv->NewStringUTF( apJXInput[ dev ]->getDirectionalName( idx ) );
}



/**
 * The main update method.
 * Here, the actual work is done.
 */
JNIEXPORT void JNICALL Java_de_hardcode_jxinput_directinput_DirectInputDriver_nativeupdate
  (JNIEnv * penv, jclass pClazz )
{

	static jdouble	axes		[ MAX_JXINPUTS ][ JXINPUT_MAX_AXES ];
	static jboolean buttons		[ MAX_JXINPUTS ][ JXINPUT_MAX_BUTTONS ];
	static jint		directions	[ MAX_JXINPUTS ][ JXINPUT_MAX_DIRECTIONALS ];

	static jobjectArray		axisarrayarray;
	static jobjectArray		buttonarrayarray;
	static jobjectArray		directionarrayarray;

	static jdoubleArray		axisarray;
	static jbooleanArray	buttonarray;
	static jintArray		directionarray;

	axisarrayarray		= (jobjectArray)penv->GetStaticObjectField( pClazz, sAxesFieldID );
	buttonarrayarray	= (jobjectArray)penv->GetStaticObjectField( pClazz, sButtonsFieldID );
	directionarrayarray	= (jobjectArray)penv->GetStaticObjectField( pClazz, sDirectionsFieldID );

	//
	// For each device....
	//
	for ( int dev = 0; dev < pJXInputManager->getNumberOfJXInputs(); ++dev )
	{
		// Do the update of the device.
		apJXInput[ dev ]->update();

		//
		// Copy all values into my arrays.
		//
		for ( int i = 0; i < JXINPUT_MAX_AXES; ++i )
			axes[ dev ][ i ] = apJXInput[ dev ]->getAxisValue( i );
		for ( int i = 0; i < JXINPUT_MAX_BUTTONS; ++i )
			buttons[ dev ][ i ] = apJXInput[ dev ]->isButtonDown( i );
		for ( int i = 0; i < JXINPUT_MAX_DIRECTIONALS; ++i )
			directions[ dev ][ i ] = apJXInput[ dev ]->getDirection( i );


		//
		// Move my arrays to the Java arrays.
		//
		axisarray = (jdoubleArray)penv->GetObjectArrayElement( axisarrayarray, dev );
		penv->SetDoubleArrayRegion( axisarray, 0, JXINPUT_MAX_AXES, axes[ dev ] );

		buttonarray = (jbooleanArray)penv->GetObjectArrayElement( buttonarrayarray, dev );
		penv->SetBooleanArrayRegion( buttonarray, 0, JXINPUT_MAX_BUTTONS, buttons[ dev ] );

		directionarray = (jintArray)penv->GetObjectArrayElement( directionarrayarray, dev );
		penv->SetIntArrayRegion( directionarray, 0, JXINPUT_MAX_DIRECTIONALS, directions[ dev ] );
	}

}

