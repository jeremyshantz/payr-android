package com.example.goldtap;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.goldtap.R;
import com.example.goldtap.MainActivity.PlaceholderFragment;
import com.example.goldtap.PayLoad;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.os.Bundle; 
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.app.Activity;

public class MainActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        this.LoadInvoiceIfStartedByNFC();
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
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    } 
    
    @Override
    protected void onResume() {
        super.onResume();
        
    }
    
 
    public void setPayload(View view) throws IOException, JSONException{
    	
    	PayLoad devtest = new PayLoad("Presh", "500", "2");
    	
    	Intent intent = new Intent(this, NFCActivity.class);
    	intent.putExtra("payload", PayLoad.serialize(devtest.getPayload().toString()));
    	startActivity(intent);
    }
    
    public void setSplit(View view){
    	Intent intent = new Intent(this, SplitActivity.class);
    	startActivity(intent);
    }
    
    private void LoadInvoiceIfStartedByNFC(){
    	
    	try		
    	{    	
	    	NdefIntentReader reader = new NdefIntentReader(getIntent());
    		
	    	Tag tag = reader.getTag();
	    	
	    	if (tag == null){
	    		System.out.println("tag is null");
	    		
	    	} else  {
	    		System.out.println("tag is not null");
	    	}
	    	
    		byte[] payload = reader.getNdefPayload();

	    	if (payload != null) {
	
	        	Intent intent = new Intent(this, InvoiceActivity.class);        	
	        	intent.putExtra(NdefIntentReader.PAYLOADEXTRANAME, payload);
	        	startActivity(intent);
	    	} else {
	    		
	    		Toast.makeText(this, "not started via NFC", Toast.LENGTH_LONG).show();	
	    	}
    	}
    	catch(Exception e)	{
    		String name = e.getClass().getName();
    		Toast.makeText(this, name +  " ERROR!!!!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
    	}
    }
}
