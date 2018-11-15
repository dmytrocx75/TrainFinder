import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.openunirest.http.JsonNode;
import org.json.JSONArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Train {

   private String number=null;
private SeatType[] seats=null;

   private static ObjectMapper mapper  =new ObjectMapper();

  static public Train[] getTrains(String from, String to,String date, String time)throws IOException{

      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//String trainjson="{\"data\":{\"list\":[{\"num\":\"050Л\",\"category\":0,\"isTransformer\":0,\"travelTime\":\"3:33\",\"from\":{\"code\":\"2218000\",\"station\":\"Lviv\",\"stationTrain\":\"Truskavets\",\"date\":\"monday, 11.12.2018\",\"time\":\"11:08 pm\",\"sortTime\":1542056880,\"srcDate\":\"2018-11-12\"},\"to\":{\"code\":\"2200300\",\"station\":\"Khmelnytsky\",\"stationTrain\":\"Kyiv-Pasazhyrsky\",\"date\":\"tuesday, 11.13.2018\",\"time\":\"02:41 am\",\"sortTime\":1542069660},\"types\":[{\"id\":\"Л\",\"title\":\"De Luxe \\/ 1-cl. sleeper\",\"letter\":\"L\",\"seats\":11},{\"id\":\"К\",\"title\":\"Compartment \\/ 2-cl. sleeper\",\"letter\":\"C\",\"seats\":99}],\"child\":{\"minDate\":\"2004-11-13\",\"maxDate\":\"2018-11-12\"},\"allowStudent\":1,\"allowBooking\":1,\"isCis\":0,\"isEurope\":0,\"allowPrivilege\":0,\"noReserve\":1},{\"num\":\"035Ш\",\"category\":0,\"isTransformer\":0,\"travelTime\":\"4:01\",\"from\":{\"code\":\"2218000\",\"station\":\"Lviv\",\"stationTrain\":\"Przemysl\",\"date\":\"monday, 11.12.2018\",\"time\":\"11:28 pm\",\"sortTime\":1542058080,\"srcDate\":\"2018-11-12\"},\"to\":{\"code\":\"2200300\",\"station\":\"Khmelnytsky\",\"stationTrain\":\"Odesa-Holovna\",\"date\":\"tuesday, 11.13.2018\",\"time\":\"03:29 am\",\"sortTime\":1542072540},\"types\":[],\"child\":{\"minDate\":\"2004-11-13\",\"maxDate\":\"2018-11-12\"},\"allowStudent\":1,\"allowBooking\":1,\"isCis\":0,\"isEurope\":0,\"allowPrivilege\":0,\"noReserve\":1},{\"num\":\"026К\",\"category\":0,\"isTransformer\":0,\"travelTime\":\"4:01\",\"from\":{\"code\":\"2218000\",\"station\":\"Lviv\",\"stationTrain\":\"Rakhiv\",\"date\":\"monday, 11.12.2018\",\"time\":\"11:28 pm\",\"sortTime\":1542058080,\"srcDate\":\"2018-11-12\"},\"to\":{\"code\":\"2200300\",\"station\":\"Khmelnytsky\",\"stationTrain\":\"Odesa-Holovna\",\"date\":\"tuesday, 11.13.2018\",\"time\":\"03:29 am\",\"sortTime\":1542072540},\"types\":[{\"id\":\"К\",\"title\":\"Compartment \\/ 2-cl. sleeper\",\"letter\":\"C\",\"seats\":3}],\"child\":{\"minDate\":\"2004-11-13\",\"maxDate\":\"2018-11-12\"},\"allowStudent\":1,\"allowBooking\":1,\"isCis\":0,\"isEurope\":0,\"allowPrivilege\":0,\"noReserve\":1}]}}";

      //region Request data aggregation
  final String URL="https://booking.uz.gov.ua/en/train_search/";
      Map<String,Object> fields=new HashMap<>();
      fields.put("from",from);
      fields.put("to",to);
      fields.put("date",date);
      fields.put("time",time);

      Map<String,String> headers=new HashMap<>();
//headers.put("Cookie","HTTPSERVERID=server2;_gv_lang=en; _gv_sessid=opirj8kpsbvfll2fabnn4jqv25");
      //endregion

        JsonNode jsonNode=Sender.send(fields,headers,URL);
       JSONArray jsonArray= jsonNode.getObject().getJSONObject("data").getJSONArray("list");

              return mapper.readValue(jsonArray.toString(),Train[].class);
    }

   static public Train getTrain(String number, String from, String to, String date)throws IOException{

       mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


       final String URL="https://booking.uz.gov.ua/en/train_wagons/";
HashMap<String,Object> fields=new HashMap<>();
fields.put("train",number);
fields.put("from",from);
fields.put("to",to);
fields.put("date",date);
HashMap<String,String>headers=new HashMap<>();

JsonNode train_node=Sender.send(fields,headers,URL);
JSONArray seat_json=train_node.getObject().getJSONObject("data").getJSONArray("types");

SeatType[]seats=mapper.readValue(seat_json.toString(),SeatType[].class);


       return new Train(number,seats);
    }


  static public class SeatType {

      String type=null;
      String cost=null;
      String amount=null;
      SeatType(String type, String cost,String amount){
         this.type=type;
         this.cost=cost;
         this.amount=amount;
      }
      SeatType(){}

        public void setLetter(String type) {
            this.type = type;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public void setFree(String amount) {
            this.amount = amount;
        }
    }

    //region Constructors, setters, overrides
    Train(){}
    Train(String number){this.number=number;}
    Train(String number, SeatType[]seats){this.number=number;this.seats=seats;}


    public void setNum(String number) {
        this.number = number;
    }
    public String getNumber() {
        return this.number;
    }
    public SeatType[] getSeats() {
        return this.seats;
    }

    @Override
    public boolean equals(Object o) {
        return this.number.equals(((Train)o).number);
    }

    @Override
    public String toString() {
        return this.number;
    }
    //endregion
}
