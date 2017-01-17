package com.qiandu.dev.util;

public class GetARandomTime {
	public static String getTheRandomTime(){
		String time="";
		int year,month,day,hour,minute,second;
		year=(int)(2014+Math.random()*3);//获取2014到1016年中的一年
		month=(int)(1+Math.random()*12);
		switch(month){
		case 1:case 3:case 5:case 7:case 8:case 10:case 12:{day=(int)(1+Math.random()*31);break;}
		case 4:case 6:case 9:case 11:{day=(int)(1+Math.random()*30);break;}
		}
		if((year%100!=0&&year%4==0)||(year%100==0&&year%400==0)){
			day=(int)(1+Math.random()*29);
		}else day=(int)(1+Math.random()*28);
		hour=(int)(Math.random()*24);
		minute=(int)(Math.random()*60);
		second=(int)(Math.random()*60);
		time=year+"-"+one2two(month)+"-"+one2two(day)+" "+one2two(hour)+":"+one2two(minute)+":"+one2two(second);
		return time;
	}
	public static String one2two(int i){
		String result="";
		if(i<10) result="0"+i;
		else result=""+i;
		return result;
	}
}
