package com.xiaomo.chcarappnew.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xiaomo.chcarappnew.R;

public class MyRecyclerView extends RecyclerView {

	 public MyRecyclerView(Context context, AttributeSet attrs)
	    {  
	        super(context, attrs);  
	    }  
	  
	    private View mCurrentView;
	    private View mMiddleCurrentView;

	    /** 
	     * 滚动时回调的接口 
	     */  
	    private OnItemScrollChangeListener mItemScrollChangeListener;  
	  
	    public void setOnItemScrollChangeListener(  
	            OnItemScrollChangeListener mItemScrollChangeListener)  
	    {  
	        this.mItemScrollChangeListener = mItemScrollChangeListener;  
	    }  
	  
	    public interface OnItemScrollChangeListener  
	    {  
	        void onChange(View view, int position);
	    }  
	  
	    @Override
	    protected void onLayout(boolean changed, int l, int t, int r, int b)  
	    {  
	        super.onLayout(changed, l, t, r, b);  
	  
	        mCurrentView = getChildAt(0);  
	  
	        if (mItemScrollChangeListener != null)  
	        {  
	            mItemScrollChangeListener.onChange(mCurrentView,  
	                    getChildPosition(mCurrentView));  
	        }
            //增加修改背景色的功能
            mMiddleCurrentView = getChildAt(1);
            //mMiddleCurrentView.setBackgroundResource(R.drawable.select);
            mMiddleCurrentView.setBackgroundResource(R.drawable.select_item_border);
            for(int i = 0, count = getChildCount(); i < count ; i++){
                if(1 != i){
                    mMiddleCurrentView = getChildAt(i);
                    mMiddleCurrentView.setBackgroundResource(0);
                }
            }
	    }  
	  
	    @Override
	    public boolean onTouchEvent(MotionEvent e)
	    {  
	  
	        if (e.getAction() == MotionEvent.ACTION_MOVE)
	        {  
	            mCurrentView = getChildAt(0);  
	            // Log.e("TAG", getChildPosition(getChildAt(0)) + "");  
	            if (mItemScrollChangeListener != null)  
	            {  
	                mItemScrollChangeListener.onChange(mCurrentView,  
	                        getChildPosition(mCurrentView));  
	  
	            }
				//增加修改背景色的功能
				mMiddleCurrentView = getChildAt(1);
               //mMiddleCurrentView.setBackgroundResource(R.drawable.select);
                mMiddleCurrentView.setBackgroundResource(R.drawable.select_item_border);
                for(int i = 0, count = getChildCount(); i < count ; i++){
                    if(1 != i){
                        mMiddleCurrentView = getChildAt(i);
                        mMiddleCurrentView.setBackgroundResource(0);
                    }
                }
	  
	        }  
	  
	        return super.onTouchEvent(e);  
	    }  
	
}
