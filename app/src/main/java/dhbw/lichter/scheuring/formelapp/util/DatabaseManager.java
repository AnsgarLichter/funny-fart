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


    public static final String TABLE_FART = "fart";
    public static final String TABLE_SEX = "sex";
    public static final String TABLE_SCORE_GIF = "score_gif";


    public static final String COL_ID = "fart_id";
    public static final String COL_FART_SCORE = "fart_score";
    public static final String COL_INTENSITY = "intensity";
    public static final String COL_LENGTH = "length";
    public static final String COL_SOCIAL_EMBARRASSMENT = "social_embarrassment";
    public static final String COL_COUNT_CHILDREN = "count_children";
    public static final String COL_AVERAGE_AGE = "average_age";
    public static final String COL_SEX = "sex";
    public static final String COL_SEX_FACTOR = "sex_factor";
    public static final String COL_FART_GIF = "fart_gif";
    public static final String COL_FART_NAME = "fart_name";
    public static final String COL_CREATION_DATE = "creation_date";


    private static final String CREATE_TABLE_FART = "CREATE TABLE " + TABLE_FART + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_FART_SCORE + " REAL NOT NULL, " +
            COL_FART_NAME + " TEXT NOT NULL, " +
            COL_INTENSITY + " INTEGER NOT NULL, " +
            COL_LENGTH + " INTEGER NOT NULL, " +
            COL_SOCIAL_EMBARRASSMENT + " NOT NULL, " +
            COL_COUNT_CHILDREN + " INTEGER NOT NULL, " +
            COL_AVERAGE_AGE + " INTEGER NOT NULL, " +
            COL_SEX + " TEXT NOT NULL, " +
            COL_CREATION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL )";
    private static final String CREATE_TABLE_SEX = "CREATE TABLE " + TABLE_SEX + " (" +
            COL_SEX + " PRIMARY KEY NOT NULL, " +
            COL_SEX_FACTOR + " REAL NOT NULL, " +
            "FOREIGN KEY (" + COL_SEX + ") REFERENCES " + TABLE_FART + "(" + COL_SEX + "));";
    private static final String CREATE_TABLE_SCORE_GIF = "CREATE TABLE "+ TABLE_SCORE_GIF + " (" +
            COL_FART_SCORE + " PRIMARY KEY NOT NULL, " +
            COL_FART_GIF + " REAL NOT NULL)";


    private static final String INSERT_FART = "INSERT INTO " + TABLE_FART +
            "(" + COL_FART_SCORE + ", " + COL_FART_NAME + ", " + COL_INTENSITY + ", " + COL_LENGTH + ", " + COL_SOCIAL_EMBARRASSMENT + ", " + COL_COUNT_CHILDREN + ", " + COL_AVERAGE_AGE + ", " + COL_SEX + ")" +
            "VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String INSERT_SEX = "INSERT INTO " + TABLE_SEX +
            "(" + COL_SEX + ", " + COL_SEX_FACTOR + ") VALUES ( ?, ?)";
    private static final String INSERT_SCORE_GIF = "INSERT INTO " + TABLE_SCORE_GIF +
            " (" + COL_FART_SCORE + ", " + COL_FART_GIF + ") VALUES (?, ?)";


    private SQLiteStatement insertFart;
    private SQLiteStatement insertSex;
    private SQLiteStatement insertScore;


    private Context context;



    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;

        SQLiteDatabase db = this.getReadableDatabase();
        insertFart  = db.compileStatement(INSERT_FART);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            this.createFartTable(db);
            this.createSexTable(db);
            this.createScoreGifTable(db);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveFart(final Fart fart) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();

        insertFart = fart.prepareInsertStatement(insertFart);
        long id = insertFart.executeInsert();

        if(id == -1) {
            throw new SQLException("Insertion of the new fart resulted in an error!");
        }
    }

    private void createFartTable(SQLiteDatabase db) throws SQLException{
        db.execSQL(CREATE_TABLE_FART);
    }

    private void createSexTable(SQLiteDatabase db) throws SQLException{
        db.execSQL(CREATE_TABLE_SEX);
        insertSex  = db.compileStatement(INSERT_SEX);

        insertSex.bindString(1, "männlich");
        insertSex.bindString(2, "1.00");
        long idMale = insertSex.executeInsert();

        insertSex.bindString(1, "weiblich");
        insertSex.bindString(2, "1.05");
        long idFemale = insertSex.executeInsert();

        if(idMale == 0 || idFemale == 0) {
            throw new SQLException("Could not insert male and female entry");
        }
    }

    private void createScoreGifTable(SQLiteDatabase db) throws SQLException{
        db.execSQL(CREATE_TABLE_SCORE_GIF);
        insertScore  = db.compileStatement(INSERT_SCORE_GIF);

        //TODO: Insert path to GIFs as soon as selected
    }
}
