package com.parse.starter;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiagnosisActivity  extends Activity   {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	protected static final String TAG = "Diagnosis Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		setContentView(R.layout.activity_diagnosis);
		populateDiagnosisList();
		/*setTitle(R.string.app_name);*/
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
			Intent diagnosisActivity = new Intent(this, DiagnosisActivity.class);
			startActivity(diagnosisActivity); 
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
	public void populateDiagnosisList(){
		final ListView list = (ListView) findViewById(R.id.listSymptoms);

		final ArrayList<HashMap<String, String>> helpSymptomList = new ArrayList<HashMap<String, String>>();


		ParseQuery query = new ParseQuery("UserSymptoms");
		
		query.findInBackground(new FindCallback() {
			public void done(List<ParseObject> symptomList, ParseException e) {
				if (e == null) {
					Log.i(TAG , "Inside done :" + symptomList.size());
					for(ParseObject symptomObj : symptomList)
					{
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("symptom" , symptomObj.getString("symptoms"));
						map.put("patient" , symptomObj.getString("username"));
						map.put("doctorRole" , symptomObj.getString("doctorRole"));
						map.put("symptomDate" , symptomObj.getDate("symptomDate").toString());

						helpSymptomList.add(map);

					}
					list.setAdapter(new SimpleAdapter(getApplicationContext(), helpSymptomList, R.layout.symptom_row,
							new String[] {"symptom", "patient" , "doctorRole", "symptomDate" }, new int[] {R.id.helpSymptom, R.id.helpPatient, R.id.helpDoctorRole, R.id.helpSymptomDate}));

				}

				else
				{
					Toast.makeText(getApplicationContext(), "No data to show!", Toast.LENGTH_SHORT).show();
				}
			}

		});
	}
}

