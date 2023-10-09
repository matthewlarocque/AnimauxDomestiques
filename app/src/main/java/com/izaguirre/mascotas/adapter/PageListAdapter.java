package com.izaguirre.mascotas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.izaguirre.mascotas.R;

import java.util.List;

public class PageListAdapter extends RecyclerView.Adapter {
    private List<PageListModel> pageListModels;

    public interface OnItemClickListener{
        void onItemClick (View view, int position);
    }

    private PageListAdapter.OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(PageListAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public PageListAdapter(List<PageListModel> pageListModels){
        this.pageListModels = pageListModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.ztv_mis);
        }

        public TextView getText(){
            return text;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PageListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.setitem_list,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PageListAdapter.ViewHolder vh = (PageListAdapter.ViewHolder) holder;
        vh.getText().setText(pageListModels.get(position).getText());
        if (mOnItemClickListener != null) {
            vh.itemView.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(vh.itemView, vh.getAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return pageListModels.size();
    }
}
