package com.sms.sendsms.database.migration;

import android.database.sqlite.SQLiteDatabase;

public abstract class MigrationImpl implements Migration {

    protected void prepareMigration(SQLiteDatabase db, int version) {

        if (db == null) {
            throw new IllegalArgumentException("Null SQLiteDatabase passed.");
        }

        if (version < 1) {
            throw new IllegalArgumentException(
                "Lowest suported schema version is 1, unable to prepare for migration from version: "
                    + version);
        }

        if (version < getTargetVersion()) {

            Migration previousMigration = getPreviousMigration();

            if (previousMigration == null) {
                if (version != getTargetVersion()) {
                    throw new IllegalStateException(
                        "Unable to apply migration as Version: "
                            + version
                            + " is not suitable for this Migration.");
                }
            }
            if (previousMigration != null && (previousMigration.applyMigration(db, version) != getTargetVersion())) {
                throw new IllegalStateException(
                    "Error, expected migration parent to update database to appropriate version");
            }
        }
    }
}
