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

import top.itning.yunshu.R;
import top.itning.yunshu.databinding.FragmentOpenAppBinding;
import top.itning.yunshu.ui.fragment.viewmodel.OpenAppViewModel;


public class OpenAppFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        OpenAppViewModel mViewModel = new ViewModelProvider(this).get(OpenAppViewModel.class);
        FragmentOpenAppBinding mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_open_app, container, false);
        mainFragmentBinding.setData(mViewModel);
        mainFragmentBinding.setLifecycleOwner(this);
        return mainFragmentBinding.getRoot();
    }

}