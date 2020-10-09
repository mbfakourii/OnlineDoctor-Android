package chamran.ir.onlinedoctor.Helper;

import android.graphics.Color;

import androidx.core.view.ViewCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;


import chamran.ir.onlinedoctor.R;
import me.anwarshahriar.calligrapher.Calligrapher;

public class Deffult_Code_In_Activitys {
    private AppCompatActivity activity;
    private Toolbar toolbar;
    private getPercent percent;
    private onLogOutListener listener_logout;


    public interface onLogOutListener {
        void onLogOut();
    }

    //--------------->constractor and get activty
    public Deffult_Code_In_Activitys(AppCompatActivity activity, int layoutResID, onLogOutListener listener) {
        initilize(activity, layoutResID, listener, false);
    }

    //--------------->constractor and get activty
    public Deffult_Code_In_Activitys(AppCompatActivity activity, int layoutResID, onLogOutListener listener, boolean show_onlyset_ContentView) {
        initilize(activity, layoutResID, listener, true);
    }

    //------>for Polymorphism Deffult_Code_In_Activitys
    private void initilize(AppCompatActivity activity, int layoutResID, onLogOutListener listener, boolean show_onlyset_ContentView) {
        this.activity = activity;
        this.listener_logout = listener;

        //------------>add view in activty
        activity.setContentView(layoutResID);

        activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //-------------> set fonts
        Calligrapher calligrapher = new Calligrapher(activity);
        calligrapher.setFont(activity, "IRANSansMobile.ttf", true);

        //----------->for landscape activitys
        if (show_onlyset_ContentView) {
            return;
        }

        //--------------->define toolbar
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        percent = new getPercent(activity);

        activity.setSupportActionBar(toolbar);


    }


    //--------->get toolbar
    public Toolbar getToolbar() {
        return toolbar;
    }

    //----------->set click on item toolbar
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                new AlertDialog.Builder(this.activity)
                        .setTitle("درباره ما")
                        .setMessage("نويسندگان :\n" + "حسين زند\n" + "مصطفی اميری\n" + "محمد باقر فکوری ")
                        .show();
                break;
            default:
        }
    }


}


