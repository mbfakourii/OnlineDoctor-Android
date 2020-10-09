package chamran.ir.onlinedoctor.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HttpPost_Volley {
    private Activity activity;
    private String str_Url;
    private Response.ErrorListener errorListener;
    private Response.Listener<String> successListener;
    private ProgressDialog dialog;

    //params.put("f1", "Test"); // index.php?f1=test
    private Map<String, String> params = new HashMap<String, String>();

    public HttpPost_Volley(Activity activity) {
        this.activity = activity;
    }

    public HttpPost_Volley Url(String str_Url) {
        this.str_Url = str_Url;

        return this;
    }

    public HttpPost_Volley event_success(Response.Listener<String> successListener) {
        this.successListener = successListener;
        return this;
    }

    public HttpPost_Volley event_error(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public HttpPost_Volley send_params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public HttpPost_Volley send_params_verify_sms_user(String mobile) {
        this.params.put("mobile", mobile);
        return this;
    }

    public HttpPost_Volley send_empty_params() {
        return this;
    }

    public HttpPost_Volley send_params_check_verify_sms_user(String mobile, String code) {
        this.params.put("mobile", mobile);
        this.params.put("code", code);
        return this;
    }


    public HttpPost_Volley send_params_Login(String person_id, String password) {
        this.params.put("mobile", person_id);
        this.params.put("password", password);
        return this;
    }

    public HttpPost_Volley send_params_SessionIntialize(String idstudent, String idsession) {
        this.params.put("idstudent", idstudent);
        this.params.put("idsession", idsession);
        return this;
    }

    public HttpPost_Volley send_params_SessionOfTerm(String idterm) {
        this.params.put("idterm", idterm);
        return this;
    }

    public HttpPost_Volley send_params_price(String idcourse) {
        this.params.put("id", idcourse);
        return this;
    }


    public HttpPost_Volley send_params_TermIntialize(String idcourse) {
        this.params.put("idcourse", idcourse);
        return this;
    }

    public HttpPost_Volley send_params_TermStatuse(String idperson, String idterm) {
        this.params.put("idperson", idperson);
        this.params.put("idterm", idterm);
        return this;
    }

    public HttpPost_Volley send_params_TermDetail(String idstudent, String idsession) {
        this.params.put("idstudent", idstudent);
        this.params.put("idterm", idsession);
        return this;
    }

    public void Run() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, str_Url, successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);

    }
}
