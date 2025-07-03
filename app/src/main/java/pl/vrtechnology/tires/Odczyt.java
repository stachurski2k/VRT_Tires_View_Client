package pl.vrtechnology.tires;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Odczyt extends Fragment {
    public Odczyt() {
        super(R.layout.fragment_odczyt); // <-- layout fragmentu
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_odczyt, container, false);
    }
}