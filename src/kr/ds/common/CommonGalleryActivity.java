package kr.ds.common;

import kr.ds.platfrom_gallery.GalleryListActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CommonGalleryActivity extends Activity {
	private int TAKE_CAMERA = 1; // 카메라 리턴 코드값 설정
	private int TAKE_GALLERY = 2; // 앨범선택에 대한 리턴 코드값 설정
	private int TAKE_GALLERYMORE = 3; // 앨범선택에 대한 리턴 코드값 설정
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LinearLayout mLayout = new LinearLayout(getApplicationContext());
		Button button1 = new Button(getApplicationContext());
		Button button2 = new Button(getApplicationContext());
		Button button3 = new Button(getApplicationContext());

		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, TAKE_CAMERA);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setType("image/*");
				intent.putExtra("return-data", true);
				startActivityForResult(intent, TAKE_GALLERY);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						GalleryListActivity.class);
				startActivityForResult(intent, TAKE_GALLERYMORE);
				overridePendingTransition(0, 0);
			}
		});
		mLayout.addView(button1);
		mLayout.addView(button2);
		mLayout.addView(button3);
		setContentView(mLayout);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == TAKE_CAMERA) {
				if (data.getExtras() != null) {
					String datas = null;
					Cursor cursor = getContentResolver()
							.query(Media.EXTERNAL_CONTENT_URI,
									new String[] {
											Media.DATA,
											Media.DATE_ADDED,
											MediaStore.Images.ImageColumns.ORIENTATION },
									Media.DATE_ADDED, null, "date_added ASC");
					if (cursor != null && cursor.moveToFirst()) {
						do {
							Uri uri = Uri.parse(cursor.getString(cursor
									.getColumnIndex(Media.DATA)));
							datas = uri.toString();
							
						} while (cursor.moveToNext());
						cursor.close();
					}
					Toast.makeText(getApplicationContext(), datas, Toast.LENGTH_SHORT).show();
				}
			} else if (requestCode == TAKE_GALLERY) {
				if (data.getExtras() != null) {
					String datas = null;
					Cursor cursor = getContentResolver().query(data.getData(),
							null, null, null, null);
					if (cursor != null) {
						cursor.moveToFirst();
						int idx = cursor.getColumnIndex(ImageColumns.DATA);
						datas = cursor.getString(idx);
						Toast.makeText(getApplicationContext(), datas, Toast.LENGTH_SHORT).show();
					}else{
						datas = data.getData().getPath();
					}
				}
			} else if (requestCode == TAKE_GALLERYMORE) {
				if (data.getExtras() != null) {
					String[] datas = data.getExtras().getStringArray("data");
					for (int i = 0; i < datas.length; i++) {
						Toast.makeText(getApplicationContext(), datas[i], Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}

}
