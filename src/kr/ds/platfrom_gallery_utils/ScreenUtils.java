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
 * ��ũ�� ��ƿ</p>
 * dipToInt(int number, Context context) - dip �� ���� �ȼ� ��ȯ</p>
 * ScreenWidth(Context context) - ���� �ػ�</p>
 * ScreenHeight(Context context) - ���� �ػ�</p>
 * Orientation(Context context) - �������� �������� üũ </P>
 * MarginWidth(Context context, int widthmargin) - ���� ���� �ػ� </p>
 * MarginHeight(Context context, int heightmargin) - ���� ���� �ػ� </p>
 * getBitmapOfWidth(Context context, int id) - �̹��� ���� ������ </p>
 * getBitmapOfHeight(Context context, int id) - �̹��� ���� ������ </p>
 * BitmapRotate(Bitmap b, int degrees) - �̹������� ��ȯ</p>
 * isTablet(Context context) - �º����� üũ</p>
 * isCheckDevice(String[] device) - ����̽� üũ </p>
 * VersionCode(Context context) - �����ڵ� </p>
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
		 * ����ȯ
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
	// * ����õ ����üũ 2.1����
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
	 * ����üũ
	 * http://developer.android.com/reference/android/os/Build.VERSION_CODES
	 * .html
	 */
	public  boolean isCheckFroyo() {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		return (SDK_INT > Build.VERSION_CODES.FROYO) ? true : false;
	}

	/*
	 * ����̽� üũ
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
	 * ����üũ
	 */
	public  int VersionCode() {
		PackageManager pm;
		PackageInfo packageInfo;
		String VersionName;
		int VersionCode = 0;
		try {
			pm = _Context.getPackageManager();
			packageInfo = pm.getPackageInfo(_Context.getPackageName(), 0);
			VersionName = packageInfo.versionName; // ���� ����
			VersionCode = packageInfo.versionCode; // ����

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return VersionCode;
	}
	
	/*
	 * ���� ���
	 */
	public int ratioyImageView(float xratio, float yratio, int margin){
		int setwidth ;
		setwidth = (int)((MarginWidth(margin)/xratio)*yratio);
		return	setwidth;
	}
	
	/*
	 * �ػ󵵺� ���� 
	 */
	public boolean AlignmentWidthBoolean(Context context){
		/* �ػ󵵺� ����
		 * 1232(10.1��ġ)
		 * �ػ� �ο︰ ���� �ػ�
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
