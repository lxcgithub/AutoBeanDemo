package com.lxc.autobeandemo;

import com.example.bean_annotion.Filed;
import com.example.bean_annotion.Name;

import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@Name
public class ExampleUnitTest extends BassData<Default> {

	@Filed("String")
	String mString;
	@Filed("Int")
	int mInt;
	
	@Before
	public void Setup() {
		initData();
	}
	
	@Override
	public Default initData() {
		Default e = new Default();
		e.setInt(4);
		e.setString("Default");
		return e;
	}
	
	@Test
	public void addition_isCorrect(){
		Default a = getInitData();
		a.setString("a");
	}
	
	@Test
	public void isCorrect(){
		Default b = getInitData();
		b.setString("b");
	}
	

}