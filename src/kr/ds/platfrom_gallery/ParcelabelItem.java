package kr.ds.platfrom_gallery;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelabelItem implements Parcelable {

	private List<String> mItem;

	public ParcelabelItem(List<String> item) {
		mItem = item;
	}

	public List<String> getmItem() {
		return mItem;
	}

	public void setmItem(List<String> mItem) {
		this.mItem = mItem;
	}

	public ParcelabelItem(Parcel src) {
		mItem = new ArrayList<String>();
		src.readList(mItem, String.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) { // �����ͳֱ�
		// TODO Auto-generated method stub
		arg0.writeList(mItem);
	}

	/**
	 * @author sungsik81
	 * 
	 *         Parcelable.Creator<T> Ŭ������ createFromParcel() �� newArray() �޼ҽ���
	 *         �ʿ��ϴ�. Parcel�� ���� ���� �о� ���� ���ؼ��� Parcelable.Creator Interface ��
	 *         �ʿ��ϴ�.
	 */
	public static final Parcelable.Creator<ParcelabelItem> CREATOR = new Creator<ParcelabelItem>() { // ������
																										// ��������

		@Override
		public ParcelabelItem createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new ParcelabelItem(in);
		}

		@Override
		public ParcelabelItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ParcelabelItem[size];
		}
	};
}
