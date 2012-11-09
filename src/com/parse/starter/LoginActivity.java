package com.parse.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

public class LoginActivity extends Activity {
	private static final String TAG = "Login Activity";

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static boolean found = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		setTitle(R.string.app_name);

		setContentView(R.layout.activity_login);
		Log.d("loginact" , "Inside login activity");
		TextView loginButton = (TextView) findViewById(R.id.btnLogin);
		loginButton.setOnClickListener(loginClickListener);// Listening to LOGIN button click
		
		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		registerScreen.setOnClickListener(registerClickListener);// Listening to register new account link
		
		TextView resetScreen = (TextView) findViewById(R.id.link_to_reset);
		resetScreen.setOnClickListener(resetPasswordClickListener);// Listening to reset password
	}
	
	private OnClickListener loginClickListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d("login clicked" , "Login button clicked!");
			EditText editTextUserName = (EditText) findViewById(R.id.login_username);
			String username = editTextUserName.getText().toString();

			EditText editTextPassword = (EditText) findViewById(R.id.login_password);
			String password = editTextPassword.getText().toString();

			ParseUser.logInInBackground(username, password, new LogInCallback() {
				public void done(final ParseUser user, ParseException e) {
					if (user != null) {
						Log.d("info" , "User succesfully logged in!");
						// Hooray! The user is logged in.
						if(user.getBoolean("emailVerified"))
						{						

							ParseQuery query = ParseRole.getQuery();
				    	  	query.whereEqualTo("name", "Admin");
				    	  	query.findInBackground(new FindCallback() {
				    	  	    public void done(List<ParseObject> roles, ParseException e1) {
				    	  	        if (e1 == null) {
				    	  	            ParseRole role = (ParseRole) roles.get(0);
				    	  	            ParseRelation userRelation= role.getUsers();
				    	  	            userRelation.getQuery().findInBackground(new FindCallback() {
											public void done(List<ParseObject> userList, ParseException e) {
												if (e == null) {
													for(ParseObject listuser : userList )
													{
														if(listuser.getObjectId().equals(user.getObjectId()))
														{	
															found = true;
															Intent homeActivity = new Intent(getApplicationContext(), AdminActivity.class);
															startActivity(homeActivity);
															finish();
														}
													}


												} else {

												}
											}
										});
				    	  	        } else {
				    	  	            
				    	  	        }
				    	  	    }
				    	  	});
				    	  	
				    	  	ParseQuery query2 = ParseRole.getQuery();
				    	  	query2.whereEqualTo("name", "Patient");
				    	  	query2.findInBackground(new FindCallback() {
				    	  	    public void done(List<ParseObject> roles, ParseException e1) {
				    	  	        if (e1 == null) {
				    	  	            ParseRole role = (ParseRole) roles.get(0);
				    	  	            ParseRelation userRelation= role.getUsers();
				    	  	            userRelation.getQuery().findInBackground(new FindCallback() {
											public void done(List<ParseObject> userList, ParseException e) {
												if (e == null) {
													for(ParseObject listuser : userList )
													{
														if(listuser.getObjectId().equals(user.getObjectId()))
														{
															found = true;
															Intent homeActivity = new Intent(getApplicationContext(), PatientHomeActivity.class);
															startActivity(homeActivity);
															finish();
														}
													} 
													
										    	  	if(found == false)
										    	  	{
											    	  	Intent homeActivity = new Intent(getApplicationContext(), DoctorHomeActivity.class);
														startActivity(homeActivity);
														finish();
										    	  	}


												} else {

												}
											}
										});
				    	  	        } else {
				    	  	            
				    	  	        }
				    	  	    }
				    	  	});

							}
						else
						{
							Toast.makeText(getApplicationContext(), "A verification link has been sent to your email address. Please verify the same and login again!", Toast.LENGTH_SHORT).show();
			      	    	Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
			      	    	startActivity(mainIntent);
						}

					} else {
						// Login failed. Look at the ParseException to see what happened.
						Toast.makeText(getApplicationContext(), "Login Failed. Incorrect Username/Password.", Toast.LENGTH_SHORT).show();
						Log.d(TAG , e.getMessage());
					}
				}
			});
		}
	};
	
	private OnClickListener registerClickListener = new OnClickListener() {
		public void onClick(View v) {
			// Switching to Register screen
			Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
			startActivity(i);	
		}
	};
	
	private OnClickListener resetPasswordClickListener = new OnClickListener() {
		public void onClick(View v) {
			// Switching to Register screen
			Intent i = new Intent(getApplicationContext(), ResetPasswordActivity.class);
			startActivity(i);	
		}
	};
}
