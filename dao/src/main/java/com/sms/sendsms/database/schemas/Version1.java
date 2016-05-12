package com.sms.sendsms.database.schemas;

import com.sms.sendsms.database.SchemaVersion;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Version1 extends SchemaVersion {

    public Version1(boolean current) {
        super(current);
        addEntities(getSchema());
    }

    @Override
    public int getVersionNumber() {
        return 1;
    }

    private static void addEntities(Schema schema) {

        Entity account = schema.addEntity("User");
        account.setTableName("user");
        account.addIdProperty()
                .columnName("id");
//        account.addStringProperty("username")
//            .columnName("username");
        account.addStringProperty("email")
                .columnName("email");
        account.addStringProperty("password")
                .columnName("password");
        account.addStringProperty("messageBody")
                .columnName("message_body");
        account.addStringProperty("messageCode")
                .columnName("message_code")
                .notNull();
        account.addDateProperty("disabledDate")
                .columnName("disabled_date");
//        account.addStringProperty("firstName")
//            .columnName("firstname");
//        account.addStringProperty("lastName")
//            .columnName("lastname");

        Entity blackList = schema.addEntity("BlackList");
        blackList.setTableName("black_list");
        blackList.addIdProperty()
                .columnName("id");
        blackList.addStringProperty("contactName")
            .columnName("contact_name");
        blackList.addStringProperty("number")
                .columnName("number")
                .notNull();
        Property blockListAccountId = blackList.addLongProperty("accountId")
                .columnName("account_id")
                .getProperty();

        Entity smsLog = schema.addEntity("SmsLog");
        smsLog.setTableName("sms_log");
        smsLog.addIdProperty()
                .columnName("id");
        smsLog.addStringProperty("sentNumber")
                .columnName("sent_number")
                .notNull();
        smsLog.addStringProperty("sentDate")
                .columnName("sent_date")
                .notNull();
        Property smsLogsAccountId = smsLog.addLongProperty("accountId")
                .columnName("account_id")
                .getProperty();

        /* Relations */

        blackList.addToOne(account, blockListAccountId);
        smsLog.addToOne(account, smsLogsAccountId);
    }
}
