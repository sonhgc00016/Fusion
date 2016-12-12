package com.tvo.nano.fusioncosmetics.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.MuPDFPageAdapter;
import com.artifex.mupdfdemo.MuPDFReaderView;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.json.JSON_DutyRosterResult;
import com.tvo.nano.network.RequestAPI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DutyRosterActivity extends Activity implements View.OnClickListener, RequestAPI.IResultApiListener {

    private ImageView imgLeft, imgRight;
    private TextView tvHeadingPage, tvItemSoldCount, tvNodata;
    private String sessionid, timestamp, userid;
    private TextView tvdate, tvReportMonth;
    private ImageView imgArrowLeft, imgArrowRight;
    private boolean isNextMonth = false;
    private boolean isCurrentMonth = true;
    private boolean isPreviousMonth = false;
    private String urlPreviousMonth = "";
    private String namePreviousMonth = "";
    private String urlCurrentMonth = "";
    private String nameCurrentMonth = "";
    private String urlNextMonth = "";
    private String nameNextMonth = "";
    private String mFilePath = "";

    private LinearLayout lnlContent;
    private MuPDFReaderView pdfView;
    private MuPDFCore core;
    private boolean isCore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_roster);

        //get Extra
        Intent intent = getIntent();
        userid = intent.getStringExtra(Constants.KEY_EXTRA_USERID);
        sessionid = intent.getStringExtra(Constants.KEY_EXTRA_SESSIONID);
        timestamp = intent.getStringExtra(Constants.KEY_EXTRA_TIME_STAMP);

        //set Actionbar values
        imgLeft = (ImageView) findViewById(R.id.actionbar_imgLeft);
        imgLeft.setBackgroundResource(R.drawable.actionbar_imgback);
        imgLeft.setOnClickListener(this);
        imgRight = (ImageView) findViewById(R.id.actionbar_imgRight);
        imgRight.setBackgroundResource(R.drawable.actionbar_imgempty);
        tvHeadingPage = (TextView) findViewById(R.id.actionbar_tvHeadingPage);
        tvHeadingPage.setText(getString(R.string.duty_roster));
        tvItemSoldCount = (TextView) findViewById(R.id.actionbar_tvItemSoldCount);
        tvItemSoldCount.setVisibility(View.GONE);

        //set staying page
        Constants.stayPage = Constants.StayPage.DUTY_ROSTER;

        imgArrowLeft = (ImageView) findViewById(R.id.dutyRoster_imgArrowLeft);
        imgArrowLeft.setOnClickListener(this);
        imgArrowRight = (ImageView) findViewById(R.id.dutyRoster_imgArrowRight);
        imgArrowRight.setOnClickListener(this);

        tvdate = (TextView) findViewById(R.id.dutyRoster_tvDate);
        Calendar cld = Calendar.getInstance();
        String currentMonth = getMonth(cld.get(Calendar.MONTH)) + " " + cld.get(Calendar.YEAR);
        tvdate.setText(currentMonth);

        tvReportMonth = (TextView) findViewById(R.id.dutyRoster_tvReportMonth);
        tvReportMonth.setText(tvReportMonth.getText() + " " + tvdate.getText());

        lnlContent = (LinearLayout) findViewById(R.id.dutyRoster_lnlContent);
        tvNodata = (TextView) findViewById(R.id.dutyRoster_tvNodata);

