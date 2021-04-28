package com.example.tasks;

public class RunningItem {
    String shopname;
    String shoptype;
    String shopaddress;
    String shopratings;
    String shopimages;
    String shopcode;
    String tags;

    public RunningItem() {
    }

    public RunningItem(String shopname, String shoptype, String shopaddress, String shopratings, String shopimages, String shopcode, String tags) {
        this.shopname = shopname;
        this.shoptype = shoptype;
        this.shopaddress = shopaddress;
        this.shopratings = shopratings;
        this.shopimages = shopimages;
        this.shopcode = shopcode;
        this.tags = tags;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShoptype() {
        return shoptype;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public String getShopcode() {
        return shopcode;
    }

    public void setShopcode(String shopcode) {
        this.shopcode = shopcode;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getShopratings() {
        return shopratings;
    }

    public void setShopratings(String shopratings) {
        this.shopratings = shopratings;
    }

    public String getShopimages() {
        return shopimages;
    }

    public void setShopimages(String shopimages) {
        this.shopimages = shopimages;
    }
}
