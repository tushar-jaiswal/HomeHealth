package com.parse.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseRole;
import com.parse.PushService;
import com.parse.ParseUser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.PushService;

public class MainActivity extends Activity {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static boolean found = false;

	/** Called when the activity is first created. */
	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		
		found=false;

		final ParseUser currentUser = ParseUser.getCurrentUser();
		if(!currentUser.getBoolean("emailVerified"))
			ParseUser.logOut();
		ParseACL.setDefaultACL(new ParseACL(), true);
		if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().getUsername() != null ) {
			// do stuff with the user
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
													if(listuser.getObjectId().equals(currentUser.getObjectId()))
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
													if(listuser.getObjectId().equals(currentUser.getObjectId()))
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
					} else {
			// Show the sign up or login screen
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} 

		/*ParseQuery query = ParseRole.getQuery();
    	query.whereEqualTo("name", "Admin");
    	query.findInBackground(new FindCallback() {
    	    public void done(List<ParseObject> roles, ParseException e) {
    	        if (e == null) {
    	        	ParseRole roleAdmin = (ParseRole) roles.get(0);
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
    	  			
    	  			//Patient Role
    	  			ParseRole rolePatient = new ParseRole("Patient", roleACL);
    	  			rolePatient.saveInBackground();
    	  			Toast.makeText(getApplicationContext(), "Admin processing done.", Toast.LENGTH_LONG).show();


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

    	  			
    	        } else {
    	           
    	        }
    	    }
    	});*/


	}
}