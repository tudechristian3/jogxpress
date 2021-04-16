package com.jdashdemo.user.item;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdashdemo.user.R;
import com.jdashdemo.user.models.CatItemModel;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * comments
 */

public class CatItemItem extends RecyclerView.Adapter<CatItemItem.ItemRowHolder> {

    private List<CatItemModel> dataList;
    private Context mContext;
    private int rowLayout;
    private int selectedPosition=0;
    private CatItemItem.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CatItemModel item);
    }

    public CatItemItem(Context context, List<CatItemModel> dataList, int rowLayout, CatItemItem.OnItemClickListener listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final CatItemModel singleItem = dataList.get(position);

        holder.text.setText(singleItem.getNama_kategori());

        if(position==selectedPosition)
        {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_rect));
        }
        else
        {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_bordered));
            Random rand = new Random();
            int i = rand.nextInt(4);


            switch (i) {
                case 1:
                    holder.background.getBackground().setColorFilter(Color.parseColor("#fd0f57"), PorterDuff.Mode.SRC_ATOP);
                    holder.text.setTextColor(Color.parseColor("#fd0f57"));
                    break;
                case 2:
                    holder.background.getBackground().setColorFilter(Color.parseColor("#03abcc"), PorterDuff.Mode.SRC_ATOP);
                    holder.text.setTextColor(Color.parseColor("#03abcc"));
                    break;

                case 3:
                    holder.background.getBackground().setColorFilter(Color.parseColor("#00d953"), PorterDuff.Mode.SRC_ATOP);
                    holder.text.setTextColor(Color.parseColor("#00d953"));
                    break;

                case 4:
                    holder.background.getBackground().setColorFilter(Color.parseColor("#ffb700"), PorterDuff.Mode.SRC_ATOP);
                    holder.text.setTextColor(Color.parseColor("#ffb700"));
                    break;

                default:
                    break;
            }
        }
        holder.bind(singleItem,listener);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout background;

        ItemRowHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            background = itemView.findViewById(R.id.background);
        }
        public void bind(final CatItemModel item, final CatItemItem.OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                    selectedPosition=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });


        }
    }


}
