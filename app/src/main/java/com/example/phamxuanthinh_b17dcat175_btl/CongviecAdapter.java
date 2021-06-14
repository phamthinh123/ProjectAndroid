package com.example.phamxuanthinh_b17dcat175_btl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phamxuanthinh_b17dcat175_btl.CongViec;

import java.util.ArrayList;
import java.util.List;

public class CongviecAdapter extends RecyclerView.Adapter<CongviecAdapter.ViewHolder> implements Filterable {
    private List<CongViec> congViecList;
    private List<CongViec> congViecListFull;
    private Context mContext;

    public CongviecAdapter(List<CongViec> congViecList, Context mContext) {
        this.congViecList = congViecList;
        congViecListFull=new ArrayList<>(congViecList);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View congViecView=inflater.inflate(R.layout.item_congviec,parent,false);
        ViewHolder viewHolder=new ViewHolder(congViecView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.ten.setText(congViecList.get(position).getTen());

        holder.ngay.setText(congViecList.get(position).getNgay());
        holder.gio.setText(congViecList.get(position).getGio());
        holder.mota.setText(congViecList.get(position).getMota());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, EditActivity.class);
                intent.putExtra("cv",congViecList.get(position));
                mContext.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return congViecList.size();
    }

    @Override
    public Filter getFilter() {
        return congViecFilter;
    }
    private Filter congViecFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CongViec> filterlist=new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filterlist.addAll(congViecListFull);
            }
            else{
                String pattrn=constraint.toString().toLowerCase().trim();
                for(CongViec item : congViecListFull){
                    if(item.getTen().toLowerCase().contains(pattrn)){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            congViecList.clear();
            congViecList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ten, mota, ngay,gio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tvTitle);

            ngay =  itemView.findViewById(R.id.tvDate);
            gio=itemView.findViewById(R.id.tvHour);
            mota =  itemView.findViewById(R.id.tvDes);
        }
    }
}
