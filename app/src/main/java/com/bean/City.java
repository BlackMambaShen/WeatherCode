package com.bean;

import java.io.Serializable;
import java.util.List;


public class City implements Serializable{
        public int _id;
        public int id;
        public int pid;
        public String city_code;
        public String city_name;

        @Override
        public String toString() {
                return "City{" +
                        "_id=" + _id +
                        ", id=" + id +
                        ", pid=" + pid +
                        ", city_code='" + city_code + '\'' +
                        ", city_name='" + city_name + '\'' +
                        '}';
        }
}
