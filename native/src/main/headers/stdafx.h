#define DIRECTINPUT_VERSION 0x0800

#include <windows.h>
#include <dinput.h>
#include <assert.h>

#ifdef JXINPUT_EXPORTS
#define JXINPUT_API __declspec(dllexport)
#else
#define JXINPUT_API __declspec(dllimport)
#endif
