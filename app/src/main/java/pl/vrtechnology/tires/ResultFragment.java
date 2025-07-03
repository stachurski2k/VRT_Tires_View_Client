package pl.vrtechnology.tires;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ResultFragment extends Fragment {

    private ResultViewModel viewModel;

    public ResultFragment() {
        super(R.layout.result_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /*MainViewActivity activity = (MainViewActivity) requireActivity();
        ImageRepository imageRepository = new ImageRepository(activity.getImageService());
        viewModel = new ViewModelProvider(this, new ResultViewModel.Factory(imageRepository)).get(ResultViewModel.class);

        ImageView resultImageView = requireView().findViewById(R.id.result_image_view);

        viewModel.getImage().observe(getViewLifecycleOwner(), drawable -> {
            if (drawable != null) {
                resultImageView.setImageDrawable(drawable);
            }
        });*/
    }
}