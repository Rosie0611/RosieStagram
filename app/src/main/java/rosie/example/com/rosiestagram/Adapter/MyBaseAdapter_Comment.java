//package rosie.example.com.rosiestagram.Adapter;
//
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//
//import io.realm.OrderedRealmCollection;
//import io.realm.RealmBaseAdapter;
//import rosie.example.com.rosiestagram.Activity.Main6Activity_Comment_Listview;
//import rosie.example.com.rosiestagram.Data.MyData_Comment;
//import rosie.example.com.rosiestagram.R;
//
///**
// * Created by skyki on 2017-07-28.
// */
//
//public class MyBaseAdapter_Comment extends RealmBaseAdapter<MyData_Comment> implements ListAdapter {
//    Main6Activity_Comment_Listview context;
//    int iLayoutResId;
//    ArrayList<MyData_Comment> arrData = new ArrayList<>();
//
//
//
//    public MyBaseAdapter_Comment(@Nullable OrderedRealmCollection<MyData_Comment> data, Main6Activity_Comment_Listview context, int iLayoutResId) {
//        super(data);
//        this.context = context;
//        this.iLayoutResId = iLayoutResId;
//    }
//    public class ViewHolder{
//        TextView tv_userID,tv_comment,tv_commentDate,bt_deleteComent;
//
//        public ViewHolder(TextView tv_userID, TextView tv_comment, TextView tv_commentDate, TextView bt_deleteComent) {
//            this.tv_userID = tv_userID;
//            this.tv_comment = tv_comment;
//            this.tv_commentDate = tv_commentDate;
//            this.bt_deleteComent = bt_deleteComent;
//        }
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        ViewHolder holder=null;
//        if(view==null) {
//            LayoutInflater inflater = context.getLayoutInflater();
//            view = inflater.inflate(iLayoutResId, null);
//            TextView tv_userID = (TextView) view.findViewById(R.id.tv_userId);
//            TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
//            TextView tv_commentDate = (TextView) view.findViewById(R.id.tv_commentDate);
//            Button bt_deleteComent = (Button) view.findViewById(R.id.bt_deleteComment);
//
//            holder = new ViewHolder(tv_userID, tv_comment, tv_commentDate, bt_deleteComent);
//            view.setTag(holder);
//        } else{
//            holder=(ViewHolder)view.getTag();
//        }
//        long date = System.currentTimeMillis();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d yyyy", Locale.ENGLISH);
//        String format = simpleDateFormat.format(new Date(date));
//
//        MyData_Comment data_comment = adapterData.get(position);
//        holder.tv_userID.setText(data_comment.getId());
//        holder.tv_comment.setText(data_comment.getComment());
//        holder.tv_commentDate.setText(format);
//
//        return view;
//    }
//
//
//}
