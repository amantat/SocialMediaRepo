package com.osi.socialmedia.adapter;

import java.util.ArrayList;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.osi.socialmedia.R;
import com.osi.socialmedia.model.FeedsModel;
import com.osi.socialmedia.utils.ImageLoader;

@SuppressLint("NewApi")
public class FeedsAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<FeedsModel> dataList;
	private LayoutInflater inflater;
	private ImageLoader imageloader;
	private String provider_name;
	
	public FeedsAdapter(ArrayList<FeedsModel> dataList,String provider_name,
			Context context) {
		this.dataList = dataList;
		this.context = context;
		this.provider_name=provider_name;
		imageloader= new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		final ListViewHolder vh;
		if (convertView == null) {
			vh=new ListViewHolder();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.list_item, parent,	false);
			vh.profile_name = (TextView) convertView.findViewById(R.id.profile_name);
			vh.feed_text = (TextView) convertView.findViewById(R.id.feed_text);
			vh.feed_date = (TextView) convertView.findViewById(R.id.feed_date);
			vh.user_image=(ImageView) convertView.findViewById(R.id.user_imag);
			
			convertView.setTag(vh);
		}else{
			vh = (ListViewHolder)convertView.getTag();
		}




		final FeedsModel ao = dataList.get(position);
		Log.d("SIZE", "SIZE"+dataList.size());

		vh.profile_name.setText(ao.getAuthorName());
		vh.feed_text.setText(ao.getFeedText());
		vh.feed_date.setText(ao.getPostedAt());
		if(provider_name.equalsIgnoreCase("twitter")){
		imageloader.DisplayImage(ao.getUserImageurl(), vh.user_image);
		}else{
			vh.user_image.setBackgroundResource(R.drawable.linkedin_icon);
		}
		
		playAnimation(convertView);
		
		return convertView;

	}
	
	private void playAnimation(View v){
		  AnimatorSet set = new AnimatorSet();
		  set.play(ObjectAnimator.ofFloat(v,View.SCALE_X, 0, 1));
		  set.play(ObjectAnimator.ofFloat(v, View.SCALE_Y, 0,1));
		  set.setDuration(300);
		  set.setInterpolator(new DecelerateInterpolator());
		  set.start();
		 }
	
	public class ListViewHolder{
		public TextView profile_name,feed_text,feed_date;
		public Button remove;
		private ImageView user_image;
	}

	
}
