
package com.mmbox.uitestrunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;



public class TestSuiteContainer extends FrameLayout {
	
	protected static final String LOGTAG = "TestSuiteListView";
	protected TestCaseListView mList;

	public TestSuiteContainer(Context context) {
		super(context);
		setupListView();
	}
	
	private void setupListView() {
		mList = new TestCaseListView(getContext()) {
			@Override
			public void onloadTestClass(String className) {
//				Toast.makeText(getContext(), "do runn test cases:" + className, Toast.LENGTH_LONG).show();
          	 //do load test case
				try {
					Class cls = Class.forName(className);
					Constructor con = cls.getConstructor(Context.class);
					UITestSuite view = (UITestSuite) con
							.newInstance(getContext());
					
					TestSuiteContainer.this.addView(view);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
	
			}

			

		};
		
//		mList.setBackgroundColor(Color.WHITE);
		mList.setTestTargetPackage(getContext().getPackageName());
		//测试TestCaseListView的子类
		mList.setTestSuper(UITestSuite.class);
		mList.loadTestList();
		addView(mList);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

}
