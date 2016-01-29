/**
 * 
 */
package com.netfinworks.rest.filter;

import java.io.InputStream;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.util.HttpHeaderName;

/**
 * 不可变的Response对象
 * @author bigknife
 *
 */
public class ImmutableResponse extends Response {
	private Response instance;
	public ImmutableResponse(){}
	public ImmutableResponse(Response resp){
		instance = resp;
	}
	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#getStatus()
	 */
	@Override
	public HttpStatus getStatus() {
		return instance.getStatus();
	}

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#setStatus(com.netfinworks.rest.enums.HttpStatus)
	 */
	@Override
	public void setStatus(HttpStatus status) {
		throw new IllegalAccessError("can't modify immutable object");
	}

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		return instance.getInputStream();
	}

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#setInputStream(java.io.InputStream)
	 */
	@Override
	public void setInputStream(InputStream inputStream) {
		throw new IllegalAccessError("can't modify immutable object");
	}

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#addHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void addHeader(String name, String value) {
		if(HttpHeaderName.Server.equals(name)){
			instance.addHeader(name, value);
			return ;
		}
		throw new IllegalAccessError("can't modify immutable object");
	}

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#getHeaderNames()
	 */
	@Override
	public String[] getHeaderNames() {
		return instance.getHeaderNames();
	}

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.filter.Response#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String name) {
		return instance.getHeader(name);
	}

}
