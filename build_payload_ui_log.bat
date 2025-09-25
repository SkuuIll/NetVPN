@echo off
setlocal
set LOGFILE="%~dp0build_log_payload_ui.txt"
if exist %LOGFILE% del /f /q %LOGFILE%
pushd "%~dp0"
.\gradlew.bat :app:assembleDebug --stacktrace --info --warning-mode all >> %LOGFILE% 2>&1
set ERR=%ERRORLEVEL%
popd
exit /b %ERR%
