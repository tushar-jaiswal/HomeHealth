package com.parse.starter;

import com.parse.Parse;
import com.parse.PushService;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;

public class DoctorHomeActivity  extends Activity   {

	private static final String TAG = "Doctor Home Activity";
	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		Log.d(TAG, "Inside Doctor Home activity");
		setContentView(R.layout.activity_doctor_home);		
		setTitle(R.string.app_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.doctormenu, menu);
		return true;
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.diagnosis:
	        Intent diagnosisActivity = new Intent(this, GiveDiagnosisActivity.class);
	        startActivity(diagnosisActivity); 
	        break;
	      default:
	        break;
	      }
	      return true;
	      }
}
