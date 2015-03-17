package todolist.huji.ac.il.todolistmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class TodoListManagerActivity extends Activity {
    ArrayList<String> items;
    MyAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        final Context context = getApplicationContext();
        items = new ArrayList<String>();

        final ListView taskList = (ListView) findViewById(R.id.tasksList);
        tasksAdapter = new MyAdapter(this, items);
        taskList.setAdapter(tasksAdapter);

        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog deleteDialog = new Dialog(TodoListManagerActivity.this);
                deleteDialog.setContentView(R.layout.dialog);
                String ttl = items.get(position);
                deleteDialog.setTitle(ttl);

                Button dialogButton = (Button) deleteDialog.findViewById(R.id.menuItemDelete);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.remove(position);
                        tasksAdapter.notifyDataSetChanged();
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            return true;
            }
        });

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        }

        TextView listTitle = (TextView) row.findViewById(R.id.task);
        listTitle.setTextColor(Color.parseColor("#405478"));

        return listTitle;
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
            final EditText taskTextBox = (EditText) findViewById(R.id.edtNewItem);
            String taskStr = taskTextBox.getText().toString();
            if (taskStr.length() != 0) {
                items.add(taskStr);
                tasksAdapter.notifyDataSetChanged();
                taskTextBox.setText("");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
