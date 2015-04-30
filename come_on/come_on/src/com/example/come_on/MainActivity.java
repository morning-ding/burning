package com.example.come_on;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.*;

import com.example.come_on.file.FileService;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private TextView textView1,textView2,Editweight,Editheight,BMI,judge;
	private ImageButton imageButton,imageButton2,begin;
	private SharedPreferences sp;
	MediaPlayer mp2 = null;
	private static int countDay;
	public static int diffDay;
	public static boolean isSame;
    public static SQLiteDatabase db;
	private FileService service = null;
	public static int isFirst=0;
    @Override
    //OnCreate菜单
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton2  = (ImageButton)this.findViewById(R.id.imageButton2);
        begin =(ImageButton)this.findViewById(R.id.imageButton3);
        imageButton = (ImageButton)this.findViewById(R.id.imageButton1);
        Editheight = (TextView)this.findViewById(R.id.height);
        Editweight = (TextView)this.findViewById(R.id.weight);
        BMI = (TextView)this.findViewById(R.id.bmi);
        judge = (TextView)this.findViewById(R.id.judge);
        textView1 = (TextView)this.findViewById(R.id.textView3);
        textView2 = (TextView)this.findViewById(R.id.textView2);
        mp2 = MediaPlayer.create(this, R.raw.yuzumusic);
        sp = getSharedPreferences("userInfo", 0);
        String name =sp.getString("USER_NAME", "");
        
        
        	
        boolean remember = sp.getBoolean("remember", false);
        if(remember){
        	textView2.setText(name);
        	textView1.setText("欢迎回来");
        }
        imageButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						InformationUp.class);
				startActivityForResult(intent, 1000);
				isFirst +=1;
			}
		});
        
        imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mp2.start();
       
			}
		});
        
        begin.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
				startActivity(intent);					
				
			}
		});
        
        service = new FileService(this);
        String previousTime = service.readContentFromFile("time.txt");
        String lastTime = service.readContentFromFile("diff.txt");
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
 

        if(previousTime!="") {
        	
        	Date date = new Date();
    		Date lastDate = new Date();
			
		   try {
			lastDate = myFormatter.parse(previousTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    		
    		long between_days=(date.getTime()-lastDate.getTime())/(1000*3600*24);
    		countDay =  Integer.parseInt(String.valueOf(between_days)) +1;
    		String countD = String.valueOf(countDay);
        	
        }
        
        else{
        	
        	previousTime = myFormatter.format(new Date());
    		boolean flag = service.saveContentToFile("time.txt",Context.MODE_WORLD_WRITEABLE,previousTime.getBytes());
    		countDay =1;
    }
 if(lastTime!="") {
        	
        	Date date = new Date();
    		Date lastDate = new Date();
			
		   try {
			lastDate = myFormatter.parse(lastTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    		String now = myFormatter.format(date);
    		long diff_days=(date.getTime()-lastDate.getTime())/(1000*3600*24);
    		diffDay =  new Long(diff_days).intValue();
    		String countD = String.valueOf(diffDay);
    		System.out.println("diffDay-->"+diffDay);
    		boolean flag = service.saveContentToFile("diff.txt",Context.MODE_WORLD_WRITEABLE,now.getBytes());
        	
        }
        
        else{
        	
        	lastTime = myFormatter.format(new Date());
    		boolean flag = service.saveContentToFile("diff.txt",Context.MODE_WORLD_WRITEABLE,lastTime.getBytes());
    		String countD = String.valueOf(diffDay);
    }
    }
    public static int getCountDay(){
    	return countDay;
    }
    public static int getDiffDay(){
    	return diffDay;
    }
    public static boolean isTheSameDay() {
    	if(diffDay==0) return true;
    	else return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == 1000 && resultCode ==1001) {
    		String height = data.getStringExtra("height");
    		String weight = data.getStringExtra("weight");
    		Editheight.setText(height);
            Editweight.setText(weight);
            Double h = Double.parseDouble(String.valueOf(height));
            h = h / 100.0;
            System.out.println(h);
            Double w = Double.parseDouble(String.valueOf(weight));
            System.out.println(w);
            Double bmi = w/h/h;
            BigDecimal b = new BigDecimal(bmi);
            BigDecimal result = b.setScale(2, RoundingMode.DOWN);
            String Bmi = String.valueOf(result);
            BMI.setText(Bmi);
            if(bmi<18.5) judge.setText("体重过轻");
            else if(bmi<24) judge.setText("正常");
            else if(bmi<27) judge.setText("过重");
            else if(bmi<30) judge.setText("轻度肥胖");
            else if(bmi<35) judge.setText("中度肥胖");
            else judge.setText("重度肥胖");
    	}
    }
    
       
}



 