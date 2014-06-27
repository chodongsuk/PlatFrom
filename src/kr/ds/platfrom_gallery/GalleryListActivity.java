package kr.ds.platfrom_gallery;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kr.ds.platfrom_gallery_utils.ProcessManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class GalleryListActivity extends Activity {
	private Map<String, List<String>> mImages;
	private Map<String, List<String>> mTreeMapImages;
	private ListView mListView;
	private GalleryClass mGallery;
	private GalleryBaseAdapter mContentBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		mListView = (ListView)findViewById(R.id.listview);
		mGallery = new GalleryClass(getApplicationContext());
		mListView.setOnItemClickListener(mClickListener);
		new Task().execute();
		ProcessManager.getInstance().addActivity(GalleryListActivity.this);
	}
	
	AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent NextIntent = new Intent(getApplicationContext(), GalleryDetailActivity.class);
			ParcelabelItem data = new ParcelabelItem(mTreeMapImages.get(mTreeMapImages.keySet().toArray(new String[mTreeMapImages.size()])[arg2]));
			NextIntent.putExtra("data", data);
			NextIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
			startActivity(NextIntent);
			overridePendingTransition(0, 0);
		}
	};
	private class Task extends AsyncTask<String, String, Map<String, List<String>>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected Map<String, List<String>> doInBackground(String... params) {
			// TODO Auto-generated method stub
			//TreeMap으로 정렬
			mImages = mGallery.getTotalImage();
			mTreeMapImages = new TreeMap<String, List<String>>(mImages);
			return mTreeMapImages;
		}
		@Override
		protected void onPostExecute(Map<String, List<String>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mContentBaseAdapter = new GalleryBaseAdapter(getApplicationContext(), result);
			mListView.setAdapter(mContentBaseAdapter);
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setResult(RESULT_CANCELED);
	}
}
