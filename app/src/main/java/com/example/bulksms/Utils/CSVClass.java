package com.example.bulksms.Utils;


import android.content.Context;
import android.widget.Toast;
import com.example.bulksms.models.CsvModel;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.IOUtils;

public class CSVClass {
	Context context;
	public  CSVClass(Context context){
		this.context=context;
	}
	
	public static ArrayList<CsvModel>  getCsvData(File file,Context context){
		ArrayList<CsvModel> csvModelArrayList=new ArrayList<CsvModel>();
		CsvSchema schema=CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
		ObjectReader objectReader=new CsvMapper().reader(CsvModel.class).with(schema);
		try {
			MappingIterator<CsvModel> mappingIterator=objectReader.readValues(file);
			while (mappingIterator.hasNext()){
				
				csvModelArrayList.add(mappingIterator.next());
			}
			}catch (Exception e){
			Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
		}
		
		return  csvModelArrayList;
	}
	public  static  File getFileFromInputStrean(InputStream inputStream){
		
		File file=null;
		try{
			file=File.createTempFile("tempFile",null);
			//file.deleteOnExit();
			OutputStream output=new FileOutputStream(file);
			IOUtils.copy(inputStream,output);
			
			}catch (Exception e){
			
		}
		
		
		return  file;
		
	}
	
}