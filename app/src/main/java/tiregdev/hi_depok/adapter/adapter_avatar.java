package tiregdev.hi_depok.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.itemObject_avatar;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class adapter_avatar extends RecyclerView.Adapter<adapter_avatar.holder_avatar> {
    private List<itemObject_avatar> itemList;
    private Context context;
    
    public adapter_avatar(Context context, List<itemObject_avatar> itemList){
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public adapter_avatar.holder_avatar onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_avatar,null);
        adapter_avatar.holder_avatar hn = new adapter_avatar.holder_avatar(layoutView);
        return hn;
    }


    @Override
    public void onBindViewHolder(adapter_avatar.holder_avatar holder, int position) {
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
