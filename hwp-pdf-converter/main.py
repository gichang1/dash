"""
HWP to PDF Converter - GUI Application
윈도우 데스크톱에서 실행 가능한 HWP/HWPX to PDF 변환 프로그램
"""

import os
import sys
import threading
import tkinter as tk
from tkinter import ttk, filedialog, messagebox
from pathlib import Path
from typing import List, Optional
import queue


class HWPtoPDFConverter(tk.Tk):
    """HWP to PDF 변환기 메인 애플리케이션"""

    def __init__(self):
        super().__init__()

        self.title("HWP to PDF 변환기")
        self.geometry("800x600")
        self.minsize(600, 400)

        # 아이콘 설정 (있는 경우)
        try:
            icon_path = self._get_resource_path("icon.ico")
            if os.path.exists(icon_path):
                self.iconbitmap(icon_path)
        except:
            pass

        # 변수 초기화
        self.file_list: List[str] = []
        self.output_dir: Optional[str] = None
        self.is_converting = False
        self.message_queue = queue.Queue()

        # UI 구성
        self._setup_ui()

        # 메시지 큐 처리
        self._process_queue()

    def _get_resource_path(self, filename: str) -> str:
        """리소스 파일 경로 반환 (PyInstaller 호환)"""
        if hasattr(sys, '_MEIPASS'):
            return os.path.join(sys._MEIPASS, filename)
        return os.path.join(os.path.dirname(__file__), filename)

    def _setup_ui(self):
        """UI 구성요소 설정"""
        # 메인 프레임
        main_frame = ttk.Frame(self, padding="10")
        main_frame.pack(fill=tk.BOTH, expand=True)

        # === 파일 선택 영역 ===
        file_frame = ttk.LabelFrame(main_frame, text="변환할 파일", padding="5")
        file_frame.pack(fill=tk.BOTH, expand=True, pady=(0, 10))

        # 버튼 프레임
        btn_frame = ttk.Frame(file_frame)
        btn_frame.pack(fill=tk.X, pady=(0, 5))

        ttk.Button(btn_frame, text="파일 추가", command=self._add_files).pack(side=tk.LEFT, padx=(0, 5))
        ttk.Button(btn_frame, text="폴더 추가", command=self._add_folder).pack(side=tk.LEFT, padx=(0, 5))
        ttk.Button(btn_frame, text="선택 삭제", command=self._remove_selected).pack(side=tk.LEFT, padx=(0, 5))
        ttk.Button(btn_frame, text="전체 삭제", command=self._clear_files).pack(side=tk.LEFT)

        # 파일 개수 표시
        self.file_count_var = tk.StringVar(value="0개 파일")
        ttk.Label(btn_frame, textvariable=self.file_count_var).pack(side=tk.RIGHT)

        # 파일 목록 (스크롤바 포함)
        list_frame = ttk.Frame(file_frame)
        list_frame.pack(fill=tk.BOTH, expand=True)

        scrollbar_y = ttk.Scrollbar(list_frame, orient=tk.VERTICAL)
        scrollbar_x = ttk.Scrollbar(list_frame, orient=tk.HORIZONTAL)

        self.file_listbox = tk.Listbox(
            list_frame,
            selectmode=tk.EXTENDED,
            yscrollcommand=scrollbar_y.set,
            xscrollcommand=scrollbar_x.set
        )

        scrollbar_y.config(command=self.file_listbox.yview)
        scrollbar_x.config(command=self.file_listbox.xview)

        scrollbar_y.pack(side=tk.RIGHT, fill=tk.Y)
        scrollbar_x.pack(side=tk.BOTTOM, fill=tk.X)
        self.file_listbox.pack(fill=tk.BOTH, expand=True)

        # === 출력 폴더 설정 ===
        output_frame = ttk.LabelFrame(main_frame, text="저장 위치", padding="5")
        output_frame.pack(fill=tk.X, pady=(0, 10))

        self.output_var = tk.StringVar(value="변환할 파일과 같은 폴더에 저장")
        ttk.Entry(output_frame, textvariable=self.output_var, state='readonly').pack(
            side=tk.LEFT, fill=tk.X, expand=True, padx=(0, 5)
        )
        ttk.Button(output_frame, text="폴더 선택", command=self._select_output_dir).pack(side=tk.LEFT, padx=(0, 5))
        ttk.Button(output_frame, text="초기화", command=self._reset_output_dir).pack(side=tk.LEFT)

        # === 진행 상황 ===
        progress_frame = ttk.LabelFrame(main_frame, text="진행 상황", padding="5")
        progress_frame.pack(fill=tk.X, pady=(0, 10))

        self.progress_var = tk.DoubleVar(value=0)
        self.progress_bar = ttk.Progressbar(
            progress_frame,
            variable=self.progress_var,
            maximum=100,
            mode='determinate'
        )
        self.progress_bar.pack(fill=tk.X, pady=(0, 5))

        self.status_var = tk.StringVar(value="대기 중")
        ttk.Label(progress_frame, textvariable=self.status_var).pack(fill=tk.X)

        # === 결과 로그 ===
        log_frame = ttk.LabelFrame(main_frame, text="변환 로그", padding="5")
        log_frame.pack(fill=tk.BOTH, expand=True, pady=(0, 10))

        log_scroll = ttk.Scrollbar(log_frame)
        log_scroll.pack(side=tk.RIGHT, fill=tk.Y)

        self.log_text = tk.Text(log_frame, height=6, yscrollcommand=log_scroll.set, state='disabled')
        self.log_text.pack(fill=tk.BOTH, expand=True)
        log_scroll.config(command=self.log_text.yview)

        # 로그 태그 설정
        self.log_text.tag_configure('success', foreground='green')
        self.log_text.tag_configure('error', foreground='red')
        self.log_text.tag_configure('info', foreground='blue')

        # === 변환 버튼 ===
        self.convert_btn = ttk.Button(
            main_frame,
            text="PDF로 변환 시작",
            command=self._start_conversion,
            style='Accent.TButton'
        )
        self.convert_btn.pack(fill=tk.X, ipady=10)

        # 스타일 설정
        style = ttk.Style()
        style.configure('Accent.TButton', font=('맑은 고딕', 12, 'bold'))

    def _add_files(self):
        """파일 추가"""
        files = filedialog.askopenfilenames(
            title="HWP 파일 선택",
            filetypes=[
                ("HWP 파일", "*.hwp *.hwpx"),
                ("HWP", "*.hwp"),
                ("HWPX", "*.hwpx"),
                ("모든 파일", "*.*")
            ]
        )
        self._add_to_list(files)

    def _add_folder(self):
        """폴더의 모든 HWP 파일 추가"""
        folder = filedialog.askdirectory(title="HWP 파일이 있는 폴더 선택")
        if folder:
            files = []
            for root, dirs, filenames in os.walk(folder):
                for filename in filenames:
                    if filename.lower().endswith(('.hwp', '.hwpx')):
                        files.append(os.path.join(root, filename))
            self._add_to_list(files)

    def _add_to_list(self, files):
        """파일 목록에 추가"""
        added = 0
        for file in files:
            if file not in self.file_list:
                self.file_list.append(file)
                self.file_listbox.insert(tk.END, file)
                added += 1

        if added > 0:
            self._update_file_count()
            self._log(f"{added}개 파일 추가됨", 'info')

    def _remove_selected(self):
        """선택된 파일 삭제"""
        selection = self.file_listbox.curselection()
        if not selection:
            return

        # 역순으로 삭제 (인덱스 변경 방지)
        for i in reversed(selection):
            self.file_listbox.delete(i)
            del self.file_list[i]

        self._update_file_count()

    def _clear_files(self):
        """모든 파일 삭제"""
        self.file_listbox.delete(0, tk.END)
        self.file_list.clear()
        self._update_file_count()

    def _update_file_count(self):
        """파일 개수 업데이트"""
        count = len(self.file_list)
        self.file_count_var.set(f"{count}개 파일")

    def _select_output_dir(self):
        """출력 폴더 선택"""
        folder = filedialog.askdirectory(title="PDF 저장 폴더 선택")
        if folder:
            self.output_dir = folder
            self.output_var.set(folder)

    def _reset_output_dir(self):
        """출력 폴더 초기화"""
        self.output_dir = None
        self.output_var.set("변환할 파일과 같은 폴더에 저장")

    def _log(self, message: str, tag: str = None):
        """로그 메시지 추가"""
        self.log_text.config(state='normal')
        if tag:
            self.log_text.insert(tk.END, message + '\n', tag)
        else:
            self.log_text.insert(tk.END, message + '\n')
        self.log_text.see(tk.END)
        self.log_text.config(state='disabled')

    def _clear_log(self):
        """로그 초기화"""
        self.log_text.config(state='normal')
        self.log_text.delete(1.0, tk.END)
        self.log_text.config(state='disabled')

    def _start_conversion(self):
        """변환 시작"""
        if self.is_converting:
            return

        if not self.file_list:
            messagebox.showwarning("경고", "변환할 파일을 추가하세요.")
            return

        # UI 상태 변경
        self.is_converting = True
        self.convert_btn.config(state='disabled')
        self._clear_log()
        self.progress_var.set(0)

        # 백그라운드 스레드에서 변환 실행
        thread = threading.Thread(target=self._conversion_thread, daemon=True)
        thread.start()

    def _conversion_thread(self):
        """변환 작업 스레드"""
        try:
            from converter import HWPConverter

            self.message_queue.put(('status', '한글 프로그램 초기화 중...'))
            self.message_queue.put(('log', ('한글 프로그램을 초기화합니다...', 'info')))

            converter = HWPConverter()
            converter.initialize()

            total = len(self.file_list)
            success_count = 0
            fail_count = 0

            for i, hwp_path in enumerate(self.file_list, 1):
                filename = os.path.basename(hwp_path)

                # 출력 디렉토리 결정
                if self.output_dir:
                    output_dir = self.output_dir
                else:
                    output_dir = os.path.dirname(hwp_path)

                # 진행 상황 업데이트
                progress = (i / total) * 100
                self.message_queue.put(('progress', progress))
                self.message_queue.put(('status', f'변환 중 ({i}/{total}): {filename}'))

                try:
                    pdf_path = converter.convert_to_pdf(hwp_path, output_dir)
                    self.message_queue.put(('log', (f'✓ 완료: {filename}', 'success')))
                    success_count += 1

                except Exception as e:
                    self.message_queue.put(('log', (f'✗ 실패: {filename} - {str(e)}', 'error')))
                    fail_count += 1

            converter.close()

            # 완료 메시지
            self.message_queue.put(('progress', 100))
            self.message_queue.put(('status', f'완료! (성공: {success_count}, 실패: {fail_count})'))
            self.message_queue.put(('log', (f'\n변환 완료: 성공 {success_count}개, 실패 {fail_count}개', 'info')))
            self.message_queue.put(('done', None))

        except Exception as e:
            self.message_queue.put(('status', f'오류: {str(e)}'))
            self.message_queue.put(('log', (f'오류 발생: {str(e)}', 'error')))
            self.message_queue.put(('done', None))

    def _process_queue(self):
        """메시지 큐 처리 (UI 스레드)"""
        try:
            while True:
                msg_type, data = self.message_queue.get_nowait()

                if msg_type == 'progress':
                    self.progress_var.set(data)
                elif msg_type == 'status':
                    self.status_var.set(data)
                elif msg_type == 'log':
                    self._log(data[0], data[1])
                elif msg_type == 'done':
                    self.is_converting = False
                    self.convert_btn.config(state='normal')

        except queue.Empty:
            pass

        # 100ms 후 다시 확인
        self.after(100, self._process_queue)


class MockConverter:
    """테스트용 Mock 변환기 (한글이 없는 환경에서 테스트)"""

    def initialize(self):
        pass

    def convert_to_pdf(self, hwp_path, output_dir):
        import time
        time.sleep(0.5)  # 변환 시뮬레이션
        return os.path.join(output_dir, Path(hwp_path).stem + '.pdf')

    def close(self):
        pass


def main():
    """애플리케이션 시작점"""
    # DPI 인식 설정 (Windows)
    try:
        from ctypes import windll
        windll.shcore.SetProcessDpiAwareness(1)
    except:
        pass

    app = HWPtoPDFConverter()
    app.mainloop()


if __name__ == "__main__":
    main()
