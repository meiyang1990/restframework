package com.netfinworks.rest.convert;

public abstract interface IParamConvert
{
  public abstract <T> T convert(String paramString, Class<T> paramClass);
}

/* Location:           /Users/bigknife/Desktop/target/classes/a.zip
 * Qualified Name:     com.netfinworks.rest.convert.IParamConvert
 * JD-Core Version:    0.6.0
 */
