package tiregdev.hi_depok.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.itemObject_ntpd;

/**
 * Created by Muhammad63 on 10/6/2017.
 */

public class adapter_ntpd extends RecyclerView.Adapter<adapter_ntpd.holder_ntpd> {
    
    private List<itemObject_ntpd> list;
    private Context context;
    
    public adapter_ntpd(Context context, List<itemObject_ntpd> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public adapter_ntpd.holder_ntpd onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ntpd,null);
        adapter_ntpd.holder_ntpd hn = new adapter_ntpd.holder_ntpd(layoutView);
        return hn;
    }

    @Override
    public void onBindViewHolder(adapter_ntpd.holder_ntpd holder, int position) {
        holder.nama.setText(list.get(position).getNama());
        holder.nmr.setText(list.get(position).getNmr());

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    
    public class holder_ntpd extends RecyclerView.ViewHolder{
        public TextView nama, nmr;
        public ImageView call, copy;
        
        public holder_ntpd(final View itemView){
            super(itemView);

            nama = (TextView)itemView.findViewById(R.id.nama);
            nmr = (TextView)itemView.findViewById(R.id.nmr);
            call = (ImageView)itemView.findViewById(R.id.call);
            copy = (ImageView)itemView.findViewById(R.id.copy);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context c = view.getContext();
                    Intent dial = new Intent();
                    dial.setAction("android.intent.action.DIAL");
                    dial.setData(Uri.parse("tel:" + list.get(getAdapterPosition()).getNmr()));
                    c.startActivity(dial);

                }
            });

            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context c = view.getContext();
                    ClipboardManager clipboard = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied to clipboard", list.get(getAdapterPosition()).getNama());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(c, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
