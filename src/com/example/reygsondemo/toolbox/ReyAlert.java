/**
 * Description:
 * ReyAlert.java Create on 2013-4-23 下午05:18:28 
 * @author Rey
 * @version 1.0
 * Copyright (c) 2013 Rey,Inc. All Rights Reserved.
 */
package com.example.reygsondemo.toolbox;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author Rey
 *
 * Title: TODO
 * Description: TODO 
 */
public class ReyAlert {

	public static void showAlertDialog_simple(Context context , String title , String msg ,String btnTitle) {
		showAlertDialog_simple(context, title, msg, btnTitle, null);
	}
	
	public static void showAlertDialog_simple(Context context , String title , String msg ,String btnTitle , final DialogInterface.OnClickListener listener) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(btnTitle, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			    dialog.dismiss();
			    
				if (listener != null) {
					listener.onClick(dialog, which);
				}
				
				
			}
		});
		builder.create().show();
		
	}
	
	public static void showAlertDialog_two(Context context , String title , String msg ,String firstBtnTitle ,String secondBtnTitle)
	{
		showAlertDialog_two(context, title, msg, firstBtnTitle, secondBtnTitle, null);
	}
	
	public static void showAlertDialog_two(Context context , String title , String msg ,String firstBtnTitle ,String secondBtnTitle, final DialogInterface.OnClickListener listener) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(firstBtnTitle, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

			    dialog.dismiss();
				
				if (listener != null) {
					listener.onClick(dialog, 0);
				}
				
				
			}
		});
		
		builder.setNegativeButton(secondBtnTitle, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			    dialog.dismiss();

			    if (listener != null) {
					listener.onClick(dialog, 1);
				}
				
				
			}
		});
		builder.create().show();
	}
	
	public static void showAlertDialog_list(Context context , String title ,ArrayList<String> stringList, final DialogInterface.OnClickListener listener) {
		
		String[] items = new String[stringList.size()];
		stringList.toArray(items);
		
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(items, listener);
		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
	}
	
}
