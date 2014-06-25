package kr.ds.platfrom_gallery;

public class GalleryDetailHandler {
	private String sdcardPath;
	private boolean isSeleted = false;
	
	public String getSdcardPath() {
		return sdcardPath;
	}
	public void setSdcardPath(String sdcardPath) {
		this.sdcardPath = sdcardPath;
	}
	public boolean isSeleted() {
		return isSeleted;
	}
	public void setSeleted(boolean isSeleted) {
		this.isSeleted = isSeleted;
	}
}
