package kr.ds.platfrom_gallery_utils;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
/**
 * 스크린 유틸</p>
 * dipToInt(int number, Context context) - dip 에 따른 픽셀 변환</p>
 * ScreenWidth(Context context) - 가로 해상도</p>
 * ScreenHeight(Context context) - 세로 해상도</p>
 * Orientation(Context context) - 가로인지 세로인지 체크 </P>
 * MarginWidth(Context context, int widthmargin) - 여백 가로 해상도 </p>
 * MarginHeight(Context context, int heightmargin) - 여백 세로 해상도 </p>
 * getBitmapOfWidth(Context context, int id) - 이미지 가로 사이즈 </p>
 * getBitmapOfHeight(Context context, int id) - 이미지 세로 사이즈 </p>
 * BitmapRotate(Bitmap b, int degrees) - 이미지비율 변환</p>
 * isTablet(Context context) - 태블릿인지 체크</p>
 * isCheckDevice(String[] device) - 디바이스 체크 </p>
 * VersionCode(Context context) - 버젼코드 </p>
 * @author chodongsuk
 * @since 2012.10.22
 * @version 1.0 
 */
public class ScreenUtils {
	private Context _Context;
	public ScreenUtils(Context context){
		this._Context = context;
	}
	public int dipToInt(int number) {
		/*
		 * 형변환
		 */
		int num = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				number, _Context.getResources().getDisplayMetrics());
		return num;
	}

	public float density() {
		float density = _Context.getResources().getDisplayMetrics().density;
		return density;
	}
	
	public  int ScreenWidth() {
		Display display = ((WindowManager) _Context
				.getSystemService(_Context.WINDOW_SERVICE)).getDefaultDisplay();
		int gwidth = display.getWidth();
		return gwidth;
	}

	
	public  int ScreenHeight() {
		Display display = ((WindowManager) _Context
				.getSystemService(_Context.WINDOW_SERVICE)).getDefaultDisplay();
		int gheigth = display.getHeight();
		return gheigth;
	}

	// public static int ScreenOrientation(Context context){
	// /*
	// * 비추천 각도체크 2.1이하
	// */
	// WindowManager wm = (WindowManager)
	// context.getSystemService(Context.WINDOW_SERVICE);
	// Display display = wm.getDefaultDisplay();
	// int orientation = display.getRotation();
	// return orientation;
	// }
	public  boolean Orientation() {
		boolean orientation = ScreenHeight() > ScreenWidth();
		return orientation;
	}

	public  int MarginWidth(int widthmargin) {
		int gwidth = ScreenWidth() - widthmargin;
		return gwidth;
	}

	public int MarginHeight(int heightmargin) {
		int gwidth = ScreenHeight() - heightmargin;
		return gwidth;
	}

	public  int getBitmapOfWidth(int id) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(_Context.getResources(), id, options);
			return options.outWidth;
		} catch (Exception e) {
			return 0;
		}
	}

	public  int getBitmapOfHeight(int id) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(_Context.getResources(), id, options);
			return options.outHeight;
		} catch (Exception e) {
			return 0;
		}
	}

	public  Bitmap BitmapRotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	public  boolean isTablet() {
		// TODO: This hacky stuff goes away when we allow users to target
		// devices
		int xlargeBit = 4; // Configuration.SCREENLAYOUT_SIZE_XLARGE; // upgrade
							// to HC SDK to get this
		Configuration config = _Context.getResources().getConfiguration();
		return (config.screenLayout & xlargeBit) == xlargeBit;
	}

	/*
	 * 버전체크
	 * http://developer.android.com/reference/android/os/Build.VERSION_CODES
	 * .html
	 */
	public  boolean isCheckFroyo() {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		return (SDK_INT > Build.VERSION_CODES.FROYO) ? true : false;
	}

	/*
	 * 디바이스 체크
	 */
	public  boolean isCheckDevice(String[] device) {
		String DEVICE = android.os.Build.MODEL;
		Boolean bDevice = null;
		for (int i = 0; i < device.length; i++) {
			if (DEVICE.equals(device[i])) {
				bDevice = true;
			} else {
				bDevice = false;
			}
		}
		return bDevice;
	}

	/*
	 * 버젼체크
	 */
	public  int VersionCode() {
		PackageManager pm;
		PackageInfo packageInfo;
		String VersionName;
		int VersionCode = 0;
		try {
			pm = _Context.getPackageManager();
			packageInfo = pm.getPackageInfo(_Context.getPackageName(), 0);
			VersionName = packageInfo.versionName; // 버전 네임
			VersionCode = packageInfo.versionCode; // 버전

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return VersionCode;
	}
	
	/*
	 * 비율 계산
	 */
	public int ratioyImageView(float xratio, float yratio, int margin){
		int setwidth ;
		setwidth = (int)((MarginWidth(margin)/xratio)*yratio);
		return	setwidth;
	}
	
	/*
	 * 해상도별 구분 
	 */
	public boolean AlignmentWidthBoolean(Context context){
		/* 해상도별 구분
		 * 1232(10.1인치)
		 * 해상도 부울린 가로 해상도
		 */
		boolean Alignment;
		int gheight = ScreenHeight();
		if(gheight == 1232){
			Alignment = true;
		}else{
			Alignment = false;
		}
		return Alignment;
	}
	
	
	public static int getScreenWidth(Context context){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		display.getMetrics(displayMetrics);
		int deviceWidth = (int) (displayMetrics.widthPixels/displayMetrics.density);
		return deviceWidth;
	}
	public static int getScreenHeight(Context context){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		display.getMetrics(displayMetrics);
		int deviceHight = displayMetrics.heightPixels;
		return deviceHight;
	}
	
	
}
