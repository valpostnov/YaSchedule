package com.postnov.android.yaschedule.utils.exception;

/**
 * Created by platon on 02.06.2016.
 */
public class NetworkConnectionError extends Exception
{
    public NetworkConnectionError()
    {
        super();
    }

    public NetworkConnectionError(String detailMessage)
    {
        super(detailMessage);
    }

    public NetworkConnectionError(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
    }

    public NetworkConnectionError(Throwable throwable)
    {
        super(throwable);
    }
}
