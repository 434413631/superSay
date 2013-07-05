/*
 * message
 *
 * Version 1.0.0
 *
 * 2013-07-03
 *
 * Copyright (c) 2012, T-Systems.
 * All rights reserved.
 */
package android.view.entities;

/**
 * Declaration.
 *
 * @author jian.wang@t-systems.com
 */
public class message {

    private String imgurl;
    private String saleurl;
    private String oldprice;
    private String newprice;

    public message(String imgurl, String saleurl, String oldprice, String newprice) {
        this.imgurl = imgurl;
        this.saleurl = saleurl;
        this.oldprice = oldprice;
        this.newprice = newprice;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getSaleurl() {
        return saleurl;
    }

    public void setSaleurl(String saleurl) {
        this.saleurl = saleurl;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }
}
