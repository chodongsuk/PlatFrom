package kr.ds.platfrom_gallery;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import kr.ds.platfrom_gallery_utils.BitmapResize;
import kr.ds.platfrom_gallery_utils.ProcessManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GalleryDetailActivity extends Activity {
	private GridView mGridView;
	private GalleryDetailBaseAdapter mGalleryDetailBaseAdapter;
	private ParcelabelItem mSavedata;
	private Bundle mBundle;
	private List<String> mData = new ArrayList<String>(); 
	private ArrayList<GalleryDetailHandler> mData2 = new ArrayList<GalleryDetailHandler>(); 
	private GalleryDetailHandler mDetailHandler = new GalleryDetailHandler();
	private ImageButton mImageButtonSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
			mSavedata = (ParcelabelItem) savedInstanceState.getParcelable("data");
			mData = mSavedata.getmItem();
		}else{
			mBundle = getIntent().getExtras();
			mSavedata = (ParcelabelItem) mBundle.getParcelable("data");
			mData = mSavedata.getmItem();
		}
		
		setContentView(R.layout.gallery_detail);
		(mImageButtonSave = (ImageButton)findViewById(R.id.imageButton_save)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String strs = "";
				for(int i = 0; i < mData2.size(); i++){
					if(mData2.get(i).isSeleted()){
						strs += mData2.get(i).getSdcardPath()+","; 
					}
				}
				Intent intent = new Intent();
				if(!strs.matches("")){
					String strs2 = strs.substring(0, strs.length()-1);
					String[] datas = strs2.split(",");
					intent.putExtra("data", datas);
				}
				setResult(RESULT_OK, intent);
				finish();
				ProcessManager.getInstance().allEndActivity();
				overridePendingTransition(0, 0);
			}
		});
		mGridView = (GridView) findViewById(R.id.gridView_layout);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//mGalleryDetailBaseAdapter.update();
			}
		});
		new LoadTask().execute();
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("data", mSavedata);
	}
	
	private class LoadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			for(int i=0; i< mData.size(); i++){
				mData2.add(new GalleryDetailHandler());
				mDetailHandler = mData2.get(mData2.size()-1);
				mDetailHandler.setSdcardPath(mData.get(i).toString());
				mDetailHandler.setSeleted(false);//±âº».
			}
			return null;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mGalleryDetailBaseAdapter = new GalleryDetailBaseAdapter(getApplicationContext(), mData2);
			mGridView.setAdapter(mGalleryDetailBaseAdapter);
		}
    }
}
