package com.lxc.autobeandemo;

import android.app.Activity;
import android.os.Bundle;

import com.example.bean_annotion.Filed;
import com.example.bean_annotion.Name;

import java.util.List;

@Name("Bean")
public class MainActivity extends Activity {
	
	@Filed("Names")
	String name;
	
	@Filed("List")
	List<String> mlist;
	
	@Filed("InitData")
	List<Integer> mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bean main = new Bean();
		main.setName("你好啊");
	}
}
