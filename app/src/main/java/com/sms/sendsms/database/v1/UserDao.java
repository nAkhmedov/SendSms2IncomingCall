package com.sms.sendsms.database.v1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.sms.sendsms.database.v1.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table user.
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "user";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "id");
        public final static Property Email = new Property(1, String.class, "email", false, "email");
        public final static Property Password = new Property(2, String.class, "password", false, "password");
        public final static Property MessageBody = new Property(3, String.class, "messageBody", false, "message_body");
        public final static Property MessageCode = new Property(4, String.class, "messageCode", false, "message_code");
        public final static Property DisabledDate = new Property(5, java.util.Date.class, "disabledDate", false, "disabled_date");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'user' (" + //
                "'id' INTEGER PRIMARY KEY ," + // 0: id
                "'email' TEXT," + // 1: email
                "'password' TEXT," + // 2: password
                "'message_body' TEXT," + // 3: messageBody
                "'message_code' TEXT NOT NULL ," + // 4: messageCode
                "'disabled_date' INTEGER);"); // 5: disabledDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'user'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(2, email);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String messageBody = entity.getMessageBody();
        if (messageBody != null) {
            stmt.bindString(4, messageBody);
        }
        stmt.bindString(5, entity.getMessageCode());
 
        java.util.Date disabledDate = entity.getDisabledDate();
        if (disabledDate != null) {
            stmt.bindLong(6, disabledDate.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // email
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // messageBody
            cursor.getString(offset + 4), // messageCode
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)) // disabledDate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEmail(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMessageBody(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMessageCode(cursor.getString(offset + 4));
        entity.setDisabledDate(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}