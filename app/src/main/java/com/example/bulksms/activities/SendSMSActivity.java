package com.example.bulksms.activities;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Toast;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import com.example.bulksms.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import android.app.Dialog;
import android.widget.CheckBox;
import android.widget.Button;
import com.example.bulksms.Utils.ForbiddenWords;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bulksms.models.RegisterIdResponse;
import com.example.bulksms.models.SendSmSResponse;
import retrofit2.Response;
import retrofit2.Callback;
import com.example.bulksms.webApi.ApiClient;
import retrofit2.Call;

public class SendSMSActivity extends AppCompatActivity {
	ApiClient apiClient;
	String apiKey = "";
	EditText bulkEdit;
	Button buttonSms;
	CheckBox corporateC;
	Dialog dialog;
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

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_sendsms);
		apiClient = ApiClient.getInstance();
		apiKey = getString(R.string.apiKey).trim();
		initViews();
	}

	public void initViews() {

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
		//registerId();
		pages.setText("0");
		registerId.setOnClickListener(v -> {
			if (registerId.isChecked() == true) {
				showDialog();
				registerId.setChecked(false);
			}
		});
		checkWrongWords(smsEdit);
		verifyId(registeredId);
		useNormalRoute.setOnClickListener(v->{
			if(useNormalRoute.isChecked()){
			 isNormalRChecked=true;
			 routeChoosed=2;
			 
			}
			
			corporateC.setOnClickListener(p ->{
				if(corporateC.isChecked()){
					isCChecked=true;
					routeChoosed=4;
				}
			});
		});
		buttonSms.setOnClickListener(v->{
			if(isNormalRChecked==true && isCChecked==true){
				useNormalRoute.setChecked(false);
				corporateC.setChecked(false);
				isNormalRChecked=false;
				isCChecked=false;
				
				routeChoosed=0;
				Toast.makeText(SendSMSActivity.this,"pick one route",Toast.LENGTH_LONG).show();
				return;
				
			}
			if(registeredId.getText().toString().isEmpty()==true){
				Toast.makeText(SendSMSActivity.this,"id empty",Toast.LENGTH_LONG).show();
				return;
			}
			if(receiptNum.getText().toString().isEmpty()==true){
				Toast.makeText(SendSMSActivity.this,"number empty",Toast.LENGTH_LONG).show();
				return;
			}
			if(smsEdit.getText().toString().isEmpty()==true){
				Toast.makeText(SendSMSActivity.this,"message empty",Toast.LENGTH_LONG).show();
				return;
			}
			sendMessage(registeredId.getText().toString(),receiptNum.getText().toString(),smsEdit.getText().toString(),String.valueOf(routeChoosed));
		});
		
		//saveC.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda0(this));
		//useNormalRoute.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda1(this));
		//corporateC.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda2(this));
		//buttonSms.setOnClickListener(new SendSMSActivity$$ExternalSyntheticLambda3(this));

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
		editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
		listener=editText.getKeyListener();	
			
			
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
    public  void startListening(EditText editText){
		editText.setOnClickListener(v->{
			editText.setKeyListener(listener);
		});
	}
	public void checkWrongWords( EditText editText) {
		editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(600)});
		listener=editText.getKeyListener();
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
	public  void sendMessage(String id,String rep,String message,String rout){
		apiClient.getWebApi().sendSmSResponseCall(apiKey,id,rep,message,"0",rout).enqueue(new Callback<SendSmSResponse>(){
			@Override
			public void onResponse(Call<SendSmSResponse> arg0, Response<SendSmSResponse> arg1) {
				StringBuilder builder=new StringBuilder();
				builder.append(arg1.body().getComment());
				showSuccessIDRegDialog("MESSAGE STATUS","success:"+" "+builder.toString());
			}

			@Override
			public void onFailure(Call<SendSmSResponse> arg0, Throwable arg1) {
				showSuccessIDRegDialog("MESSAGE STATUS",arg1.getMessage() +"error");
			
			
			
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
                      showSuccessIDRegDialog("ID Registration ",stringBuilder.toString());
					}

					@Override
					public void onFailure(Call<RegisterIdResponse> call, Throwable th) {
						Toast.makeText(SendSMSActivity.this, th.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});
	}
	
	public void showSuccessIDRegDialog(String title,String comment){
		
		AlertDialog.Builder idDialog=new AlertDialog.Builder(SendSMSActivity.this);
		idDialog.setTitle(title);
		idDialog.setMessage(comment);
		idDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
			@Override
			public  void onClick(DialogInterface dialog,int which){
				dialog.cancel();
				}
		});
		idDialog.create().show();
	}
}