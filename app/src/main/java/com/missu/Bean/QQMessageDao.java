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
 * DAO for table "QQMESSAGE".
*/
public class QQMessageDao extends AbstractDao<QQMessage, Void> {

    public static final String TABLENAME = "QQMESSAGE";

    /**
     * Properties of entity QQMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Type = new Property(0, String.class, "type", false, "TYPE");
        public final static Property From = new Property(1, String.class, "from", false, "FROM");
        public final static Property FromNick = new Property(2, String.class, "fromNick", false, "FROM_NICK");
        public final static Property FromAvatar = new Property(3, String.class, "fromAvatar", false, "FROM_AVATAR");
        public final static Property To = new Property(4, String.class, "to", false, "TO");
        public final static Property Content = new Property(5, String.class, "content", false, "CONTENT");
        public final static Property SendTime = new Property(6, String.class, "sendTime", false, "SEND_TIME");
    }


    public QQMessageDao(DaoConfig config) {
        super(config);
    }
    
    public QQMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QQMESSAGE\" (" + //
                "\"TYPE\" TEXT," + // 0: type
                "\"FROM\" TEXT," + // 1: from
                "\"FROM_NICK\" TEXT," + // 2: fromNick
                "\"FROM_AVATAR\" TEXT," + // 3: fromAvatar
                "\"TO\" TEXT," + // 4: to
                "\"CONTENT\" TEXT," + // 5: content
                "\"SEND_TIME\" TEXT);"); // 6: sendTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QQMESSAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, QQMessage entity) {
        stmt.clearBindings();
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(1, type);
        }
 
        String from = entity.getFrom();
        if (from != null) {
            stmt.bindString(2, from);
        }
 
        String fromNick = entity.getFromNick();
        if (fromNick != null) {
            stmt.bindString(3, fromNick);
        }
 
        String fromAvatar = entity.getFromAvatar();
        if (fromAvatar != null) {
            stmt.bindString(4, fromAvatar);
        }
 
        String to = entity.getTo();
        if (to != null) {
            stmt.bindString(5, to);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }
 
        String sendTime = entity.getSendTime();
        if (sendTime != null) {
            stmt.bindString(7, sendTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, QQMessage entity) {
        stmt.clearBindings();
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(1, type);
        }
 
        String from = entity.getFrom();
        if (from != null) {
            stmt.bindString(2, from);
        }
 
        String fromNick = entity.getFromNick();
        if (fromNick != null) {
            stmt.bindString(3, fromNick);
        }
 
        String fromAvatar = entity.getFromAvatar();
        if (fromAvatar != null) {
            stmt.bindString(4, fromAvatar);
        }
 
        String to = entity.getTo();
        if (to != null) {
            stmt.bindString(5, to);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }
 
        String sendTime = entity.getSendTime();
        if (sendTime != null) {
            stmt.bindString(7, sendTime);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public QQMessage readEntity(Cursor cursor, int offset) {
        QQMessage entity = new QQMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // type
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // from
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fromNick
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // fromAvatar
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // to
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // content
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // sendTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, QQMessage entity, int offset) {
        entity.setType(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setFrom(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFromNick(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFromAvatar(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setContent(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSendTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(QQMessage entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(QQMessage entity) {
        return null;
    }

    @Override
    public boolean hasKey(QQMessage entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}