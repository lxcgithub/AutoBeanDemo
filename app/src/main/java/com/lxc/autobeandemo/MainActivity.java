package com.lxc.autobeandemo;

import android.app.Activity;
import android.os.Bundle;

import com.example.bean_annotion.Filed;
import com.example.bean_annotion.Name;

import java.util.List;

/**
 * @Name 注解是生成 JavaBean 的类名
 * @Filed 是生成 JavaBean 中所包含的属性，一个类仅生成一个 Bean。
 */

@Name("Bean")
public class MainActivity extends Activity {
	
	@Filed("Name")
	String name = "Init";
	
	@Filed("List")
	List<String> mlist;
	
	@Filed("InitData")
	List<Integer> mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bean main = new Bean();
		main.setName(name);
	}
}
