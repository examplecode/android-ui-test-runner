package com.mmbox.uitestrunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;



/**
 * 列表控件用于生成测试用例的列表
 */
public abstract class TestCaseListView extends ListView {

	private final static String TAG = "MxTestListView";
	
	protected List<Class<?>> mViewClasses; 
	
	/**
	 * 忽略的类的列表,在此列表中的类将被忽略不显示
	 */
	private List<String> mIgnoreList;
	
	Class<?> mSuperClass;
	/**
	 * 测试目标包名
	 */
	String mTestPackage = null;
	
      
//   protected void onListItemClick(ListView l, View v, int position, long id){
//	   onloadTestClass(mViewClasses.get(position).getName());
//   }
	public TestCaseListView(Context context) {
		super(context);
		mViewClasses = new ArrayList<Class<?>>();
		mIgnoreList = new ArrayList<String>();
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onloadTestClass(mViewClasses.get(position).getName());
			}
		});
	}
	
	/**
	 * 设置需要测试的包名,测试框架会遍历该包名下所有从目标视图继承类名
	 * @param name
	 */
	public void setTestTargetPackage(String name) {
		mTestPackage = name;
	}
	
	public void addToIgnoreList(String className) {
		mIgnoreList.add(className);
	}
	
	private boolean isIgnoreClass(String name) {
		for(int i=0; i < mIgnoreList.size(); i++) {
			if(mIgnoreList.get(i).equals(name)) return true;
		}
		return false;
	}
	/**
	 * 遍历指定类的子类
	 * @param cls
	 */
	public void setTestSuper(Class<?> cls) {
		mSuperClass = cls;
	}
	
	
	public class ViewListAdapter extends BaseAdapter {
		
		List<Class<?>> mViewClasses ;
		Context mContext ;
		
		public ViewListAdapter(Context context,List<Class<?>> viewClasses) {
			mViewClasses = viewClasses;
			mContext = context;
		}

		public int getCount() {
			return mViewClasses.size();
		}

		public Object getItem(int arg0) {
			return arg0;
		}

		public long getItemId(int arg0) {
			return arg0;
		}
		
		 
		public View getView(int position, View convertView, ViewGroup parent) {
//			TextView tv = new TextView(mContext);
//			tv.setText(getItemName(mViewClasses.get(position).getName()));
//			tv.setHeight(60);
//			tv.setGravity(Gravity.CENTER_VERTICAL);
			TextView tv = (TextView)  View.inflate(getContext(),  android.R.layout.simple_list_item_1, null);
			tv.setText(getItemName(mViewClasses.get(position).getName()));
			return tv;
		}
		
		private String getItemName(String className)  {
			int pos = className.lastIndexOf('.');
			if(pos < 0) {
				return className;
			} else {
				return className.substring(pos + 1);
			}
		}
		
	}
	
	public void loadTestList() {
		
		loadTestClasses();
		ViewListAdapter adapter = new ViewListAdapter(getContext(),mViewClasses);
		setAdapter(adapter); 
	}
	
	private void loadTestClasses() {
		if(mSuperClass == null ||mTestPackage ==  null) {
			throw new IllegalStateException("test base class or target package not specifyed");
		}
		String apkName;
		try {
			apkName = getContext().getPackageManager().getApplicationInfo(mTestPackage,0).sourceDir;
			ArrayList<String> classNames = new ArrayList<String>();
			ClassUtils.findClassesInApk(apkName,classNames);
			
			for (String className : classNames) {
	          if (className.endsWith(".R") || className.endsWith(".Manifest")) {
	                continue;
	            }
	          final Class<?> cls = Class.forName(className);
	          if(mSuperClass.isAssignableFrom(cls) 
	        		  && ! className.equals(mSuperClass.getName())
	        		   && ! isIgnoreClass(className)
	        		  ) {
	        	  
	        	  mViewClasses.add(Class.forName(className));
	         	}
	         
			}
		} 
		catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public abstract void onloadTestClass(String className);
	
}
