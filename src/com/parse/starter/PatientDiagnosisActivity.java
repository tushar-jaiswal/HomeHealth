package com.parse.starter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PatientDiagnosisActivity extends Activity {

	protected static final String TAG = "Patient Diagnosis Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_diagnosis);
		populatePatientDiagnosis();
	}

	public void populatePatientDiagnosis()
	{
		final ListView list = (ListView) findViewById(R.id.listDiagnosis);

		final ArrayList<HashMap<String, String>> patientDiagnosisList = new ArrayList<HashMap<String, String>>();


		ParseQuery query = new ParseQuery("Diagnosis");

		query.findInBackground(new FindCallback() {
			public void done(List<ParseObject> diagnosisList, ParseException e) {
				if (e == null) {
					Log.i(TAG , "Inside done :" + diagnosisList.size());
					for(ParseObject diagObj : diagnosisList)
					{

						HashMap<String, String> map = new HashMap<String, String>();
						map.put("result" , diagObj.getString("result"));
						Log.i(TAG, diagObj.getString("result"));
						map.put("patient" , diagObj.getString("doctor"));
						Log.i(TAG, diagObj.getString("doctor"));
						map.put("diagnosisDate" , diagObj.getCreatedAt().toString());
						Log.i(TAG, diagObj.getCreatedAt().toString());

						patientDiagnosisList.add(map);

					}
					list.setAdapter(new SimpleAdapter(getApplicationContext(), patientDiagnosisList, R.layout.diagnosis_row,
							new String[] {"result", "patient" , "diagnosisDate" }, new int[] {R.id.diagnosisResult, R.id.diagnosisDoctorName, R.id.diagnosisDate}));

				}

				else
				{
					Toast.makeText(getApplicationContext(), "No data to show!", Toast.LENGTH_SHORT).show();
				}
			}

		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_patient_diagnosis, menu);
		return true;
	}
}
