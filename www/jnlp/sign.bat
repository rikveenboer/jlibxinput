rem keytool -genkey -alias jxinput -keypass jxinput -keystore JXInputKeystore

set PATH=c:\j2sdk1.4.1\bin;%PATH%
copy /Y ..\..\Distribution\0.3\JXInput.jar .
copy /Y ..\..\Distribution\0.3\jxinput.dll .
jar cf jxinputdll.jar jxinput.dll
jarsigner -keystore JXInputKeystore -storepass jxinput -keypass jxinput JXInput.jar jxinput
jarsigner -keystore JXInputKeystore -storepass jxinput -keypass jxinput jxinputdll.jar jxinput