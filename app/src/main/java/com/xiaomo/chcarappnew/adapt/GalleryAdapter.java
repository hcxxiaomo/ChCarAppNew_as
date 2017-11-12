package com.xiaomo.chcarappnew.adapt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaomo.chcarappnew.R;

import java.util.List;

public class GalleryAdapter extends
RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
	
	 /** 
     * ItemClick的回调接口 
     * @author zhy 
     * 
     */  
    public interface OnItemClickLitener  
    {  
        void onItemClick(View view, int position);
    }  
  
    private OnItemClickLitener mOnItemClickLitener;  
  
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)  
    {  
        this.mOnItemClickLitener = mOnItemClickLitener;  
    }  
  
    private LayoutInflater mInflater;
    private List<String> mDatas;
    private String[] data;
  
    public GalleryAdapter(Context context, List<String> datats)
    {  
        mInflater = LayoutInflater.from(context);
        mDatas = datats;  
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder
    {  
        public ViewHolder(View arg0)
        {  
            super(arg0);  
        }  
  
        //ImageView mImg;
        TextView txtTitle;
        TextView txtContent;
    }


    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        View view = mInflater.inflate(R.layout.select_item,
                viewGroup, false);  
        ViewHolder viewHolder = new ViewHolder(view);  
  
        viewHolder.txtTitle = (TextView) view
                .findViewById(R.id.id_main_select_item_title);
        viewHolder.txtContent = (TextView) view
                .findViewById(R.id.id_main_select_item_content);
        return viewHolder;
    }  
  
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)  
    {  
        //viewHolder.mImg.setImageResource(mDatas.get(i));
        data = mDatas.get(i).split("_");
        viewHolder.txtTitle.setText(data[1]);
        viewHolder.txtContent.setText(data[0]);

        //如果设置了回调，则设置点击事件  
        if (mOnItemClickLitener != null)  
        {  
            viewHolder.itemView.setOnClickListener(new OnClickListener()
            {  
                @Override
                public void onClick(View v)
                {  
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
                }  
            });  
  
        }  
  
    }  

}
