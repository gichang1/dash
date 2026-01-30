# -*- mode: python ; coding: utf-8 -*-
"""
PyInstaller spec file for HWP to PDF Converter
고급 빌드 옵션이 필요한 경우 이 파일을 수정하세요.

빌드 명령: pyinstaller HWP-PDF-Converter.spec
"""

block_cipher = None

a = Analysis(
    ['main.py'],
    pathex=[],
    binaries=[],
    datas=[
        ('converter.py', '.'),
    ],
    hiddenimports=[
        'win32com.client',
        'win32com.gen_py',
        'pythoncom',
        'pywintypes',
    ],
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[],
    win_no_prefer_redirects=False,
    win_private_assemblies=False,
    cipher=block_cipher,
    noarchive=False,
)

pyz = PYZ(a.pure, a.zipped_data, cipher=block_cipher)

exe = EXE(
    pyz,
    a.scripts,
    a.binaries,
    a.zipfiles,
    a.datas,
    [],
    name='HWP-PDF-Converter',
    debug=False,
    bootloader_ignore_signals=False,
    strip=False,
    upx=True,
    upx_exclude=[],
    runtime_tmpdir=None,
    console=False,  # GUI 모드 (콘솔 창 숨김)
    disable_windowed_traceback=False,
    argv_emulation=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
    icon=None,  # 아이콘 파일이 있으면 여기에 경로 지정: icon='icon.ico'
    version=None,  # 버전 정보 파일이 있으면 여기에 경로 지정
)
