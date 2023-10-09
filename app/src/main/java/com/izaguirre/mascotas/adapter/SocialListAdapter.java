package com.izaguirre.mascotas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.izaguirre.mascotas.R;

import java.util.List;

public class SocialListAdapter extends RecyclerView.Adapter {
    private List<SocialListModel> socialListModels;

    public interface OnItemClickListener{
        void onItemClick (View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public SocialListAdapter(List<SocialListModel> socialListModels){
        this.socialListModels = socialListModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView picture;
        private TextView title,text;
        public ViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.iv_pic);
            title = itemView.findViewById(R.id.ztv_title);
            text = itemView.findViewById(R.id.ztv_text);
        }

        public ImageView getPicture(){
            return picture;
        }

        public TextView getTitle(){
            return title;
        }

        public TextView getText(){
            return text;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;
        vh.getPicture().setImageResource(socialListModels.get(position)
                .getPicture());
        vh.getTitle().setText(socialListModels.get(position).getTitle());
        vh.getText().setText(socialListModels.get(position).getText());
        //ExpandListWidth(vh);
        if (mOnItemClickListener != null) {
            vh.itemView.setOnClickListener(v -> {
                mOnItemClickListener.onItemClick(vh.itemView, vh.getAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return socialListModels.size();
    }
}
