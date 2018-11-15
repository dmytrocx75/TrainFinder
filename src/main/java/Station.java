import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Station implements Comparable {

   private String name=null;
   private int code=0;
/*
    Station(String name, int code) {
        this.name = name;
        this.code = code;
    }
*/
    public void setTitle(String name) {
        this.name = name;
    }
    public void setValue(int code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }

    static public Set<Station> getStations(String input){

    String stationjson="[{\"title\":\"Lviv\",\"region\":null,\"value\":2218000},{\"title\":\"Lvivski Otruby\",\"region\":\" \",\"value\":2208311},{\"title\":\"Cherkasy Lvivski\",\"region\":null,\"value\":2218024},{\"title\":\"Dublyany-Lvivski\",\"region\":null,\"value\":2219059},{\"title\":\"Horodok Lvivsky\",\"region\":null,\"value\":2218340},{\"title\":\"Zatoka(Lviv.obl)\",\"region\":\" \",\"value\":2218370}]";
     //  Set<String> stations = new TreeSet<String>();

       Station[]stations=null;
       try {
          ObjectMapper mapper =new ObjectMapper();
           mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           stations=mapper.readValue(stationjson,Station[].class);
       }catch (IOException e){
           System.out.print(e);
       }

        return new TreeSet<Station>(Arrays.asList(stations));
    }

    @Override
    public boolean equals(Object o) {
        return this.name.equals(((Station)o).name);
    }

    public int compareTo(Object o) {
        return this.name.compareTo(((Station)o).name);

    }


}
