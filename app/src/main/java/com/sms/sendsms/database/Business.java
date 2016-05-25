package com.sms.sendsms.database;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table business.
 */
public class Business {

    private Long id;
    private String storeCode;
    private String businessName;
    private String logo;
    /** Not-null value. */
    private String backgroundImage;
    private String backgroundColor;
    private String headerColor;
    private String footerColor;
    private boolean ifLogo;
    private boolean ifMail;
    private boolean ifFacebook;
    private boolean ifTwitter;
    private boolean ifLinkedin;
    private boolean ifGoogleplus;
    private boolean ifYoutube;
    private boolean ifPhone;
    private boolean ifGallery;
    private boolean ifAbout;
    private boolean ifWebsite;
    private boolean ifMap;
    private boolean ifPinterest;
    private boolean ifAndroid;
    private String mainText;
    private String mailIconColor;
    private String mailLabelColor;
    private String mailLabel;
    private String facebookIconColor;
    private String facebookLabelColor;
    private String facebookLabel;
    private String twitterIconColor;
    private String twitterLabelColor;
    private String twitterLabel;
    private String linkedinIconColor;
    private String linkedinLabelColor;
    private String linkedinLabel;
    private String googleplusIconColor;
    private String googleplusLabelColor;
    private String googleplusLabel;
    private String mailAddress;
    private String facebookAddress;
    private String twitterAddress;
    private String linkedinAddress;
    private String googleplusAddress;
    private String youtubeIconColor;
    private String youtubeLabelColor;
    private String youtubeLabel;
    private String youtubeAddress;
    private String phoneLabelColor;
    private String phoneIconColor;
    private String phoneLabel;
    private String phoneAddress;
    private String galleryIconColor;
    private String galleryLabelColor;
    private String galleryLabel;
    private String galleryAddress;
    private String aboutIconColor;
    private String aboutLabelColor;
    private String aboutLabel;
    private String aboutAddress;
    private String websiteIconColor;
    private String websiteLabelColor;
    private String websiteLabel;
    private String websiteAddress;
    private String mapIconColor;
    private String mapLabelColor;
    private String mapLabel;
    private String mapAddress;
    private String pinterestIconColor;
    private String pinterestLabelColor;
    private String pinterestLabel;
    private String pinterestAddress;
    private String androidIconColor;
    private String androidLabelColor;
    private String androidLabel;
    private String androidAddress;
    private boolean ifBusinessname;
    private String businessnameColor;
    private String maintextColor;
    private boolean ifFooter;
    private boolean ifHeader;
    private boolean ifUserplus;
    private String userplusIconColor;
    private String userplusLabelColor;
    private String userplusLabel;
    private String bitly;
    private String maskyoo;
    private boolean active;
    private String aboutTextColor;
    private String footerIconsColor;
    private String footerIconsBackground;
    private String realPhone;
    private int monthlySms;
    private String chatIconColor;
    private String chatLabelColor;
    private String chatLabel;
    private boolean ifChat;
    private int test;

    // KEEP FIELDS - put your custom fields here

    private String defaultColor = "#ffffff";
    // KEEP FIELDS END

    public Business() {
    }

    public Business(Long id) {
        this.id = id;
    }

