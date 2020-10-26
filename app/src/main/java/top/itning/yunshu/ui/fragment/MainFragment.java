package top.itning.yunshu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import top.itning.yunshu.R;
import top.itning.yunshu.databinding.MainFragmentBinding;
import top.itning.yunshu.ui.fragment.viewmodel.MainViewModel;

/**
 * @author itning
 */
public class MainFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        MainFragmentBinding mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        mainFragmentBinding.setData(mViewModel);
        mainFragmentBinding.setLifecycleOwner(this);
        mainFragmentBinding.button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_main_fragment_to_del_fragment));
        mainFragmentBinding.button3.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_main_fragment_to_openAppFragment));
        return mainFragmentBinding.getRoot();
    }
}
