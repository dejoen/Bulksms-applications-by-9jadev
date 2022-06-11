package com.example.bulksms.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.View;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.bulksms.LocalDataBase.StoreSaveFileDb;
import com.example.bulksms.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import android.app.Dialog;
import android.widget.CheckBox;
import android.widget.Button;
import com.example.bulksms.Utils.CSVClass;
import com.example.bulksms.Utils.ForbiddenWords;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bulksms.adapters.SaveItemAdapter;
import com.example.bulksms.models.CsvModel;
import com.example.bulksms.models.RegisterIdResponse;
import com.example.bulksms.models.SaveFileClass;
import com.example.bulksms.models.SendSmSResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Response;
import retrofit2.Callback;
import com.example.bulksms.webApi.ApiClient;
import retrofit2.Call;
import android.Manifest.*;


public class SendSMSActivity extends AppCompatActivity {
	ApiClient apiClient;
	String apiKey = "";
	EditText bulkEdit;
	Button buttonSms;
	CheckBox corporateC, getSavedFileC;
	Dialog dialog, openSaveDialog, openSavedFileDialog;
	String id;
	KeyListener listener;
	boolean isCChecked = false;
	boolean isIdValidWord = true;
	boolean isNormalRChecked = false;
	boolean isWordSaved;
	ActivityResultLauncher<Intent> launcher;
	TextView pages;
	EditText receiptNum;
	CheckBox registerId;
	EditText registeredId;
	int routeChoosed = 0;
	CheckBox saveC;
	CheckBox sendLaterC;
	SharedPreferences sharedPref;
	EditText smsEdit;
	CheckBox useNormalRoute;
	ArrayList<SaveFileClass> saveFileList;
	SharedPreferences sharedPreferences;
	StoreSaveFileDb storeSaveFileDb;
    String fileUrl;
	Uri fileUrlL;
	ArrayList<CsvModel> csvModelArrayList;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_sendsms);
		apiClient = ApiClient.getInstance();
		apiKey = getString(R.string.apiKey).trim();
		initViews();
	}

	public void initViews() {
     
	launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
				            public void onActivityResult(ActivityResult activityResult) {
					                if (activityResult != null) {
						                    Intent data = activityResult.getData();
											fileUrl=data.toString();
											fileUrlL=data.getData();
						                    SendSMSActivity sendSMSActivity = SendSMSActivity.this;
					                    StringBuilder stringBuilder = new StringBuilder();
					                    stringBuilder.append("not null url");
						                    stringBuilder.append(data.toString());
					                    //Toast.makeText(sendSMSActivity, stringBuilder.toString(), Toast.LENGTH_LONG).show();
										getCvsFile();
				                }
			            }
		     });
		
		
		
		
		
		saveFileList = new ArrayList<>();
		/*	sharedPref=this.getSharedPreferences("com.exanple.bulksms",Context.MODE_PRIVATE);
			if(!sharedPref.getString("savedFile",null).equals(null)){
			loadSavedFile();
			}*/
		storeSaveFileDb = new StoreSaveFileDb(this);
		saveFileList = storeSaveFileDb.getAllSavedFilesData();
		registerId = (CheckBox) findViewById(R.id.registerId);
		registeredId = (EditText) findViewById(R.id.editTextId);
		receiptNum = (EditText) findViewById(R.id.editTextRep);
		bulkEdit = (EditText) findViewById(R.id.addBulkEdit);
		smsEdit = (EditText) findViewById(R.id.sendSmsEdit);
		pages = (TextView) findViewById(R.id.numberPages);
		buttonSms = (Button) findViewById(R.id.sendMB);
		corporateC = (CheckBox) findViewById(R.id.corporateC);
		saveC = (CheckBox) findViewById(R.id.saveC);
		sendLaterC = (CheckBox) findViewById(R.id.sendLaterC);
		useNormalRoute = (CheckBox) findViewById(R.id.useNormalRC);
		getSavedFileC = findViewById(R.id.getsaveC);
		//registerId()d
		pages.setText("0");
		registerId.setOnClickListener(v -> {
			if (registerId.isChecked() == true) {
				showDialog();
				registerId.setChecked(false);
			}
		});
		checkWrongWords(smsEdit);
		verifyId(registeredId);
		useNormalRoute.setOnClickListener(v -> {
			if (useNormalRoute.isChecked()) {
				isNormalRChecked = true;
				routeChoosed = 2;

			}

			corporateC.setOnClickListener(p -> {
				if (corporateC.isChecked()) {
					isCChecked = true;
					routeChoosed = 4;
				}
			});
		});
		buttonSms.setOnClickListener(v -> {
			if (isNormalRChecked == true && isCChecked == true) {
				useNormalRoute.setChecked(false);
				corporateC.setChecked(false);
				isNormalRChecked = false;
				isCChecked = false;

				routeChoosed = 0;
				Toast.makeText(SendSMSActivity.this, "pick one route", Toast.LENGTH_LONG).show();
				return;

			}
			if (registeredId.getText().toString().isEmpty() == true) {
				Toast.makeText(SendSMSActivity.this, "id empty", Toast.LENGTH_LONG).show();
				return;
			}
			if (receiptNum.getText().toString().isEmpty() == true && bulkEdit.getText().toString().isEmpty()==true) {
				Toast.makeText(SendSMSActivity.this, "number empty", Toast.LENGTH_LONG).show();
				return;
			}
			if (smsEdit.getText().toString().isEmpty() == true) {
				Toast.makeText(SendSMSActivity.this, "message empty", Toast.LENGTH_LONG).show();
				return;
			}
			if(receiptNum.getText().toString().isEmpty()==false && bulkEdit.getText().toString().isEmpty()==true){
			sendMessage(registeredId.getText().toString(), receiptNum.getText().toString(),
					smsEdit.getText().toString(), String.valueOf(routeChoosed));
		}else if(bulkEdit.getText().toString().isEmpty()==false && receiptNum.getText().toString().isEmpty()==true){
			sendMessage(registeredId.getText().toString(), bulkEdit.getText().toString(),
			smsEdit.getText().toString(), String.valueOf(routeChoosed));
		}else{
			Toast.makeText(SendSMSActivity.this, "please pick one either bulk number or single number root", Toast.LENGTH_LONG).show();
		}			
		});

		saveC.setOnClickListener(v -> {
			if (saveC.isChecked()) {
				initSaveDialog();

			}
		});

		getSavedFileC.setOnClickListener(v -> {
			if (getSavedFileC.isChecked() == true) {
				initFileSavedDialog();
			}
		});
		
		bulkEdit.setOnClickListener(v->{
			if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
			}else{
			Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
			 intent.setType("*/*");
			 intent.addCategory("android.intent.category.OPENABLE");
			launcher.launch(intent);
			}
			
		}); 
		
		sendLaterC.setOnClickListener(v->{
			setTimePickerDialog();
		});

		//saveC.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda0(this));
		//useNormalRoute.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda1(this));
		//corporateC.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda2(this));
		//buttonSms.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda3(this));

	}
  public void setTimePickerDialog(){
	  Dialog  timePickerDialog=new Dialog(this);
	  timePickerDialog.setContentView(R.layout.sendlaterlayout);
	  timePickerDialog.show();
  }
	public void showDialog() {
		dialog = new Dialog(SendSMSActivity.this);

		dialog.setContentView(R.layout.dialog_activity_send);
		EditText editText = (EditText) dialog.findViewById(R.id.regIdDialog);
		EditText editText2 = (EditText) dialog.findViewById(R.id.regBussinessDialog);
		EditText editText3 = (EditText) dialog.findViewById(R.id.messageDialog);
		EditText editText4 = (EditText) dialog.findViewById(R.id.govIdDialog);
		EditText editText5 = (EditText) dialog.findViewById(R.id.buAddressDialog);
		Button button = (Button) dialog.findViewById(R.id.registerButtonDialog);

		dialog.findViewById(R.id.cancelDialog).setOnClickListener(v -> {
			dialog.cancel();
		});
		verifyId(editText);
		button.setOnClickListener(v -> {
			if (id.isEmpty()) {
				Toast.makeText(SendSMSActivity.this, "id is empty", Toast.LENGTH_LONG).show();
				return;
			}

			if (editText2.getText().toString().isEmpty()) {
				Toast.makeText(SendSMSActivity.this, "reg bussiness name id empty", Toast.LENGTH_LONG).show();
				return;
			}
			if (editText3.getText().toString().isEmpty()) {
				Toast.makeText(SendSMSActivity.this, "message empty", Toast.LENGTH_LONG).show();
				return;
			}
			if (editText4.getText().toString().isEmpty()) {
				Toast.makeText(SendSMSActivity.this, "gov issued id empty", Toast.LENGTH_LONG).show();
				return;
			}
			if (editText5.getText().toString().isEmpty()) {
				Toast.makeText(SendSMSActivity.this, "address empty", Toast.LENGTH_LONG).show();
				return;
			}

			if (!id.isEmpty()) {
				RegisterIdKey(id, editText3.getText().toString(), editText2.getText().toString(),
						editText4.getText().toString(), editText5.getText().toString());
			}

			// new SendSMSActivity$$ExternalSyntheticLambda7(this, editText3, editText2, editText4, editText5));
		});
		dialog.create();
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
	}

	public void verifyId(final EditText editText) {
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
		listener = editText.getKeyListener();

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				editText.setBackgroundResource(R.drawable.send_sms_design);
				//	editText.setKeyListener(editText.getKeyListener());
			}

			@Override
			public void afterTextChanged(Editable editable) {
				for (String str : editable.toString().toLowerCase().split(" ")) {
					for (String str2 : ForbiddenWords.getListOfForbiddenWordId()) {
						if (str.toString().toLowerCase().equals(str2.toString().toLowerCase())) {
							editText.setBackgroundResource(R.drawable.send_message_error);

							SendSMSActivity sendSMSActivity = SendSMSActivity.this;
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(str);
							stringBuilder.append(" not allowed");
							Toast.makeText(sendSMSActivity, stringBuilder.toString(), Toast.LENGTH_LONG).show();
							SendSMSActivity.this.isIdValidWord = false;
							editText.setKeyListener(null);
							startListening(editText);
						}
					}
				}
				SendSMSActivity.this.isIdValidWord = true;
				if (editable.length() > 10) {

					Toast.makeText(SendSMSActivity.this, "character greater than 10", 1).show();
					return;
				}
				if (editable.length() <= 10) {

					SendSMSActivity.this.id = editable.toString();
				}
			}
		});

	}

	public void startListening(EditText editText) {
		editText.setOnClickListener(v -> {
			editText.setKeyListener(listener);
		});
	}

	public void checkWrongWords(EditText editText) {
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(600) });
		listener = editText.getKeyListener();
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				editText.setBackgroundResource(R.drawable.send_sms_design);
			}

			@Override
			public void afterTextChanged(Editable editable) {
				int i;
				StringBuilder stringBuilder;
				for (String str : editable.toString().toLowerCase().split(" ")) {
					for (String str2 : ForbiddenWords.getListOfForbiddenWordId()) {
						if (str.toString().toLowerCase().equals(str2.toString().toLowerCase())) {
							editText.setBackgroundResource(R.drawable.send_message_error);
							SendSMSActivity sendSMSActivity = SendSMSActivity.this;
							stringBuilder = new StringBuilder();
							stringBuilder.append(str);
							stringBuilder.append(" not allowed");
							Toast.makeText(sendSMSActivity, stringBuilder.toString(), Toast.LENGTH_LONG).show();
							editText.setKeyListener(null);
							startListening(editText);
						}
					}
				}
				int length = editable.length();
				int[] iArr = new int[] { 150, 300, 450, 600 };
				for (i = 0; i < 4; i++) {
					TextView textView;
					StringBuilder stringBuilder2;
					String str3 = "number of pages:";
					if (length >= iArr[0]) {
						textView = SendSMSActivity.this.pages;
						stringBuilder2 = new StringBuilder();
						stringBuilder2.append(str3);
						stringBuilder2.append(String.valueOf(1));
						textView.setText(stringBuilder2.toString());
					}
					if (length >= iArr[1]) {
						textView = SendSMSActivity.this.pages;
						stringBuilder = new StringBuilder();
						stringBuilder.append(str3);
						stringBuilder.append(String.valueOf(2));
						textView.setText(stringBuilder.toString());
					}
					if (length >= iArr[2]) {
						textView = SendSMSActivity.this.pages;
						stringBuilder = new StringBuilder();
						stringBuilder.append(str3);
						stringBuilder.append(String.valueOf(3));
						textView.setText(stringBuilder.toString());
					}
					if (length >= iArr[3]) {
						textView = SendSMSActivity.this.pages;
						stringBuilder2 = new StringBuilder();
						stringBuilder2.append(str3);
						stringBuilder2.append(String.valueOf(4));
						textView.setText(stringBuilder2.toString());
					}
				}
				if (length > 610) {
					editText.setBackgroundResource(R.drawable.send_message_error);

				} else {
					editText.setBackgroundResource(R.drawable.send_sms_design);
				}
			}
		});
	}

	public void saveText() {
		//	SaveMessageClass saveMessageClass = new SaveMessageClass();
		/*	Editor edit = this.sharedPref.edit();
		if ((TextUtils.isEmpty(this.registeredId.getText().toString())
		& TextUtils.isEmpty(this.smsEdit.getText().toString())) == 0) {
			saveMessageClass.setId(this.registeredId.getText().toString());
			saveMessageClass.setMessage(this.smsEdit.getText().toString());
			saveMessageClass.setRecipient(this.receiptNum.getText().toString());
			edit.putString("id", saveMessageClass.getId());
			edit.putString("message", saveMessageClass.getMessage());
			edit.putString("repNum", saveMessageClass.getRecipient());
			edit.putBoolean("isWordSaved", true);
			edit.commit();
			Toast.makeText(this, "messages saved", 1).show();
		}
		*/
	}

	public void getSaveWords() {
		String str = "null";
		String string = sharedPref.getString("id", str);
		String string2 = sharedPref.getString("message", str);
		str = sharedPref.getString("repNum", str);
		registeredId.setText(string);
		smsEdit.setText(string2);
		receiptNum.setText(str);
	}

	public void sendMessage(String id, String rep, String message, String rout) {
		apiClient.getWebApi().sendSmSResponseCall(apiKey, id, rep, message, "0", rout)
				.enqueue(new Callback<SendSmSResponse>() {
					@Override
					public void onResponse(Call<SendSmSResponse> arg0, Response<SendSmSResponse> arg1) {
						StringBuilder builder = new StringBuilder();
						builder.append(arg1.body().getComment());
						showSuccessIDRegDialog("MESSAGE STATUS", "success:" + " " + builder.toString());
					}

					@Override
					public void onFailure(Call<SendSmSResponse> arg0, Throwable arg1) {
						showSuccessIDRegDialog("MESSAGE STATUS", arg1.getMessage() + "error");

					}

				});
	}

	public void RegisterIdKey(String str, String str2, String str3, String str4, String str5) {

		apiClient.getWebApi().registerId(apiKey, str, str2, str3, str4, str5)
				.enqueue(new Callback<RegisterIdResponse>() {
					@Override
					public void onResponse(Call<RegisterIdResponse> call, Response<RegisterIdResponse> response) {
						SendSMSActivity sendSMSActivity = SendSMSActivity.this;
						StringBuilder stringBuilder = new StringBuilder();
						//stringBuilder.append(response.body().getSuccessResponse());
						stringBuilder.append(response.body().getComment());
						//stringBuilder.append("success");
						//	Toast.makeText(SendSMSActivity.this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
						dialog.cancel();
						registeredId.setText(id);
						showSuccessIDRegDialog("ID Registration ", stringBuilder.toString());
					}

					@Override
					public void onFailure(Call<RegisterIdResponse> call, Throwable th) {
						Toast.makeText(SendSMSActivity.this, th.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});
	}

	public void showSuccessIDRegDialog(String title, String comment) {

		AlertDialog.Builder idDialog = new AlertDialog.Builder(SendSMSActivity.this);
		idDialog.setTitle(title);
		idDialog.setMessage(comment);
		idDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		idDialog.create().show();
	}

	public void initFileSavedDialog() {
		openSavedFileDialog = new Dialog(this);
		openSavedFileDialog.setContentView(R.layout.saveditemlayout);
		ArrayList<SaveFileClass> updatedFile = storeSaveFileDb.getAllSavedFilesData();
		Button deleteB, useB;
		deleteB = openSavedFileDialog.findViewById(R.id.deleteSaveF);
		useB = openSavedFileDialog.findViewById(R.id.useSaveF);

		ListView listView = openSavedFileDialog.findViewById(R.id.listSavedFile);
		SaveItemAdapter adapter = new SaveItemAdapter(this, updatedFile);
		deleteB.setOnClickListener(v -> {
			deleteSaveFile(adapter, updatedFile);

		});
		useB.setOnClickListener(v -> {
			getSavedFile(updatedFile);
			openSavedFileDialog.cancel();
		});
		listView.setAdapter(adapter);
		openSavedFileDialog.show();
	}

	public void loadSavedFile() {

		/*	String savedFile=sharedPref.getString("savedFile",null);
			Gson gson=new Gson();
			Type type=new TypeToken<ArrayList<SaveFileClass>>() {}.getType();
				saveF=saveF=gson.fromJson(savedFile,type);*/
		
	}

	public void initSaveDialog() {
		openSaveDialog = new Dialog(this);
		openSaveDialog.setContentView(R.layout.savelayout_dialog);
		Button cancel, saveB;
		EditText fileName;
		cancel = openSaveDialog.findViewById(R.id.cancelSD);
		saveB = openSaveDialog.findViewById(R.id.saveFileB);
		fileName = openSaveDialog.findViewById(R.id.nameSave);
		cancel.setOnClickListener(v -> {
			saveC.setChecked(false);
			openSaveDialog.cancel();

		});
		saveB.setOnClickListener(v -> {
			saveAllFiles(fileName);
		});
		openSaveDialog.show();
	}

	public void saveAllFiles(EditText fileN) {
		String fileName = fileN.getText().toString();
		String idN = registeredId.getText().toString();
		String repNN = receiptNum.getText().toString();
		String smsN = smsEdit.getText().toString();
		String multRep=bulkEdit.getText().toString();
		long sc=storeSaveFileDb.addSavedFile(idN, repNN, multRep, smsN, fileName);
		if(sc>0)
		openSaveDialog.cancel();

	}

	public void deleteSaveFile(SaveItemAdapter adapter, ArrayList<SaveFileClass> updatedFile) {
		int position = SaveItemAdapter.fPosition;
		boolean checked = SaveItemAdapter.isChecked;
		if (checked == false) {
			Toast.makeText(this, "no file seleted", Toast.LENGTH_LONG).show();
			return;
		}
		int resp = storeSaveFileDb.deleteFile(updatedFile.get(position).getFileName());
		if (resp > 0)
			adapter.removeItem(position);

	}

	public void getSavedFile(ArrayList<SaveFileClass> updatedList) {
		int position = SaveItemAdapter.fPosition;
		boolean checked = SaveItemAdapter.isChecked;
		if (checked == false) {
			Toast.makeText(this, "no file seleted", Toast.LENGTH_LONG).show();
			return;
		}

		String id = updatedList.get(position).getid();
		String recep = updatedList.get(position).getRecipient();
		String multRep = updatedList.get(position).getMultiRecipiet();
		String message = updatedList.get(position).getMessage();
	//	Toast.makeText(this,message,Toast.LENGTH_LONG).show();

		if (id.isEmpty()) {
			registeredId.setText("");
		} else {
			registeredId.setText(id);
		}

		if (recep.isEmpty()) {
			receiptNum.setText("");
		} else {
			receiptNum.setText(recep);
		}

		if (multRep.isEmpty()) {
			bulkEdit.setText("");

		} else {
          bulkEdit.setText(multRep);
		}
		

		if (message.isEmpty()) {
			smsEdit.setText("");
		} else {
			smsEdit.setText(message);
		}

	}
	
	
	public void getCvsFile(){
		File file=new File(fileUrl);
	if(!fileUrl.contains(".csv")){
		Toast.makeText(this,"not csv file",Toast.LENGTH_LONG).show();
		return;
	}
	try{
	InputStream input=getContentResolver().openInputStream(fileUrlL);
	File f=CSVClass.getFileFromInputStrean(input);
	
csvModelArrayList=CSVClass.getCsvData(f,this);
//Toast.makeText(this,csvModelArrayList.get(0).getPhoneNumbers(),Toast.LENGTH_LONG).show();
StringBuilder builder=new StringBuilder();
new Thread(new Runnable(){
	@Override
	public void  run(){
		for(int i=0;i<csvModelArrayList.size();i++){
			builder.append(csvModelArrayList.get(i).getPhoneNumbers());
			
				if(i!=csvModelArrayList.size()-1){
			builder.append(",");
			}
		}
		
     runOnUiThread(new Runnable(){
		 @Override
		 public void run(){
			 bulkEdit.setText(builder.toString());
		 }
	 });
	}
}).start();
	

	}catch(Exception e){
		Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
	}
	
		}
}
