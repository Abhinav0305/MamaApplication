package mudaribak.manishsingh.example.com.mamaapplication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainerRegistration extends Activity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText inputFName;
    EditText inputLName;
    EditText inputMobileNumber;
    String fname;
    String lname ;
    String mobilenumber;
    private static final String TAG_SUCCESS = "success";
    private static String url_createTrainer = "http://mudaribak.com/create_trainer.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_registration);
        inputFName = (EditText) findViewById(R.id.editFName);
        inputLName= (EditText) findViewById(R.id.editLName);
        inputMobileNumber = (EditText) findViewById(R.id.editTextNumber);
        Button btnCreateProduct = (Button) findViewById(R.id.bsubmit);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                fname = inputFName.getText().toString();
                lname = inputLName.getText().toString();
                mobilenumber =inputMobileNumber.getText().toString();
                new CreateNewTrainer().execute();
            }
        });
    }
    class CreateNewTrainer extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TrainerRegistration .this);
            Toast.makeText(getApplicationContext(), "Trainer going to be created", Toast.LENGTH_SHORT);
            pDialog.setMessage("Registering Trainer");
            // check log cat fro response
            Log.d("Create Response 1", "2");
            Toast.makeText(getApplicationContext(), "Trainer created", Toast.LENGTH_SHORT);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fname", fname));
            params.add(new BasicNameValuePair("lname", lname));
            params.add(new BasicNameValuePair("mobileno", mobilenumber));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_createTrainer,"POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    Toast.makeText(getApplicationContext(), "Trainer created", Toast.LENGTH_SHORT);
                    // successfully created product
                    // Intent i = new Intent(getApplicationContext(), AllTrainersActivity.class);
                    //  startActivity(i);


                    // closing this screen
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Trainer created",Toast.LENGTH_SHORT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }


    }
}

