package com.example.goldtap;

import com.example.goldtap.SimpleCrypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class PayLoad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String _recipientId;
	private String _amount;
	private String _nopeople;
	
	public PayLoad(String recipientId, String amount, String nopeople){
		
		_recipientId = recipientId;
		_amount = amount;
		_nopeople= nopeople;
	}
	
	public JSONObject getPayload() throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put("ID", _recipientId);
		obj.put("Amount", _amount);
		obj.put("People", _nopeople);
		return obj;
	}
	
	public String getRecipientId(){
		return _recipientId;
	}
	
	public String getAmount(){
		return _amount;
	}
	
	public String getNoPeople(){
		return _nopeople;
	}
	
	public void setRecipientId(String recipientId){
		this._recipientId = recipientId;
	}

	public void setAmount(String amount){
		this._amount = amount;
	}
	
	public void setNoPeople(String nopeople){
		this._nopeople = nopeople;
	}
	
	public void encrypt(String data){
		try {
			SimpleCrypto.encrypt("test", data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
    
    public static String parseJson(String result, String tag) {
        try {
            JSONObject json= (JSONObject) new JSONTokener(result).nextValue();
            JSONObject json2 = json.getJSONObject("results");
            return (String) json2.get(tag);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
