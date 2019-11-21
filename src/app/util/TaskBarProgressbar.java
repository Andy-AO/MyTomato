package app.util;

import com.sun.javafx.stage.StageHelper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.stage.Stage;
import org.bridj.Pointer;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;

public final class TaskBarProgressbar {

    private final Stage stage;

    private ExecutorService es;
    private ITaskbarList3 list;
    private Pointer<?> hwnd;

    private TaskBarProgressbar() {
        stage = null;

        es = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);

            t.setDaemon(true);

            return t;
        });

        es.execute(() -> {
            try {
                list = COMRuntime.newInstance(ITaskbarList3.class);
            } catch (ClassNotFoundException e) {
                GL.logger.warn(getClass().getSimpleName(),e);
            }
        });
    }

    public TaskBarProgressbar(Stage stage) {
        this.stage = stage;

        es = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);

            t.setDaemon(true);

            return t;
        });

        es.execute(() -> {
            try {
                list = COMRuntime.newInstance(ITaskbarList3.class);
            } catch (ClassNotFoundException e) {
                GL.logger.warn(getClass().getSimpleName(),e);
            }
        });
    }

    public void close() {
        es.submit(() -> list.Release());
    }

    public enum TaskBarProgressbarType {
        ERROR {
            @Override
            ITaskbarList3.TbpFlag getPair() {
                return ITaskbarList3.TbpFlag.TBPF_ERROR;
            }
        },
        INDETERMINATE {
            @Override
            ITaskbarList3.TbpFlag getPair() {
                return ITaskbarList3.TbpFlag.TBPF_INDETERMINATE;
            }
        },
        NOPROGRESS {
            @Override
            ITaskbarList3.TbpFlag getPair() {
                return ITaskbarList3.TbpFlag.TBPF_NOPROGRESS;
            }
        },
        NORMAL {
            @Override
            ITaskbarList3.TbpFlag getPair() {
                return ITaskbarList3.TbpFlag.TBPF_NORMAL;
            }
        },
        PAUSED {
            @Override
            ITaskbarList3.TbpFlag getPair() {
                return ITaskbarList3.TbpFlag.TBPF_PAUSED;
            }
        };

        abstract ITaskbarList3.TbpFlag getPair();
    }

    public void stopProgress() {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(StageHelper.getStages().indexOf(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> {
            list.SetProgressState((Pointer) hwnd, TaskBarProgressbarType.NOPROGRESS.getPair());
        });
    }

    public void showIndeterminateProgress() {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(StageHelper.getStages().indexOf(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> {
            list.SetProgressState((Pointer) hwnd, TaskBarProgressbarType.INDETERMINATE.getPair());
        });
    }

    public void showOtherProgress(long startValue, long endValue, TaskBarProgressbarType type) {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(StageHelper.getStages().indexOf(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> {
            list.SetProgressValue((Pointer) hwnd, startValue, endValue);
            list.SetProgressState((Pointer) hwnd, type.getPair());
        });
    }

    public void showOtherProgress(double value, TaskBarProgressbarType type) {
        int endValue = 100;
        value *= 100;
        int startValue = (int) value;
        showOtherProgress(startValue, endValue, type);
    }

    public void showErrorProgress() {
        long hwndVal = com.sun.glass.ui.Window.getWindows().get(StageHelper.getStages().indexOf(stage)).getNativeWindow();
        hwnd = Pointer.pointerToAddress(hwndVal);

        es.execute(() -> {
            list.SetProgressValue((Pointer) hwnd, 100, 100);
            list.SetProgressState((Pointer) hwnd, TaskBarProgressbarType.ERROR.getPair());
        });
    }

    public static void stopProgress(int windowIndex) {
        TaskBarProgressbar progressbar = new TaskBarProgressbar();

        long hwndVal = com.sun.glass.ui.Window.getWindows().get(windowIndex).getNativeWindow();
        progressbar.hwnd = Pointer.pointerToAddress(hwndVal);

        progressbar.es.execute(() -> {
            progressbar.list.SetProgressState((Pointer) progressbar.hwnd, TaskBarProgressbarType.NOPROGRESS.getPair());
        });
    }

    public static void showIndeterminateProgress(int windowIndex) {
        TaskBarProgressbar progressbar = new TaskBarProgressbar();

        long hwndVal = com.sun.glass.ui.Window.getWindows().get(windowIndex).getNativeWindow();
        progressbar.hwnd = Pointer.pointerToAddress(hwndVal);

        progressbar.es.execute(() -> {
            progressbar.list.SetProgressState((Pointer) progressbar.hwnd, TaskBarProgressbarType.INDETERMINATE.getPair());
        });
    }

    public static void showOtherProgress(int windowIndex, long startValue, long endValue, TaskBarProgressbarType type) {
        TaskBarProgressbar progressbar = new TaskBarProgressbar();

        long hwndVal = com.sun.glass.ui.Window.getWindows().get(windowIndex).getNativeWindow();
        progressbar.hwnd = Pointer.pointerToAddress(hwndVal);

        progressbar.es.execute(() -> {
            progressbar.list.SetProgressValue((Pointer) progressbar.hwnd, startValue, endValue);
            progressbar.list.SetProgressState((Pointer) progressbar.hwnd, type.getPair());
        });
    }
    
    public static void showErrorProgress(int windowIndex) {
        TaskBarProgressbar progressbar = new TaskBarProgressbar();

        long hwndVal = com.sun.glass.ui.Window.getWindows().get(windowIndex).getNativeWindow();
        progressbar.hwnd = Pointer.pointerToAddress(hwndVal);

        progressbar.es.execute(() -> {
            progressbar.list.SetProgressValue((Pointer) progressbar.hwnd, 100, 100);
            progressbar.list.SetProgressState((Pointer) progressbar.hwnd, TaskBarProgressbarType.ERROR.getPair());
        });
    }

}
