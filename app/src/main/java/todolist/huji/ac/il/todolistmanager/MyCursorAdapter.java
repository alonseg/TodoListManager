package todolist.huji.ac.il.todolistmanager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alon on 4/24/2015.
 */
public class MyCursorAdapter extends SimpleCursorAdapter {

    private static LayoutInflater inflater = null;
    Context con;

    public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.con = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView ttl = (TextView) view.findViewById(R.id.txtTodoTitle);
        String toBeText= cursor.getString(cursor.getColumnIndex(DBHelper.TITLE));
        ttl.setText(toBeText);

        TextView date = (TextView) view.findViewById(R.id.txtTodoDueDate);
        Date regDate = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(DBHelper.DUE)), 10));
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(simpleDate.format(regDate).toString());
        if (regDate.before(today)) {
            date.setTextColor(Color.RED);
            ttl.setTextColor(Color.RED);
        }else{
            date.setTextColor(Color.BLUE);
            ttl.setTextColor(Color.BLUE);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.row,parent,false);
    }
}
