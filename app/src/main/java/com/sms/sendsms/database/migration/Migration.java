package com.sms.sendsms.database.migration;

import android.database.sqlite.SQLiteDatabase;

public interface Migration {

    int applyMigration(SQLiteDatabase db, int version);

    Migration getPreviousMigration();

    int getTargetVersion();

    int getMigratedVersion();
}
