package com.mmbox.uitestrunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;



public class UITestSuite extends FrameLayout {
	
	protected ListView mList;
	
	//以此开头的方法为目标用例方法
	protected static final String TESTTAG = "test";

	public UITestSuite(Context context) {
		super(context);
		setupListView();
	}
	
	
	protected void showView(View v) {
//		FrameLayout rootContainer =  (FrameLayout) getParent();
		View parent = (View) getParent();
		if(parent instanceof TestSuiteContainer) {
			TestSuiteContainer container = (TestSuiteContainer) parent;
			container.addView(v);		
		} else {
			throw new IllegalStateException("the view parent must be TestSuiteContainer");
		}
		
	}
	
	protected void showLayout(int resId) 
	{
		View v = View.inflate(getContext(), resId, null);
		showView(v);
	}
	
	
	private void setupListView() {
		mList = new ListView(getContext());
		
		mList.setBackgroundColor(Color.WHITE);
		final TestMethodListAdapter adapter = new TestMethodListAdapter(getContext(),this);
		mList.setAdapter(adapter);
		
		mList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter.runTest(arg2);
			}
		}
		);
		addView(mList);
	}
	
	
	
	public class TestMethodListAdapter extends BaseAdapter {
		
		private Context mContext;
		private Object mTarget;
		
		Method[] mTestMethods = null;

		public TestMethodListAdapter(Context context,Object target) {
			mContext = context;
			mTarget = target;
			
			Class c = mTarget.getClass();
			Method[] m = c.getDeclaredMethods();
			Vector<Method> v = new Vector(); 
			
			for(int i=0; i< m.length; i++)
			{
				if(m[i].getName().indexOf(TESTTAG) >=0)
				{
					v.add(m[i]);
				}
			}
			Method[]  tmp = new Method[v.size()];
			v.copyInto(tmp);
			mTestMethods = tmp;
		}

		public int getCount() {
			if(mTestMethods == null)
			{
				return 0;
			}
			return mTestMethods.length;
		}

		public Object getItem(int arg0) {
			return arg0;
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			
			TextView tv = (TextView)  View.inflate(getContext(),  android.R.layout.simple_list_item_1, null);
//			TextView tv = new TextView(mContext);
//			tv.setHeight(60);
//			tv.setGravity(Gravity.CENTER_VERTICAL);
			
			tv.setText(mTestMethods[position].getName());

			return tv;
		}
		
		public void runTest(int testId)
		{
			try { 
				mTestMethods[testId].invoke(mTarget, null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

	}

}