    public Business(Long id, String storeCode, String businessName, String logo, String backgroundImage, String backgroundColor, String headerColor, String footerColor, boolean ifLogo, boolean ifMail, boolean ifFacebook, boolean ifTwitter, boolean ifLinkedin, boolean ifGoogleplus, boolean ifYoutube, boolean ifPhone, boolean ifGallery, boolean ifAbout, boolean ifWebsite, boolean ifMap, boolean ifPinterest, boolean ifAndroid, String mainText, String mailIconColor, String mailLabelColor, String mailLabel, String facebookIconColor, String facebookLabelColor, String facebookLabel, String twitterIconColor, String twitterLabelColor, String twitterLabel, String linkedinIconColor, String linkedinLabelColor, String linkedinLabel, String googleplusIconColor, String googleplusLabelColor, String googleplusLabel, String mailAddress, String facebookAddress, String twitterAddress, String linkedinAddress, String googleplusAddress, String youtubeIconColor, String youtubeLabelColor, String youtubeLabel, String youtubeAddress, String phoneLabelColor, String phoneIconColor, String phoneLabel, String phoneAddress, String galleryIconColor, String galleryLabelColor, String galleryLabel, String galleryAddress, String aboutIconColor, String aboutLabelColor, String aboutLabel, String aboutAddress, String websiteIconColor, String websiteLabelColor, String websiteLabel, String websiteAddress, String mapIconColor, String mapLabelColor, String mapLabel, String mapAddress, String pinterestIconColor, String pinterestLabelColor, String pinterestLabel, String pinterestAddress, String androidIconColor, String androidLabelColor, String androidLabel, String androidAddress, boolean ifBusinessname, String businessnameColor, String maintextColor, boolean ifFooter, boolean ifHeader, boolean ifUserplus, String userplusIconColor, String userplusLabelColor, String userplusLabel, String bitly, String maskyoo, boolean active, String aboutTextColor, String footerIconsColor, String footerIconsBackground, String realPhone, int monthlySms, String chatIconColor, String chatLabelColor, String chatLabel, boolean ifChat, int test) {
        this.id = id;
        this.storeCode = storeCode;
        this.businessName = businessName;
        this.logo = logo;
        this.backgroundImage = backgroundImage;
        this.backgroundColor = backgroundColor;
        this.headerColor = headerColor;
        this.footerColor = footerColor;
        this.ifLogo = ifLogo;
        this.ifMail = ifMail;
        this.ifFacebook = ifFacebook;
        this.ifTwitter = ifTwitter;
        this.ifLinkedin = ifLinkedin;
        this.ifGoogleplus = ifGoogleplus;
        this.ifYoutube = ifYoutube;
        this.ifPhone = ifPhone;
        this.ifGallery = ifGallery;
        this.ifAbout = ifAbout;
        this.ifWebsite = ifWebsite;
        this.ifMap = ifMap;
        this.ifPinterest = ifPinterest;
        this.ifAndroid = ifAndroid;
        this.mainText = mainText;
        this.mailIconColor = mailIconColor;
        this.mailLabelColor = mailLabelColor;
        this.mailLabel = mailLabel;
        this.facebookIconColor = facebookIconColor;
        this.facebookLabelColor = facebookLabelColor;
        this.facebookLabel = facebookLabel;
        this.twitterIconColor = twitterIconColor;
        this.twitterLabelColor = twitterLabelColor;
        this.twitterLabel = twitterLabel;
        this.linkedinIconColor = linkedinIconColor;
        this.linkedinLabelColor = linkedinLabelColor;
        this.linkedinLabel = linkedinLabel;
        this.googleplusIconColor = googleplusIconColor;
        this.googleplusLabelColor = googleplusLabelColor;
        this.googleplusLabel = googleplusLabel;
        this.mailAddress = mailAddress;
        this.facebookAddress = facebookAddress;
        this.twitterAddress = twitterAddress;
        this.linkedinAddress = linkedinAddress;
        this.googleplusAddress = googleplusAddress;
        this.youtubeIconColor = youtubeIconColor;
        this.youtubeLabelColor = youtubeLabelColor;
        this.youtubeLabel = youtubeLabel;
        this.youtubeAddress = youtubeAddress;
        this.phoneLabelColor = phoneLabelColor;
        this.phoneIconColor = phoneIconColor;
        this.phoneLabel = phoneLabel;
        this.phoneAddress = phoneAddress;
        this.galleryIconColor = galleryIconColor;
        this.galleryLabelColor = galleryLabelColor;
        this.galleryLabel = galleryLabel;
        this.galleryAddress = galleryAddress;
        this.aboutIconColor = aboutIconColor;
        this.aboutLabelColor = aboutLabelColor;
        this.aboutLabel = aboutLabel;
        this.aboutAddress = aboutAddress;
        this.websiteIconColor = websiteIconColor;
        this.websiteLabelColor = websiteLabelColor;
        this.websiteLabel = websiteLabel;
        this.websiteAddress = websiteAddress;
        this.mapIconColor = mapIconColor;
        this.mapLabelColor = mapLabelColor;
        this.mapLabel = mapLabel;
        this.mapAddress = mapAddress;
        this.pinterestIconColor = pinterestIconColor;
        this.pinterestLabelColor = pinterestLabelColor;
        this.pinterestLabel = pinterestLabel;
        this.pinterestAddress = pinterestAddress;
        this.androidIconColor = androidIconColor;
        this.androidLabelColor = androidLabelColor;
        this.androidLabel = androidLabel;
        this.androidAddress = androidAddress;
        this.ifBusinessname = ifBusinessname;
        this.businessnameColor = businessnameColor;
        this.maintextColor = maintextColor;
        this.ifFooter = ifFooter;
        this.ifHeader = ifHeader;
        this.ifUserplus = ifUserplus;
        this.userplusIconColor = userplusIconColor;
        this.userplusLabelColor = userplusLabelColor;
        this.userplusLabel = userplusLabel;
        this.bitly = bitly;
        this.maskyoo = maskyoo;
        this.active = active;
        this.aboutTextColor = aboutTextColor;
        this.footerIconsColor = footerIconsColor;
        this.footerIconsBackground = footerIconsBackground;
        this.realPhone = realPhone;
        this.monthlySms = monthlySms;
        this.chatIconColor = chatIconColor;
        this.chatLabelColor = chatLabelColor;
        this.chatLabel = chatLabel;
        this.ifChat = ifChat;
        this.test = test;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    /** Not-null value. */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public String getFooterColor() {
        return footerColor;
    }

    public void setFooterColor(String footerColor) {
        this.footerColor = footerColor;
    }

    public boolean getIfLogo() {
        return ifLogo;
    }

    public void setIfLogo(boolean ifLogo) {
        this.ifLogo = ifLogo;
    }

    public boolean getIfMail() {
        return ifMail;
    }

    public void setIfMail(boolean ifMail) {
        this.ifMail = ifMail;
    }

    public boolean getIfFacebook() {
        return ifFacebook;
    }

    public void setIfFacebook(boolean ifFacebook) {
        this.ifFacebook = ifFacebook;
    }

    public boolean getIfTwitter() {
        return ifTwitter;
    }

    public void setIfTwitter(boolean ifTwitter) {
        this.ifTwitter = ifTwitter;
    }

    public boolean getIfLinkedin() {
        return ifLinkedin;
    }

    public void setIfLinkedin(boolean ifLinkedin) {
        this.ifLinkedin = ifLinkedin;
    }

    public boolean getIfGoogleplus() {
        return ifGoogleplus;
    }

    public void setIfGoogleplus(boolean ifGoogleplus) {
        this.ifGoogleplus = ifGoogleplus;
    }

    public boolean getIfYoutube() {
        return ifYoutube;
    }

    public void setIfYoutube(boolean ifYoutube) {
        this.ifYoutube = ifYoutube;
    }

    public boolean getIfPhone() {
        return ifPhone;
    }

    public void setIfPhone(boolean ifPhone) {
        this.ifPhone = ifPhone;
    }

    public boolean getIfGallery() {
        return ifGallery;
    }

    public void setIfGallery(boolean ifGallery) {
        this.ifGallery = ifGallery;
    }

    public boolean getIfAbout() {
        return ifAbout;
    }

    public void setIfAbout(boolean ifAbout) {
        this.ifAbout = ifAbout;
    }

    public boolean getIfWebsite() {
        return ifWebsite;
    }

    public void setIfWebsite(boolean ifWebsite) {
        this.ifWebsite = ifWebsite;
    }

    public boolean getIfMap() {
        return ifMap;
    }

    public void setIfMap(boolean ifMap) {
        this.ifMap = ifMap;
    }

    public boolean getIfPinterest() {
        return ifPinterest;
    }

    public void setIfPinterest(boolean ifPinterest) {
        this.ifPinterest = ifPinterest;
    }

    public boolean getIfAndroid() {
        return ifAndroid;
    }

    public void setIfAndroid(boolean ifAndroid) {
        this.ifAndroid = ifAndroid;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getMailIconColor() {
        return mailIconColor;
    }

    public void setMailIconColor(String mailIconColor) {
        this.mailIconColor = mailIconColor;
    }

    public String getMailLabelColor() {
        return mailLabelColor;
    }

    public void setMailLabelColor(String mailLabelColor) {
        this.mailLabelColor = mailLabelColor;
    }

    public String getMailLabel() {
        return mailLabel;
    }

    public void setMailLabel(String mailLabel) {
        this.mailLabel = mailLabel;
    }

    public String getFacebookIconColor() {
        return facebookIconColor;
    }

    public void setFacebookIconColor(String facebookIconColor) {
        this.facebookIconColor = facebookIconColor;
    }

    public String getFacebookLabelColor() {
        return facebookLabelColor;
    }

    public void setFacebookLabelColor(String facebookLabelColor) {
        this.facebookLabelColor = facebookLabelColor;
    }

    public String getFacebookLabel() {
        return facebookLabel;
    }

    public void setFacebookLabel(String facebookLabel) {
        this.facebookLabel = facebookLabel;
    }

    public String getTwitterIconColor() {
        return twitterIconColor;
    }

    public void setTwitterIconColor(String twitterIconColor) {
        this.twitterIconColor = twitterIconColor;
    }

    public String getTwitterLabelColor() {
        return twitterLabelColor;
    }

    public void setTwitterLabelColor(String twitterLabelColor) {
        this.twitterLabelColor = twitterLabelColor;
    }

    public String getTwitterLabel() {
        return twitterLabel;
    }

    public void setTwitterLabel(String twitterLabel) {
        this.twitterLabel = twitterLabel;
    }

    public String getLinkedinIconColor() {
        return linkedinIconColor;
    }

    public void setLinkedinIconColor(String linkedinIconColor) {
        this.linkedinIconColor = linkedinIconColor;
    }

    public String getLinkedinLabelColor() {
        return linkedinLabelColor;
    }

    public void setLinkedinLabelColor(String linkedinLabelColor) {
        this.linkedinLabelColor = linkedinLabelColor;
    }

    public String getLinkedinLabel() {
        return linkedinLabel;
    }

    public void setLinkedinLabel(String linkedinLabel) {
        this.linkedinLabel = linkedinLabel;
    }

    public String getGoogleplusIconColor() {
        return googleplusIconColor;
    }

    public void setGoogleplusIconColor(String googleplusIconColor) {
        this.googleplusIconColor = googleplusIconColor;
    }

    public String getGoogleplusLabelColor() {
        return googleplusLabelColor;
    }

    public void setGoogleplusLabelColor(String googleplusLabelColor) {
        this.googleplusLabelColor = googleplusLabelColor;
    }

    public String getGoogleplusLabel() {
        return googleplusLabel;
    }

    public void setGoogleplusLabel(String googleplusLabel) {
        this.googleplusLabel = googleplusLabel;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getFacebookAddress() {
        return facebookAddress;
    }

    public void setFacebookAddress(String facebookAddress) {
        this.facebookAddress = facebookAddress;
    }

    public String getTwitterAddress() {
        return twitterAddress;
    }

    public void setTwitterAddress(String twitterAddress) {
        this.twitterAddress = twitterAddress;
    }

    public String getLinkedinAddress() {
        return linkedinAddress;
    }

    public void setLinkedinAddress(String linkedinAddress) {
        this.linkedinAddress = linkedinAddress;
    }

    public String getGoogleplusAddress() {
        return googleplusAddress;
    }

    public void setGoogleplusAddress(String googleplusAddress) {
        this.googleplusAddress = googleplusAddress;
    }

    public String getYoutubeIconColor() {
        return youtubeIconColor;
    }

    public void setYoutubeIconColor(String youtubeIconColor) {
        this.youtubeIconColor = youtubeIconColor;
    }

    public String getYoutubeLabelColor() {
        return youtubeLabelColor;
    }

    public void setYoutubeLabelColor(String youtubeLabelColor) {
        this.youtubeLabelColor = youtubeLabelColor;
    }

    public String getYoutubeLabel() {
        return youtubeLabel;
    }

    public void setYoutubeLabel(String youtubeLabel) {
        this.youtubeLabel = youtubeLabel;
    }

    public String getYoutubeAddress() {
        return youtubeAddress;
    }

    public void setYoutubeAddress(String youtubeAddress) {
        this.youtubeAddress = youtubeAddress;
    }

    public String getPhoneLabelColor() {
        return phoneLabelColor;
    }

    public void setPhoneLabelColor(String phoneLabelColor) {
        this.phoneLabelColor = phoneLabelColor;
    }

    public String getPhoneIconColor() {
        return phoneIconColor;
    }

    public void setPhoneIconColor(String phoneIconColor) {
        this.phoneIconColor = phoneIconColor;
    }

    public String getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(String phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public String getPhoneAddress() {
        return phoneAddress;
    }

    public void setPhoneAddress(String phoneAddress) {
        this.phoneAddress = phoneAddress;
    }

    public String getGalleryIconColor() {
        return galleryIconColor;
    }

    public void setGalleryIconColor(String galleryIconColor) {
        this.galleryIconColor = galleryIconColor;
    }

    public String getGalleryLabelColor() {
        return galleryLabelColor;
    }

    public void setGalleryLabelColor(String galleryLabelColor) {
        this.galleryLabelColor = galleryLabelColor;
    }

    public String getGalleryLabel() {
        return galleryLabel;
    }

    public void setGalleryLabel(String galleryLabel) {
        this.galleryLabel = galleryLabel;
    }

    public String getGalleryAddress() {
        return galleryAddress;
    }

    public void setGalleryAddress(String galleryAddress) {
        this.galleryAddress = galleryAddress;
    }

    public String getAboutIconColor() {
        return aboutIconColor;
    }

    public void setAboutIconColor(String aboutIconColor) {
        this.aboutIconColor = aboutIconColor;
    }

    public String getAboutLabelColor() {
        return aboutLabelColor;
    }

    public void setAboutLabelColor(String aboutLabelColor) {
        this.aboutLabelColor = aboutLabelColor;
    }

    public String getAboutLabel() {
        return aboutLabel;
    }

    public void setAboutLabel(String aboutLabel) {
        this.aboutLabel = aboutLabel;
    }

    public String getAboutAddress() {
        return aboutAddress;
    }

    public void setAboutAddress(String aboutAddress) {
        this.aboutAddress = aboutAddress;
    }

    public String getWebsiteIconColor() {
        return websiteIconColor;
    }

    public void setWebsiteIconColor(String websiteIconColor) {
        this.websiteIconColor = websiteIconColor;
    }

    public String getWebsiteLabelColor() {
        return websiteLabelColor;
    }

    public void setWebsiteLabelColor(String websiteLabelColor) {
        this.websiteLabelColor = websiteLabelColor;
    }

    public String getWebsiteLabel() {
        return websiteLabel;
    }

    public void setWebsiteLabel(String websiteLabel) {
        this.websiteLabel = websiteLabel;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public String getMapIconColor() {
        return mapIconColor;
    }

    public void setMapIconColor(String mapIconColor) {
        this.mapIconColor = mapIconColor;
    }

    public String getMapLabelColor() {
        return mapLabelColor;
    }

    public void setMapLabelColor(String mapLabelColor) {
        this.mapLabelColor = mapLabelColor;
    }

    public String getMapLabel() {
        return mapLabel;
    }

    public void setMapLabel(String mapLabel) {
        this.mapLabel = mapLabel;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public String getPinterestIconColor() {
        return pinterestIconColor;
    }

    public void setPinterestIconColor(String pinterestIconColor) {
        this.pinterestIconColor = pinterestIconColor;
    }

    public String getPinterestLabelColor() {
        return pinterestLabelColor;
    }

    public void setPinterestLabelColor(String pinterestLabelColor) {
        this.pinterestLabelColor = pinterestLabelColor;
    }

    public String getPinterestLabel() {
        return pinterestLabel;
    }

    public void setPinterestLabel(String pinterestLabel) {
        this.pinterestLabel = pinterestLabel;
    }

    public String getPinterestAddress() {
        return pinterestAddress;
    }

    public void setPinterestAddress(String pinterestAddress) {
        this.pinterestAddress = pinterestAddress;
    }

    public String getAndroidIconColor() {
        return androidIconColor;
    }

    public void setAndroidIconColor(String androidIconColor) {
        this.androidIconColor = androidIconColor;
    }

    public String getAndroidLabelColor() {
        return androidLabelColor;
    }

    public void setAndroidLabelColor(String androidLabelColor) {
        this.androidLabelColor = androidLabelColor;
    }

    public String getAndroidLabel() {
        return androidLabel;
    }

    public void setAndroidLabel(String androidLabel) {
        this.androidLabel = androidLabel;
    }

    public String getAndroidAddress() {
        return androidAddress;
    }

    public void setAndroidAddress(String androidAddress) {
        this.androidAddress = androidAddress;
    }

    public boolean getIfBusinessname() {
        return ifBusinessname;
    }

    public void setIfBusinessname(boolean ifBusinessname) {
        this.ifBusinessname = ifBusinessname;
    }

    public String getBusinessnameColor() {
        return businessnameColor;
    }

    public void setBusinessnameColor(String businessnameColor) {
        this.businessnameColor = businessnameColor;
    }

    public String getMaintextColor() {
        return maintextColor;
    }

    public void setMaintextColor(String maintextColor) {
        this.maintextColor = maintextColor;
    }

    public boolean getIfFooter() {
        return ifFooter;
    }

    public void setIfFooter(boolean ifFooter) {
        this.ifFooter = ifFooter;
    }

    public boolean getIfHeader() {
        return ifHeader;
    }

    public void setIfHeader(boolean ifHeader) {
        this.ifHeader = ifHeader;
    }

    public boolean getIfUserplus() {
        return ifUserplus;
    }

    public void setIfUserplus(boolean ifUserplus) {
        this.ifUserplus = ifUserplus;
    }

    public String getUserplusIconColor() {
        return userplusIconColor;
    }

    public void setUserplusIconColor(String userplusIconColor) {
        this.userplusIconColor = userplusIconColor;
    }

    public String getUserplusLabelColor() {
        return userplusLabelColor;
    }

    public void setUserplusLabelColor(String userplusLabelColor) {
        this.userplusLabelColor = userplusLabelColor;
    }

    public String getUserplusLabel() {
        return userplusLabel;
    }

    public void setUserplusLabel(String userplusLabel) {
        this.userplusLabel = userplusLabel;
    }

    public String getBitly() {
        return bitly;
    }

    public void setBitly(String bitly) {
        this.bitly = bitly;
    }

    public String getMaskyoo() {
        return maskyoo;
    }

    public void setMaskyoo(String maskyoo) {
        this.maskyoo = maskyoo;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAboutTextColor() {
        return aboutTextColor;
    }

    public void setAboutTextColor(String aboutTextColor) {
        this.aboutTextColor = aboutTextColor;
    }

    public String getFooterIconsColor() {
        return footerIconsColor;
    }

    public void setFooterIconsColor(String footerIconsColor) {
        this.footerIconsColor = footerIconsColor;
    }

    public String getFooterIconsBackground() {
        return footerIconsBackground;
    }

    public void setFooterIconsBackground(String footerIconsBackground) {
        this.footerIconsBackground = footerIconsBackground;
    }

    public String getRealPhone() {
        return realPhone;
    }

    public void setRealPhone(String realPhone) {
        this.realPhone = realPhone;
    }

    public int getMonthlySms() {
        return monthlySms;
    }

    public void setMonthlySms(int monthlySms) {
        this.monthlySms = monthlySms;
    }

    public String getChatIconColor() {
        return chatIconColor;
    }

    public void setChatIconColor(String chatIconColor) {
        this.chatIconColor = chatIconColor;
    }

    public String getChatLabelColor() {
        return chatLabelColor;
    }

    public void setChatLabelColor(String chatLabelColor) {
        this.chatLabelColor = chatLabelColor;
    }

    public String getChatLabel() {
        return chatLabel;
    }

    public void setChatLabel(String chatLabel) {
        this.chatLabel = chatLabel;
    }

    public boolean getIfChat() {
        return ifChat;
    }

    public void setIfChat(boolean ifChat) {
        this.ifChat = ifChat;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
