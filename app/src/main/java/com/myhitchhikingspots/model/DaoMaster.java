package com.myhitchhikingspots.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.ArrayList;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * Master of DAO (schema version 5): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 5;

    /**
     * Creates underlying database table using DAOs.
     */
    public static void createAllTables(Database db, boolean ifNotExists) {
        SpotDao.createTable(db, ifNotExists);
    }

    /**
     * Drops underlying database table using DAOs.
     */
    public static void dropAllTables(Database db, boolean ifExists) {
        SpotDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(SpotDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Crashlytics.log(Log.INFO, "greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /**
     * Checks if a column already exists on a specific table of a database. This can be used because calling a SQL ALTER table command in order to add new columns to a table schema.
     * @param db The database to check the existence of the given column
     * @param table The name of the table
     * @param column The column name
     * @return True if the column already exists in the database table schema
     */
    public static boolean isColumnExists(Database db, String table, String column) {
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Crashlytics.log(Log.INFO, "greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + ".");
            try {


                String[] changesRequeriedByVersion2 = new String[]{
                        "ALTER TABLE " + SpotDao.TABLENAME + " ADD COLUMN '" + SpotDao.Properties.IsDestination.columnName + "' " + SpotDao.Properties.IsDestination.type.getSimpleName() + ";"
                };
                //Version 3 created on November 06, 2016
                String[] changesRequeriedByVersion3 = new String[]{
                        "ALTER TABLE " + SpotDao.TABLENAME + " ADD COLUMN '" + SpotDao.Properties.CountryCode.columnName + "' " + SpotDao.Properties.CountryCode.type.getSimpleName() + ";",
                        "ALTER TABLE " + SpotDao.TABLENAME + " ADD COLUMN '" + SpotDao.Properties.HasAccuracy.columnName + "' " + SpotDao.Properties.HasAccuracy.type.getSimpleName() + ";",
                        "ALTER TABLE " + SpotDao.TABLENAME + " ADD COLUMN '" + SpotDao.Properties.Accuracy.columnName + "' " + SpotDao.Properties.Accuracy.type.getSimpleName() + ";"
                };
                String[] changesRequeriedByVersion4 = new String[]{
                        "ALTER TABLE " + SpotDao.TABLENAME + " ADD '" + SpotDao.Properties.IsPartOfARoute.columnName + "' " + SpotDao.Properties.IsPartOfARoute.type.getSimpleName() + " DEFAULT 1;"
                };
                String[] changesRequeriedByVersion5 = new String[]{
                        "ALTER TABLE " + SpotDao.TABLENAME + " ADD '" + SpotDao.Properties.IsHitchhikingSpot.columnName + "' " + SpotDao.Properties.IsHitchhikingSpot.type.getSimpleName() + " DEFAULT 1;"
                };

                ArrayList<String[]> versionsUpdate = new ArrayList<>();
                versionsUpdate.add(changesRequeriedByVersion2);
                versionsUpdate.add(changesRequeriedByVersion3);
                versionsUpdate.add(changesRequeriedByVersion4);
                versionsUpdate.add(changesRequeriedByVersion5);

                String changesRequiredByNewVersion = "";

                for (int i = oldVersion; i < newVersion && i <= versionsUpdate.size(); i++) {
                    //Subtract 1 from oldVersion because the versions started from version 1 but the arrays start from 0
                    for (String s : versionsUpdate.get(i - 1))
                        changesRequiredByNewVersion += s;
                }

                //1,2 - 0
                //1,3 - 0,1
                //2,3 - 1
                //2,4 - 1,2

                if (!changesRequiredByNewVersion.isEmpty()) {
                    Crashlytics.log(Log.INFO, "greenDAO", "Will try to execute changesRequiredByNewVersion:\n" + changesRequiredByNewVersion);
                    db.execSQL(changesRequiredByNewVersion);
                    Crashlytics.log(Log.INFO, "greenDAO", "Changes executed successfully");
                } else
                    Crashlytics.log(Log.WARN, "greenDAO", "Nothing was changed on the database schema from version " + oldVersion + " to version " + newVersion + "?");


                /*dropAllTables(db, true);
                        onCreate(db);
                        Crashlytics.log(Log.INFO, "greenDAO", "All tables dropped.");*/

            } catch (Exception ex) {
                Crashlytics.logException(ex);

            }
        }
    }

}
