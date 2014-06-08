package com.examplecode.myuitests;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.examplecode.uitestrunner.R;
import com.mmbox.uitestrunner.UITestSuite;

public class SampleTestSuiteForShowTestView extends UITestSuite {

	public SampleTestSuiteForShowTestView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	

	public void testShowSimpleButton() {
		Button btn = new Button(getContext());
		btn.setText("Click Me");
		btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		showView(btn);
	}
	
	
	public void testShowSimpleLayout() {
		showLayout(R.layout.show_layout_demo);
	}
}
