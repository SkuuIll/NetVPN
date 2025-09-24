@echo off
setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

set LOGFILE="%~dp0build_log_debug.txt"
if exist %LOGFILE% del /f /q %LOGFILE%

echo ================================ >> %LOGFILE%
echo Building NetVPN APK (Debug) >> %LOGFILE%
echo ================================ >> %LOGFILE%

REM Ensure JAVA_HOME for this session if not set
if "%JAVA_HOME%"=="" (
  set "JAVA_HOME=C:\Program Files\Java\jdk-17"
)
if exist "%JAVA_HOME%\bin" (
  set "PATH=%JAVA_HOME%\bin;%PATH%"
)

REM Move to script directory (project root)
pushd "%~dp0"

echo Using JAVA_HOME: %JAVA_HOME% >> %LOGFILE%
echo Gradle Wrapper: %CD%\gradlew.bat >> %LOGFILE%

.\gradlew.bat :app:assembleDebug --stacktrace --info --warning-mode all >> %LOGFILE% 2>&1
set ERR=%ERRORLEVEL%

if %ERR% NEQ 0 (
  echo.>> %LOGFILE%
  echo Build FAILED with exit code %ERR%. >> %LOGFILE%
  echo Check the logs above for the error details. >> %LOGFILE%
  popd
  exit /b %ERR%
)

echo.>> %LOGFILE%
echo Build SUCCESS. >> %LOGFILE%
echo APK path: %CD%\app\build\outputs\apk\debug\app-debug.apk >> %LOGFILE%
popd
exit /b 0
