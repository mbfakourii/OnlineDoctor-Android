package chamran.ir.onlinedoctor.Question.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import chamran.ir.onlinedoctor.Helper.HttpPost_Volley;
import chamran.ir.onlinedoctor.Helper.getPercent;
import chamran.ir.onlinedoctor.Question.activity.Question_Main_Activity;
import chamran.ir.onlinedoctor.Question.struct_recyclerview.Question_Info;
import chamran.ir.onlinedoctor.Question.struct_recyclerview.SeparatorQuestion;
import chamran.ir.onlinedoctor.R;
import chamran.ir.onlinedoctor.Result.Result_Main_Activity;

public class Question_RecyclerAdapter extends RecyclerView.Adapter<Question_RecyclerAdapter.ViewHolder> {
    public static final int CALCULATE_BUTTON = 2;
    public static final int QUESTION = 1;
    private getPercent percent;
    private ArrayList<SeparatorQuestion> list;

    //get list questions
    public Question_RecyclerAdapter(ArrayList<SeparatorQuestion> list, getPercent percent) {
        this.list = list;
        this.percent = percent;
    }


    //get type struct and set for RecyclerView
    @Override
    public int getItemViewType(int position) {
        SeparatorQuestion item = list.get(position);
        return item.type;
    }

    //separator among ITEM ITEM and CALCULATE BUTTON layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder = null;

        if (viewType == QUESTION) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_answer, parent, false);

            view.getLayoutParams().height = percent.percent(2, 15);
            view = setMargins(view, percent.percent(1, 2), percent.percent(2, 1), percent.percent(1, 2), percent.percent(2, 1));

            viewHolder = new ViewHolder(view);

        }
        return viewHolder;
    }

    public static View setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
            return view;
        }
        return view;
    }

    //separator among ITEM ITEM and CALCULATE BUTTON  and set Contents any layout
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get type
        int type = holder.getItemViewType();
        SeparatorQuestion item = list.get(position);

        //set content list question
        final Question_Info colorObject = (Question_Info) item;
        holder.answer1.setText(colorObject.answer1);
        holder.tag_answer = colorObject.tag_answer;


        holder.cly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemFromInternet(colorObject.tag_answer);
            }
        });

    }


    public void Snakbar2(String value) {
        Snackbar snackbar = Snackbar.make(((Activity) percent.getGlobal_Context()).findViewById(android.R.id.content), value, Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }

    public void ShowError() {
        Snakbar2("در دانلود با مشکل مواجه شد !");
    }

    private void addItemFromInternet(final String tag_answer) {

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(percent.getGlobal_Context());
        pDialog.setMessage("در حال دریافت...");
        pDialog.setCancelable(false);
        pDialog.show();

        new HttpPost_Volley((Activity) percent.getGlobal_Context()).event_error(new Response.ErrorListener() {
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
                        Intent intent = new Intent(percent.getGlobal_Context(), Result_Main_Activity.class);
                        intent.putExtra("response", response);
                        percent.getGlobal_Context().startActivity(intent);
                        return;
                    }

                    Intent intent = new Intent(percent.getGlobal_Context(), Question_Main_Activity.class);
                    intent.putExtra("response", response);
                    percent.getGlobal_Context().startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                    ShowError();
                }
            }
        }).Url("http://mfakori.ir/other_pages/doucter_online/service_android.php").send_params_price(tag_answer).Run();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Holder RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup root;
        ImageView img_item;
        TextView answer1;
        String tag_answer;
        ConstraintLayout cly;

        public ViewHolder(View view) {
            super(view);

            //initialize
            root = (ViewGroup) view;
            img_item = (ImageView) view.findViewById(R.id.img_item);
            answer1 = (TextView) view.findViewById(R.id.radio_answer);
            cly = (ConstraintLayout) view.findViewById(R.id.cly);
        }
    }
}
