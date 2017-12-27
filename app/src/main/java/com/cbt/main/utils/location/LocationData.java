package com.cbt.main.utils.location;

import java.io.Serializable;

/**
 * @author fancw
 * @date 2014-5-20
 */
public class LocationData implements Serializable {

    public double lat;
    public double lon;
    public String city;
    public String province;
    public String addr;
    public int errorcode;

}
