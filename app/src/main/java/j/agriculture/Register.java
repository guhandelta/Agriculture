package j.agriculture;
package j.agriculture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class Register extends AppCompatActivity {

    private Toolbar toolbar;

    JSONParser jsonParser = new JSONParser();

    Utils linkurl;

    //private static String url = "http://192.168.1.8/grocery/MobileApp/customerdetails.php";

    private EditText edtcustomerName, edtcustomerMobileNumber, edtcustomeremailid, edtcustomerAddress, edtcustomerDOB;
    private TextView tvIMEInumber;
    private Button btnreset, btnsubmit;
    String customerName, customerMobileNumber, customeremailid, customerAddress, customerDOB, customerIMEInumber;
    String imei;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Customer Registration");

        linkurl = new Utils();

        Intent intent = getIntent();
        imei = intent.getStringExtra("imeinumber");


        edtcustomerName = (EditText) findViewById(R.id.edt_customername);
        edtcustomerMobileNumber = (EditText) findViewById(R.id.edt_customermobilenumber);
        edtcustomeremailid = (EditText) findViewById(R.id.edt_customermemailid);
        edtcustomerAddress = (EditText) findViewById(R.id.edt_customeraddress);
        edtcustomerDOB = (EditText) findViewById(R.id.edt_customerdateofbirth);
        tvIMEInumber = (TextView) findViewById(R.id.txtIMEINumber);
        tvIMEInumber.setText(imei);


        btnsubmit = (Button) findViewById(R.id.btn_submit);
        btnreset = (Button) findViewById(R.id.btn_reset);


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtcustomerName.setText("");
                edtcustomerMobileNumber.setText("");
                edtcustomeremailid.setText("");
                edtcustomerAddress.setText("");
                edtcustomerDOB.setText("");
            }
        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerName = edtcustomerName.getText().toString().trim();
                customerMobileNumber = edtcustomerMobileNumber.getText().toString().trim();
                customeremailid = edtcustomeremailid.getText().toString().trim();
                customerAddress = edtcustomerAddress.getText().toString().trim();
                customerDOB = edtcustomerDOB.getText().toString().trim();
                customerIMEInumber = tvIMEInumber.getText().toString().trim();

                // Toast.makeText(getApplicationContext(), customerName + "\n" + customerMobileNumber + "\n" + customerAddress + "\n" + customerLandMark + "\n" + customerPinCode + "\n" + customerLatitude + "\n" + customerLongitude + "\n" + customerIMEInumber + "\n" + customerDeviceName + "\n" + applicationdownloadedfrom + "\n" + username + "\n" + password + "\n" + status, Toast.LENGTH_LONG).show();

                new customerdetail().execute(customerName, customerMobileNumber, customeremailid, customerAddress, customerDOB, customerIMEInumber);
            }
        });

    }


    public class customerdetail extends AsyncTask<String, String, String> {
        String success;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String cname = params[0];
            String cmobilenumber = params[1];
            String cemailid = params[2];
            String caddress = params[3];
            String cdob = params[4];
            String cIMEInumber = params[5];

            Log.d("Response: ", "> " + cname + cmobilenumber + caddress + cdob + cIMEInumber);

            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("customername", cname));
            list.add(new BasicNameValuePair("mobilenumber", cmobilenumber));
            list.add(new BasicNameValuePair("emailid", cemailid));
            list.add(new BasicNameValuePair("address", caddress));
            list.add(new BasicNameValuePair("dob", cdob));
            list.add(new BasicNameValuePair("IMEInumber", cIMEInumber));


            try {
                JSONObject jsonObject = jsonParser.getJSONFromUrl(linkurl.APICUSTOMERDETAILS, "POST", list);

                Log.d("JSON Data: ", "> " + jsonObject);

                success = jsonObject.getString("response");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            progressDialog.dismiss();
            if (success.equals("success")) {

                Toast.makeText(Register.this, "Submission Success",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Register.this, MainActivity.class);
                // i.putExtra("customerid", CustomerId);
                //i.putExtra("customername", CustomerName);
                startActivity(i);

                finish();

            } else {

                Toast.makeText(Register.this, "Submission Failed",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }
}
