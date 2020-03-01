package top.itning.yunshu.ui.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import top.itning.yunshu.R;
import top.itning.yunshu.databinding.DeleteFragmentBinding;
import top.itning.yunshu.ui.fragment.viewmodel.DeleteViewModel;

public class DeleteFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 1;

    private DeleteViewModel deleteViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        deleteViewModel = new ViewModelProvider(this).get(DeleteViewModel.class);
        deleteViewModel.getPermissionRequest().observe(getViewLifecycleOwner(), this::requestPermission);
        DeleteFragmentBinding deleteFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.delete_fragment, container, false);
        deleteFragmentBinding.setData(deleteViewModel);
        deleteFragmentBinding.setLifecycleOwner(this);
        return deleteFragmentBinding.getRoot();
    }

    private void requestPermission(String[] permission) {
        if (shouldShowRequestPermissionRationale(permission[0])) {
            new AlertDialog
                    .Builder(requireContext())
                    .setTitle("请授予权限")
                    .setMessage("请授予权限")
                    .setPositiveButton("好的", (dialog, which) -> {
                        dialog.dismiss();
                        requestPermissions(permission, PERMISSIONS_REQUEST_CODE);
                    })
                    .create()
                    .show();
        } else {
            requestPermissions(permission, PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "权限已授予", Toast.LENGTH_LONG).show();
                deleteViewModel.onPermissionResult();
            } else {
                Toast.makeText(getContext(), "权限未授予", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(DeleteFragment.this).popBackStack();
            }
        }
    }
}
