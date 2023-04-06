package TextForce;

public class TimeParcer {
	private int sec;
	private int minute;
	private int hour;
	
	public TimeParcer(int sec) {
		this.sec = sec;
	}
	
	public int[] Parce() {
		int[] time = {};
		
		if(sec>3600) {
			int hor = sec/3600;
			Integer.lowestOneBit(hor);
		}
		
		return time;
	}
}
