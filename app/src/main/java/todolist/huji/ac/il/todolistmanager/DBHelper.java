package todolist.huji.ac.il.todolistmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alon on 4/23/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TITLE = "title";
    public static final String DUE = "due";
    public static final String ID = "_id";
    private final String DB_NAME = "todo_db";
    private final String CREATE_TABLE = "CREATE TABLE " + DB_NAME + " ( "
            + "  " + ID + " integer primary key autoincrement, "
            + "  " + TITLE + " TEXT, "
            + "  " + DUE + " long );";

    public DBHelper(Context context) {
        super(context, "todo_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        System.out.println("created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertTask(DBHelper dbh, String title, long due){
        SQLiteDatabase SQDB = dbh.getWritableDatabase();
        ContentValues task = new ContentValues();
        task.put(TITLE, title);
        task.put(DUE, due);
        SQDB.insert(DB_NAME, null, task);
    }

    public Cursor getData(DBHelper dbh){
        SQLiteDatabase SQDB = dbh.getReadableDatabase();
        String[] cols = {ID, TITLE, DUE};

        return SQDB.query(DB_NAME,cols,null,null,null,null,null);
    }

    public void deleteEntry(DBHelper dbh, long id){
        SQLiteDatabase SQDB = dbh.getReadableDatabase();
        SQDB.delete(DB_NAME,ID + "=" + id,null );
//        String selction = DB_NAME
    }
}