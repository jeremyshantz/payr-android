package com.example.goldtap;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class InvoiceActivity extends ActionBarActivity {

    NfcAdapter mNfcAdapter;
    TextView textView;
    NdefIntentReader reader;
	
	@Override
	public void onResume(){
	
		super.onResume();


		String payload = getIntent().getStringExtra(NdefIntentReader.PAYLOADEXTRANAME);
		
		if (payload != null){
			Toast.makeText(this, payload, Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
		}
		
		//reader = new NdefIntentReader(getIntent());
		
		// Check to see that the Activity started due to an Android Beam
		//if (reader.HasNdef()) {
		//    processIntent(getIntent());
		//} else {			
		//	Toast.makeText(this, "no ndef discovered", Toast.LENGTH_LONG).show();
		//}
	}
	
    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
    
    	byte[] msg = reader.getNdefPayload();
    	
    	if (msg == null) {
    		msg = new byte[]{};
    	}
    	
    	Tag tag = reader.getTag();
		String output = String.format("msgs=%s tag=%s", msg.toString(), (tag == null));
		
		Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }

    @Override 
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.invoice, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_invoice,
					container, false);
			return rootView;
		}
	}
}
