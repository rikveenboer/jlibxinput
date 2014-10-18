// stdafx.h : Include-Datei für Standard-System-Include-Dateien,
//  oder projektspezifische Include-Dateien, die häufig benutzt, aber
//      in unregelmäßigen Abständen geändert werden.
//

#if !defined(AFX_STDAFX_H__68E14C76_098F_47ED_932B_4C01E8E9EFFB__INCLUDED_)
#define AFX_STDAFX_H__68E14C76_098F_47ED_932B_4C01E8E9EFFB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


// Fügen Sie hier Ihre Header-Dateien ein
#define WIN32_LEAN_AND_MEAN		// Selten benutzte Teile der Windows-Header nicht einbinden
#define STRICT
#include <windows.h>

// ZU ERLEDIGEN: Verweisen Sie hier auf zusätzliche Header-Dateien, die Ihr Programm benötigt
#ifdef JXINPUT_EXPORTS
#define JXINPUT_API __declspec(dllexport)
#else
#define JXINPUT_API __declspec(dllimport)
#endif

#include <dinput.h>
#include <assert.h>

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ fügt zusätzliche Deklarationen unmittelbar vor der vorherigen Zeile ein.

#endif // !defined(AFX_STDAFX_H__68E14C76_098F_47ED_932B_4C01E8E9EFFB__INCLUDED_)
