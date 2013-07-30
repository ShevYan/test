package com.example.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.t3.MUtil;
import com.example.t3.R;

public class PagerView extends RelativeLayout {
	private ViewPager mPager;
	private OnPageChangeListener pageListener;
	private MyPagerAdapter mAdapter;
	/** 页面Header */
	private LinearLayout mHeaderLayout;
	/** 游标View */
	private ImageView mCursorView;
	int mCurrentIndex = 0;
	
	public PagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.pager_view, this, true);
		//初始化mPager
		mPager = (ViewPager)findViewById(R.id.pager);
		mHeaderLayout = (LinearLayout)findViewById(R.id.header);
		mCursorView = (ImageView)findViewById(R.id.record_cursor);
		
		ViewTreeObserver vto = mHeaderLayout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            public boolean onPreDraw()
            {
            	// 调整head的宽度
        		int childCount = mHeaderLayout.getChildCount();
        		MUtil.ASSERT(childCount == mAdapter.getCount());
        		for (int i=0; i<childCount; i++) {
        			View v = mHeaderLayout.getChildAt(i);
        			ViewGroup.LayoutParams lp = v.getLayoutParams();
        			lp.width = mHeaderLayout.getMeasuredWidth() / mAdapter.getCount();
        			v.setLayoutParams(lp);
        		}
        		
        		//调整cursor宽度 
        		ViewGroup.LayoutParams lp = mCursorView.getLayoutParams();
        		lp.width = mHeaderLayout.getMeasuredWidth() / mAdapter.getCount();
        		mCursorView.setLayoutParams(lp);
        		
                return true;
            }
        });
		
		//初始化Adapter
		mAdapter = new MyPagerAdapter();
		//为Pager设置Adapter
		mPager.setAdapter(mAdapter);
		
		//初始化pageListener
		pageListener = new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setCurrentPage(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		mPager.setOnPageChangeListener(pageListener);
	}

	public void addPage(View headerItem, View pageItem) {
		// 设置headerItem的listener
		headerItem.setOnClickListener(new MyOnClickListener(mAdapter.getCount()));
		
		//添加到HeaderLayout
		mHeaderLayout.addView(headerItem);
		
		//添加子View
		mPager.addView(pageItem);
		mAdapter.addPage(pageItem);
	}
	
	public void setCurrentPage(int index) {
		int from, to;
		if (mCurrentIndex == index) {
			return;
		}
		int headerItemWidth = mHeaderLayout.getMeasuredWidth() / mAdapter.getCount();
		from = mCurrentIndex * headerItemWidth;
		to = index * headerItemWidth;
		
		Animation animation = null;
		animation = new TranslateAnimation(from, to, 0, 0);
		mCurrentIndex = index;
		Log.v("PagerView", "mCurrentIndex:" + mCurrentIndex + " move from " + from
				+ " to " + to);
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		mCursorView.startAnimation(animation);
	}
	
	private class MyOnClickListener implements OnClickListener{  
        private int index=0;  
        
        public MyOnClickListener(int i){  
            index=i;  
        }  
        
        public void onClick(View v) {  
        	mPager.setCurrentItem(index);              
        }  
          
    }
	
	private class MyPagerAdapter extends PagerAdapter {

		private List<View> mListViews = new ArrayList<View>();

		public void setPageList(List<View> views) {
			mListViews.addAll(views);
			notifyDataSetChanged();
		}

		public void addPage(View view) {
			mListViews.add(view);
			notifyDataSetChanged();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListViews.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			// super.destroyItem(container, position, object);
			//container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			// return super.instantiateItem(container, position);
			return mListViews.get(position);
		}

	}
}
