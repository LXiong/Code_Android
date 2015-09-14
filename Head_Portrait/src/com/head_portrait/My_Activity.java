package com.head_portrait;

import java.io.File;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

public class My_Activity extends Activity
{
	//����xUtils ��ܵ�����ؼ���ʼ�����������Ǽ��ֱִ�
	private @ViewInject(R.id.head_portrait) My_View head_portrait;
	@OnClick(R.id.head_portrait) public void OnClick(View v){ShowPickDialog();}
	
	private String head_portrait_url = "http://p4.so.qhimg.com/t012acbd2ad8c398334.jpg"; //ͷ���ʼ������Դ��ַ
	private String post_url = "http://192.168.1.182:8081/admin/upload_file" ; //ͼ���ϴ�post��ַ
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		//ʹ��xUtils ��ܵĳ�ʼ����ʽ����ʹ�ÿؼ�ǰ��Ҫ����һ�д��룬������©
		ViewUtils.inject(this);
		
		//������Ϊ���ͷ������URL����ͼƬ��Դ
		head_portrait.setImageUrl(head_portrait_url);
		head_portrait.CreateFile();
	}
	
	private void ShowPickDialog() 
	{
		new AlertDialog.Builder(this)
				.setTitle("����ͷ��...")
				.setNegativeButton("���", new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.dismiss();						
						Intent intent = new Intent(Intent.ACTION_PICK, null);					
						intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
						startActivityForResult(intent, 1);
					}
				})
				.setPositiveButton("����", new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						dialog.dismiss();					
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/my_photo.jpg")));
						startActivityForResult(intent, 2);
					}
				}).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		switch (requestCode) 
		{
		// �����ֱ�Ӵ�����ȡ
		case 1:
			startPhotoZoom(data.getData());
			break;
		// ����ǵ����������ʱ
		case 2:
			File temp = new File(Environment.getExternalStorageDirectory() + "/my_photo.jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// ȡ�òü����ͼƬ
		case 3:			
			if(data != null)
			{
				head_portrait.ShowView(data , head_portrait , My_Activity.this , 1 , post_url);				
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * �ü�ͼƬ����ʵ��
	 */
	public void startPhotoZoom(Uri uri) 
	{		
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

}
