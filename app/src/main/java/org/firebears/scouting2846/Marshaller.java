/*
 * Copyright  2017-2018  Douglas P Lau
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.firebears.scouting2846;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.firebears.scouting2846.ScoutingRec.REC;

/**
 * Helper to marshall JSON messages.
 */
public class Marshaller {

	static private final String TAG = "Marshaller";

	static private String readMsg(InputStream is) throws IOException {
		byte[] buf = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while (true) {
			int n = is.read(buf);
			if (n <= 0)
				break;
			Log.d(TAG, "recv: " + n);
			out.write(buf, 0, n);
			// Check for end of message (gap of 500 ms)
			if (!pollStream(is))
				break;
		}
		if (out.size() == 0)
			throw new IOException("Read timed out");
		String data = inflateData(out.toByteArray());
		Log.d(TAG, "recv uncompressed: " + data.length());
		return data;
	}

	static private boolean pollStream(InputStream is)
		throws IOException
	{
		for (int i = 0; i < 5; i++) {
			if (is.available() > 0)
				return true;
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) { }
		}
		return false;
	}

	static private String inflateData(byte[] data) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		GZIPInputStream gzin = new GZIPInputStream(in);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while (gzin.available() > 0) {
			int n_bytes = gzin.read(buf);
			if (n_bytes <= 0)
				break;
			out.write(buf, 0, n_bytes);
		}
		return out.toString("UTF-8");
	}

	static private void writeMsg(OutputStream os, String msg)
		throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzout = new GZIPOutputStream(out);
		byte[] data = msg.getBytes("UTF-8");
		Log.d(TAG, "write: " + data.length);
		gzout.write(data);
		gzout.close();
		byte[] buffer = out.toByteArray();
		Log.d(TAG, "write compressed: " + buffer.length);
		os.write(buffer);
	}

	static private final String[] COLS = {
		REC.COL_SCOUTER, REC.COL_OBSERVATION,
	};

	static private String lookupFinalObservations(ContentResolver cr)
		throws IOException, JSONException
	{
		Cursor c = cr.query(REC.getContentUri(), COLS, null, null,
			null);
		try {
			if (c != null)
				return lookupFinalObservations(c);
			else
				throw new IOException("No cursor");
		}
		finally {
			if (c != null)
				c.close();
		}
	}

	static private String lookupFinalObservations(Cursor c)
		throws JSONException
	{
		HashMap<Integer, Integer> map =
			new HashMap<Integer, Integer>();
		int cs = c.getColumnIndex(REC.COL_SCOUTER);
		int co = c.getColumnIndex(REC.COL_OBSERVATION);
		while (c.moveToNext()) {
			int s = c.getInt(cs);
			int o = c.getInt(co);
			Integer v = map.get(s);
			if (null == v || v < o)
				map.put(s, o);
		}
		return buildArray(map).toString();
	}

	static private JSONArray buildArray(HashMap<Integer, Integer> map)
		throws JSONException
	{
		JSONArray ja = new JSONArray();
		for (Integer s : map.keySet()) {
			JSONObject jo = new JSONObject();
			Integer o = map.get(s);
			jo.put(REC.COL_SCOUTER, s);
			jo.put(REC.COL_OBSERVATION, o);
			ja.put(jo);
		}
		Log.d(TAG, "final observations: " + ja.length());
		return ja;
	}

	static private String lookupExtraObservations(ContentResolver cr,
		String msg) throws IOException, JSONException
	{
		HashMap<Integer, Integer> map = parseFinalObservations(msg);
		return lookupExtraObservations(cr, map).toString();
	}

	static private HashMap<Integer, Integer> parseFinalObservations(
		String msg) throws JSONException
	{
		HashMap<Integer, Integer> map =
			new HashMap<Integer, Integer>();
		JSONArray ja = new JSONArray(msg);
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			int s = jo.getInt(REC.COL_SCOUTER);
			int o = jo.getInt(REC.COL_OBSERVATION);
			map.put(s, o);
		}
		return map;
	}

	static private JSONArray lookupExtraObservations(ContentResolver cr,
		HashMap<Integer, Integer> map) throws IOException,
		JSONException
	{
		Cursor c = cr.query(REC.getContentUri(), REC.getCols(), null,
			null, null);
		try {
			if (c != null)
				return lookupExtraObservations(c, map);
			else
				throw new IOException("No cursor");
		}
		finally {
			if (c != null)
				c.close();
		}
	}

	static private JSONArray lookupExtraObservations(Cursor c,
		HashMap<Integer, Integer> map) throws JSONException
	{
		JSONArray ja = new JSONArray();
		int cs = c.getColumnIndex(REC.COL_SCOUTER);
		int co = c.getColumnIndex(REC.COL_OBSERVATION);
		while (c.moveToNext()) {
			int s = c.getInt(cs);
			int o = c.getInt(co);
			Integer v = map.get(s);
			if (null == v || v < o) {
				JSONObject jo = buildObservation(c);
				ja.put(jo);
			}
		}
		Log.d(TAG, "sending " + ja.length() + " observations");
		return ja;
	}

	static private JSONObject buildObservation(Cursor c)
		throws JSONException
	{
		JSONObject jo = new JSONObject();
		for (ScoutingData sd : REC.getAllData()) {
			if (!sd.getCol().equals(BaseColumns._ID))
				sd.update(jo, c);
		}
		return jo;
	}

	static private void parseExtraObservations(ContentResolver cr,
		String obs) throws JSONException
	{
		JSONArray ja = new JSONArray(obs);
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.getJSONObject(i);
			ContentValues cv = REC.parse(jo);
			cr.insert(REC.getContentUri(), cv);
		}
		Log.d(TAG, "received " + ja.length() + " observations");
	}

	static public void sendObservations(ContentResolver cr, InputStream is,
		OutputStream os) throws IOException, JSONException
	{
		Log.d(TAG, "sending...");
		String msg = readMsg(is);
		String obs = lookupExtraObservations(cr, msg);
		writeMsg(os, obs);
		Log.d(TAG, "send done");
	}

	static public void recvObservations(ContentResolver cr, InputStream is,
		OutputStream os) throws IOException, JSONException
	{
		String msg = lookupFinalObservations(cr);
		Log.d(TAG, "receiving...");
		writeMsg(os, msg);
		String obs = readMsg(is);
		parseExtraObservations(cr, obs);
		Log.d(TAG, "receive done");
	}
}
