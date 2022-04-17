package methods;

public class timeParcer {
	int banTime;
	int time;
	int hour;
	int minute;
	int sec;
	
	
	public timeParcer(int time, char type) {
		this.time = time;
		if(type == 'h') {
			hour = time*3600;
		}else if(type == 'm') {
			minute = time*60;
		}else if(type == 's') {
			sec = time;
		}else {
			return;
		}
	}
	public int getTime() {
		return time;
	}
	public int getBanTime() {
		return banTime;
	}
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public int getSecond() {
		return sec;
	}
	
}
