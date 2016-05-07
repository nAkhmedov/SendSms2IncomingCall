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

        Entity business = schema.addEntity("Business");
        business.setTableName("business");
        business.addIdProperty()
                .columnName("id");
        business.addStringProperty("storeCode")
                .columnName("storecode");
        business.addStringProperty("businessName")
                .columnName("businessname");
        business.addStringProperty("logo")
                .columnName("logo");
        business.addStringProperty("backgroundImage")
                .columnName("backgroundimage")
                .notNull();
        business.addStringProperty("backgroundColor")
                .columnName("backgroundcolor");
        business.addStringProperty("headerColor")
                .columnName("headercolor");
        business.addStringProperty("footerColor")
                .columnName("footercolor");
        business.addBooleanProperty("ifLogo")
                .columnName("iflogo")
                .notNull();
        business.addBooleanProperty("ifMail")
                .columnName("ifmail")
                .notNull();
        business.addBooleanProperty("ifFacebook")
                .columnName("iffacebook")
                .notNull();
        business.addBooleanProperty("ifTwitter")
                .columnName("iftwitter")
                .notNull();
        business.addBooleanProperty("ifLinkedin")
                .columnName("iflinkedin")
                .notNull();
        business.addBooleanProperty("ifGoogleplus")
                .columnName("ifgoogleplus")
                .notNull();
        business.addBooleanProperty("ifYoutube")
                .columnName("ifyoutube")
                .notNull();
        business.addBooleanProperty("ifPhone")
                .columnName("ifphone")
                .notNull();
        business.addBooleanProperty("ifGallery")
                .columnName("ifgallery")
                .notNull();
        business.addBooleanProperty("ifAbout")
                .columnName("ifabout")
                .notNull();
        business.addBooleanProperty("ifWebsite")
                .columnName("ifwebsite")
                .notNull();
        business.addBooleanProperty("ifMap")
                .columnName("ifmap")
                .notNull();
        business.addBooleanProperty("ifPinterest")
                .columnName("ifpinterest")
                .notNull();
        business.addBooleanProperty("ifAndroid")
                .columnName("ifandroid")
                .notNull();
        business.addStringProperty("mainText")
                .columnName("maintext");
        business.addStringProperty("mailIconColor")
                .columnName("mail_icon_color");
        business.addStringProperty("mailLabelColor")
                .columnName("mail_label_color");
        business.addStringProperty("mailLabel")
                .columnName("mail_label");
        business.addStringProperty("facebookIconColor")
                .columnName("facebook_icon_color");
        business.addStringProperty("facebookLabelColor")
                .columnName("facebook_label_color");
        business.addStringProperty("facebookLabel")
                .columnName("facebook_label");
        business.addStringProperty("twitterIconColor")
                .columnName("twitter_icon_color");
        business.addStringProperty("twitterLabelColor")
                .columnName("twitter_label_color");
        business.addStringProperty("twitterLabel")
                .columnName("twitter_label");
        business.addStringProperty("linkedinIconColor")
                .columnName("linkedin_icon_color");
        business.addStringProperty("linkedinLabelColor")
                .columnName("linkedin_label_color");
        business.addStringProperty("linkedinLabel")
                .columnName("linkedin_label");
        business.addStringProperty("googleplusIconColor")
                .columnName("googleplus_icon_color");
        business.addStringProperty("googleplusLabelColor")
                .columnName("googleplus_label_color");
        business.addStringProperty("googleplusLabel")
                .columnName("googleplus_label");
        business.addStringProperty("mailAddress")
                .columnName("mail_address");
        business.addStringProperty("facebookAddress")
                .columnName("facebook_address");
        business.addStringProperty("twitterAddress")
                .columnName("twitter_address");
        business.addStringProperty("linkedinAddress")
                .columnName("linkedin_address");
        business.addStringProperty("googleplusAddress")
                .columnName("googleplus_address");
        business.addStringProperty("youtubeIconColor")
                .columnName("youtube_icon_color");
        business.addStringProperty("youtubeLabelColor")
                .columnName("youtube_label_color");
        business.addStringProperty("youtubeLabel")
                .columnName("youtube_label");
        business.addStringProperty("youtubeAddress")
                .columnName("youtube_address");
        business.addStringProperty("phoneLabelColor")
                .columnName("phone_label_color");
        business.addStringProperty("phoneIconColor")
                .columnName("phone_icon_color");
        business.addStringProperty("phoneLabel")
                .columnName("phone_label");
        business.addStringProperty("phoneAddress")
                .columnName("phone_address");
        business.addStringProperty("galleryIconColor")
                .columnName("gallery_icon_color");
        business.addStringProperty("galleryLabelColor")
                .columnName("gallery_label_color");
        business.addStringProperty("galleryLabel")
                .columnName("gallery_label");
        business.addStringProperty("galleryAddress")
                .columnName("gallery_address");
        business.addStringProperty("aboutIconColor")
                .columnName("about_icon_color");
        business.addStringProperty("aboutLabelColor")
                .columnName("about_label_color");
        business.addStringProperty("aboutLabel")
                .columnName("about_label");
        business.addStringProperty("aboutAddress")
                .columnName("about_address");
        business.addStringProperty("websiteIconColor")
                .columnName("website_icon_color");
        business.addStringProperty("websiteLabelColor")
                .columnName("website_label_color");
        business.addStringProperty("websiteLabel")
                .columnName("website_label");
        business.addStringProperty("websiteAddress")
                .columnName("website_address");
        business.addStringProperty("mapIconColor")
                .columnName("map_icon_color");
        business.addStringProperty("mapLabelColor")
                .columnName("map_label_color");
        business.addStringProperty("mapLabel")
                .columnName("map_label");
        business.addStringProperty("mapAddress")
                .columnName("map_address");
        business.addStringProperty("pinterestIconColor")
                .columnName("pinterest_icon_color");
        business.addStringProperty("pinterestLabelColor")
                .columnName("pinterest_label_color");
        business.addStringProperty("pinterestLabel")
                .columnName("pinterest_label");
        business.addStringProperty("pinterestAddress")
                .columnName("pinterest_address");
        business.addStringProperty("androidIconColor")
                .columnName("android_icon_color");
        business.addStringProperty("androidLabelColor")
                .columnName("android_label_color");
        business.addStringProperty("androidLabel")
                .columnName("android_label");
        business.addStringProperty("androidAddress")
                .columnName("android_address");
        business.addBooleanProperty("ifBusinessname")
                .columnName("ifbusinessname")
                .notNull();
        business.addStringProperty("businessnameColor")
                .columnName("businessname_color");
        business.addStringProperty("maintextColor")
                .columnName("maintext_color");
        business.addBooleanProperty("ifFooter")
                .columnName("iffooter")
                .notNull();
        business.addBooleanProperty("ifHeader")
                .columnName("ifheader")
                .notNull();
        business.addBooleanProperty("ifUserplus")
                .columnName("ifuserplus")
                .notNull();
        business.addStringProperty("userplusIconColor")
                .columnName("userplus_icon_color");
        business.addStringProperty("userplusLabelColor")
                .columnName("userplus_label_color");
        business.addStringProperty("userplusLabel")
                .columnName("userplus_label");
        business.addStringProperty("bitly")
                .columnName("bitly");
        business.addStringProperty("maskyoo")
                .columnName("maskyoo");
        business.addBooleanProperty("active")
                .columnName("active")
                .notNull();
        business.addStringProperty("aboutTextColor")
                .columnName("about_text_color");
        business.addStringProperty("footerIconsColor")
                .columnName("footer_icons_color");
        business.addStringProperty("footerIconsBackground")
                .columnName("footer_icons_background");
        business.addStringProperty("realPhone")
                .columnName("realphone");
        business.addIntProperty("monthlySms")
                .columnName("monthlysms")
                .notNull();
        business.addStringProperty("chatIconColor")
                .columnName("chat_icon_color");
        business.addStringProperty("chatLabelColor")
                .columnName("chat_label_color");
        business.addStringProperty("chatLabel")
                .columnName("chat_label");
        business.addBooleanProperty("ifChat")
                .columnName("ifchat")
                .notNull();
        business.addIntProperty("test")
                .columnName("test")
                .notNull();


        /* Relations */

        blackList.addToOne(account, blockListAccountId);
        smsLog.addToOne(account, smsLogsAccountId);
    }
}
