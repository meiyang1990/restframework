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
 * @since Aug 31, 2012
 * 
 */
public class InterveneInputStream extends InputStream {
	private InputStream is;
	private OutputStream[] oss;

	/**
	 * 
	 */
	public InterveneInputStream(InputStream is, OutputStream... oss) {
		AssertUtil.assetNotNull("inputStream", is);
		this.is = is;
		this.oss = oss;
	}

	@Override
	public int read() throws IOException {
		int i = is.read();
		this.writeOuts(i);
		return i;
	}

	/**
	 * @param i
	 * @throws IOException
	 */
	private void writeOuts(int i) {
		if (oss != null) {
			for (OutputStream os : oss) {
				if (os != null) {
					try {
						os.write(i);
					} catch (IOException e) {
					}
				}
			}
		}
	}

	@Override
	public int available() throws IOException {
		return this.is.available();
	}

	@Override
	public void close() throws IOException {
		this.is.close();
	}

	@Override
	public synchronized void mark(int readlimit) {
		this.is.mark(readlimit);
	}

	@Override
	public synchronized void reset() throws IOException {
		this.is.reset();
	}

	@Override
	public boolean markSupported() {
		return this.is.markSupported();
	}
}
