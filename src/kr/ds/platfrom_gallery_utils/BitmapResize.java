package kr.ds.platfrom_gallery_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.media.ExifInterface;
import android.util.Log;
import android.util.TypedValue;

public class BitmapResize {
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		Log.i("inSampleSize",inSampleSize+"");
		
		return inSampleSize;
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String Url,
	        int reqWidth, int reqHeight) {
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(Url, options);
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(Url, options);
	}
	
	public Bitmap resizeBitmapImageEfft(Bitmap source, int width, int height)
	{
		return Bitmap.createScaledBitmap(source,
        		width, height, true);
	}
	
	public Bitmap resizeBitmapImage(Bitmap source, int maxResolution)
	{
	        int width = source.getWidth();
	        int height = source.getHeight();
	        int newWidth = width;
	        int newHeight = height;
	        float rate = 0.0f;
	        
	        if(width > height)
	        {
	                if(maxResolution < width)
	                {
	                        rate = maxResolution / (float) width;
	                        newHeight = (int) (height * rate);
	                        newWidth = maxResolution;
	                }
	        }
	        else
	        {
	                if(maxResolution < height)
	                {
	                        rate = maxResolution / (float) height;
	                        newWidth = (int) (width * rate);
	                        newHeight = maxResolution;
	                }
	        }
	        
	        return Bitmap.createScaledBitmap(source,

	        		newWidth, newHeight, true);
	}
	public static int dipToInt(int number, Context context){
		int num = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, number, context.getResources().getDisplayMetrics());
		return num;
	}
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	        bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 12;

	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);

	    return output;
	  }
	public static Bitmap getRoundedCornerBitmap2(Bitmap bitmap) {
        final Paint paint = new Paint();
        float radius = bitmap.getWidth() / 2f < bitmap.getHeight() / 2f ? bitmap.getWidth() / 2f : bitmap.getHeight() / 2f;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(Math.round(radius * 2f), Math.round(radius * 2f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        RoundRectShape rrs = new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setAntiAlias(true);
        paint.setColor(0xFF000000);
        rrs.resize(radius * 2f, radius * 2f);
        rrs.draw(canvas, paint);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 12, 12, paint);
        return output;
    }
	
	//파일 위치!!
	public static Bitmap decodeFile(String path, Context context, int width, int height) {// you can provide file path here
		int orientation;
		try {
			if (path == null) {
				return null;
			}
			Bitmap bitmap = decodeSampledBitmapFromResource(path,dipToInt(width,context),dipToInt(height,context));
			// 이미지를 상황에 맞게 회전시킨다
			ExifInterface exif = new ExifInterface(path);
			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			int exifDegree = exifOrientationToDegrees(exifOrientation);
			bitmap = rotate(bitmap, exifDegree);

			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int exifOrientationToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			return 90;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			return 180;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			return 270;
		}
		return 0;
	}
	
	public static Bitmap rotate(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2,
					(float) bitmap.getHeight() / 2);

			try {
				Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), m, true);
				if (bitmap != converted) {
					bitmap.recycle();
					bitmap = converted;
				}
			} catch (OutOfMemoryError ex) {
				// 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
			}
		}
		return bitmap;
	}
}
