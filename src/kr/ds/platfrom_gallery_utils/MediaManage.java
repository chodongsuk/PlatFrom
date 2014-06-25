package kr.ds.platfrom_gallery_utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;

public class MediaManage {

	private Activity activity;
	private ContentResolver contentResolver;
	private String[] projection = {"_data" };

	public MediaManage(Activity activity){
		this.activity = activity;
		contentResolver = this.activity.getContentResolver();
	}

	public Cursor getImageCursor(){
		Cursor cursor = null;
		cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DISPLAY_NAME);
		return cursor;
	}

	public String getImage(long id){
		Cursor cursor = null;
		String thumbnail_path = null;
		cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, projection);
		if(cursor.moveToFirst())
			thumbnail_path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
		return thumbnail_path;
	}

	public Cursor getMusicCursor(){
		Cursor cursor = null;
		cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DISPLAY_NAME);
		return cursor;
	}

	public Cursor getVideoCursor(){
		Cursor cursor = null;
		cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DISPLAY_NAME);
		return cursor;
	}

	public Bitmap getvideo(long id){
		Bitmap cursor = null;
		cursor = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Video.Thumbnails.MINI_KIND, null);
		return cursor;
	}
}