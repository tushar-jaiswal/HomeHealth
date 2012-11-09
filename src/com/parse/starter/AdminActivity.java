package com.parse.starter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.PushService;

public class AdminActivity  extends Activity {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";
	private static final String TAG = "Admin Activity";
	private static String objectID="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		PushService.subscribe(this, "verifyingchannel", AdminActivity.class);
		setTitle(R.string.app_name);

		setContentView(R.layout.activity_admin);
		Log.d(TAG, "Inside admin activity");
		
		TextView approveButton = (TextView) findViewById(R.id.btnApprove);
		approveButton.setOnClickListener(approveClickListener);// Listening to Logout button click

		
		TextView logoutButton = (TextView) findViewById(R.id.btnLogout);
		logoutButton.setOnClickListener(logoutClickListener);// Listening to Logout button click	
	}
	
	private OnClickListener approveClickListener = new OnClickListener() {
		public void onClick(View v) {
			ParseQuery query = new ParseQuery("RoleRequest");
			query.findInBackground(new FindCallback() {
			    public void done(List<ParseObject> requestList, ParseException e) {
			        if (e == null) {
			            for(int i=0;i<requestList.size();i++)
			            {
			            	String email = requestList.get(i).getString("email");
			            	final String userRole = requestList.get(i).getString("role");

			            	ParseQuery query = ParseUser.getQuery();
			        		query.whereEqualTo("email", email);
			        		query.findInBackground(new FindCallback() {
			        		  public void done(List<ParseObject> objects, ParseException e) {
			        		    if (e == null) {
			        		        // The query was successful.
			        		    	for (int j=0; j<objects.size();j++)
			        		    	{
			        		    		final ParseUser user = (ParseUser) objects.get(j);
			        		    		objectID=user.getObjectId();
			        		    		Log.d(TAG,"ObjectID:"+objectID);
			        		    		ParseQuery query = ParseRole.getQuery();
			        		    	  	query.whereEqualTo("name", userRole);
			        		    	  	query.findInBackground(new FindCallback() {
			        		    	  	    public void done(List<ParseObject> roles, ParseException e1) {
			        		    	  	        if (e1 == null) {
			        		    	  	            ParseRole role = (ParseRole) roles.get(0);
			        		    	  	            role.getUsers().add(user);
			        		    	  	            role.saveInBackground();
			        		    	  	        } else {
			        		    	  	            Log.d(TAG, "Error: " + e1.getMessage());
			        		    	  	        }
			        		    	  	    }
			        		    	  	});
			        		    	  	ParsePush push = new ParsePush();
						      	    	//push.setQuery(pushQuery);
						      	    	push.setChannel("verifyingchannel_"+objectID); // Set the channel
						      	    	push.setMessage("Account verified. Please log in to continue.");
						      	    	push.sendInBackground();
			        		    	}
			        		    } else {
			        		        // Something went wrong.
			        		    }
			        		  }
			        		});
			        		
			        		requestList.get(i).deleteInBackground();
			        		
			        		// Create our installation query
			      	    	//ParseQuery pushQuery = ParseInstallation.getQuery();
			      	    	//pushQuery.whereEqualTo("user", email);
			      	    	 
			      	    	/*// Send push notification to query
			      	    	ParsePush push = new ParsePush();
			      	    	//push.setQuery(pushQuery);
			      	    	push.setChannel("verifyingchannel_"+objectID); // Set the channel
			      	    	push.setMessage("Account verified. Please log in to continue.");
			      	    	push.sendInBackground();*/
			            }
			        } else {
			            Log.d(TAG, "Error: " + "Error in getting role approval requests.");
			        }
			    }
			});
		}
	};
	
	private OnClickListener logoutClickListener = new OnClickListener() {
		public void onClick(View v) {
			//Logout	
		}
	};
}
