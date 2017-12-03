package rosie.example.com.rosiestagram.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import rosie.example.com.rosiestagram.Activity.MainActivity;
import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.R;

/**
 * Created by skyki on 2017-07-12.
 */

public class MyBaseAdapter extends BaseAdapter {
    MainActivity context;
    int iResID;
    ArrayList<MyData> bindArrData;

    public MyBaseAdapter(MainActivity context, int iResID, ArrayList<MyData> bindArrData) {
        this.context = context;
        this.iResID = iResID;
        this.bindArrData = bindArrData;
    }

    @Override
    public int getCount() {
        return bindArrData.size();
    }
    @Override
    public Object getItem(int position) {
        return bindArrData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class viewHolder{
        ImageView iv;

        public viewHolder(ImageView iv) {
            this.iv = iv;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflatedView = convertView;
        viewHolder holder = null;
        if(inflatedView ==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            inflatedView = layoutInflater.inflate(iResID,null);
            ImageView iv = (ImageView)inflatedView.findViewById(R.id.iv_photo);
            holder = new viewHolder(iv);
            
            int columnWidth = ((GridView)parent).getColumnWidth();
            iv.getLayoutParams().width = columnWidth;
            iv.getLayoutParams().height = columnWidth;
            inflatedView.setTag(holder);
        }  else{
            holder =(viewHolder)inflatedView.getTag();
        }
        MyData myData = bindArrData.get(position);
        Glide.with(context).load(myData.photo_id).into(holder.iv);

        return inflatedView;
    }
}
