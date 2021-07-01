package com.example.ademirestudo.database;

    import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


    /**
     * Created by williangcarv on 22/08/17.
     */

    public class ImportarBD extends SQLiteOpenHelper {
        public static final String NOMEDB = "satflex";
        public static final String LOCALDB = "/data/data/com.example.ademirestudo/databases/";
        private static final int VERSION = 58;
        private Context mContext;
        private SQLiteDatabase mSQSqLiteDatabase;


        public ImportarBD(Context context) {
            super(context, NOMEDB, null, VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public void openDataBase(){
            String dbPath = mContext.getDatabasePath(NOMEDB).getPath();
            if (mSQSqLiteDatabase != null && mSQSqLiteDatabase.isOpen()){
                return;
            }
            mSQSqLiteDatabase = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READWRITE);
        }


    }
