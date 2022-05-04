package Server.utils;

public class LoadingManager {

	private boolean[] subLoads;
	private int haveLoaded;
	
	public LoadingManager(int len) {
		this.subLoads = new boolean[len];
		this.haveLoaded = 0;
	}
	
	public void subLoad() {
		if(loadingComplete())
			return;
		this.subLoads[haveLoaded] = true;
		haveLoaded++;
	}
	
	public boolean loadingComplete() {
		return haveLoaded >= subLoads.length;
	}
	
	public void refresh() {
		this.subLoads = new boolean[this.subLoads.length];
		this.haveLoaded = 0;
	}
	
	public int loadsComplete() {
		return haveLoaded;
	}
}
