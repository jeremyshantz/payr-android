package com.example.goldtap;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SplitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_split);
	}
	
    public void setSplit(View view) throws IOException, JSONException{
    	
    	Account account = new Account();
    	
    	String id = "jshantz"; //(String) findViewById(R.id.);
    	
    	View v_amount = findViewById(R.id.ediamount);
    	TextView tv_amount = (TextView)v_amount;
    	String amount = tv_amount.getText().toString();
    	
    	View v_people = findViewById(R.id.edipeople);
    	TextView tv_people = (TextView)v_people;
    	String people = tv_people.getText().toString();
    	
    	int amount_to_pay = Integer.parseInt(amount) / Integer.parseInt(people);
    	
    	account.setId(id);
    	
    	PayLoad payload = new PayLoad(account.getId(), Integer.toString(amount_to_pay), people);
    	
    	Intent intent = new Intent(this, NFCActivity.class);
    	intent.putExtra("payload", PayLoad.serialize(payload.getPayload().toString()));
    	startActivity(intent);
    }
}
