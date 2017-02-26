/*
 * Copyright  2017  Douglas P Lau
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

import android.util.Log;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;

/**
 * Helper class to fetch data from The Blue Alliance.
 */
public class TBAFetcher {

	static private final String TAG = "TBAFetcher";

	/** Timeout for fetching data */
	static private final int TIMEOUT_MS = 5000;

	/** Base URL */
	static private final String BASE_URL =
		"https://www.thebluealliance.com/api/v2/";

	/** URL for event list */
	static private final String EVENT_URL = BASE_URL + "events/2016";

	/** Fetch event list as JSON */
	static public String fetchEvents() {
		return fetchData(EVENT_URL);
	}

	/** URL for event team list */
	static private final String EVENT_TEAM_URL = BASE_URL + "event/";

	/** Fetch event team list as JSON */
	static public String fetchTeams(String ev_key) {
		return fetchData(EVENT_TEAM_URL + ev_key + "/teams");
	}

	/** Fetch event match list as JSON */
	static public String fetchMatches(String ev_key) {
		return fetchData(EVENT_TEAM_URL + ev_key + "/matches");
	}

	/** Fetch data from TBA site */
	static private String fetchData(String u) {
		try {
			Log.d(TAG, "reading " + u);
			return readUrl(u);
		}
		catch (Exception e) {
			Log.e(TAG, "exception " + e);
			return "";
		}
	}

	/** Read data from a URL */
	static private String readUrl(String u) throws IOException {
		URL url = new URL(u);
		URLConnection uc = url.openConnection();
		if (uc instanceof HttpURLConnection)
			return readUrl((HttpURLConnection) uc);
		else
			throw new IOException("Bad URL: " + u);
	}

	/** Read data from a URL */
	static private String readUrl(HttpURLConnection hc) throws IOException
	{
		try {
			hc.setConnectTimeout(TIMEOUT_MS);
			hc.setReadTimeout(TIMEOUT_MS);
			hc.setRequestProperty("User-Agent", "scouting");
			hc.setRequestProperty("X-TBA-App-Id",
				"frc2846:scouting:0");
			hc.setRequestProperty("charset", "utf-8");
			return readToString(new InputStreamReader(
				hc.getInputStream(), "UTF-8"));
		}
		finally {
			hc.disconnect();
		}
	}

	/** Read data into a string */
	static private String readToString(Reader r) throws IOException {
		StringBuilder sb = new StringBuilder();
		CharBuffer cb = CharBuffer.allocate(4096);
		while (r.read(cb) > 0) {
			cb.flip();
			sb.append(cb);
			cb.clear();
		}
		return sb.toString();
	}
}
