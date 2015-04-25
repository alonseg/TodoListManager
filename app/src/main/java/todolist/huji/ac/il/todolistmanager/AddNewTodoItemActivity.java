package todolist.huji.ac.il.todolistmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;


public class AddNewTodoItemActivity extends Activity {
    public static final String TITLE = "title";
    public static final String DATE = "date";

    private final int RES_OK =     204;
    private final int RES_CNCL =     599;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);

//        items = new ArrayList<String>();

        final DatePicker dateP = (DatePicker) findViewById(R.id.datePicker);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        // set current date into datepicker
        dateP.init(year, month, day, null);

        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        Button okBtn = (Button) findViewById(R.id.btnOK);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RES_CNCL, null);
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateP.getYear(), dateP.getMonth(), dateP.getDayOfMonth());
                Date date = calendar.getTime();
                Intent res = new Intent();
                res.putExtra(DATE, date);
                EditText ttl = (EditText)findViewById(R.id.edtNewItem);
                res.putExtra(TITLE, ttl.getText().toString());
                setResult(RES_OK, res);


                finish();

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
