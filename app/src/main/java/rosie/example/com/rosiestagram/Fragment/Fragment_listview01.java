package rosie.example.com.rosiestagram.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import rosie.example.com.rosiestagram.Activity.MainActivity;
import rosie.example.com.rosiestagram.Adapter.MyBaseAdapter;
import rosie.example.com.rosiestagram.Data.MyListData;
import rosie.example.com.rosiestagram.R;

public class Fragment_listview01 extends Fragment {

    MainActivity main = new MainActivity();
    ListView listView;
    MyBaseAdapter myBaseAdapter;
    ArrayList<MyListData> listData = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listview_secondpage, container, false);
        listView = (ListView)view.findViewById(R.id.listview_secondpage);



        return view;
    }
}