//        pdfView = (PDFView) findViewById(R.id.dutyRoster_pdfView);
        getDutyRosterResult(userid, sessionid, timestamp);
    }

    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_duty_roster, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Calendar cld = Calendar.getInstance();
        switch (id) {
            case R.id.actionbar_imgLeft:
                Constants.stayPage = Constants.stayPage.HOME;
                finish();
            case R.id.dutyRoster_imgArrowLeft:
                String previousMonth;
                if (isCurrentMonth) {
                    previousMonth = getMonth(cld.get(Calendar.MONTH) - 1) + " " + cld.get(Calendar.YEAR);
                    checkPDFAndRead(urlPreviousMonth, namePreviousMonth);
                    tvReportMonth.setText(getResources().getText(R.string.report_month) + " " + namePreviousMonth);
                    isCurrentMonth = false;
                    isPreviousMonth = true;
                } else if (isNextMonth) {
                    previousMonth = getMonth(cld.get(Calendar.MONTH)) + " " + cld.get(Calendar.YEAR);
                    checkPDFAndRead(urlCurrentMonth, nameCurrentMonth);
                    tvReportMonth.setText(getResources().getText(R.string.report_month) + " " + nameCurrentMonth);
                    isNextMonth = false;
                    isCurrentMonth = true;
                } else {
                    return;
                }
                tvdate.setText(previousMonth);
                break;
            case R.id.dutyRoster_imgArrowRight:
                String nextMonth;
                if (isCurrentMonth) {
                    nextMonth = getMonth(cld.get(Calendar.MONTH) + 1) + " " + cld.get(Calendar.YEAR);
                    checkPDFAndRead(urlNextMonth, nameNextMonth);
                    tvReportMonth.setText(getResources().getText(R.string.report_month) + " " + nameNextMonth);
                    isCurrentMonth = false;
                    isNextMonth = true;
                } else if (isPreviousMonth) {
                    nextMonth = getMonth(cld.get(Calendar.MONTH)) + " " + cld.get(Calendar.YEAR);
                    checkPDFAndRead(urlCurrentMonth, nameCurrentMonth);
                    tvReportMonth.setText(getResources().getText(R.string.report_month) + " " + nameCurrentMonth);
                    isCurrentMonth = true;
                    isPreviousMonth = false;
                } else {
                    return;
                }
                tvdate.setText(nextMonth);
                break;
            default:
                break;
        }
    }

    private void getDutyRosterResult(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + "GetDutyRoster";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);

        RequestAPI.requestAPI(this, URL, params, Request.Method.POST,
                JSON_DutyRosterResult.class, this, 2, "GetDutyRosterResult");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(this, "No connection internet", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public void downloadPdfFromUrl(final String pdfURL, final String fileName) {
        if (isNetworkConnected()) {
            String fileNamecut = fileName.replaceAll("[^a-zA-Z0-9.-]", "");
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/Download");
            dir.mkdirs();
            mFilePath = dir + "/" + fileNamecut;
            final File file = new File(dir, fileNamecut);
            if (URLUtil.isValidUrl(pdfURL)) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        if (!isCore) {
                            RequestAPI.pDialog.show();
                        }
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            URL url = new URL(pdfURL);
                            URLConnection conn = url.openConnection();
                            conn.connect();
                            int contentLength = conn.getContentLength();

                            DataInputStream stream = new DataInputStream(url.openStream());

                            byte[] buffer = new byte[contentLength];
                            stream.readFully(buffer);
                            stream.close();

                            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
                            fos.write(buffer);
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if (!isCore) {
                            readPDF();
                            isCore = true;
                        } else {
                            core = openFile(Uri.decode(mFilePath));
                            pdfView.setAdapter(new MuPDFPageAdapter(DutyRosterActivity.this, core));
                            pdfView.invalidate();
                        }
                    }
                }.execute();
            } else {
                tvNodata.setVisibility(View.VISIBLE);
                pdfView = new MuPDFReaderView(this);
                pdfView.setVisibility(View.GONE);
            }
        }
    }

    private void checkPDFAndRead(String url, String filename) {
        if (isNetworkConnected()) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" +
                    File.separator + filename);
            if (file.exists()) {
                readPDF();
            } else {
                downloadPdfFromUrl(url, filename);
            }
        }
    }

    private void readPDF() {
        //set pdfView from file downloaded
        core = openFile(Uri.decode(mFilePath));

        if (core != null && core.countPages() == 0) {
            core = null;
        }
        if (core == null || core.countPages() == 0 || core.countPages() == -1) {
            Log.e("PDFView", "Document Not Opening");
        }
        if (core != null) {
            pdfView = new MuPDFReaderView(this) {
                @Override
                protected void onMoveToChild(int i) {
                    if (core == null)
                        return;
                    super.onMoveToChild(i);
                }
            };

            pdfView.setAdapter(new MuPDFPageAdapter(this, core));
            lnlContent.addView(pdfView);
            pdfView.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            RequestAPI.pDialog.dismiss();
        }
    }

    private MuPDFCore openFile(String path) {
        int lastSlashPos = path.lastIndexOf('/');
        mFilePath = new String(lastSlashPos == -1
                ? path
                : path.substring(lastSlashPos + 1));
        try {
            core = new MuPDFCore(this, path);
            // New file: drop the old outline data
        } catch (Exception e) {
            return null;
        }
        return core;
    }

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_DutyRosterResult) {
            JSON_DutyRosterResult result = (JSON_DutyRosterResult) object;
            if (result.getArrRosterReport() != null && result.getArrRosterReport().size() > 0) {
                urlPreviousMonth = result.getArrRosterReport().get(0).getReportpath();
                namePreviousMonth = result.getArrRosterReport().get(0).getReportmonth().trim();
                urlCurrentMonth = result.getArrRosterReport().get(1).getReportpath();
                nameCurrentMonth = result.getArrRosterReport().get(1).getReportmonth().trim();
                urlNextMonth = result.getArrRosterReport().get(2).getReportpath();
                nameNextMonth = result.getArrRosterReport().get(2).getReportmonth().trim();
                checkPDFAndRead(urlCurrentMonth, nameCurrentMonth);
            }
        }
    }
}
