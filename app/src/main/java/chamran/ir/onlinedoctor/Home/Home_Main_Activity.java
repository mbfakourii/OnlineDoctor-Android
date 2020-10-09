package chamran.ir.onlinedoctor.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import chamran.ir.onlinedoctor.Helper.Deffult_Code_In_Activitys;
import chamran.ir.onlinedoctor.Helper.HttpPost_Volley;
import chamran.ir.onlinedoctor.Question.activity.Question_Main_Activity;
import chamran.ir.onlinedoctor.R;
import chamran.ir.onlinedoctor.Result.Result_Main_Activity;

public class Home_Main_Activity extends AppCompatActivity {
    private Deffult_Code_In_Activitys deffultCodeInActivitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--------->add first deffult
        deffultCodeInActivitys = new Deffult_Code_In_Activitys(this, R.layout.home_main, new Deffult_Code_In_Activitys.onLogOutListener() {
            @Override
            public void onLogOut() {
                finish();
            }
        });

        findViewById(R.id.img_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemFromInternet("1");

            }
        });

    }

    public void body_click(View v) {
        Snakbar2("در حال توسعه");
    }

    private void addItemFromInternet(final String tag_answer) {

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("در حال دریافت...");
        pDialog.setCancelable(false);
        pDialog.show();

        new HttpPost_Volley(Home_Main_Activity.this).event_error(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                ShowError();
            }
        }).event_success(new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    pDialog.dismiss();
                    Log.i("TAG", "--------------------------------------------");
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object1 = jsonArray.getJSONObject(0);

                    if (object1.getString("showtwo") == "null") {
                        Intent intent = new Intent(Home_Main_Activity.this, Result_Main_Activity.class);
                        intent.putExtra("response", response);
                        startActivity(intent);
                        return;
                    }

                    Intent intent = new Intent(Home_Main_Activity.this, Question_Main_Activity.class);
                    intent.putExtra("response", response);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    ShowError();
                }
            }
        }).Url("http://mfakori.ir/other_pages/doucter_online/service_android.php").send_params_price(tag_answer).Run();
    }

    public void Snakbar2(String value) {
        Snackbar snackbar = Snackbar.make(Home_Main_Activity.this.findViewById(android.R.id.content), value, Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }


    public void ShowError() {
        Snakbar2("در دانلود با مشکل مواجه شد !");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        deffultCodeInActivitys.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
