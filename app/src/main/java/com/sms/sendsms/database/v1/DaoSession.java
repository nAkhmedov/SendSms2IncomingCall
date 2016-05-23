package com.sms.sendsms.database.v1;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.sms.sendsms.database.v1.User;
import com.sms.sendsms.database.v1.BlackList;
import com.sms.sendsms.database.v1.SmsLog;

import com.sms.sendsms.database.v1.UserDao;
import com.sms.sendsms.database.v1.BlackListDao;
import com.sms.sendsms.database.v1.SmsLogDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig blackListDaoConfig;
    private final DaoConfig smsLogDaoConfig;

    private final UserDao userDao;
    private final BlackListDao blackListDao;
    private final SmsLogDao smsLogDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        blackListDaoConfig = daoConfigMap.get(BlackListDao.class).clone();
        blackListDaoConfig.initIdentityScope(type);

        smsLogDaoConfig = daoConfigMap.get(SmsLogDao.class).clone();
        smsLogDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        blackListDao = new BlackListDao(blackListDaoConfig, this);
        smsLogDao = new SmsLogDao(smsLogDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(BlackList.class, blackListDao);
        registerDao(SmsLog.class, smsLogDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        blackListDaoConfig.getIdentityScope().clear();
        smsLogDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BlackListDao getBlackListDao() {
        return blackListDao;
    }

    public SmsLogDao getSmsLogDao() {
        return smsLogDao;
    }

}
