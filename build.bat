@echo off
echo ========================================
echo  Employee Leave Management System
echo  Building Project...
echo ========================================
echo.

cd /d "%~dp0"

echo Checking Maven...
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 goto :build_app
echo Maven not found in PATH. Adding NetBeans Maven...
set "PATH=%PATH%;C:\Program Files\Apache NetBeans\java\maven\bin"

:build_app
echo.
echo Cleaning and Building...
echo.

call mvn clean compile

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo  BUILD SUCCESS!
    echo ========================================
) else (
    echo.
    echo ========================================
    echo  BUILD FAILED!
    echo ========================================
)

echo.
pause
