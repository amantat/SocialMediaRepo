package com.osi.socialmedia.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.osi.socialmedia.R;
import com.osi.socialmedia.model.GridBlock;

public class MainGridAdapter extends BaseAdapter {

	private ArrayList<GridBlock> dataArray;
	private LayoutInflater inflater;
	private Context  context;

	public MainGridAdapter(Context cont,ArrayList<GridBlock> arr) {
		this.dataArray = arr;
		this.inflater = LayoutInflater.from(cont);
		this.context = cont;
	}

	@Override
	public int getCount() {
		return dataArray.size();
	}

	@Override
	public GridBlock getItem(int position) {
		return dataArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.single_grid_item, parent,false);
			viewHolder = new ViewHolder();
			viewHolder.gridImg = (ImageView)convertView.findViewById(R.id.gridImage);
			viewHolder.gridTxt = (TextView)convertView.findViewById(R.id.gridText);

			convertView.setTag(viewHolder);

		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		GridBlock gBlock = dataArray.get(position);

		int ImageResourceId = context.getResources().getIdentifier(gBlock.getGridImageName(), "drawable", context.getPackageName());

		viewHolder.gridImg.setImageResource(ImageResourceId);

		viewHolder.gridTxt.setText(gBlock.getGridText());

		return convertView;
	}

	class ViewHolder{
		ImageView gridImg;
		TextView gridTxt;
	}

}
