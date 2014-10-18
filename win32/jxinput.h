
#define JXINPUT_MAX_AXES			8
#define JXINPUT_MAX_BUTTONS			256
#define JXINPUT_MAX_DIRECTIONALS	4


/**
 * This class will be exported by jxinput.dll.
 */
class JXINPUT_API JXInput 
{

public:

	typedef enum AXISTYPE 
	{
		TYPE_TRANSLATION,
		TYPE_ROTATION,
		TYPE_SLIDER
	};

	typedef enum BUTTONTYPE 
	{
		TYPE_PUSHBUTTON,
		TYPE_TOGGLEBUTTON
	};

	typedef enum AXISID 
	{
		ID_X, ID_Y, ID_Z,
		ID_ROTX, ID_ROTY, ID_ROTZ,
		ID_SLIDER0, ID_SLIDER1
	};


	//
	// Ctor
	//
						JXInput( LPDIRECTINPUTDEVICE8 pJoystick, HWND hWnd = NULL );
	
	//
	// Dtor
	//
	virtual				~JXInput();

	//
	// Operational methods
	//
	void				update();

	// Ask for the name
	TCHAR * const		getName()	const;

	//
	// Numbering methods
	//
	int					getNumberOfAxes()			const;
	int					getNumberOfButtons()		const;
	int					getNumberOfDirectionals()	const;


	//
	// Access axes
	//
	double				getX()			const;	/** -1.0 .... 1.0 */
	double				getY()			const;	/** -1.0 .... 1.0 */
	double				getZ()			const;	/** -1.0 .... 1.0 */
	double				getRotX()		const;	/** -1.0 .... 1.0 */
	double				getRotY()		const;	/** -1.0 .... 1.0 */
	double				getRotZ()		const;	/** -1.0 .... 1.0 */
	double				getSlider0()	const;	/**  0.0 .... 1.0 */
	double				getSlider1()	const;	/**  0.0 .... 1.0 */


	//
	// Axis methods
	// 
	bool				isAxisAvailable( int idx )	const;
	TCHAR* const		getAxisName( int idx )		const;
	int					getAxisType( int idx )		const;
	double				getAxisValue( int idx )		const;

	//
	// Button methods
	// 
	bool				isButtonAvailable( int idx )	const;
	TCHAR* const		getButtonName( int idx )		const;
	int					getButtonType( int idx )		const;
	bool				isButtonDown( int idx )			const;

	//
	// Directional methods
	//
	bool				isDirectionalAvailable( int idx )	const;
	TCHAR* const		getDirectionalName( int idx )		const;
	int					getDirection( int idx )				const;

private://-----------------------------------------------------------------------------------------
	LPDIRECTINPUTDEVICE8 mpJoystick;     

	DIDEVICEINSTANCE	mdiDevInfo;
 	DIDEVCAPS			mdiDevCaps;
    DIJOYSTATE2			mJS;           // DInput joystick state 

	int					mSliderCount;
	int					mPOVCount;
	int					mButtonCount;

	double				getAxisValueHelper( LONG val, int idx ) const;

	HRESULT				SwitchAutoCenter( bool onoff = true );

	HRESULT				InitDirectInput( HWND hWnd = NULL );
	HRESULT				FreeDirectInput();
	HRESULT				UpdateInputState();


	static BOOL CALLBACK EnumAxesCallback
							( 
							const DIDEVICEOBJECTINSTANCE*	pdidoi,
							VOID*							pContext 
							);

	static BOOL CALLBACK EnumButtonsCallback
							( 
							const DIDEVICEOBJECTINSTANCE*	pdidoi,
							VOID*							pContext 
							);

	static BOOL CALLBACK EnumPOVsCallback
							( 
							const DIDEVICEOBJECTINSTANCE*	pdidoi,
							VOID*							pContext 
							);

	static BOOL CALLBACK EnumEffectsCallback
							( 
							const DIEFFECTINFO*				pdidoi,
							VOID*							pContext 
							);


	class JXINPUT_API	AxisConfig
	{

	public:
		bool			mIsAvailable;
	    CHAR			mName[MAX_PATH];
		AXISTYPE		mType;
		LONG			mMinValue;
		LONG			mMaxValue;
		double (JXInput::*mGetValueMethod)() const;

	} mAxisConfig [ JXINPUT_MAX_AXES ];

	void				initAxisConfig();


	class JXINPUT_API	ButtonConfig
	{

	public:
		bool			mIsAvailable;
	    CHAR			mName[MAX_PATH];
		BUTTONTYPE		mType;

	} mButtonConfig[ JXINPUT_MAX_BUTTONS ];

	void initButtonsConfig();


	class JXINPUT_API	DirectionalConfig
	{

	public:
		bool			mIsAvailable;
	    CHAR			mName[MAX_PATH];

	} mDirectionalConfig[ JXINPUT_MAX_DIRECTIONALS ];

	void initDirectionalsConfig();
};

