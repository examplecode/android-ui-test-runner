package com.examplecode.myuitests;

import android.content.Context;
import android.widget.Toast;

import com.mmbox.uitestrunner.UITestSuite;

public class MySampleTestSuite3 extends UITestSuite {

	public MySampleTestSuite3(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void testThanksForYourUseUITestRunner() {
		Toast.makeText(getContext(), "Hi, I just wanted to say thanks",Toast.LENGTH_SHORT).show();
	}
	


}
