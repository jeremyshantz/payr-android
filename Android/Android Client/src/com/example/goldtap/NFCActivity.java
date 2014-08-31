package com.example.goldtap;

import java.io.IOException;
import java.nio.charset.Charset;

import org.json.JSONException;

import com.example.goldtap.R;

import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NFCActivity extends Activity {
	
	private byte[] _payload;
	private int _count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		Intent intent = getIntent();
		_payload = intent.getByteArrayExtra("payload");
		
		try {
			_count = Integer.parseInt(PayLoad.parseJson(PayLoad.deserialize(_payload).toString(), "People"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
    @Override
    protected void onResume() {
        super.onResume();
        //set Ndef message to send by beam
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        assert nfcAdapter != null;
        nfcAdapter.setNdefPushMessageCallback(
                new NfcAdapter.CreateNdefMessageCallback() {
                    public NdefMessage createNdefMessage(NfcEvent event) {
                        try {
							return createMessage();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}
                    }
                }, this);

        //See if app got called by AndroidBeam intent.
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            extractPayload(getIntent());
        }
        
    }

    /**
     * Creates a new NdefMessage with payload of text field.
     * @return NFC Data Exchange Format
     * @throws JSONException 
     */
    private NdefMessage createMessage() throws JSONException {
        String mimeType = "application/com.example.goldtap";
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));

        //GENERATE PAYLOAD
        byte[] payLoad = _payload;

        //GENERATE NFC MESSAGE
        return new NdefMessage(
                new NdefRecord[]{
                new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                        mimeBytes,
                        null,
                        payLoad),
                NdefRecord.createApplicationRecord("com.example.goldtap")
        });
    }
    private void extractPayload(Intent beamIntent) {
        Parcelable[] messages = beamIntent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage message = (NdefMessage) messages[0];
        NdefRecord record = message.getRecords()[0];
        String payload = new String(record.getPayload().toString());
    }

    private Ndef writeSomeStuffToTag(Tag tag) throws IOException, FormatException, JSONException {
    	  Ndef ndefTag = Ndef.get(tag);
    	  ndefTag.connect();
    	  ndefTag.writeNdefMessage(createMessage());
    	  return ndefTag;
    	}
    

}
