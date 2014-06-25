package kr.ds.platfrom_gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

public class GalleryClass {
	private Context mContext;
	
	public GalleryClass(Context context){
		mContext = context;
	}
	
	/**
	 * Total 
	 * ���� ���� ����.
	 */
	public Map<String, List<String>> getTotalImage() {
		Map<String, List<String>> images = new HashMap<String, List<String>>();
		List<String> dir = new ArrayList<String>();
		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
			if(cursor != null){
			    cursor.moveToLast();
			    int length = cursor.getCount();
			    cursor.moveToFirst();
			    for(int i = 0; i < length; ++i){
			        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)); 
			        String key = "/��ü����"; //�ӽ� ���� �ϱ�..
			        if(i == 0){
			        	images.put(key, dir);
			        }
			        dir.add(data);
			        cursor.moveToNext();
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getFolderImage(images);
        // show newest photo at beginning of the list
	}
	/**
	 * Map<String, List<String>> 
	 * ����, �迭 
	 */
	public Map<String, List<String>> getFolderImage(Map<String, List<String>> images){
		/*
		for(String s : images.keySet()){//set �̾Ƴ���.
			Log.i("TEST",s+"");
			Log.i("TEST",images.get(s)+"");
		}
		*/
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
		if(cursor != null){
		    cursor.moveToLast();
		    int length = cursor.getCount();
		    cursor.moveToFirst();
		    for(int i = 0; i < length; ++i){
		        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)); 
		        // '/���ϸ�.Ȯ����' �� �߶󳻰� ��θ�
		        String key = data.replaceFirst("/.[^/]+$", "").split("/")[data.replaceFirst("/.[^/]+$", "").split("/").length-1];
		        List<String> dir = images.get(key);
		        if(dir == null){
		        	dir = new ArrayList<String>();
		        	images.put(key, dir);
		        }
		        dir.add(data);
		        cursor.moveToNext();
		    }
		}
		return images;
	}
	
}
