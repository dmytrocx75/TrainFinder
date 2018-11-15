import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.util.*;

public class Station {

   private String name=null;
   private int code=0;
 private  static ObjectMapper mapper=new ObjectMapper();

     static public Station[] getStations (String key) throws Exception{
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
 //   String stationjson="[{\"title\":\"Lviv\",\"region\":null,\"value\":2218000},{\"title\":\"Lvivski Otruby\",\"region\":\" \",\"value\":2208311},{\"title\":\"Cherkasy Lvivski\",\"region\":null,\"value\":2218024},{\"title\":\"Dublyany-Lvivski\",\"region\":null,\"value\":2219059},{\"title\":\"Horodok Lvivsky\",\"region\":null,\"value\":2218340},{\"title\":\"Zatoka(Lviv.obl)\",\"region\":\" \",\"value\":2218370}]";
String URL="https://booking.uz.gov.ua/en/train_search/station/";
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("term",key);

        JSONArray stationjson=Sender.send(URL,parameters).getArray();

       Station[]stations=mapper.readValue(stationjson.toString(),Station[].class);


        return stations;
    }

    //region Constructors, setters, overrides
    Station(){}
    Station(Integer code, String name) {
        this.name = name;
        this.code = code;
    }

    public void setTitle(String name) {
        this.name = name;
    }
    public void setValue(Integer code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
      //  if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return code == station.code &&
                Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, code);
    }

    @Override
    public String toString() {
        return this.code+this.name;
    }
    //endregion


}
