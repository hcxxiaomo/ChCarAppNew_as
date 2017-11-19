package com.xiaomo.chcarappnew.adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaomo.chcarappnew.R;
import com.xiaomo.chcarappnew.util.BaseViewHolder;
import com.xiaomo.db.model.CarIllegalInfo;

import java.util.List;

public class CarIllegalHistoryResultInfoAdapter extends BaseAdapter {

	private List<CarIllegalInfo> mListCarHisInfo = null;
	private Context mContext;

	public CarIllegalHistoryResultInfoAdapter(
			List<CarIllegalInfo> mListCarHisInfo, Context mContext) {
		super();
		this.mListCarHisInfo = mListCarHisInfo;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mListCarHisInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mListCarHisInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.carnumber_illegal_list_item, parent,false);
		}
		CarIllegalInfo ci = mListCarHisInfo.get(position);
		TextView raid_carnumber = BaseViewHolder.get(convertView, R.id.raid_carnumber_illegal);
		TextView raid_illegalstring = BaseViewHolder.get(convertView, R.id.raid_illegalstring_illegal);
		TextView raid_isreport = BaseViewHolder.get(convertView, R.id.raid_isreport_illegal);
		TextView raid_time = BaseViewHolder.get(convertView, R.id.raid_time_illegal);
		raid_carnumber.setText(ci.carNumber);
		
		raid_illegalstring.setText(ci.illegalId.concat("-").concat(ci.illegalInfo));
		if (ci.isReported == 1){
            raid_isreport.setText("已上报");
        }else {
            raid_isreport.setText("未上报");
        }
		raid_time.setText(ci.createTime);
		return convertView;
	}

}
