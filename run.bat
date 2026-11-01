@echo off
echo ========================================
echo  Employee Leave Management System
echo  Starting Application...
echo ========================================
echo.

cd /d "%~dp0"

echo Freeing port 8081 if occupied...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8081 ^| findstr LISTENING 2^>nul') do (
    echo Stopping old process on port 8081 (PID %%a)...
    taskkill /f /pid %%a >nul 2>&1
)

echo Checking Maven...
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 goto :run_app
echo Maven not found in PATH. Adding NetBeans Maven...
set "PATH=%PATH%;C:\Program Files\Apache NetBeans\java\maven\bin"

:run_app
echo.
echo Launching Spring Boot on http://localhost:8081...
echo.

call mvn spring-boot:run

pause
