@echo off
echo --- FranchiseConnect Backend Starter ---

:: Check if Node.js is installed
node -v >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Node.js is not installed!
    echo Please install it from https://nodejs.org/
    pause
    exit /b
)

echo [1/2] Installing/Updating dependencies...
call npm install

echo [2/2] Starting server...
node server.js

if %errorlevel% neq 0 (
    echo [ERROR] Server failed to start.
)

pause
