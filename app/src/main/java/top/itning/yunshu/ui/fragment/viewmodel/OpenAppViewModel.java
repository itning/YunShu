package top.itning.yunshu.ui.fragment.viewmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * @author itning
 */
public class OpenAppViewModel extends AndroidViewModel {
    @NonNull
    private final Application application;
    private final MutableLiveData<String> packageNameLiveData;
    private final MutableLiveData<String> activityNameLivaData;

    public OpenAppViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        packageNameLiveData = new MutableLiveData<>();
        activityNameLivaData = new MutableLiveData<>();
        packageNameLiveData.setValue("com.jd.jrapp");
        activityNameLivaData.setValue("com.jd.jrapp.bm.mainbox.DispatchTransparentActivity");
    }

    public MutableLiveData<String> getPackageNameLiveData() {
        return packageNameLiveData;
    }

    public MutableLiveData<String> getActivityNameLivaData() {
        return activityNameLivaData;
    }

    public void start() {
        String packageName = packageNameLiveData.getValue();
        String activityName = activityNameLivaData.getValue();
        if (null != packageName && null != activityName) {
            ComponentName componentName = new ComponentName(packageName, activityName);
            Intent intent = new Intent();
            intent.setComponent(componentName);
            // intent.putExtra("type", "110");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                application.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("OpenAppViewModel", e.toString());
            }
        }
    }
}
