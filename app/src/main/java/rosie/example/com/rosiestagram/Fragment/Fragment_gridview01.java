package rosie.example.com.rosiestagram.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rosie.example.com.rosiestagram.Activity.MainActivity;
import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.R;

public class Fragment_gridview01 extends Fragment {

    ArrayList<MyData> data;
    MainActivity mainActivity = new MainActivity();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedview = inflater.inflate(R.layout.gridview_mainpage, container, false);
        return inflatedview;
    }

}
