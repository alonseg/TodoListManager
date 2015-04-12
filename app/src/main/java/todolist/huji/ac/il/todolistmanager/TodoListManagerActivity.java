package todolist.huji.ac.il.todolistmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends Activity {
    public static final int NEW_TASK = 420;
    private final int RES_CNCL =     599;//"Dec";
    private final int RES_OK =     204;//"Dec";

    public static final String CALL = "call ";
    public static final String TITLE = "title";
    public static final String DATE = "date";

    ArrayList<String> items;
    ArrayList<Date> dates;
    MyAdapter tasksAdapter;
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        items = new ArrayList<String>();
        dates = new ArrayList<Date>();

        final ListView taskList = (ListView) findViewById(R.id.tasksList);
        tasksAdapter = new MyAdapter(this, items, dates);
        taskList.setAdapter(tasksAdapter);

        setDeleteDialog(taskList);

    }

    private void setDeleteDialog(ListView taskList) {
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog deleteDialog = new Dialog(TodoListManagerActivity.this);
                deleteDialog.setContentView(R.layout.delete_dialog);
                String ttl = items.get(position);
                deleteDialog.setTitle(ttl);

                Button dialogButton = (Button) deleteDialog.findViewById(R.id.menuItemDelete);
                // if button is clicked, close the custom add_dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.remove(position);
                        dates.remove(position);
                        tasksAdapter.notifyDataSetChanged();
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
                        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
//                        System.out.println(dateStr);
                    }

                    items.add(ttl);
                    dates.add(date);
                    tasksAdapter.notifyDataSetChanged();
                }
                break;
            case RES_CNCL:
                break;
            default:
                break;
        }

    }
}
