

#Nombre del directorio
!define NOMBRE "Inmobi"
#Tamaño necesario para la instalacion
!define INSTALLSIZE 150000000
#Directorio de instalacion
InstallDir "$PROGRAMFILES\${NOMBRE}"

#Nombre del instalador
outFile "instaler.exe"

#Paginas que queremos usar
 Page license
 #Page components
 Page directory
 Page instfiles
 
 #Lo que va a decir la licencia
 #LicenseData "Readme.txt"


Section "instaladorJar"
	#seteamos el destino
	setOutPath $INSTDIR
	#Elegimos los archivos que queremos instalar, en caso de que sea una carpeta ponemos /r + "nombreCarpeta"
	File jetty-runner.jar
	File app.jar
	File Inmobi.war
	File serverLauncher.jar
	File /r "Files"
	File "icon.ico"
	File "Privs.bat"
	
	
	#File "db.properties"
	#File "addcicon.png"
	#File "addpicon.png"
	#File "addticon.png"
	#File "ticon.png"
	#File "dbicon.png"
	#File "icon.png"

	#aca ejecutamos los instaladores de jre y mysql el /s es para la instalacion silenciosa
	setOutPath $TEMP
	File /r "Dependencias"
	
	
	ExecWait '"$TEMP\Dependencias\jre-8u144-windows-i586.exe" /s'
	ExecWait 'msiexec /q /i "$TEMP\Dependencias\chrome.msi" '
	#ExecWait 'msiexec /i "$TEMP\Dependencias\mysql-installer-community-5.6.20.0.msi" /quiet'
	#ExecWait '"$TEMP\Dependencias\Connector.bat"'
	#ExecWait '"$TEMP\Dependencias\Server.bat"'
	#ExecWait '"$TEMP\Dependencias\GrantPrivileges.bat"'
	setOutPath $INSTDIR
	ExecWait '"$INSTDIR\Privs.bat"'
	#ExecWait '"$INSTDIR\serverLauncher.jar"'
	#Exec '"$INSTDIR\app.jar"'
	
	#createShortCut "$DESKTOP\Inmobi.lnk" "$INSTDIR\desktopApp\Inmobi.exe" "" "$INSTDIR\icon.ico" #
	createShortCut "$DESKTOP\Inmobi.lnk" "$INSTDIR\app.jar" "" "$INSTDIR\icon.ico" #
	#crearAccesos:
	#CreateInternetShortcut "$DESKTOP\Inmobi.url" "http://localhost:8080" "$INSTDIR" "$INSTDIR\icon.ico"
	
	createShortCut "$SMSTARTUP\Inmobi.lnk" "$INSTDIR\serverLauncher.jar" "" "$INSTDIR\icon.ico" #
	
	
	#Con esto se informa el tamaño de la 	createShortCut "$DESKTOP" -jar $\"$INSTDIR\programa.jar" "" "$INSTDIR\icono.ico" #instalacion
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${NOMBRE}" "EstimatedSize" ${INSTALLSIZE}
	setOutPath $DESKTOP
	File "Inmobi_Chrome_Extension.crx"
	
	#Exec '"$INSTDIR\reinicio.bat"'
	MessageBox MB_YESNO|MB_ICONQUESTION "Para finalizar la Instalacion debe reinciar el equipo, desea hacerlo ahora?" IDNO +2
  Reboot
	
SectionEnd

Function .onInit
  !ifdef IsSilent
    SetSilent silent
  !endif
FunctionEnd