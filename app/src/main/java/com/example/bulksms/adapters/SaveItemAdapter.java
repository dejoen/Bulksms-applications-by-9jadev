package com.example.bulksms.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bulksms.R;

import android.widget.BaseAdapter;
import com.example.bulksms.models.SaveFileClass;
import java.util.ArrayList;

public class SaveItemAdapter extends BaseAdapter {
ArrayList<SaveFileClass> saveFileClassArrayList;
Context context;
public  static  int fPosition;
public static boolean isChecked=false;
 public SaveItemAdapter(Context context,ArrayList<SaveFileClass> sf){
	 this.saveFileClassArrayList=sf;
	 this.context=context;
	 }
	@Override
	public int getCount() {
	    return saveFileClassArrayList.size();
	}

	@Override
	public Object getItem(int position) {
	    return null;
	}

	@Override
	public long getItemId(int position) {
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=LayoutInflater.from(context).inflate(R.layout.saveanddeleteitem,null);
		TextView textView=v.findViewById(R.id.textS);
		CheckBox checkBox=v.findViewById(R.id.checkC);
		textView.setText(saveFileClassArrayList.get(position).getFileName());
		checkBox.setOnClickListener(cView->{
			if(checkBox.isChecked()==true){
				fPosition=position;
				isChecked=true;
				
			//	Toast.makeText(context,"position"+String.valueOf(position),Toast.LENGTH_LONG).show();
			}else if(checkBox.isChecked()==false){
				isChecked=false;
			}
		});
	    return v;
	}
	
	public  void removeItem(int position){
		saveFileClassArrayList.remove(position);
		notifyDataSetChanged();
	}
	
}