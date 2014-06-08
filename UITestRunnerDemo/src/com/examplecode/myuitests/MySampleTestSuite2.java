package com.examplecode.myuitests;

import com.mmbox.uitestrunner.UITestSuite;

import android.content.Context;
import android.widget.Toast;


public class MySampleTestSuite2 extends UITestSuite {

	public MySampleTestSuite2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void testCase1InTestSuite2() {
		Toast.makeText(getContext(), "testCase1InTestSuite2 is runnning ",Toast.LENGTH_SHORT).show();
	}
	
	public void testCase2InTestSuite2() 
	{
		Toast.makeText(getContext(), "testCase2InTestSuite2 is runnning ",Toast.LENGTH_SHORT).show();
	}
	

}
