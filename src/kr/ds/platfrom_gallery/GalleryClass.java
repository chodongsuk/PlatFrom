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
	 * 폴더 없이 정렬.
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
			        String key = "/전체보기"; //임시 설정 하기..
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
	 * 폴더, 배열 
	 */
	public Map<String, List<String>> getFolderImage(Map<String, List<String>> images){
		/*
		for(String s : images.keySet()){//set 뽑아내기.
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
		        // '/파일명.확장자' 를 잘라내고 경로만
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
