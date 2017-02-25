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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Helper to marshall JSON messages.
 */
public class Marshaller {

	static private final String TAG = "Marshaller";

	static public String readMsg(InputStream is) throws IOException {
		byte[] buf = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while (true) {
			if (!pollStream(is))
				break;
			int n = is.read(buf);
			if (n <= 0)
				break;
			Log.d(TAG, "recv: " + n);
			out.write(buf, 0, n);
		}
		return inflateData(out.toByteArray());
	}

	static private boolean pollStream(InputStream is) throws IOException {
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

	static public void writeMsg(OutputStream os, String msg)
		throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzout = new GZIPOutputStream(out);
		gzout.write(msg.getBytes("UTF-8"));
		gzout.close();
		os.write(out.toByteArray());
	}
}
