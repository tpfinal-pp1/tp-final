@ECHO OFF
:myLabel

SET /P continue="Para Continuar Debe reinicar la PC,desa hacerlo ahora? (S/N): "
IF %continue% EQU S (
shutdown /r /t 00
TIMEOUT /T 600 /NOBREAK
GOTO myLabel
)