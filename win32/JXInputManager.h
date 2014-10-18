// JXInputManager.h: Schnittstelle für die Klasse JXInputManager.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_JXINPUTMANAGER_H__24862402_14C9_407D_8532_A16A6E3A7D64__INCLUDED_)
#define AFX_JXINPUTMANAGER_H__24862402_14C9_407D_8532_A16A6E3A7D64__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


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
	LPDIRECTINPUT8		mpDI;         
	HWND				mhWnd;
	JXInput*			mDevices[ MAX_JXINPUTS ];
	int					mDeviceCounter;

	HRESULT InitDirectInput( HWND hWnd = NULL );
	HRESULT FreeDirectInput();

	static BOOL CALLBACK EnumJoysticksCallback( const DIDEVICEINSTANCE* pdidInstance,
                                     VOID* pContext );
	void addJXInput( JXInput* pJ ); 
};

#endif // !defined(AFX_JXINPUTMANAGER_H__24862402_14C9_407D_8532_A16A6E3A7D64__INCLUDED_)
