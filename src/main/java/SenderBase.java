import io.github.openunirest.http.Unirest;

public class SenderBase {
static Unirest unirest;

  static   public void init(){

    //    Unirest.setDefaultHeader("Accept", "application/json");
        unirest.setDefaultHeader("Accept", "application/json");
        unirest.setDefaultHeader("Host","booking.uz.gov.ua");
        unirest.setDefaultHeader("Origin","https://booking.uz.gov.ua");
        unirest.setDefaultHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");

    }
}
