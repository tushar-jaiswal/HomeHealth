package com.parse.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GiveDiagnosisActivity  extends Activity   {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static final String TAG = "Give Diagnosis Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		setContentView(R.layout.activity_give_diagnosis);		
		setTitle(R.string.app_name);
		TextView sendButton = (TextView) findViewById(R.id.btnSend);
		sendButton.setOnClickListener(sendClickListener);// Listening to Logout button click
		
		ParseQuery query = new ParseQuery("UserSymptoms");
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> objects, ParseException e) {
		        if (e == null) {
		        	ArrayAdapter<String> patientArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
		        	patientArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        	Spinner patientSpinner = (Spinner)findViewById(R.id.patients);
		        	patientSpinner.setAdapter(patientArrayAdapter);
		        	for (int i=0; i<objects.size();i++)
    		    	{
		        		Log.d(TAG,"fsaddfafa"+objects.get(i).getString("username"));
		        		patientArrayAdapter.add(objects.get(i).getString("username"));
		        		patientArrayAdapter.notifyDataSetChanged();
    		    	}
		        } else {
		            Log.d(TAG, "Error in querying Symptoms");
		        }
		    }
		});
	}
	
	private OnClickListener sendClickListener = new OnClickListener() {
		public void onClick(View v) {
						
			Spinner patientSpinner = (Spinner)findViewById(R.id.patients);
			String username = String.valueOf(patientSpinner.getSelectedItem());
			
			ParseQuery query = ParseUser.getQuery();
    		query.whereEqualTo("username", username);
    		query.findInBackground(new FindCallback() {
    		  public void done(List<ParseObject> objects, ParseException e) {
    		    if (e == null) {
    		    	ParseUser patient = (ParseUser)objects.get(0);
    		    	
    		    	EditText editTextDiagnosis = (EditText) findViewById(R.id.diagnosis);
    				String diagnosis = editTextDiagnosis.getText().toString();
    				
    				ParseUser doctor = ParseUser.getCurrentUser();
    				
    		    	ParseObject doctosDiagnosis = new ParseObject("Diagnosis");
    				doctosDiagnosis.put("result", diagnosis);
    				doctosDiagnosis.put("doctor", doctor.getUsername());
    				doctosDiagnosis.put("patient", patient.getUsername());
    				
    				ParseACL groupACL = new ParseACL();
    				groupACL.setReadAccess(doctor, true);
    				groupACL.setReadAccess(patient, true);
    				doctosDiagnosis.setACL(groupACL);
    				doctosDiagnosis.saveInBackground();
    							
    				ParsePush push = new ParsePush();
    	  	    	push.setChannel("patientchannel_"+patient.getObjectId()); // Set the channel
    	  	    	push.setMessage("Please see the diagnosis I've sent you. Get well soon!.");
    	  	    	push.sendInBackground();
    	  	    	
    	  	    	Toast.makeText(getApplicationContext(), "Diagnosis sent successfully!", Toast.LENGTH_SHORT).show();

    		    }
    		  else
	    		  {
	  		        // Something went wrong.
    			  }
    		  }
    		  });
					}
	};
}
