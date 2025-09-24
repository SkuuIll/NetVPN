@echo off
setlocal ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION

echo ================================
echo Building NetVPN APK (Debug)
echo ================================

REM Ensure JAVA_HOME for this session if not set
if "%JAVA_HOME%"=="" (
  set "JAVA_HOME=C:\Program Files\Java\jdk-17"
)
if exist "%JAVA_HOME%\bin" (
  set "PATH=%JAVA_HOME%\bin;%PATH%"
)

REM Move to script directory (project root)
pushd "%~dp0"

echo Using JAVA_HOME: %JAVA_HOME%
echo Gradle Wrapper: %CD%\gradlew.bat

.\gradlew.bat :app:assembleDebug --stacktrace --info --warning-mode all
set ERR=%ERRORLEVEL%

if %ERR% NEQ 0 (
  echo.
  echo Build FAILED with exit code %ERR%.
  echo Check the logs above for the error details.
  popd
  exit /b %ERR%
)

echo.
echo Build SUCCESS.
echo APK path: %CD%\app\build\outputs\apk\debug\app-debug.apk
popd
exit /b 0
