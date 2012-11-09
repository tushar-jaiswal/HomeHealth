package com.parse.starter;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;

@SuppressLint("NewApi")
public class ProfileActivity extends Activity {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static final String TAG = "Profile Activity";
	private static ParseObject userProfile = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

		ParseQuery query = new ParseQuery("UserProfile");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.getFirstInBackground(new GetCallback() {

			public void done(ParseObject object, ParseException e) {
				if (object == null) {
					setContentView(R.layout.activity_profile);
					/*setTitle(R.string.app_name);*/
					Log.d(TAG, "The getFirst request failed.");
				} else {
					Log.i(TAG, (String) object.get("height"));

					setContentView(R.layout.activity_profile);
					/*setTitle(R.string.app_name);*/
					populateValues(object);

				}
			}
		});




	}

	// Populate values for the user profile form
	private void populateValues(ParseObject userProfile) {
		Log.i(TAG, "Inside populate values");
		Log.i(TAG, userProfile.getString("allergies"));
		EditText editTextAllergies = (EditText) findViewById(R.id.allergies);
		if(editTextAllergies == null)
			Log.i(TAG, "editTextAllergies is null");
		editTextAllergies.setText(userProfile.getString("allergies"));


		EditText editTextLongTermIllness = (EditText) findViewById(R.id.longTermIllness);
		editTextLongTermIllness.setText(userProfile.getString("longTermIllness"));
		Log.i(TAG, userProfile.getString("longTermIllness"));

		EditText editTextHeight = (EditText) findViewById(R.id.height);
		editTextHeight.setText(userProfile.getString("height"));
		Log.i(TAG, userProfile.getString("height"));

		EditText editTextWeight = (EditText) findViewById(R.id.weight);
		editTextWeight.setText(userProfile.getString("weight"));
		Log.i(TAG, userProfile.getString("weight"));

		/*	DateFormat dateFormat = DateFormat.getDateInstance();
		Date date;
		try {
			DatePicker dobWidget = (DatePicker) findViewById(R.id.dob);
			date = dateFormat.parse(userProfile.getString("dob"));
			dobWidget.init(date.getYear(), date.getMonth(), date.getDate(),null);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "Error in initializing date");
		}*/
		//TODO : Initialize date to prev value


	}

	// Returns profile for  the current user , null if profile doesnot exists
	private ParseObject retreiveUserProfile(ParseUser currentUser) {
		ParseQuery query = new ParseQuery("UserProfile");
		query.whereEqualTo("user", currentUser);
		query.getFirstInBackground(new GetCallback() {

			public void done(ParseObject object, ParseException e) {
				if (object == null) {

					Log.d(TAG, "The getFirst request failed.");
				} else {
					userProfile = object;
					Log.i(TAG, (String) object.get("height"));
				}
			}
		});



		return userProfile;
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

	@SuppressLint({ "NewApi", "NewApi" })
	public void createProfile(View view){
		Log.i(TAG, "Inside create profile");
		try
		{
			EditText editTextAllergies = (EditText) findViewById(R.id.allergies);
			final String allergies = editTextAllergies.getText().toString();

			EditText editTextLongTermIllness = (EditText) findViewById(R.id.longTermIllness);
			final String longTermIllness = editTextLongTermIllness.getText().toString();

			EditText editTextHeight = (EditText) findViewById(R.id.height);
			final String height = editTextHeight.getText().toString();

			EditText editTextWeight = (EditText) findViewById(R.id.weight);
			final String weight = editTextWeight.getText().toString();

			DatePicker dobWidget = (DatePicker) findViewById(R.id.dob);
			final String dob = DateFormat.getDateInstance().format(dobWidget.getCalendarView().getDate());

			ParseQuery query = new ParseQuery("UserProfile");
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			query.getFirstInBackground(new GetCallback() {

				public void done(ParseObject object, ParseException e) {
					if (object == null) {
						ParseObject myProfile = new ParseObject("UserProfile");
						myProfile.put("allergies", allergies);
						myProfile.put("longTermIllness", longTermIllness);
						myProfile.put("height", height);
						myProfile.put("weight", weight);
						myProfile.put("dob", dob);
						myProfile.put("user", ParseUser.getCurrentUser());
						myProfile.saveInBackground();
						Log.d(TAG, "Profile created successfully");
						Toast.makeText(getApplicationContext(), "Profile created sucessfully", Toast.LENGTH_SHORT).show();
					} else {
						object.put("allergies", allergies);
						object.put("longTermIllness", longTermIllness);
						object.put("height", height);
						object.put("weight", weight);
						object.put("dob", dob);
						object.put("user", ParseUser.getCurrentUser());
						object.saveInBackground();
						Log.i(TAG, "Profile updated successfully");
						Toast.makeText(getApplicationContext(), "Profile updated sucessfully", Toast.LENGTH_SHORT).show();
					}
				}
			});

			/*ParseUser currentUser = ParseUser.getCurrentUser();




			myProfile.saveInBackground(new SaveCallback() {

				public void done(ParseException e) {
					if (e == null) {
						Log.i(TAG, "User Profile saved successfully");

					} else {
						Log.e(TAG, "Error in saving profile :" + e.getMessage());
					}
				}
			});*/
		}
		catch(Exception e)
		{
			Log.e(TAG, "Error in saving profile :" + e.getMessage());

		}


	}
}
