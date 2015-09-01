#define MAX_JXINPUTS 10

class JXInput;

class JXINPUT_API JXInputManager  
{
public:
    JXInputManager( HWND hWnd );
    virtual ~JXInputManager();

    int getNumberOfJXInputs() const;
    JXInput& getJXInput( int idx ) const; 

    //
    // Numbering methods
    //
    int getMaxNumberOfAxes() const;
    int getMaxNumberOfButtons() const;
    int getMaxNumberOfDirectionals() const;

private:
    LPDIRECTINPUT8        mpDI;         
    HWND                mhWnd;
    JXInput*            mDevices[ MAX_JXINPUTS ];
    int                    mDeviceCounter;

    HRESULT InitDirectInput( HWND hWnd = NULL );
    HRESULT FreeDirectInput();

    static BOOL CALLBACK EnumJoysticksCallback( const DIDEVICEINSTANCE* pdidInstance,
                                     VOID* pContext );
    void addJXInput( JXInput* pJ ); 
};
