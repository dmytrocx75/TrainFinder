import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.http.JsonNode;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class Sender {

    static JsonNode send(Map<String,Object> fields, Map<String,String> headers,String URL){

        HttpResponse<JsonNode> response = Unirest.post(URL)
                .header("Accept", "application/json")
                .header("Host","booking.uz.gov.ua")
                .header("Origin","https://booking.uz.gov.ua")
                .header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.3")
                .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                .headers(headers)
                .fields(fields)
                .asJson();


return response.getBody();

    }
}
