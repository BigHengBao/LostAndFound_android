package com.heng.lostandfound.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HengZhang
 * @date : 2022/3/25 16:03
 */

@NoArgsConstructor
@Data
public class WeatherInfo {
    @SerializedName("cityid")
    private String cityid;
    private String date;
    private String week;
    @SerializedName("update_time")
    private String updateTime;
    @SerializedName("city")
    private String city;
    private String cityEn;
    @SerializedName("country")
    private String country;
    private String countryEn;
    private String wea;
    private String weaImg;
    private String tem;
    private String tem1;
    private String tem2;
    private String win;
    private String winSpeed;
    private String winMeter;
    private String humidity;
    private String visibility;
    private String pressure;
    private String air;
    @SerializedName("air_pm25")
    private String airPm25;
    @SerializedName("air_level")
    private String airLevel;
    @SerializedName("air_tips")
    private String airTips;
    private AlarmDTO alarm;
    private String winSpeedDay;
    private String winSpeedNight;
    private AqiDTO aqi;

    @NoArgsConstructor
    @Data
    public static class AlarmDTO {
        @SerializedName("alarm_type")
        private String alarmType;
        @SerializedName("alarm_level")
        private String alarmLevel;
        @SerializedName("alarm_title")
        private String alarmTitle;
        @SerializedName("alarm_content")
        private String alarmContent;

        public String getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(String alarmType) {
            this.alarmType = alarmType;
        }

        public String getAlarmLevel() {
            return alarmLevel;
        }

        public void setAlarmLevel(String alarmLevel) {
            this.alarmLevel = alarmLevel;
        }

        public String getAlarmTitle() {
            return alarmTitle;
        }

        public void setAlarmTitle(String alarmTitle) {
            this.alarmTitle = alarmTitle;
        }

        public String getAlarmContent() {
            return alarmContent;
        }

        public void setAlarmContent(String alarmContent) {
            this.alarmContent = alarmContent;
        }
    }

    @NoArgsConstructor
    @Data
    public static class AqiDTO {
        private String updateTime;
        private String cityid;
        private String city;
        private String cityEn;
        private String country;
        private String countryEn;
        private String air;
        private String airLevel;
        private String airTips;
        private String pm25;
        private String pm25Desc;
        private String pm10;
        private String pm10Desc;
        private String o3;
        private String o3Desc;
        private String no2;
        private String no2Desc;
        private String so2;
        private String so2Desc;
        private String co;
        private String coDesc;
        private String kouzhao;
        private String yundong;
        private String waichu;
        private String kaichuang;
        private String jinghuaqi;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityEn() {
            return cityEn;
        }

        public void setCityEn(String cityEn) {
            this.cityEn = cityEn;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountryEn() {
            return countryEn;
        }

        public void setCountryEn(String countryEn) {
            this.countryEn = countryEn;
        }

        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getAirLevel() {
            return airLevel;
        }

        public void setAirLevel(String airLevel) {
            this.airLevel = airLevel;
        }

        public String getAirTips() {
            return airTips;
        }

        public void setAirTips(String airTips) {
            this.airTips = airTips;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm25Desc() {
            return pm25Desc;
        }

        public void setPm25Desc(String pm25Desc) {
            this.pm25Desc = pm25Desc;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm10Desc() {
            return pm10Desc;
        }

        public void setPm10Desc(String pm10Desc) {
            this.pm10Desc = pm10Desc;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getO3Desc() {
            return o3Desc;
        }

        public void setO3Desc(String o3Desc) {
            this.o3Desc = o3Desc;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getNo2Desc() {
            return no2Desc;
        }

        public void setNo2Desc(String no2Desc) {
            this.no2Desc = no2Desc;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getSo2Desc() {
            return so2Desc;
        }

        public void setSo2Desc(String so2Desc) {
            this.so2Desc = so2Desc;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getCoDesc() {
            return coDesc;
        }

        public void setCoDesc(String coDesc) {
            this.coDesc = coDesc;
        }

        public String getKouzhao() {
            return kouzhao;
        }

        public void setKouzhao(String kouzhao) {
            this.kouzhao = kouzhao;
        }

        public String getYundong() {
            return yundong;
        }

        public void setYundong(String yundong) {
            this.yundong = yundong;
        }

        public String getWaichu() {
            return waichu;
        }

        public void setWaichu(String waichu) {
            this.waichu = waichu;
        }

        public String getKaichuang() {
            return kaichuang;
        }

        public void setKaichuang(String kaichuang) {
            this.kaichuang = kaichuang;
        }

        public String getJinghuaqi() {
            return jinghuaqi;
        }

        public void setJinghuaqi(String jinghuaqi) {
            this.jinghuaqi = jinghuaqi;
        }
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWeaImg() {
        return weaImg;
    }

    public void setWeaImg(String weaImg) {
        this.weaImg = weaImg;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWinSpeed() {
        return winSpeed;
    }

    public void setWinSpeed(String winSpeed) {
        this.winSpeed = winSpeed;
    }

    public String getWinMeter() {
        return winMeter;
    }

    public void setWinMeter(String winMeter) {
        this.winMeter = winMeter;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getAirPm25() {
        return airPm25;
    }

    public void setAirPm25(String airPm25) {
        this.airPm25 = airPm25;
    }

    public String getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(String airLevel) {
        this.airLevel = airLevel;
    }

    public String getAirTips() {
        return airTips;
    }

    public void setAirTips(String airTips) {
        this.airTips = airTips;
    }

    public AlarmDTO getAlarm() {
        return alarm;
    }

    public void setAlarm(AlarmDTO alarm) {
        this.alarm = alarm;
    }

    public String getWinSpeedDay() {
        return winSpeedDay;
    }

    public void setWinSpeedDay(String winSpeedDay) {
        this.winSpeedDay = winSpeedDay;
    }

    public String getWinSpeedNight() {
        return winSpeedNight;
    }

    public void setWinSpeedNight(String winSpeedNight) {
        this.winSpeedNight = winSpeedNight;
    }

    public AqiDTO getAqi() {
        return aqi;
    }

    public void setAqi(AqiDTO aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "cityid='" + cityid + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", city='" + city + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", country='" + country + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", wea='" + wea + '\'' +
                ", weaImg='" + weaImg + '\'' +
                ", tem='" + tem + '\'' +
                ", tem1='" + tem1 + '\'' +
                ", tem2='" + tem2 + '\'' +
                ", win='" + win + '\'' +
                ", winSpeed='" + winSpeed + '\'' +
                ", winMeter='" + winMeter + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", air='" + air + '\'' +
                ", airPm25='" + airPm25 + '\'' +
                ", airLevel='" + airLevel + '\'' +
                ", airTips='" + airTips + '\'' +
                ", alarm=" + alarm +
                ", winSpeedDay='" + winSpeedDay + '\'' +
                ", winSpeedNight='" + winSpeedNight + '\'' +
                ", aqi=" + aqi +
                '}';
    }
}
