import io.github.openunirest.http.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
static private LocalDate localDate;

@BeforeAll
static void Setup(){
localDate =LocalDate.now();
}


@ParameterizedTest
@CsvFileSource(resources = "/stations.csv")
 void getStations(ArgumentsAccessor arguments){

String key=arguments.getString(0);

    Set<Station> file_stations=new TreeSet<Station>();

  //  Station[] file_stations=new Station[arguments.size()];

    for(int i=1; i<arguments.size(); i++){
        Station st=new Station();
        st.setTitle(arguments.getString(i));
      file_stations.add(st);
    }

 Set<Station> app_stations=  Station.getStations(key); //retrieve station set from application



 assertEquals(file_stations,app_stations);
    //   System.out.print(st+" "+station);

    System.out.print("");

}

@ParameterizedTest
@CsvFileSource(resources = "/trains.csv",numLinesToSkip = 1)
    void getTrains(ArgumentsAccessor arguments){


    //region Reading file input data and trains
    String from=arguments.getString(0);
    String to=arguments.getString(1);
    int schema=arguments.getInteger(2);
    String time=arguments.getString(3);
String date;


if((schema%2)==(localDate.getDayOfMonth()%2)){
    date =localDate.toString();
}else {
    date =localDate.plusDays(1).toString();
}

    int train_amount=arguments.size()-4;

    Train[] file_trains=new Train[train_amount];
    for (int i=0, k=4;i<train_amount;i++,k++){
        String arg_train=arguments.getString(k);
        file_trains[i] =new Train(arg_train.substring(0,4));
    }
    //endregion


    Train[] app_trains=null;
         try {
             app_trains=Train.getTrains(from,to, date,time);
         }
         catch (Exception e){
             System.out.println(e); fail();
         }

         //assertTrue(Arrays.deepEquals(file_trains,app_trains));
    assertArrayEquals(file_trains,app_trains,"Train comparison");

}

@ParameterizedTest
@CsvFileSource(resources = "/trains.csv",numLinesToSkip = 1)
    void getTrain(ArgumentsAccessor arguments){
    String from=arguments.getString(0);
String to=arguments.getString(1);
String date=localDate.toString();
String number=arguments.getString(4).substring(0,4);

    Train train=null;
    try {
     train=Train.getTrain(number,from,to,date);
    }catch (Exception e){
        System.out.println(e);fail();
    }
assertEquals(number,train.getNumber());
    assertNotNull(train.getSeats());



}

@AfterAll
    static void Teardown(){
    Unirest.shutdown();
}
}
