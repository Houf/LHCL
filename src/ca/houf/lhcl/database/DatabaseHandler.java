package ca.houf.lhcl.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lhcl.db";
    private static final String TABLE_PLAYERS = "players";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GOAL = "goal";
    public static final String COLUMN_ASSIST = "assist";

    public DatabaseHandler(final Context context, final String name, final CursorFactory factory, final int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(final SQLiteDatabase db)
    {
        final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
            TABLE_PLAYERS + "("
            + COLUMN_NAME + " INTEGER PRIMARY KEY," + COLUMN_GOAL
            + " TEXT," + COLUMN_ASSIST + " INTEGER" + ")";

        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }

    public void addPlayer(final Player player)
    {
        final ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, player.getName());
        values.put(COLUMN_GOAL, player.getGoal());
        values.put(COLUMN_ASSIST, player.getAssist());

        final SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PLAYERS, null, values);
        db.close();
    }

    public Player findPlayer(final String playerName)
    {
        final String query = "Select * FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_NAME + " =  \"" + playerName + "\"";

        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor cursor = db.rawQuery(query, null);

        if(!cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return null;
        }

        cursor.moveToFirst();
        final Player player = new Player(cursor.getString(0), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        cursor.close();
        db.close();
        return player;
    }

    public boolean deletePlayer(final String playerName)
    {
        final String query = "Select * FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_NAME + " =  \"" + playerName + "\"";

        final SQLiteDatabase db = this.getWritableDatabase();

        final Cursor cursor = db.rawQuery(query, null);

        if(!cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return false;
        }

        db.delete(TABLE_PLAYERS, COLUMN_NAME + " = ?", new String[] { String.valueOf(cursor.getString(0)) });
        cursor.close();
        db.close();

        return true;
    }
}
