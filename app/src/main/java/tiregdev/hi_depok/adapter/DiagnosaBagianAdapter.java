package tiregdev.hi_depok.adapter;

/**
 * Created by SONY-VAIO on 3/15/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import tiregdev.hi_depok.R;
import tiregdev.hi_depok.model.DataModel;
import tiregdev.hi_depok.activity.Gejala;


public class DiagnosaBagianAdapter extends RecyclerView.Adapter<DiagnosaBagianAdapter.ViewHolder> {

    Context context;

    List<DataModel> adapter;
    String id;
    String nama;
    String idgejala;
    String simpen;

    public DiagnosaBagianAdapter(List<DataModel> adapter, Context context){

        super();

        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnosa_ensiklopedia_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        id = adapter.get(position).getId();
        nama = adapter.get(position).getNama();

        holder.title.setText(nama);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {

        return adapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;


        public ViewHolder(final View itemView) {

            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title_ensiklopedia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    simpen = pref.getString("idgejala","0");
                    Intent intent = new Intent(context, Gejala.class);
                    idgejala = (String) itemView.getTag();
                    if(simpen == "0"){
                        simpen = idgejala;
                    }
                    else {
                        simpen = simpen + "," +idgejala;
                    }
//                    intent.putExtra("getPage", hal);
                    Log.d("JSONAdapter", "get page: " + simpen);
                    editor.putString("idgejala", simpen);
                    editor.commit();
                    context.startActivity(intent);
                }
            });


        }
    }
}
