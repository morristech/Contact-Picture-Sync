package heinrisch.contact.picture.sync;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class Tools {
	public static Bitmap downloadBitmap(String url) {      
		Bitmap bitmap = null;     
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.connect();
			InputStream is = connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e1) {
			// TODO: handle
		}

		return bitmap;                
	}

	public static void saveStringToFile(String string, File file) {
		if(string == null) return;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getStringFromFile(File file) {
		if(!file.exists()) return null;
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String read = null;

			while(null != (read = br.readLine())){
				result += read;
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.equals("") ? null : result;
	}
	
	
	public static void storePictureToFile(File file, Bitmap picture) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			picture.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Bitmap getBitmapFromFile(File file) {
		if(file.exists())
			return BitmapFactory.decodeFile(file.getPath());

		return null;
	}
	
	public static byte[] bitmapToByteArray(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		bitmap.compress(CompressFormat.PNG, 0, baos); 
		return baos.toByteArray();
	}
	
	public static void showError(String Error, Context ctx){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(Error)
		.setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

}
