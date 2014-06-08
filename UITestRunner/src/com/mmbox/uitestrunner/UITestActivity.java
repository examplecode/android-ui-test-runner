package com.mmbox.uitestrunner;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;

public class UITestActivity extends Activity {
	
	TestSuiteContainer mTestSuiteContainer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 mTestSuiteContainer = new TestSuiteContainer(this);
		setContentView(mTestSuiteContainer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_uitest, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			int childCount = mTestSuiteContainer.getChildCount();
			if(childCount >1) {
				mTestSuiteContainer.removeViewAt(childCount -1);
				return true;
			} 
		}	
		return super.onKeyDown(keyCode, event);
		
	}

}
