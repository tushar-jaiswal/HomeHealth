package com.parse.starter;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_reset_password, menu);
		return true;
	}


	public void resetPassword(View view){
		EditText editTextUserNameEmail = (EditText) findViewById(R.id.reset_username_email);
		String usernameEmail = editTextUserNameEmail.getText().toString();
		ParseUser.requestPasswordResetInBackground(usernameEmail,
				new RequestPasswordResetCallback() {
			public void done(ParseException e) {
				if (e == null) {
					Toast.makeText(getApplicationContext(), "A password reset link has been successfully sent to your email address!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "Oops! There was some problem in reseting you password. Please enter the correct username / password!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(mainIntent);

	}
}
