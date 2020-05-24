package dhbw.lichter.scheuring.formelapp.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import dhbw.lichter.scheuring.formelapp.util.Fart;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "FunnyFart";
    private static final int VERSION = 1;

    private SQLiteStatement insertStatement;

    private Context context;

    //TODO: Constants for column names

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;

        SQLiteDatabase db = this.getReadableDatabase();
        insertStatement  = db.compileStatement("INSERT INTO fart (fart_score, intensity, length, social_embarrassment, count_children, average_age, sex) VALUES ( ?, ?, ?, ?, ?, ?, ? )");
        //this.onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE fart (" +
                    "fart_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "fart_score REAL NOT NULL," +
                    "intensity NOT NULL," +
                    "length INTEGER NOT NULL," +
                    "social_embarrassment NOT NULL," +
                    "count_children NOT NULL," +
                    "average_age NOT NULL," +
                    "sex NOT NULL )"
            );

            //TODO: Create second table for sex & factor

            //TODO: Add male / female entries to database

            //TODO: Set second table read-only
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }

    public void saveFart(final Fart fart) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();

        insertStatement.bindString(1, fart.ToInsertString());
        long id = insertStatement.executeInsert();

        if(id == -1) {
            throw new SQLException("Insert of the new fart resulted in an error!");
        }
    }
}
