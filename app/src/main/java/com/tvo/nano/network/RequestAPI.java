package com.tvo.nano.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.fragments.MembershipFragment;
import com.tvo.nano.fusioncosmetics.fragments.MembershipUpdateProfileFragment;
import com.tvo.nano.json.JSON_RegisterCustomer;
import com.tvo.nano.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import com.tvo.nano.submitsales.SubmitSales;

public class RequestAPI {
    public static final int HINT_DIALOG = 1;
    public static final int SHOW_DIALOG = 2;
    static JsonObjectRequest jsonObjectRequest;

    public static ProgressDialog pDialog;

    public static void requestAPI(final Context context, String link, final Map<String, String> param,
                                  int method, final Class<?> cls, final IResultApiListener listener,
                                  final int flag, final String rootNode) {
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading. Please wait...");
        if (flag == SHOW_DIALOG) {
            pDialog.show();
        }

        if (method == Method.GET) {
            // create request
            jsonObjectRequest = new JsonObjectRequest(link, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (flag == SHOW_DIALOG) {
                                if (pDialog.isShowing() && pDialog != null) {
                                    pDialog.dismiss();
                                }
                            }
                            listener.onResult(JsonUtil.convertObjectFromJsonObject(response, cls, rootNode));
                        }
                    }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResult(null);
                    if (flag == SHOW_DIALOG) {
                        if (pDialog.isShowing() && pDialog != null) {
                            pDialog.dismiss();
                        }
                        String messageError = VolleyErrorHanddling.getMessage(error);
                        Toast.makeText(context, messageError, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (method == Method.POST) {
            if (param != null) {
                jsonObjectRequest = new JsonObjectRequest(link, new JSONObject(param),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (flag == SHOW_DIALOG) {
                                    pDialog.dismiss();
                                }
                                Log.i("Response", String.valueOf(response));
                                listener.onResult(JsonUtil.convertObjectFromJsonObject(response, cls, rootNode));
                            }
                        }, new ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onResult(null);

                        if (flag == SHOW_DIALOG) {
                            pDialog.dismiss();
                            String messageError = VolleyErrorHanddling.getMessage(error);
                            Toast.makeText(context, messageError, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return param;
                    }
                };
            } else {
                Gson gson = new Gson();
                String dataString = "";
                if (Constants.stayPage == Constants.StayPage.ITEM_SOLD) {
                    SubmitSales submitSales = Constants.submitSales;
                    dataString = gson.toJson(submitSales);
                } else if (Constants.stayPage == Constants.StayPage.MEMBERSHIP) {
                    FrameContentActivity frameContentActivity = (FrameContentActivity) context;
                    MembershipFragment fMembershipFragment =
                            (MembershipFragment) frameContentActivity
                                    .getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    JSON_RegisterCustomer registerCustomer = fMembershipFragment.getRegisterCus();
                    dataString = gson.toJson(registerCustomer);
                } else if (Constants.stayPage == Constants.StayPage.MEMBERSHIP_PROFILE_UPDATE) {
                    FrameContentActivity frameContentActivity = (FrameContentActivity) context;
                    MembershipUpdateProfileFragment fMembershipUpdateProfileFragment =
                            (MembershipUpdateProfileFragment) frameContentActivity
                                    .getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    JSON_RegisterCustomer registerCustomer = fMembershipUpdateProfileFragment.getUpdateCustomer();
                    dataString = gson.toJson(registerCustomer);
                }
                try {
                    JSONObject jsonObject = new JSONObject(dataString);
                    jsonObjectRequest = new JsonObjectRequest(link, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (flag == SHOW_DIALOG) {
                                        pDialog.dismiss();
                                    }
                                    Log.i("Response", String.valueOf(response));
                                    listener.onResult(JsonUtil.convertObjectFromJsonObject(response, cls, rootNode));
                                }
                            }, new ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onResult(null);

                            if (flag == SHOW_DIALOG) {
                                pDialog.dismiss();
                                String messageError = VolleyErrorHanddling.getMessage(error);
                                Toast.makeText(context, messageError, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return param;
                        }
                    };
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //Request dispatched with Socket Timeout of 30 Secs
        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, link);
    }


    public static void cancelAll() {
        AppController.getInstance().cancelPendingRequests(jsonObjectRequest);
    }

    public interface IResultApiListener {
        public void onResult(Object object);
    }

}
