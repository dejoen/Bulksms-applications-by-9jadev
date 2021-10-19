package com.example.bulksms.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bulksms.R;
import com.example.bulksms.adapters.ItemAdapter;
import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
 TextView userName,userAmount;
 RecyclerView itemRec;
 ImageView mWalletPayment;
 int aamountt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeView();
        activityResult();
    }
    private  void initializeView(){
userName=findViewById(R.id.userName);
userAmount=findViewById(R.id.userAmount);
itemRec=findViewById(R.id.itemRec);
mWalletPayment=findViewById(R.id.myWallet);
setupRecycler();
mWalletPayment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        paymentView();
    }
});
    }
    private  void setupRecycler(){
        ArrayList<String>  arrayList=new ArrayList<>();
        arrayList.add("send sms");
        arrayList.add("buy airtime/data");
        arrayList.add("bills");
        arrayList.add("withdraw");
        ArrayList<Integer> arrayList1=new ArrayList<>();
        arrayList1.add(R.drawable.sms);
        arrayList1.add(R.drawable.data);
        arrayList1.add(R.drawable.data);
        arrayList1.add(R.drawable.wallet);
        ItemAdapter itemAdapter=new ItemAdapter(this,arrayList,arrayList1);
        itemRec.setLayoutManager(new GridLayoutManager(this,2));
        itemRec.setAdapter(itemAdapter);
    }

    private void makePayment(int amount){
        new RavePayManager(this).setAmount(8000)
  .setEmail("test@gmail.com")
                .setCountry("NG")
                .setCurrency("NGN")
                .setfName("Licio")
                .setlName("Lentimo")
                .setNarration("Purchase Goods")
                .setPublicKey("FLWPUBK-21f8ced7f34c395d298d8eb1c2dd1196-X")
                .setEncryptionKey("3100f40d6a976b5dde078054")
                .setTxRef(System.currentTimeMillis() + "Ref")
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .onStagingEnv(true)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize();

    }


    private void  paymentView(){
        Dialog dialog =new Dialog(this);
        dialog.setContentView(R.layout.paymentgateway);
        EditText amount=dialog.findViewById(R.id.editTextNumberAmount);
        Button pay=dialog.findViewById(R.id.buttonPay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAmount=amount.getText().toString();
                int ama=Integer.parseInt(mAmount);

                makePayment(ama);
                dialog.cancel();

            }
        });
        dialog.show();


    }
private  void activityResult(){
    registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== RaveConstants.RAVE_REQUEST_CODE){
                Toast.makeText(HomeActivity.this, "caled", Toast.LENGTH_SHORT).show();

                Intent data=result.getData();
                if (data!=null) {

                        String message=data.getStringExtra("response");
                        if(result.getResultCode()== RavePayActivity.RESULT_SUCCESS){

                            Toast.makeText(HomeActivity.this, "success"+message, Toast.LENGTH_SHORT).show();
                        }else  if(result.getResultCode()== RavePayActivity.RESULT_ERROR){
                            Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }else  if(result.getResultCode()== RavePayActivity.RESULT_CANCELLED){
                            Toast.makeText(HomeActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        }
    });
}


}