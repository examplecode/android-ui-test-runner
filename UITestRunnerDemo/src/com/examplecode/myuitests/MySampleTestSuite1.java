package com.examplecode.myuitests;

import android.content.Context;
import android.widget.Toast;

import com.mmbox.uitestrunner.UITestSuite;

public class MySampleTestSuite1 extends UITestSuite {
	
	

	public MySampleTestSuite1(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void testSayHello() {
		Toast.makeText(getContext(), "Hello UITestRunner",Toast.LENGTH_SHORT).show();
	}
	
	public void testCase1InTestSuite1() {
		Toast.makeText(getContext(), "testCase1InTestSuite1 is runngin",Toast.LENGTH_SHORT).show();
	}
	
	public void testCase2InTestSuite1() 
	{
		Toast.makeText(getContext(), "testCase2InTestSuite1 is runngin",Toast.LENGTH_SHORT).show();
	}
	
}
