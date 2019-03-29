package edu.ecnu.scsse.pizza.consumer.server.utils;

import lombok.Data;

import java.util.List;

@Data
public class AmapLocation {
	private Integer status;
    private String info;
    private String infocode;
    private Integer count;

    private List<Geocode> geocodes;

    public static class Geocode {
        private String formattedAddress;
        private String country;
        private String province;
        private String citycode;
        private String city;
        private String district;
        private String location;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
