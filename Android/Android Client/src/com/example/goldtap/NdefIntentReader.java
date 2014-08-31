package com.example.goldtap;

import android.content.Intent;
import android.nfc.*;
import android.os.Bundle;
import android.os.Parcelable;
public class NdefIntentReader {

	private Intent intent;
	private String action;
	
	public static String PAYLOADEXTRANAME = "PLEN";
	
	public NdefIntentReader(Intent intent) {
	 	
		this.intent = intent;
		this.action = intent.getAction();
		System.out.println(this.action);	
	}
	
	public Boolean hasFuckAll() {
				
    	Bundle b = intent.getExtras();
    	
    	System.out.println(action);
    	System.out.println("Bundle "  + (b != null));
    
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);		
		System.out.println("Tag = " + (tag != null));
    	
		
		
    	return false;
	}
	
	public byte[] getNdefPayload() {
 
    	NdefMessage[] msgs = new NdefMessage[]{};
	
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
            }
        } 
        else {
        	
        	System.out.println("getParcelableArrayExtra is null" );
            return null;
        }
        
		System.out.println(msgs.length);
        
		NdefRecord[] records = msgs[0].getRecords();
		
        NdefRecord record = records[0];
        
        System.out.println(records.length);
        
        System.out.println(record.getPayload());
        return record.getPayload();
	}

	public Tag getTag() {

		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		
		System.out.println("Tag = " + (tag != null));
		return tag;
	}
}
