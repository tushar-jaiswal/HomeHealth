package com.parse.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class RegisterActivity extends Activity {
	private static final String TAG = "Register Activity";

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		setContentView(R.layout.activity_register);
        
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
		loginScreen.setOnClickListener(loginClickListener);// Listening to register new account link
	}

	/** Register User */
	public void registerUser(View view) {

		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editTextUserName = (EditText) findViewById(R.id.reg_username);
		String username = editTextUserName.getText().toString();

		EditText editTextPassword = (EditText) findViewById(R.id.reg_password);
		String password = editTextPassword.getText().toString();

		EditText editTextEmail = (EditText) findViewById(R.id.reg_email);
		String email = editTextEmail.getText().toString();

		Spinner rolesSpinner = (Spinner) findViewById(R.id.rolesSpinner);
		final String userRole = String.valueOf(rolesSpinner.getSelectedItem());

		final ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.

					ParseObject request = new ParseObject("RoleRequest");
	      	    	request.put("email", user.getEmail());
	      	    	request.put("role", userRole);
	      	    	ParseACL ACL = new ParseACL();
	      	    	ACL.setRoleWriteAccess("Admin",true);
	      	    	ACL.setRoleReadAccess("Admin",true);
	      	    	request.setACL(ACL);
	      	    	request.saveInBackground();
	      	    	
	      	    	/*// Saving the device's owner
	      	    	ParseInstallation installation = ParseInstallation.getCurrentInstallation();
	      	    	installation.put("user",user.getEmail());
	      	    	installation.saveEventually();*/
	      	    	
	      	    	PushService.subscribe(getApplicationContext(), "verifyingchannel_"+user.getObjectId(), MainActivity.class);
	      	    	if(userRole.equals("Patient"))
	      	    		PushService.subscribe(getApplicationContext(), "patientchannel_"+user.getObjectId(), DiagnosisActivity.class);
	      	    	else
	      	    		PushService.subscribe(getApplicationContext(), "doctorchannel_"+user.getObjectId(), DoctorHomeActivity.class);
	      	    	// Create our installation query
	      	    	//ParseQuery pushQuery = ParseInstallation.getQuery();
	      	    	//pushQuery.whereEqualTo("user", "admin");
	      	    	 
	      	    	// Send push notification to query
	      	    	ParsePush push = new ParsePush();
	      	    	//push.setQuery(pushQuery);
	      	    	push.setChannel("verifyingchannel"); // Set the channel
	      	    	push.setMessage("Please add user(s) to role(s).");
	      	    	push.sendInBackground();
	      	    	//TODO: Signout user

					Toast.makeText(getApplicationContext(), "Registration Successful.\nPlease verify your email address by checking your inbox and await approval for your role!", Toast.LENGTH_LONG).show();
					Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(mainIntent);
				} else {
					// Sign up didn't succeed. Look at the ParseException to figure out what went wrong
					Toast.makeText(getApplicationContext(), "Registration Failed. Invalid Entries.", Toast.LENGTH_SHORT).show();
					Log.d(TAG , e.getMessage());    	    	
				}
			}
		});
		
		// other fields can be set just like with ParseObject
    	//user.put("phone", "650-253-0000");
	
	}

	private OnClickListener loginClickListener = new OnClickListener() {
		public void onClick(View v) {
			// Switching to Register screen
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);	
		}
	};
	
	/*ParseACL roleAdminACL = new ParseACL();
		roleAdminACL.setPublicReadAccess(true);
		ParseRole roleAdmin = new ParseRole("Admin", roleAdminACL);
		roleAdmin.saveInBackground();
		roleAdmin.getUsers().add(user);roleAdmin.saveInBackground();
  	
  	ParseACL roleACL = new ParseACL();
  	roleACL.setPublicReadAccess(true);
  	roleACL.setRoleWriteAccess(roleAdmin,true);
  	
		ParseRole roleDoctor = new ParseRole("Doctor", roleACL);
		roleDoctor.saveInBackground();

		ParseRole roleDoctorPhy = new ParseRole("Physician", roleACL);
		roleDoctorPhy.saveInBackground();

		ParseRole roleDoctorPed = new ParseRole("Pediatrician", roleACL);
		roleDoctorPed.saveInBackground();

		ParseRole roleDoctorCard = new ParseRole("Cardiologist", roleACL);
		roleDoctorCard.saveInBackground();

		ParseRole roleDoctorGastro = new ParseRole("Gastrologist", roleACL);
		roleDoctorGastro.saveInBackground();

		ParseRole roleDoctorGynae = new ParseRole("Gynecologist", roleACL);
		roleDoctorGynae.saveInBackground();

		ParseRole roleDoctorPath = new ParseRole("Pathologist", roleACL);
		roleDoctorPath.saveInBackground();

		ParseRole roleDoctorOrth = new ParseRole("Orthopedist", roleACL);
		roleDoctorOrth.saveInBackground();

		ParseRole roleDoctorOpth = new ParseRole("Ophthalmologist", roleACL);
		roleDoctorOpth.saveInBackground();

		ParseRole roleDoctorDent = new ParseRole("Dentist", roleACL);
		roleDoctorDent.saveInBackground();
		
		ParseRole roleDoctorDerm = new ParseRole("Dermatologist", roleACL);
		roleDoctorDerm.saveInBackground();

		roleDoctor.getRoles().add(roleDoctorDerm);
		roleDoctor.getRoles().add(roleDoctorDent);
		roleDoctor.getRoles().add(roleDoctorOpth);
		roleDoctor.getRoles().add(roleDoctorOrth);
		roleDoctor.getRoles().add(roleDoctorPath);
		roleDoctor.getRoles().add(roleDoctorGynae);
		roleDoctor.getRoles().add(roleDoctorGastro);
		roleDoctor.getRoles().add(roleDoctorCard);
		roleDoctor.getRoles().add(roleDoctorPed);
		roleDoctor.getRoles().add(roleDoctorPhy);
		roleDoctor.saveInBackground();

		//Patient Role
		ParseRole rolePatient = new ParseRole("Patient", roleACL);
		rolePatient.saveInBackground();
		Toast.makeText(getApplicationContext(), "Admin processing done.", Toast.LENGTH_LONG).show();
  	
  	ParseQuery query = ParseRole.getQuery();
  	query.whereEqualTo("name", userRole);
  	query.findInBackground(new FindCallback() {
  	    public void done(List<ParseObject> roles, ParseException e) {
  	        if (e == null) {
  	        	
  	            ParseRole role = (ParseRole) roles.get(0);
  	            role.getUsers().add(user);
  	            role.saveInBackground();
  	        } else {
  	            Log.d(TAG, "Error: " + e.getMessage());
  	        }
  	    }
  	});*/
}
