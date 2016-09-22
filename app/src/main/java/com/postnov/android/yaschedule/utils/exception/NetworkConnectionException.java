package com.postnov.android.yaschedule.utils.exception;

/**
 * Created by platon on 02.06.2016.
 */
public class NetworkConnectionException extends Exception
{
    public NetworkConnectionException()
    {
        super();
    }

    public NetworkConnectionException(String detailMessage)
    {
        super(detailMessage);
    }

    public NetworkConnectionException(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
    }

    public NetworkConnectionException(Throwable throwable)
    {
        super(throwable);
    }
}
