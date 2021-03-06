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
 * DAO for table "CONTACT_INFO".
*/
public class ContactInfoDao extends AbstractDao<ContactInfo, Long> {

    public static final String TABLENAME = "CONTACT_INFO";

    /**
     * Properties of entity ContactInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Account = new Property(1, String.class, "account", false, "ACCOUNT");
        public final static Property Nick = new Property(2, String.class, "nick", false, "NICK");
        public final static Property Avatar = new Property(3, String.class, "avatar", false, "AVATAR");
        public final static Property Sex = new Property(4, String.class, "sex", false, "SEX");
    }


    public ContactInfoDao(DaoConfig config) {
        super(config);
    }
    
    public ContactInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONTACT_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ACCOUNT\" TEXT," + // 1: account
                "\"NICK\" TEXT," + // 2: nick
                "\"AVATAR\" TEXT," + // 3: avatar
                "\"SEX\" TEXT);"); // 4: sex
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONTACT_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ContactInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(2, account);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(3, nick);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(4, avatar);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ContactInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String account = entity.getAccount();
        if (account != null) {
            stmt.bindString(2, account);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(3, nick);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(4, avatar);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(5, sex);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ContactInfo readEntity(Cursor cursor, int offset) {
        ContactInfo entity = new ContactInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // account
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nick
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // avatar
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // sex
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ContactInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAccount(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNick(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAvatar(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSex(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ContactInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ContactInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ContactInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
