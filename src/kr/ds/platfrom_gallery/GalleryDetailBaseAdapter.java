package kr.ds.platfrom_gallery;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import kr.ds.platfrom_gallery_utils.BitmapResize;
import kr.ds.platfrom_gallery_utils.LRUCache;
import kr.ds.platfrom_gallery_utils.ScreenUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryDetailBaseAdapter extends BaseAdapter{
	
	private Context mContext;
	private ArrayList<GalleryDetailHandler> mData;
	private LayoutInflater mInflater;
	private String[] mKeys;
	private LRUCache mCache;
	private SparseArray<WeakReference<View>> mViewArray;
	private int mPosition;
	private int selecteds = 0;
	public GalleryDetailBaseAdapter(Context context, ArrayList<GalleryDetailHandler> data) {
		mContext = context;
		mData = data;
		mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		mViewArray = new SparseArray<WeakReference<View>>(mData.size());
		// 캐쉬 초기화 : 캐쉬의 최대 보관 크기 100개
		mCache = new LRUCache<String, Bitmap>(100);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public void update() {
		mViewArray.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(mViewArray != null && mViewArray.get(position) != null) {
			convertView = mViewArray.get(position).get();
			if(convertView != null)
				return convertView;
        }
        try {
        	convertView = mInflater.inflate(R.layout.gallery_detail_item, null);
        	ImageView list_image = (ImageView) convertView.findViewById(R.id.imageView_photo);
        	final ImageView list_selected = (ImageView) convertView.findViewById(R.id.imageView_selected);
        	if (mData.get(position).isSeleted()){
        		list_selected.setBackgroundResource(R.drawable.pg_selected);
        	}else{
				list_selected.setBackgroundResource(R.drawable.pg_selected_up);
        	}
        	
        	mPosition = position;
        	list_selected.setTag(mPosition);
        	list_selected.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    					mPosition = (Integer) v.getTag();
	    				if(mData.get(mPosition).isSeleted()){
	    					selecteds--;
	    					if(selecteds == 0){
	    						selecteds = 0;
	    					}
	    					mData.get(mPosition).setSeleted(false);
	    				}else{
	    					if(selecteds < GallerySetting.IMAGE_SELECT){
	    						selecteds++;
	    						mData.get(mPosition).setSeleted(true);
	    					}else{
	    						Toast.makeText(mContext, GallerySetting.IMAGE_SELECT + "개 까지만 선택이 가능 합니다.", Toast.LENGTH_SHORT).show();
	    					}
	    				}	
	    				update();
    			}
    		});
        	
        	String path = mData.get(position).getSdcardPath().toString();
			Bitmap bmp = (Bitmap) mCache.get(path);
				if (bmp != null) {
					list_image.setImageBitmap(bmp);
				}else{
					new LoadImageTask(list_image).execute(path);
					
				}
        } finally {
            mViewArray.put(position, new WeakReference<View>(convertView));
        }
        return convertView;
	}
	private class LoadImageTask extends AsyncTask<String, String, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private ImageView mImageView;
		LoadImageTask(ImageView imageView) {
			mImageView = imageView;
			imageViewReference = new WeakReference<ImageView>(imageView);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

        @Override
        protected Bitmap doInBackground(String... params) {
        	Bitmap bitmap = new BitmapResize().decodeFile(params[0], mContext, ScreenUtils.getScreenWidth(mContext)/5, ScreenUtils.getScreenHeight(mContext)/20);
        	mCache.put(params[0], bitmap); 
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
				final ImageView mImageView = imageViewReference.get();
				if (mImageView != null) {
					mImageView.setImageBitmap(bitmap);
		        	mImageView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
				}
			}
        }
    }
}
