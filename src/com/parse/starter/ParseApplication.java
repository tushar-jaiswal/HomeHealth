package com.parse.starter;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		ParseUser.enableAutomaticUser();
     
	}

}
