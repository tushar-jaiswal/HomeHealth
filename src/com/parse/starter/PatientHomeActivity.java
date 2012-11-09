package com.parse.starter;

import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PatientHomeActivity  extends Activity   {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static final String TAG = "Patient Home Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		setContentView(R.layout.activity_patient_home);	
		setTitle(R.string.app_name);
		Log.d(TAG , "Inside patient home activity");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patientmenu, menu);
		return true;
	}
	
	 @Override
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
			case R.id.patientDiagnosis:
				Intent patientDiagnosis = new Intent(this, PatientDiagnosisActivity.class);
				startActivity(patientDiagnosis); 
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
}
