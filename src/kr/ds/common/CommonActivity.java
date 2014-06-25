package kr.ds.common;

import kr.ds.platfrom_gallery.GalleryListActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CommonActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		LinearLayout mLayout = new LinearLayout(getApplicationContext());
		Button button = new Button(getApplicationContext());
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), GalleryListActivity.class);
	            startActivityForResult(intent, 0);
	            overridePendingTransition(0, 0);
			}
		});
		mLayout.addView(button);
		setContentView(mLayout);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			if(resultCode == RESULT_OK){
				if(data.getExtras() != null){
					String[] datas = data.getExtras().getStringArray("data");
					for(int i = 0; i < datas.length; i++){
						Log.i("TEST",datas[i]);
					}
				}
			}
			break;
		}
	}

}
