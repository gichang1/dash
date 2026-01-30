@echo off
chcp 65001 > nul
echo ========================================
echo HWP to PDF Converter - EXE 빌드
echo ========================================
echo.

:: Python 확인
python --version > nul 2>&1
if errorlevel 1 (
    echo [오류] Python이 설치되어 있지 않습니다.
    echo Python 3.8 이상을 설치하세요: https://www.python.org/downloads/
    pause
    exit /b 1
)

:: 가상환경 생성 (없는 경우)
if not exist "venv" (
    echo [1/4] 가상환경 생성 중...
    python -m venv venv
)

:: 가상환경 활성화
call venv\Scripts\activate.bat

:: 의존성 설치
echo [2/4] 의존성 설치 중...
pip install -r requirements.txt --quiet

:: 이전 빌드 정리
if exist "dist" rmdir /s /q dist
if exist "build" rmdir /s /q build

:: PyInstaller로 EXE 빌드
echo [3/4] EXE 빌드 중...
pyinstaller --noconfirm --onefile --windowed ^
    --name "HWP-PDF-Converter" ^
    --add-data "converter.py;." ^
    --hidden-import win32com.client ^
    --hidden-import win32com.gen_py ^
    main.py

if errorlevel 1 (
    echo.
    echo [오류] 빌드 실패!
    pause
    exit /b 1
)

echo.
echo [4/4] 빌드 완료!
echo.
echo ========================================
echo 실행 파일 위치: dist\HWP-PDF-Converter.exe
echo ========================================
echo.

:: 빌드 파일 열기
explorer dist

pause
