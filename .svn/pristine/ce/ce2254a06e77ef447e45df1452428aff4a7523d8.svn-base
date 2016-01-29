package com.netfinworks.rest.filter;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

public class Response
{
  private Map<String, String> headers = new HashMap<String, String>();
  private InputStream inputStream;
  private HttpStatus status;

  private void init()
  {
	this.headers.put(HttpHeaderName.Server, Magic.RestServerInfo);
    this.status = HttpStatus.OK;
  }

  public HttpStatus getStatus()
  {
    return this.status;
  }

  public void setStatus(HttpStatus status)
  {
    this.status = status;
  }

  public Response()
  {
    init();
  }

  public InputStream getInputStream() {
    return this.inputStream;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public void addHeader(String name, String value) {
    this.headers.put(name, value);
  }

  public String[] getHeaderNames() {
    return StringUtils.toStringArray(this.headers.keySet());
  }

  public String getHeader(String name) {
    return (String)this.headers.get(name);
  }
}
