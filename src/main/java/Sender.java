import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.http.JsonNode;


import java.util.Map;

public class Sender extends SenderBase{


    static JsonNode send(Map<String,Object> fields, String URL){

        HttpResponse<JsonNode> response = unirest.post(URL)
                .fields(fields)
                .asJson();

return response.getBody();

    }
static JsonNode send(String URL,Map<String,Object> parameters){
        HttpResponse<JsonNode>response=unirest.get(URL)
                .queryString(parameters)
                .asJson();

    return response.getBody();
}

}
