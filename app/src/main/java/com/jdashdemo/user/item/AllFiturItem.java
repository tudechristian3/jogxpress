package com.jdashdemo.user.item;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdashdemo.user.R;
import com.jdashdemo.user.activity.AllMerchantActivity;
import com.jdashdemo.user.activity.RentCarActivity;
import com.jdashdemo.user.activity.RideCarActivity;
import com.jdashdemo.user.activity.SendActivity;
import com.jdashdemo.user.constants.Constants;
import com.jdashdemo.user.models.AllFiturModel;
import com.jdashdemo.user.utils.PicassoTrustAll;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Ccomments
 */

public class AllFiturItem extends RecyclerView.Adapter<AllFiturItem.ItemRowHolder> {

    private List<AllFiturModel> dataList;
    private Context mContext;
    private int rowLayout;


    public AllFiturItem(Context context, List<AllFiturModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final AllFiturModel singleItem = dataList.get(position);
        holder.text.setText(singleItem.getFitur());
            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESFITUR + singleItem.getIcon())
                    .resize(100, 100)
                    .into(holder.image);


        if (!singleItem.getHome().equals("1")) {
            if (!singleItem.getHome().equals("2")) {
                if (singleItem.getHome().equals("3")) {
                    holder.background.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(mContext, RentCarActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("FiturKey", singleItem.getIdFitur());
                            i.putExtra("icon", singleItem.getIcon());
                            mContext.startActivity(i);

                        }
                    });
                } else if (singleItem.getHome().equals("4")) {
                    holder.background.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(mContext, AllMerchantActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("FiturKey", singleItem.getIdFitur());
                            mContext.startActivity(i);

                        }
                    });
                }
            } else {
                holder.background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, SendActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("FiturKey", singleItem.getIdFitur());
                        i.putExtra("job", singleItem.getJob());
                        i.putExtra("icon", singleItem.getIcon());
                        mContext.startActivity(i);

                    }
                });
            }
        } else {
            holder.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, RideCarActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("FiturKey", singleItem.getIdFitur());
                    i.putExtra("job", singleItem.getJob());
                    i.putExtra("icon", singleItem.getIcon());
                    mContext.startActivity(i);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView background,image;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
        }
    }
}
