package top.itning.yunshu.ui.fragment.viewmodel;

import android.app.Application;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import top.itning.yunshu.service.VideoWallPaperService;

/**
 * @author itning
 */
public class MainViewModel extends AndroidViewModel {
    @NonNull
    private final Application application;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void setWallpaper() {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(application, VideoWallPaperService.class));
        application.startActivity(intent);
    }
}
