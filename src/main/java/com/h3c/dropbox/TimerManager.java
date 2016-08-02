package com.h3c.dropbox;

import java.util.Calendar;
import java.util.Timer;

public class TimerManager {
//	public void task() {
	public TimerManager() {
		Timer timer = new Timer();//定时器
		Cleansing ts = new Cleansing();//定时任务目标
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 42);
		calendar.set(Calendar.SECOND, 0);
		long period = 1000 * 60 * 1;//执行间隔1天
		timer.scheduleAtFixedRate(ts, calendar.getTime(), period);//设置执行参数并且开始定时任务
	}
}