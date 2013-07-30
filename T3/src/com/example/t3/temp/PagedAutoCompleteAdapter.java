package com.example.t3.temp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.t3.R;

public class PagedAutoCompleteAdapter extends BaseAdapter
		implements Filterable {
	private Context context;

	// 原始数据
	ArrayList<String[]> mOriginalValues;

	// 过滤后生成的View
	ArrayList<String[]> mObjects;
	private final Object mLock = new Object();
	int mPageSize;
	int mPageIndex;
	private Filter mFilter;

	public PagedAutoCompleteAdapter(Context context,
			ArrayList<String[]> mOriginalValues, int mPageSize) {
		super();
		this.context = context;
		this.mOriginalValues = mOriginalValues;
		this.mPageSize = mPageSize;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if (null == mFilter) {
			mFilter = new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					// TODO Auto-generated method stub
					FilterResults results = new FilterResults();
					results.values = mOriginalValues;
					results.count = mOriginalValues.size();
				/*	ArrayList<String[]> list = new ArrayList<String[]>();
					// 只返回特定页的数据
					for (int i = mPageIndex * mPageSize; i < (mPageIndex + 1)
							* mPageSize; i++) {
						if (i < mOriginalValues.size()) {
							list.add(mOriginalValues.get(i));
						}
					}
					// 增加一项，用来存放上下页按钮
					list.add(new String[1]);
					results.values = list;
					results.count = list.size();*/
					return results;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					// TODO Auto-generated method stub
					synchronized (mLock) {
						mObjects = (ArrayList<String[]>) results.values;
						if (results.count > 0) {
							notifyDataSetChanged();
						} else {
							notifyDataSetInvalidated();
						}
					}
				}
			};
		}

		return mFilter;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int start = mPageIndex * mPageSize;
		int end = start + mPageSize;
		if (end >= mObjects.size()) {
			return mObjects.size() % mPageSize + 1;
		} else {
			return mPageSize + 1;
		}		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		class DropdDownViewHolder {
			// 正常数据
			TextView tv0;
			TextView tv1;
			TextView tv2;
		}

		class SwitchPageViewHolder {
			Button prePage;
			TextView desc;
			Button nextPage;
		}

		int totalPage = mObjects.size() % mPageSize == 0 ? mObjects
				.size() / mPageSize
				: mObjects.size() / mPageSize + 1;
				
		// TODO Auto-generated method stub
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			boolean shouldAddSwitchLayout = false;
			if (mPageIndex < totalPage - 1) {
				//如果不是最后一页，则当position 等于mPageSize时，需要增加;
				shouldAddSwitchLayout = position == mPageSize;
			} else {
				//如果是最后一页
				if (totalPage % mPageSize == 0) {
					//如果最后一页能被页面整除，则当position等于mPageSize时，需要增加
					shouldAddSwitchLayout = position == mPageSize;
				} else {
					shouldAddSwitchLayout = position == mObjects.size() % mPageSize;
				}
			}

			if (shouldAddSwitchLayout) {
				// 处理分页按钮
				SwitchPageViewHolder switchHolder = new SwitchPageViewHolder();
				convertView = inflater.inflate(R.layout.autocomplete_switch,
						null);
				switchHolder.prePage = (Button) convertView
						.findViewById(R.id.prePage);
				switchHolder.desc = (TextView) convertView
						.findViewById(R.id.desc);
				switchHolder.nextPage = (Button) convertView
						.findViewById(R.id.nextPage);

				switchHolder.prePage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mPageIndex - 1 >= 0) {
							mPageIndex--;
							
							notifyDataSetChanged();
						}
						
					}
				});

				switchHolder.nextPage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int totalPage = mObjects.size() % mPageSize == 0 ? mObjects
								.size() / mPageSize
								: mObjects.size() / mPageSize + 1;
						if (mPageIndex + 1 <= totalPage  -1) {
							mPageIndex++;
							
							notifyDataSetChanged();
						}
					}
				});

				

				switchHolder.desc.setText(String.format("总共 %d 页，第 %d 页",
						totalPage, mPageIndex + 1));
			} else {
				
				DropdDownViewHolder viewHolder = new DropdDownViewHolder();
				convertView = inflater.inflate(R.layout.autocomplete_drop_down,
						null);
				viewHolder.tv0 = (TextView) convertView.findViewById(R.id.tv0);
				viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
				viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
				viewHolder.tv0.setText(mObjects.get(position + mPageIndex * mPageSize)[0]);
				viewHolder.tv1.setText(mObjects.get(position + mPageIndex * mPageSize)[1]);
				viewHolder.tv2.setText(mObjects.get(position + mPageIndex * mPageSize)[2]);
			}
		}

		return convertView;
	}
}
