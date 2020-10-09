package chamran.ir.onlinedoctor.Question.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import chamran.ir.onlinedoctor.Helper.Deffult_Code_In_Activitys;
import chamran.ir.onlinedoctor.Helper.getPercent;
import chamran.ir.onlinedoctor.Question.adapter.Question_RecyclerAdapter;
import chamran.ir.onlinedoctor.Question.struct_recyclerview.Question_Info;
import chamran.ir.onlinedoctor.Question.struct_recyclerview.SeparatorQuestion;
import chamran.ir.onlinedoctor.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question_Main_Activity extends AppCompatActivity {

    private Deffult_Code_In_Activitys deffultCodeInActivitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--------->add first deffult
        deffultCodeInActivitys = new Deffult_Code_In_Activitys(this, R.layout.question_main, new Deffult_Code_In_Activitys.onLogOutListener() {
            @Override
            public void onLogOut() {
                finish();
            }
        });

        TextView txt_title = (TextView) findViewById(R.id.txt_title);


        //initialize RecyclerView
        RecyclerView list_colors = (RecyclerView) findViewById(R.id.list_colors2);
        list_colors.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<SeparatorQuestion> questions = new ArrayList<SeparatorQuestion>();

        //Add item

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getExtras().getString("response"));
            JSONObject object1 = jsonArray.getJSONObject(0);

            //regext
            final Pattern pattern = Pattern.compile("\\[(.*)\\|(.*)\\]", Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(object1.getString("showtwo"));

            while (matcher.find()) {
                questions.add(new Question_Info(matcher.group(2), matcher.group(1)));
            }


            txt_title.setText(object1.getString("showone"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        //initialize get percent
        getPercent percent = new getPercent(this);

        //add RecyclerView
        final Question_RecyclerAdapter adapter = new Question_RecyclerAdapter(questions, percent);
        list_colors.setAdapter(adapter);
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
