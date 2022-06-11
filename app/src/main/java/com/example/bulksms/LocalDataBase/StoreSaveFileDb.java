package com.example.bulksms.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.bulksms.adapters.SaveItemAdapter;
import com.example.bulksms.models.SaveFileClass;
import java.util.ArrayList;

public class StoreSaveFileDb extends SQLiteOpenHelper {
	public static String dataBaseName = "SaveFileDb";
	public static String tableName = "saveTable";
	public static final String id = "_id";
	public static final String userId = "userId";
	public static final String repNum = "repNum";
	public static final String multiRepNum = "multRepNum";
	public static final String message = "message";
	public static final String fileName = "filrName";
	public static int version = 1;
	SQLiteDatabase sQLiteDatabase;
	Context context;
    
	public StoreSaveFileDb(Context context) {
		super(context, dataBaseName, null, version);
		this.context = context;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String query = " CREATE TABLE " + tableName + " ( " + id + " INTEGER PRIMARY KEY AUTOINCREMENT ," + userId
				+ " TEXT, " + repNum + " TEXT, " + multiRepNum + " TEXT, " + message + " TEXT, " + fileName + " TEXT )";
		db.execSQL(query);
		Toast.makeText(context, "db created", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP IF TABLE EXIST" + tableName);
	}

	public long addSavedFile(String myid, String repNumS, String multRep, String messageS, String fileNameS) {
		sQLiteDatabase = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(userId, myid);
		values.put(repNum, repNumS);
		values.put(multiRepNum, multRep);
		values.put(message, messageS);
		values.put(fileName, fileNameS);

		long suc = sQLiteDatabase.insert(tableName, null, values);
		if (suc > 0)
			Toast.makeText(context, "file successfullySaved", Toast.LENGTH_LONG).show();
		return suc;

	}

	public ArrayList<SaveFileClass> getAllSavedFilesData() {
		ArrayList<SaveFileClass> saveFileClassArrayList = new ArrayList<>();
		String[] columns = { id, userId, repNum, multiRepNum, message, fileName };
		Cursor cursor;
		sQLiteDatabase = this.getReadableDatabase();
		try {
			cursor = sQLiteDatabase.query(tableName, columns, null, null, null, null, null);

			assert cursor != null;
			while (cursor.moveToNext()) {
				//	fileNa
				//	String fileName=cursor.getString(cursor.getColumnIndex(fileName));
				String fileN = cursor.getString(cursor.getColumnIndex(fileName));
				String rep = cursor.getString(cursor.getColumnIndex(repNum));
				String multRepNumS = cursor.getString(cursor.getColumnIndex(multiRepNum));
				String me = cursor.getString(cursor.getColumnIndex(message));
				String myId = cursor.getString(cursor.getColumnIndex(userId));
				SaveFileClass saveFileClass = new SaveFileClass();
				saveFileClass.setFileName(fileN);
				saveFileClass.setId(myId);
				saveFileClass.setRecipient(rep);
				saveFileClass.setMessage(me);
				saveFileClass.setMultiRecipient(multRepNumS);

				//	Toast.makeText(context, "data in db not null" + fileN + myId, Toast.LENGTH_LONG).show();
				//	Toast.makeText(context,"data in db not null",Toast.LENGTH_LONG).show();

				//	saveFileClass.setRecipient(r);
			//	Toast.makeText(context, "data:" + fileN + myId, Toast.LENGTH_LONG).show();
				saveFileClassArrayList.add(saveFileClass);

			}
			cursor.close();
		} catch (Exception e) {
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
		return saveFileClassArrayList;

	}

	public int deleteFile(String fileNameD) {
		SQLiteDatabase database = this.getWritableDatabase();
		String[] whereArg = { fileNameD };
		int resp = database.delete(tableName, fileName + " = ?", whereArg);
		if (resp > 0)
			Toast.makeText(context, "file deleted successfully ", Toast.LENGTH_LONG).show();
			return  resp;
	}

}