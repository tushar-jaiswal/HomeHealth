package com.parse.starter;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.PushService;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TableRow.LayoutParams;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class SymptomActivity extends Activity {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static final String TAG = "Help Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

		setContentView(R.layout.activity_symptom);
		
	
		/*setTitle(R.string.app_name);*/
	}

	
	/*public void populateSpinner() {
		
		final Spinner docRolesSpinner = (Spinner)findViewById(R.id.docRolesSpinner);
		Spinner docNameSpinner = (Spinner)findViewById(R.id.docNameSpinner);
		final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		docNameSpinner.setAdapter(spinnerAdapter);
		docRolesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v,
                    int position, long rowId) 
            {
                String selection =   docRolesSpinner.getItemAtPosition(position).toString();
                
            	ParseQuery query = ParseRole.getQuery();
      	    	query.whereEqualTo("name", selection);
      	    	query.getFirstInBackground(new GetCallback() {

      				public void done(ParseObject object, ParseException e) {
      					if (e == null) {
      						 ParseRelation userRelation = object.getRelation("users");
      						 userRelation.getQuery().findInBackground(new FindCallback() {
      						    public void done(List<ParseObject> doctorList, ParseException e) {
      						        if (e == null) {
      						        	for(ParseObject doctor : doctorList )
      						        	{
      						        		Log.i(TAG , doctor.getString("username"));
      						        		spinnerAdapter.add(doctor.getString("username"));
      						        		spinnerAdapter.notifyDataSetChanged();
      						        	}
      						        
      						
      					} 
      				}
      			});
      					}
      				}
      	    	});
		}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Please select a doctor role!", Toast.LENGTH_SHORT)
                .show();
				
			}
		
		});
	}*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patientmenu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.profile:
			Intent profileActivity = new Intent(this, ProfileActivity.class);
			startActivity(profileActivity); 
			break;
		case R.id.symptom:
			Intent symptomActivity = new Intent(this, SymptomActivity.class);
			startActivity(symptomActivity); 
			break;
		case R.id.help:
			Intent helpActivity = new Intent(this, HelpActivity.class);
			startActivity(helpActivity); 
			break;
		case R.id.logout:
			Intent logoutActivity = new Intent(this, LogoutActivity.class);
			startActivity(logoutActivity); 
			break;
		default:
			break;
		}
		return true;
	}
	
	@SuppressLint("NewApi")
	public void saveSymptom(View view){
		Log.i(TAG, "Inside save symptom");
		try
		{
			EditText editTextSymptoms = (EditText) findViewById(R.id.symptoms);
			String symptoms = editTextSymptoms.getText().toString();


			DatePicker symptomDateWidget = (DatePicker) findViewById(R.id.sympDate);
			Date symptomDate = new Date(symptomDateWidget.getCalendarView().getDate());



			Spinner doctorRolesSpinner = (Spinner) findViewById(R.id.docRolesSpinner);
			String doctorRole = String.valueOf(doctorRolesSpinner.getSelectedItem());

			
			

			ParseObject mySymptom = new ParseObject("UserSymptoms");
			mySymptom.put("symptoms", symptoms);
			mySymptom.put("symptomDate", symptomDate);
			mySymptom.put("doctorRole", doctorRole);
			
			/*mySymptom.put("doctorName", doctorName);*/
			mySymptom.put("username", ParseUser.getCurrentUser().getUsername());
			mySymptom.saveInBackground();
			Toast.makeText(getApplicationContext(), "Symptom saved successfully!", Toast.LENGTH_SHORT).show();
			Intent helpActivity = new Intent(this, HelpActivity.class);
			startActivity(helpActivity); 
			Log.i(TAG , "Symptom saved successfully");


		}
		catch(Exception e)
		{
			Log.e(TAG, "Error in saving profile :" + e.getMessage());

		}


	}

}
