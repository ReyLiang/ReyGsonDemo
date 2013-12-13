package com.example.reygsondemo;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reygsondemo.toolbox.ReyAlert;
import com.example.reygsondemo.xmltojson.XMLToJSON;
import com.example.reygsondemo.xmltojson.XMLToJSONException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String[] TITLE_STRS = { "GSON解析", "JSON解析",
			"GSON Annotation @Expose", "GSON Annotation @SerializedName" };

	
	private static final int SHOW_LOADING = 0;
	private static final int HIDE_LOADING = 1;
	private static final int LOADDATA_SUCCESS = 2;
	private static final int LOADDATA_FAILED = 3;
	
	
	/**
	 * @Fields RequestQueue mRequestQueue : 下载队列
	 */
	private RequestQueue mRequestQueue;
	
	/** 
	* @Fields String mMainJsonString : 要解析的json字符串
	*/ 
	private String mMainJsonString;
	
	
	private ProgressDialog mLoadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initSubViews();
		
		loadNetNewsData();
	}
	
	@Override
	protected void onDestroy() {
		if (mLoadingView != null) {
			mLoadingView.dismiss();
		}
		super.onDestroy();
	}

	private void initSubViews() {
		
		initLoadingView();

		ListView listView = (ListView) this.findViewById(R.id.listView1);

		listView.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return TITLE_STRS.length;
			}
		});
	}
	
	private void initLoadingView() {
		mLoadingView = new ProgressDialog(this);
		mLoadingView.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mLoadingView.setMessage("正在获取数据，请稍等...");
	}

	/**
	 * @Title: loadNetNewsData
	 * @Description: 获取网易RSS中心消息
	 *               http://news.163.com/special/00011K6L/rss_newstop.xml
	 * @return void
	 * @throws
	 */
	private void loadNetNewsData() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(this);
		}
		
		mHandler.sendEmptyMessage(SHOW_LOADING);
		
		StringRequest stringRequest = new StringRequest("http://news.163.com/special/00011K6L/rss_newstop.xml", new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					mMainJsonString = XMLToJSON.convert(response);
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mMainJsonString = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mMainJsonString = null;
				} catch (XMLToJSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mMainJsonString = null;
				}
				
				mHandler.sendEmptyMessage(HIDE_LOADING);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//错误信息
				mHandler.sendEmptyMessage(HIDE_LOADING);
				Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
				
			}
		});
		
		mRequestQueue.add(stringRequest);
	}
	
	private Handler mHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			
			int what = msg.what;
			
			switch (what) {
			case SHOW_LOADING:
			{
				mLoadingView.show();
				break;
			}
			case HIDE_LOADING:
			{
				mLoadingView.hide();
				break;
			}
			case LOADDATA_SUCCESS:
			{
				//TODO 加载数据成功
				break;
			}
			case LOADDATA_FAILED:
			{
				ReyAlert.showAlertDialog_simple(MainActivity.this, "提示", "加载数据错误", "重试", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						loadNetNewsData();
					}
				});
				break;
			}
			default:
				break;
			}
			
			return false;
		}
	});
}
