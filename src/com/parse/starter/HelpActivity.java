package com.parse.starter;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseRole;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import java.util.HashMap;

public class HelpActivity extends Activity {

	protected static final String TAG = "HelpActivity";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		populateSpinner();
	}

	public void populateSpinner() {

		final Spinner docRolesSpinner = (Spinner)findViewById(R.id.helpDocRolesSpinner);
		Spinner docNameSpinner = (Spinner)findViewById(R.id.helpDocNameSpinner);
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


									} else {

									}
								}
							});
						}
					}
				});
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void getHelp(View view){
		Spinner doctorRolesSpinner = (Spinner) findViewById(R.id.helpDocRolesSpinner);
		final String doctorRole = String.valueOf(doctorRolesSpinner.getSelectedItem());

		Spinner doctorNameSpinner = (Spinner) findViewById(R.id.helpDocNameSpinner);
		final String doctorName = String.valueOf(doctorNameSpinner.getSelectedItem());

		ParseQuery query = new ParseQuery("UserSymptoms");
		query.whereEqualTo("doctorRole", doctorRole);
		query.findInBackground(new FindCallback() {
			public void done(List<ParseObject> symtomList, ParseException e) {
				if (e == null) {
					Log.i(TAG, "Inside Symptom List" + symtomList.size());
					for(final ParseObject symptomObj : symtomList )
					{
						ParseQuery query = ParseUser.getQuery();
						query.whereEqualTo("username", doctorName);
						query.getFirstInBackground(new GetCallback() {

							public void done(ParseObject object, ParseException e) {
								if (e == null) {
									Log.i(TAG, "Inside Symptom List Final");
									ParseACL symptomObjACL = symptomObj.getACL();
									symptomObjACL.setReadAccess((ParseUser)object, true);
									symptomObj.setACL(symptomObjACL);
									symptomObj.saveInBackground();
									Toast.makeText(getApplicationContext(), "Help Requested!", Toast.LENGTH_SHORT).show();
								}
								else
								{
									Toast.makeText(getApplicationContext(), "Error in requesting help! Try again later.", Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});


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
		case R.id.logout:
			Intent logoutActivity = new Intent(this, LogoutActivity.class);
			startActivity(logoutActivity); 
			break;
		default:
			break;
		}
		return true;
	}




	/*public void addSymtomData()
	{	

		final ListView list = (ListView) findViewById(R.id.helpSymptomList);

		final ArrayList<HashMap<String, String>> helpSymptomList = new ArrayList<HashMap<String, String>>();

        final HashMap<Integer, String> symptomIdMap = new HashMap<Integer, String>(); 
		ParseQuery query = new ParseQuery("UserSymptoms");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback() {
			public void done(List<ParseObject> symptomList, ParseException e) {
				if (e == null) {
					Log.i(TAG , "Inside add symptom data");

					Log.i(TAG , "" + symptomList.size());
					int symptomIndex = 0;
					for(ParseObject symptomObj : symptomList)
					{
						Log.i(TAG , "Inside for loop");	
						Log.i(TAG , symptomObj.getObjectId());
						symptomIdMap.put(symptomIndex, symptomObj.getObjectId());
						symptomIndex++;
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("symptom" , symptomObj.getString("symptoms"));
						map.put("doctorRole" , symptomObj.getString("doctorRole"));
						map.put("doctorName" , symptomObj.getString("doctorName"));
						map.put("symptomDate" , symptomObj.getString("symptomDate"));
						helpSymptomList.add(map);

					}

					list.setAdapter(new SimpleAdapter(getApplicationContext(), helpSymptomList, R.layout.help_symptom_row,
							new String[] {"symptom", "doctorRole", "doctorName" ,"symptomDate" }, new int[] {R.id.helpSymptom, R.id.helpSymptomDoctorRole, R.id.helpSymptomDoctor, R.id.helpSymptomDate}));




					list.setOnItemClickListener(new OnItemClickListener() {
						  @Override
						  public void onItemClick(AdapterView<?> parent, View view,
						    int position, long id) {
                           Log.i(TAG , "Item selected");
                           TextView v=(TextView) view.findViewById(R.id.helpSymptom);
						    Log.i(TAG , "Help symptom" + v.getText());
						    Log.i(TAG , "Help symptom GET ID" + v.getId());
						    Log.i(TAG , "Help symptom ID" + id );
						    Log.i(TAG , "Help symptom Object ID" + symptomIdMap.get(position) );

						    ParseQuery query = new ParseQuery("UserSymptoms");
						    query.getInBackground(symptomIdMap.get(position), new GetCallback() {
						      public void done(ParseObject object, ParseException e) {
						        if (e == null) {
						        	ParseACL objACL = object.getACL();
						        	objACL.
						        } else {
						          // something went wrong
						        }
						      }
						    });

						  }
						});

				}
			}
		});

		Log.i(TAG , "Before adding to list");	



		Log.i(TAG , "After adding to list");

		final TableLayout tl = (TableLayout)findViewById(R.id.tableSymptomLayout);

		ParseQuery query = new ParseQuery("UserSymptoms");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> symptomList, ParseException e) {
		        if (e == null) {
		        	Log.i(TAG , "Inside add symptom data");
		        	 Create a new row to be added. 

		        	for(ParseObject symptomObj : symptomList)
		        	{
		    		TableRow tr = new TableRow(getApplicationContext());
		    		tr.setLayoutParams(new LayoutParams(
		    				LayoutParams.WRAP_CONTENT,
		    				LayoutParams.WRAP_CONTENT));

		            TextView symptomView = new TextView(getApplicationContext());
		            symptomView.setText("Nov 7, 2011");
		            symptomView.setTextColor(Color.WHITE);
		            symptomView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            symptomView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		            //symptomView.setPadding(5, 5, 5, 0);
		            tr.addView(symptomView);  // Adding textView to tablerow.



		            Log.i(TAG , symptomObj.getString("symptomDate"));
		            TextView symptomDateView = new TextView(getApplicationContext());
		            symptomDateView.setText(symptomObj.getString("symptomDate"));
		            symptomDateView.setTextColor(Color.WHITE);
		            symptomDateView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		            //symptomDateView.setPadding(5, 5, 5, 0);
		            symptomDateView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            tr.addView(symptomDateView);


		            TextView doctorRoleView = new TextView(getApplicationContext());
		            symptomDateView.setText(symptomObj.getString("doctorRolesSpinner"));
		            symptomDateView.setTextColor(Color.WHITE);
		            symptomDateView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		            //symptomDateView.setPadding(5, 5, 5, 0);
		            symptomDateView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		            tr.addView(doctorRoleView);

		            // Add the TableRow to the TableLayout
		            tl.addView(tr);


		        	}

		        } 
		    }
		});
	}
	 */

}
