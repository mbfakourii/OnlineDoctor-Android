package chamran.ir.onlinedoctor.Result;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.List;

import chamran.ir.onlinedoctor.Helper.Deffult_Code_In_Activitys;
import chamran.ir.onlinedoctor.R;

public class Result_Main_Activity extends AppCompatActivity {
    private Deffult_Code_In_Activitys deffultCodeInActivitys;
    private String str_name_pdf = "";
    private String str_result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //--------->add first deffult
        deffultCodeInActivitys = new Deffult_Code_In_Activitys(this, R.layout.result_main, new Deffult_Code_In_Activitys.onLogOutListener() {
            @Override
            public void onLogOut() {
                finish();
            }
        });

        TextView txt_Show = (TextView) findViewById(R.id.txt_Show);


        //regext
        try {
            JSONArray jsonArray = new JSONArray(getIntent().getExtras().getString("response"));
            JSONObject object1 = jsonArray.getJSONObject(0);

            txt_Show.setText(object1.getString("showone"));
            str_result = object1.getString("showone");
            str_name_pdf = "file_" + object1.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //click on btn_new
        findViewById(R.id.btn_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(Result_Main_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == 0) {
                    savePDF();
                } else {
                    ActivityCompat.requestPermissions(Result_Main_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1120);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("TAG", grantResults[0] + "");
        if (grantResults[0] == -1) {
            Toast.makeText(this, "برنامه دسترسی برای نوشتن رو دریافت نکرد اگر صفعه درخواست دسترسی برای شما نشان داده نمیشود به صفعه تنظیمات رفته و در صفعه برنامه دسترسی نوشتن را به برنامه بدهید!", Toast.LENGTH_LONG).show();
        } else {
            savePDF();
        }
    }

    private void savePDF() {
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/OnlineDoctor/";

        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }


        File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/OnlineDoctor/", str_name_pdf + ".pdf");
        if (file2.exists()) {
            Snakbar2("همچین نسخه pdf قبلا در پوشه OnlineDoctor با نام " + str_name_pdf + ".pdf ذخیره شده");
        } else {
            CratePdfFromHtml("<p style=\"text-align: center;\"><strong>به نام خدا</strong></p><p style=\"text-align: center;\">نسخه طبیب انلاین برای بیماری شما</p><p style=\"text-align: center;\">" + str_result + "</p>", Environment.getExternalStorageDirectory() + "/OnlineDoctor/" + str_name_pdf + ".pdf");
            Snakbar2("نسخه pdf ساخته شد و در پوشه OnlineDoctor با نام " + str_name_pdf + ".pdf" + " ذخیره شد.");
        }
    }

    public void Snakbar2(String value) {
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), value, Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }

    private void CratePdfFromHtml(String str_html, String str_path) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(str_path));
            document.open();

            FontFactory.register("assets/tahoma.ttf");
            StyleSheet styles = new StyleSheet();
            styles.loadTagStyle(HtmlTags.BODY, HtmlTags.FONTFAMILY, "tahoma");
            styles.loadTagStyle(HtmlTags.BODY, HtmlTags.ENCODING, "Identity-H");
            styles.loadTagStyle(HtmlTags.BODY, HtmlTags.BORDER, "0");

            List<Element> parsedHtmlElements = HTMLWorker.parseToList(new StringReader(str_html), styles);

            PdfPCell pdfCell = new PdfPCell();
            pdfCell.setBorder(Rectangle.NO_BORDER);
            pdfCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            for (Element htmlElement : parsedHtmlElements)

            {
                pdfCell.addElement(htmlElement);
            }


            PdfPTable table1 = new PdfPTable(1);

            table1.addCell(pdfCell);
            document.add(table1);


            document.close();

        } catch (Exception e) {
            Log.i("TAG", "Error in Crate Pdf");
            e.printStackTrace();
        }
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
