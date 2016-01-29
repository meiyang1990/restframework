package com.netfinworks.rest.auth;

import java.util.Map;

public abstract interface IAuthCheck
{
  public abstract AuthCheckResult checkAuth(Map<String, String> paramMap);
}

/* Location:           /Users/bigknife/Desktop/target/classes/a.zip
 * Qualified Name:     com.netfinworks.rest.auth.IAuthCheck
 * JD-Core Version:    0.6.0
 */
