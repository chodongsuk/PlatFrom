package kr.ds.platfrom_gallery;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import kr.ds.platfrom_gallery_utils.BitmapResize;
import kr.ds.platfrom_gallery_utils.LRUCache;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryBaseAdapter extends BaseAdapter{
	
	private Context mContext;
	private Map<String, List<String>> mData;
	private LayoutInflater mInflater;
	private String[] mKeys;
	private LRUCache mCache;
	
	private Boolean mScrolling = false;
	private SparseArray<WeakReference<View>> mViewArray;

	public GalleryBaseAdapter(Context context, Map<String, List<String>> data) {
		mContext = context;
		mData = data;
		mKeys = mData.keySet().toArray(new String[mData.size()]);
		mInflater = LayoutInflater.from(mContext);
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
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
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
        	convertView = mInflater.inflate(R.layout.gallery_item, null);
        	ImageView list_image = (ImageView) convertView.findViewById(R.id.list_imageview);
			TextView list_text1 = (TextView) convertView.findViewById(R.id.list_textview1);
			TextView list_text2 = (TextView) convertView.findViewById(R.id.list_textview2);
 
			list_text1.setText(mKeys[position].toString().replace("/", ""));
			String path = mData.get(mKeys[position]).get(0).toString();
			Bitmap bmp = (Bitmap) mCache.get(path);
				if (bmp != null) {
					list_image.setImageBitmap(bmp);
				}else{
					new LoadImageTask(list_image).execute(path);
					mCache.put(path, bmp); 
				}
			list_text2.setText(String.valueOf(mData.get(mKeys[position]).size()));
        } finally {
            mViewArray.put(position, new WeakReference<View>(convertView));
        }
        return convertView;
	}
	class ViewHolder {
		TextView list_text1;
		TextView list_text2;
		ImageView list_image;
	}
	
	private class LoadImageTask extends AsyncTask<String, String, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		LoadImageTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}
        @Override
        protected Bitmap doInBackground(String... params) {
        	Bitmap bitmap = new BitmapResize().decodeFile(params[0], mContext, 70, 70);
            //Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.dessert, width, height);
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
