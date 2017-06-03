package com.missu.Bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USERS".
*/
public class UsersDao extends AbstractDao<Users, Long> {

    public static final String TABLENAME = "USERS";

    /**
     * Properties of entity Users.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property User_name = new Property(1, String.class, "user_name", false, "USER_NAME");
        public final static Property User_password = new Property(2, String.class, "user_password", false, "USER_PASSWORD");
        public final static Property User_nickname = new Property(3, String.class, "user_nickname", false, "USER_NICKNAME");
        public final static Property User_sex = new Property(4, String.class, "user_sex", false, "USER_SEX");
        public final static Property User_profile = new Property(5, String.class, "user_profile", false, "USER_PROFILE");
    }


    public UsersDao(DaoConfig config) {
        super(config);
    }
    
    public UsersDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USERS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_NAME\" TEXT," + // 1: user_name
                "\"USER_PASSWORD\" TEXT," + // 2: user_password
                "\"USER_NICKNAME\" TEXT," + // 3: user_nickname
                "\"USER_SEX\" TEXT," + // 4: user_sex
                "\"USER_PROFILE\" TEXT);"); // 5: user_profile
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USERS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Users entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(2, user_name);
        }
 
        String user_password = entity.getUser_password();
        if (user_password != null) {
            stmt.bindString(3, user_password);
        }
 
        String user_nickname = entity.getUser_nickname();
        if (user_nickname != null) {
            stmt.bindString(4, user_nickname);
        }
 
        String user_sex = entity.getUser_sex();
        if (user_sex != null) {
            stmt.bindString(5, user_sex);
        }
 
        String user_profile = entity.getUser_profile();
        if (user_profile != null) {
            stmt.bindString(6, user_profile);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Users entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String user_name = entity.getUser_name();
        if (user_name != null) {
            stmt.bindString(2, user_name);
        }
 
        String user_password = entity.getUser_password();
        if (user_password != null) {
            stmt.bindString(3, user_password);
        }
 
        String user_nickname = entity.getUser_nickname();
        if (user_nickname != null) {
            stmt.bindString(4, user_nickname);
        }
 
        String user_sex = entity.getUser_sex();
        if (user_sex != null) {
            stmt.bindString(5, user_sex);
        }
 
        String user_profile = entity.getUser_profile();
        if (user_profile != null) {
            stmt.bindString(6, user_profile);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Users readEntity(Cursor cursor, int offset) {
        Users entity = new Users( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // user_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // user_password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // user_nickname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // user_sex
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // user_profile
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Users entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUser_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUser_password(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUser_nickname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUser_sex(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUser_profile(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Users entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Users entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Users entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}