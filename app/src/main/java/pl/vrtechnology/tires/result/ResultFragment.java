package pl.vrtechnology.tires.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dagger.hilt.android.AndroidEntryPoint;
import pl.vrtechnology.tires.R;

@AndroidEntryPoint
public class ResultFragment extends Fragment {

    private ImageView resultView;

    public ResultFragment() {
        super(R.layout.result_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        resultView = view.findViewById(R.id.result_image_view);
        ResultViewModel viewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        viewModel.getImage().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                resultView.setImageBitmap(bitmap);
            }
        });
    }
}
