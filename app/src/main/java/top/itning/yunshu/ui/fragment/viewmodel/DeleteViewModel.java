package top.itning.yunshu.ui.fragment.viewmodel;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeleteViewModel extends AndroidViewModel {
    private static final String TAG = "DeleteViewModel";
    @NonNull
    private final Application application;

    private final MutableLiveData<String> text;

    private final MutableLiveData<String[]> permissionRequest;

    private final Handler mHandler = new Handler();

    private boolean permissionFlag = false;

    public DeleteViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        text = new MutableLiveData<>();
        permissionRequest = new MutableLiveData<>();
        if (ContextCompat.checkSelfPermission(application, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionRequest.setValue(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        } else {
            onPermissionResult();
        }
    }

    public MutableLiveData<String> getText() {
        return text;
    }

    public MutableLiveData<String[]> getPermissionRequest() {
        return permissionRequest;
    }

    public void onPermissionResult() {
        permissionFlag = true;
    }

    private void addMsg(String msg) {
        if (null == text.getValue()) {
            text.setValue(msg + "\n");
        } else {
            text.setValue(text.getValue() + msg + "\n");
        }
    }

    public void startDel() {
        if (ContextCompat.checkSelfPermission(application, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(application, "没读取权限", Toast.LENGTH_LONG).show();
        }
        if (permissionFlag) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(application, "SD卡未找到", Toast.LENGTH_LONG).show();
            }
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            addMsg("SD卡目录：" + externalStorageDirectory.toString());
            File[] files = externalStorageDirectory.listFiles();
            if (null == files) {
                Toast.makeText(application, "listFiles is null", Toast.LENGTH_LONG).show();
            } else {
                delFileAndDir(files);
            }
        }
    }

    private void delFileAndDir(File[] externalStorageDirectory) {
        Observable.fromPublisher(
                (Publisher<File>) s -> {
                    Arrays
                            .stream(externalStorageDirectory)
                            .filter(file -> file.getName().startsWith("."))
                            .forEach(s::onNext);
                    s.onComplete();
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(File file) {
                        delDir(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void delFileAndAddMsg(File file) {
        final String msg = file.isFile() ? "file: " : "dir: ";
        mHandler.post(() -> {
            if (file.delete()) {
                addMsg("success del " + msg + file.toString());
            } else {
                addMsg("fail del " + msg + file.toString());
            }
        });
    }

    private void delDir(File dirOrFile) {
        if (dirOrFile.isFile()) {
            delFileAndAddMsg(dirOrFile);
        } else {
            File[] files = dirOrFile.listFiles();
            if (null != files) {
                if (0 == files.length) {
                    // empty dir
                    delFileAndAddMsg(dirOrFile);
                } else {
                    for (File file : files) {
                        delDir(file);
                    }
                }
            }
        }
    }
}
