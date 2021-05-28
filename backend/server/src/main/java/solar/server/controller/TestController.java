package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import solar.server.mqtt.MyMqttClient;

import java.util.HashMap;
import java.util.function.Consumer;

@RequiredArgsConstructor
@RestController
public class TestController {



//    @GetMapping("/mqtt")
//    public void mqtt (){
//
//        final Consumer<HashMap<Object, Object>> pdk = (arg)->{  //메시지를 받는 콜백 행위
//            arg.forEach((key, value)->{
//                System.out.println( String.format("메시지 도착 : 키 -> %s, 값 -> %s", key, value) );
//            });
//        };
//
//
//        MyMqttClient client = new MyMqttClient(pdk);
//        client.init("tcp://3.37.2.34:1883", "solar")
//                .subscribe(new String[]{"test","test2","test3"});
//
//        client.initConnectionLost( (arg)->{  //콜백행위1, 서버와의 연결이 끊기면 동작
//            arg.forEach((key, value)->{
//                System.out.println( String.format("커넥션 끊김~! 키 -> %s, 값 -> %s", key, value) );
//            });
//        });
//
//        client.initDeliveryComplete((arg)-> {  //콜백행위2, 메시지를 전송한 이후 동작
//            arg.forEach((key, value)->{
//                System.out.println( String.format("메시지 전달 완료~! 키 -> %s, 값 -> %s", key, value) );
//            });
//        });
//
//
//
//        new Thread( ()->{
//            try {
//                Thread.sleep(9000);
//                client.sender("solar", "Hello world!");  //이런식으로 보낸다.
//
//
////                client.close();  //종료는 이렇게!
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } ).start();
//
//
//
//
//
//    }
}
