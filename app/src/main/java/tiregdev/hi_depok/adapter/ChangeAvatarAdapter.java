package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.AvatarList;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class ChangeAvatarAdapter extends RecyclerView.Adapter<ChangeAvatarAdapter.holder_avatar> {
    private List<AvatarList> itemList;
    private Context context;
    
    public ChangeAvatarAdapter(Context context, List<AvatarList> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ChangeAvatarAdapter.holder_avatar onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_avatar,null);
        ChangeAvatarAdapter.holder_avatar hn = new ChangeAvatarAdapter.holder_avatar(layoutView);
        return hn;
    }


    @Override
    public void onBindViewHolder(ChangeAvatarAdapter.holder_avatar holder, int position) {
        holder.nama.setText(itemList.get(position).getNama());
        holder.avatar.setImageResource(itemList.get(position).getAvatar());
    }

    @Override
    public int getItemCount(){
        return this.itemList.size();
    }

    public class holder_avatar extends RecyclerView.ViewHolder {
        public TextView nama;
        public ImageView avatar;

        public holder_avatar(View itemView){
            super(itemView);

            nama = (TextView)itemView.findViewById(R.id.nama);
            avatar = (ImageView)itemView.findViewById(R.id.avatar);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, detail_avatar.class);
//                    context.startActivity(intent);
//                }
//            });
        }
    }
}
