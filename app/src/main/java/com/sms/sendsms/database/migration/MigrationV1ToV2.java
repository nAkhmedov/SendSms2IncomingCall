package com.sms.sendsms.database.migration;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MigrationV1ToV2 extends MigrationImpl {

    @Override
    public int applyMigration(SQLiteDatabase db, int version) {
        prepareMigration(db, version);
        db.beginTransaction();
        String constraint = "IF NOT EXISTS ";
        db.execSQL("CREATE TABLE " + constraint + "'business' (" + //
                "'id' INTEGER PRIMARY KEY ," + // 0: id
                "'storecode' TEXT," + // 1: storeCode
                "'businessname' TEXT," + // 2: businessName
                "'logo' TEXT," + // 3: logo
                "'backgroundimage' TEXT NOT NULL ," + // 4: backgroundImage
                "'backgroundcolor' TEXT," + // 5: backgroundColor
                "'headercolor' TEXT," + // 6: headerColor
                "'footercolor' TEXT," + // 7: footerColor
                "'iflogo' INTEGER NOT NULL ," + // 8: ifLogo
                "'ifmail' INTEGER NOT NULL ," + // 9: ifMail
                "'iffacebook' INTEGER NOT NULL ," + // 10: ifFacebook
                "'iftwitter' INTEGER NOT NULL ," + // 11: ifTwitter
                "'iflinkedin' INTEGER NOT NULL ," + // 12: ifLinkedin
                "'ifgoogleplus' INTEGER NOT NULL ," + // 13: ifGoogleplus
                "'ifyoutube' INTEGER NOT NULL ," + // 14: ifYoutube
                "'ifphone' INTEGER NOT NULL ," + // 15: ifPhone
                "'ifgallery' INTEGER NOT NULL ," + // 16: ifGallery
                "'ifabout' INTEGER NOT NULL ," + // 17: ifAbout
                "'ifwebsite' INTEGER NOT NULL ," + // 18: ifWebsite
                "'ifmap' INTEGER NOT NULL ," + // 19: ifMap
                "'ifpinterest' INTEGER NOT NULL ," + // 20: ifPinterest
                "'ifandroid' INTEGER NOT NULL ," + // 21: ifAndroid
                "'maintext' TEXT," + // 22: mainText
                "'mail_icon_color' TEXT," + // 23: mailIconColor
                "'mail_label_color' TEXT," + // 24: mailLabelColor
                "'mail_label' TEXT," + // 25: mailLabel
                "'facebook_icon_color' TEXT," + // 26: facebookIconColor
                "'facebook_label_color' TEXT," + // 27: facebookLabelColor
                "'facebook_label' TEXT," + // 28: facebookLabel
                "'twitter_icon_color' TEXT," + // 29: twitterIconColor
                "'twitter_label_color' TEXT," + // 30: twitterLabelColor
                "'twitter_label' TEXT," + // 31: twitterLabel
                "'linkedin_icon_color' TEXT," + // 32: linkedinIconColor
                "'linkedin_label_color' TEXT," + // 33: linkedinLabelColor
                "'linkedin_label' TEXT," + // 34: linkedinLabel
                "'googleplus_icon_color' TEXT," + // 35: googleplusIconColor
                "'googleplus_label_color' TEXT," + // 36: googleplusLabelColor
                "'googleplus_label' TEXT," + // 37: googleplusLabel
                "'mail_address' TEXT," + // 38: mailAddress
                "'facebook_address' TEXT," + // 39: facebookAddress
                "'twitter_address' TEXT," + // 40: twitterAddress
                "'linkedin_address' TEXT," + // 41: linkedinAddress
                "'googleplus_address' TEXT," + // 42: googleplusAddress
                "'youtube_icon_color' TEXT," + // 43: youtubeIconColor
                "'youtube_label_color' TEXT," + // 44: youtubeLabelColor
                "'youtube_label' TEXT," + // 45: youtubeLabel
                "'youtube_address' TEXT," + // 46: youtubeAddress
                "'phone_label_color' TEXT," + // 47: phoneLabelColor
                "'phone_icon_color' TEXT," + // 48: phoneIconColor
                "'phone_label' TEXT," + // 49: phoneLabel
                "'phone_address' TEXT," + // 50: phoneAddress
                "'gallery_icon_color' TEXT," + // 51: galleryIconColor
                "'gallery_label_color' TEXT," + // 52: galleryLabelColor
                "'gallery_label' TEXT," + // 53: galleryLabel
                "'gallery_address' TEXT," + // 54: galleryAddress
                "'about_icon_color' TEXT," + // 55: aboutIconColor
                "'about_label_color' TEXT," + // 56: aboutLabelColor
                "'about_label' TEXT," + // 57: aboutLabel
                "'about_address' TEXT," + // 58: aboutAddress
                "'website_icon_color' TEXT," + // 59: websiteIconColor
                "'website_label_color' TEXT," + // 60: websiteLabelColor
                "'website_label' TEXT," + // 61: websiteLabel
                "'website_address' TEXT," + // 62: websiteAddress
                "'map_icon_color' TEXT," + // 63: mapIconColor
                "'map_label_color' TEXT," + // 64: mapLabelColor
                "'map_label' TEXT," + // 65: mapLabel
                "'map_address' TEXT," + // 66: mapAddress
                "'pinterest_icon_color' TEXT," + // 67: pinterestIconColor
                "'pinterest_label_color' TEXT," + // 68: pinterestLabelColor
                "'pinterest_label' TEXT," + // 69: pinterestLabel
                "'pinterest_address' TEXT," + // 70: pinterestAddress
                "'android_icon_color' TEXT," + // 71: androidIconColor
                "'android_label_color' TEXT," + // 72: androidLabelColor
                "'android_label' TEXT," + // 73: androidLabel
                "'android_address' TEXT," + // 74: androidAddress
                "'ifbusinessname' INTEGER NOT NULL ," + // 75: ifBusinessname
                "'businessname_color' TEXT," + // 76: businessnameColor
                "'maintext_color' TEXT," + // 77: maintextColor
                "'iffooter' INTEGER NOT NULL ," + // 78: ifFooter
                "'ifheader' INTEGER NOT NULL ," + // 79: ifHeader
                "'ifuserplus' INTEGER NOT NULL ," + // 80: ifUserplus
                "'userplus_icon_color' TEXT," + // 81: userplusIconColor
                "'userplus_label_color' TEXT," + // 82: userplusLabelColor
                "'userplus_label' TEXT," + // 83: userplusLabel
                "'bitly' TEXT," + // 84: bitly
                "'maskyoo' TEXT," + // 85: maskyoo
                "'active' INTEGER NOT NULL ," + // 86: active
                "'about_text_color' TEXT," + // 87: aboutTextColor
                "'footer_icons_color' TEXT," + // 88: footerIconsColor
                "'footer_icons_background' TEXT," + // 89: footerIconsBackground
                "'realphone' TEXT," + // 90: realPhone
                "'monthlysms' INTEGER NOT NULL ," + // 91: monthlySms
                "'chat_icon_color' TEXT," + // 92: chatIconColor
                "'chat_label_color' TEXT," + // 93: chatLabelColor
                "'chat_label' TEXT," + // 94: chatLabel
                "'ifchat' INTEGER NOT NULL ," + // 95: ifChat
                "'test' INTEGER NOT NULL );"); // 96: test

        Log.i("CHECKING", "EXECUTED VERSION 2");

        db.execSQL("ALTER TABLE user ADD COLUMN guid TEXT NOT NULL;");

        db.setTransactionSuccessful();
        db.endTransaction();

        return getMigratedVersion();
    }

    @Override
    public Migration getPreviousMigration() {
        return null;
    }

    @Override
    public int getTargetVersion() {
        return 1;
    }

    @Override
    public int getMigratedVersion() {
        return 2;
    }
}
