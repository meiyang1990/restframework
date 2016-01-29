/**
 * 
 */
package com.netfinworks.rest.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.netfinworks.rest.utils.AssertUtil;


/**
 * @author knico
 * @since Aug 29, 2012
 * 
 */
public final class StreamUtil {
	private static final int READ_STREAM_WINDOW_MIN_SIZE = 1024;

	public static final boolean transferStream(InputStream is, OutputStream os, IStreamMonitor monitor) throws IOException {
		AssertUtil.assetNotNull("is", is);
		AssertUtil.assetNotNull("os", os);

		boolean ret = false;
		try {
			int size = is.available();
			if (size < StreamUtil.READ_STREAM_WINDOW_MIN_SIZE) {
				size = StreamUtil.READ_STREAM_WINDOW_MIN_SIZE;
			}
			byte[] b = new byte[size];
			int read = -1;
			if (monitor != null) {
				monitor.onStart();
			}
			while (true) {
				read = is.read(b, 0, size);
				if (read < 0) {
					break;
				}
				if (monitor != null) {
					monitor.onTransfer(read);
				}
				os.write(b, 0, read);
			}
			if (monitor != null) {
				monitor.onFinish();
			}
			ret = true;
		} catch (IOException ex) {
			if (monitor != null) {
				monitor.onException(ex);
			}
			throw ex;
		}

		return ret;
	}

	public static final long countInputStreamLength(InputStream is, boolean needClose) throws IOException {
		AssertUtil.assetNotNull("is", is);
		long ret = 0;
		byte[] b = new byte[1];
		while (is.read(b, 0, 1) >= 0) {
			ret++;
		}
		if (needClose) {
			is.close();
		}
		return ret;
	}
}
