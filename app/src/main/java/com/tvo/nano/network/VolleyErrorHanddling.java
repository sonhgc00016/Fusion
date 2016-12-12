package com.tvo.nano.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class VolleyErrorHanddling {

    public static String getMessage(VolleyError error) {
        String messageError = "Error";
        if (error.getClass().equals(TimeoutError.class)) {
            messageError = "Error time out";
        }
        if (isServerProblem(error)) {
            messageError = "Server problem";
        }

        if (isNetworkProblem(error)) {
            messageError = "Network error";
        }

        if (isConnection(error)) {
            messageError = "No connection internet";
        }

        return messageError;

    }

    private static boolean isNetworkProblem(VolleyError error) {
        return (error instanceof NetworkError);

    }

    private static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError)
                || (error instanceof AuthFailureError);
    }

    private static boolean isConnection(VolleyError error) {
        return (error instanceof NoConnectionError);
    }

}
