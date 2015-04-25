package todolist.huji.ac.il.todolistmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class TodoListManagerActivity extends Activity {
    public static final int NEW_TASK = 420;
    private final int RES_CNCL =     599;//"Dec";
    private final int RES_OK =     204;//"Dec";

    public static final String CALL = "call ";
    public static final String TITLE = "title";
    public static final String DATE = "date";

    public String[] from = new String[] { DBHelper.TITLE, DBHelper.DUE};
    public int[] to = new int[] { R.id.txtTodoTitle, R.id.txtTodoDueDate };

    String[] words;
    MyCursorAdapter simpleAd;
    Cursor tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        DBHelper myDB = new DBHelper(this);
        tasks = myDB.getData(myDB);

        final ListView taskList = (ListView) findViewById(R.id.tasksList);
        simpleAd = new MyCursorAdapter(this,R.layout.row, tasks, from, to, 0);
        taskList.setAdapter(simpleAd);

        setDeleteDialog(taskList);
    }

    private void setDeleteDialog(final ListView taskList) {
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                final Dialog deleteDialog = new Dialog(TodoListManagerActivity.this);
                deleteDialog.setContentView(R.layout.delete_dialog);
                LinearLayout item = (LinearLayout)view;
                TextView itemTtl = (TextView)item.findViewById(R.id.txtTodoTitle);
                String ttl = itemTtl.getText().toString();
                deleteDialog.setTitle(ttl);

                Button dialogButton = (Button) deleteDialog.findViewById(R.id.menuItemDelete);
                // if button is clicked, close the custom add_dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dbh = new DBHelper(getApplicationContext());
                        dbh.deleteEntry(dbh, id);
                        simpleAd.changeCursor(dbh.getData(dbh));
                        deleteDialog.dismiss();
                    }
                });
                if (ttl.toLowerCase().contains(CALL)){
                    handleCallBtn(ttl, deleteDialog);
                }
                deleteDialog.show();
            return true;
            }
        });
    }

    private void handleCallBtn(String ttl, Dialog deleteDialog) {
        Button callBtn = new Button(getApplicationContext());
        callBtn.setText(ttl);
        callBtn.setId(R.id.menuItemCall);
        LinearLayout ll = (LinearLayout)deleteDialog.findViewById(R.id.deleteLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView(callBtn, lp);

        words = ttl.split(" ");
        if (words.length > 1)
        {
            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + words[1]));
                    startActivity(call);

                }
            });

        }
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
        int id = item.getItemId();
        if (id == R.id.menuItemAdd){
            Intent intent = new Intent(this, AddNewTodoItemActivity.class);

            startActivityForResult(intent, NEW_TASK);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int reqCode, int resCode, Intent data){
        switch (resCode){
            case RES_OK:
                if(data.hasExtra(TITLE)){
                    String ttl = data.getStringExtra(TITLE);
                    Date date = null;
                    if (data.hasExtra(DATE)){
                        date = (Date)data.getSerializableExtra(DATE);
                    }
                    DBHelper dbh = new DBHelper(getApplicationContext());
                    try{
                        dbh.insertTask(dbh, ttl, date.getTime());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "caught an exception", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getBaseContext(), "inserted task to DB", Toast.LENGTH_LONG).show();
                    tasks = dbh.getData(dbh);
                    simpleAd.changeCursor(tasks);
                }
                break;
            case RES_CNCL:
                break;
            default:
                break;
        }

    }
}
