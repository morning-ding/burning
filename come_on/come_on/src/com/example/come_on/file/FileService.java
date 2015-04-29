package com.example.come_on.file;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

public class FileService {
	private Context context;

	public FileService(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public String readContentFromFile(String fileName) {
		FileInputStream fileInputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			fileInputStream = context.openFileInput(fileName);
			int len = 0;
			byte[] data = new byte[1024];
			while((len = fileInputStream.read(data))!=-1){
				outputStream.write(data, 0, len);
			}
			return new String(outputStream.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @param fileName
	 * @param mode
	 * @param data
	 * @return
	 */
	public boolean saveContentToFile(String fileName, int mode, byte[] data) {
		boolean flag = false;
		FileOutputStream outputStream = null;
		try {
			outputStream = context.openFileOutput(fileName, mode);
			outputStream.write(data, 0, data.length);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		return flag;
	}
}
