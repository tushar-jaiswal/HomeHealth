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

public class DiagnosisActivity  extends Activity   {

	private static final String YOUR_APPLICATION_ID = "vQyhiWo3htopZhxEX2t7pspvbB2vDRSSuPPAASuX";
	private static final String YOUR_CLIENT_KEY = "JCtAgree2otOnZI1inaziB4tM0RrlNJoZMe5lDJ5";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
		setContentView(R.layout.activity_diagnosis);		
		/*setTitle(R.string.app_name);*/
	}
}
