"""
HWP/HWPX to PDF Converter
한컴오피스 COM 인터페이스를 사용하여 HWP 파일을 PDF로 변환합니다.
"""

import os
import time
from typing import Callable, Optional
from pathlib import Path


class HWPConverter:
    """HWP/HWPX 파일을 PDF로 변환하는 클래스"""

    def __init__(self):
        self.hwp = None
        self._initialized = False

    def initialize(self) -> bool:
        """한글 COM 객체 초기화"""
        try:
            import win32com.client

            # 한글 애플리케이션 COM 객체 생성
            self.hwp = win32com.client.gencache.EnsureDispatch("HWPFrame.HwpObject")

            # 보안 모듈 등록 (필요한 경우)
            self.hwp.RegisterModule("FilePathCheckDLL", "FilePathCheckerModule")

            # 백그라운드 실행 설정
            self.hwp.XHwpWindows.Item(0).Visible = False

            self._initialized = True
            return True

        except ImportError:
            raise RuntimeError(
                "pywin32가 설치되지 않았습니다.\n"
                "다음 명령어로 설치하세요: pip install pywin32"
            )
        except Exception as e:
            if "HWPFrame.HwpObject" in str(e) or "Class not registered" in str(e):
                raise RuntimeError(
                    "한컴오피스(한글)가 설치되어 있지 않거나\n"
                    "COM 인터페이스가 등록되지 않았습니다.\n\n"
                    "한컴오피스를 설치한 후 다시 시도하세요."
                )
            raise RuntimeError(f"한글 초기화 실패: {str(e)}")

    def convert_to_pdf(
        self,
        hwp_path: str,
        output_dir: str,
        progress_callback: Optional[Callable[[str], None]] = None
    ) -> str:
        """
        HWP/HWPX 파일을 PDF로 변환

        Args:
            hwp_path: 변환할 HWP 파일 경로
            output_dir: PDF 저장 디렉토리
            progress_callback: 진행 상황 콜백 함수

        Returns:
            생성된 PDF 파일 경로
        """
        if not self._initialized:
            self.initialize()

        hwp_path = os.path.abspath(hwp_path)
        output_dir = os.path.abspath(output_dir)

        if not os.path.exists(hwp_path):
            raise FileNotFoundError(f"파일을 찾을 수 없습니다: {hwp_path}")

        # 출력 디렉토리 생성
        os.makedirs(output_dir, exist_ok=True)

        # PDF 파일 경로 생성
        filename = Path(hwp_path).stem
        pdf_path = os.path.join(output_dir, f"{filename}.pdf")

        if progress_callback:
            progress_callback(f"파일 열기: {os.path.basename(hwp_path)}")

        try:
            # HWP 파일 열기
            self.hwp.Open(hwp_path, "HWP", "forceopen:true")

            if progress_callback:
                progress_callback(f"PDF 변환 중: {os.path.basename(hwp_path)}")

            # PDF로 저장
            # SaveAs 메서드의 format 파라미터:
            # "PDF" - PDF 형식으로 저장
            self.hwp.HAction.GetDefault("FileSaveAs_S", self.hwp.HParameterSet.HFileOpenSave.HSet)
            self.hwp.HParameterSet.HFileOpenSave.filename = pdf_path
            self.hwp.HParameterSet.HFileOpenSave.Format = "PDF"
            self.hwp.HAction.Execute("FileSaveAs_S", self.hwp.HParameterSet.HFileOpenSave.HSet)

            # 파일 닫기
            self.hwp.Clear(1)  # 1: 변경 사항 저장 안 함

            if progress_callback:
                progress_callback(f"완료: {os.path.basename(pdf_path)}")

            return pdf_path

        except Exception as e:
            # 오류 발생 시 파일 닫기 시도
            try:
                self.hwp.Clear(1)
            except:
                pass
            raise RuntimeError(f"변환 실패 ({os.path.basename(hwp_path)}): {str(e)}")

    def convert_multiple(
        self,
        hwp_paths: list,
        output_dir: str,
        progress_callback: Optional[Callable[[str, int, int], None]] = None,
        file_callback: Optional[Callable[[str, bool, str], None]] = None
    ) -> dict:
        """
        여러 HWP 파일을 PDF로 변환

        Args:
            hwp_paths: 변환할 HWP 파일 경로 목록
            output_dir: PDF 저장 디렉토리
            progress_callback: 전체 진행 상황 콜백 (message, current, total)
            file_callback: 개별 파일 완료 콜백 (filename, success, message)

        Returns:
            결과 딕셔너리 {'success': [...], 'failed': [...]}
        """
        results = {
            'success': [],
            'failed': []
        }

        total = len(hwp_paths)

        for i, hwp_path in enumerate(hwp_paths, 1):
            filename = os.path.basename(hwp_path)

            if progress_callback:
                progress_callback(f"변환 중: {filename}", i, total)

            try:
                pdf_path = self.convert_to_pdf(hwp_path, output_dir)
                results['success'].append({
                    'source': hwp_path,
                    'output': pdf_path
                })
                if file_callback:
                    file_callback(filename, True, "변환 완료")

            except Exception as e:
                results['failed'].append({
                    'source': hwp_path,
                    'error': str(e)
                })
                if file_callback:
                    file_callback(filename, False, str(e))

        return results

    def close(self):
        """한글 애플리케이션 종료"""
        if self.hwp:
            try:
                self.hwp.Quit()
            except:
                pass
            self.hwp = None
            self._initialized = False

    def __enter__(self):
        self.initialize()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.close()
        return False


def is_hwp_file(filepath: str) -> bool:
    """HWP 또는 HWPX 파일인지 확인"""
    ext = Path(filepath).suffix.lower()
    return ext in ['.hwp', '.hwpx']


def get_hwp_files_from_directory(directory: str) -> list:
    """디렉토리에서 모든 HWP/HWPX 파일 목록 반환"""
    hwp_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if is_hwp_file(file):
                hwp_files.append(os.path.join(root, file))
    return hwp_files
