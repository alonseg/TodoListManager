package todolist.huji.ac.il.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alon on 3/16/2015.
 */
public class MyAdapter extends BaseAdapter{
    Context context;
    ArrayList<String> data;
    ArrayList<Date> dates;
    private static LayoutInflater inflater = null;

    public MyAdapter(Context context, ArrayList<String> data, ArrayList<Date> dates) {
        this.context = context;
        this.data = data;
        this.dates = dates;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView text = (TextView) vi.findViewById(R.id.txtTodoTitle);
        TextView dateTV = (TextView) vi.findViewById(R.id.txtTodoDueDate);
        Date date = dates.get(position);

        Date today = Calendar.getInstance().getTime();
        if (date != null) {
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            dateTV.setText(simpleDate.format(date).toString());
            if (date.before(today)) {
                dateTV.setTextColor(Color.RED);
                text.setTextColor(Color.RED);
            }else{
                dateTV.setTextColor(Color.BLUE);
                text.setTextColor(Color.BLUE);
            }
        }else{
            dateTV.setText("No due date");
            dateTV.setTextColor(Color.RED);
            text.setTextColor(Color.RED);
        }
        text.setText(data.get(position));
        this.notifyDataSetChanged();
        return vi;
    }
}
