package com.cbnu.josimair.Model.util;

import com.cbnu.josimair.Model.entity.OutdoorAir;
import com.cbnu.josimair.Model.service.RestAPIService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonParser {
    String json;

    public JsonParser(String json) {
        this.json = json;
    }

    /**
     * jObject로 부터 이름이 name 인 속성의 값을 가져온다
     *
     * @param jObject json Object
     * @param name attribute name
     */
    public float getValue(JSONObject jObject, String name){
        try {
            return (float)jObject.getDouble(name);
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * jObject로 부터 이름이 name 인 속성의 값을 가져온다
     *
     * @param jObject json Object
     * @param name attribute name
     */
    public int getGrade(JSONObject jObject, String name){
        try {
            return jObject.getInt(name);
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * json 데이터에서 외부 대기 정보를 추출한다
     *
     * @param stationName attribute name
     */
    public OutdoorAir getOutdoorAir(String stationName){
        float value = -1;
        int quality = -1;

        OutdoorAir outdoorAir = new OutdoorAir(stationName);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jarray = jsonObject.getJSONArray("list");
            JSONObject jObject = jarray.getJSONObject(0);

            try {
                Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(jObject.getString("dataTime"));
                outdoorAir.setDataTime(time);
            }
            catch(Exception e){ }

            outdoorAir.setMangName(jObject.getString("mangName"));
            outdoorAir.setSo2Value(getValue(jObject,"so2Value"));
            outdoorAir.setCoValue(getValue(jObject,"coValue"));
            outdoorAir.setO3Value(getValue(jObject,"o3Value"));
            outdoorAir.setNo2Value(getValue(jObject,"no2Value"));

            outdoorAir.setSo2Grade(getGrade(jObject,"so2Grade"));
            outdoorAir.setCoGrade(getGrade(jObject,"coGrade"));
            outdoorAir.setO3Grade(getGrade(jObject,"o3Grade"));
            outdoorAir.setNo2Grade(getGrade(jObject,"no2Grade"));

            outdoorAir.setPm10Value(getValue(jObject,"pm10Value"));
            outdoorAir.setPm25Value(getValue(jObject,"pm25Value"));
            outdoorAir.setPm10Value24(getValue(jObject,"pm10Value24"));
            outdoorAir.setPm25Value24(getValue(jObject,"pm25Value24"));

            outdoorAir.setPm10Grade(getGrade(jObject,"pm10Grade"));
            outdoorAir.setPm10Grade(getGrade(jObject,"pm25Grade"));
            outdoorAir.setPm25Grade(getGrade(jObject,"pm10Grade1h"));
            outdoorAir.setPm25Grade(getGrade(jObject,"pm25Grade1h"));

            outdoorAir.setKhaiValue(getValue(jObject,"khaiValue"));
            outdoorAir.setKhaiGrade(getGrade(jObject,"khaiGrade"));

            return outdoorAir;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * json 데이터에서 TMLocation 을 추출한다
     */
    public RestAPIService.TMLocation getTMLocation(){
        Double x = 0.0, y = 0.0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jarray = jsonObject.getJSONArray("documents");
            for(int i=0; i < jarray.length(); i++){
                JSONObject jObject = jarray.getJSONObject(i);
                x = Double.parseDouble(jObject.getString("x"));
                y = Double.parseDouble(jObject.getString("y"));
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if(x==0)
                return null;
            else
                return new RestAPIService.TMLocation(x,y);
        }
    }

    /**
     * json 데이터에서 최근접 관측소의 이름을 추출한다
     */
    public String getNearByStation(){
        String stationName=null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jarray = jsonObject.getJSONArray("list");
            for(int i=0; i < jarray.length(); i++){
                JSONObject jObject = jarray.getJSONObject(i);
                stationName = jObject.getString("stationName");
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            return stationName;
        }
    }
}
