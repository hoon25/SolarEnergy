package solar.server.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import solar.server.domain.Member;
import solar.server.domain.Monitoring;
import solar.server.domain.monitoring.MonitoringMember;
import solar.server.domain.mqtt.MinuteMqtt;
import solar.server.mapper.MemberMapper;
import solar.server.mapper.MonitoringMapper;
import solar.server.mqtt.MyMqttClient;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MemberMapper memberMapper;
    private final MonitoringMapper monitoringMapper;


    HashMap<Object,Object> minuteData = new HashMap<Object,Object>();



    public Monitoring monitoring() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
//        String userId = getUserId();
        Optional<Member> member = memberMapper.selectUser(userId);
        String userLoc = member.get().getUserLoc();
        Long userVolume = member.get().getUserVolume();

        return getMonitoring(userId, userLoc, userVolume);
    }

    // 모니터링 데이터 구성
    private Monitoring getMonitoring(String userId, String userLoc, Long userVolume) {

        MonitoringMember monitoringMember = MonitoringMember.builder()
                                                .userId(userId)
                                                .userLoc(userLoc)
                                                .userVolume(userVolume)
                                                .build();

        MinuteMqtt minuteMqtt = getMinuteMqtt();

        return Monitoring.builder()
                .monitoringMember(monitoringMember)
                .month(monitoringMapper.getMonthData(userId, userLoc))
                .day(monitoringMapper.getDayData(userId, userLoc))
                .hour(monitoringMapper.getHourData(userId, userLoc))
                .minuteMqtt(minuteMqtt)
                .build();

    }


//     MQTT

    // MQTT 발신용
    public void senderMqtt(String topic, String message) {


        final Function<HashMap<Object, Object>,HashMap<Object,Object>> pdk = (arg) -> {  //메시지를 받는 콜백 행위
            arg.forEach((key, value) -> {
                System.out.println(String.format("메시지 도착 : 키 -> %s, 값 -> %s", key, value));
                minuteData.put(key,value);
            });
            return minuteData;
        };



        MyMqttClient client = new MyMqttClient(pdk);
        client.init("tcp://3.37.2.34:1883", "solar")
                .subscribe(new String[]{"temperature", "humidity", "panelCover","angular","panelTemperature","illumination","lastCleanTime"});


        client.initConnectionLost((arg) -> {  //콜백행위1, 서버와의 연결이 끊기면 동작
            arg.forEach((key, value) -> {
                System.out.println(String.format("커넥션 끊김~! 키 -> %s, 값 -> %s", key, value));
            });
        });

        client.initDeliveryComplete((arg) -> {  //콜백행위2, 메시지를 전송한 이후 동작
            arg.forEach((key, value) -> {
                System.out.println(String.format("메시지 전달 완료~! 키 -> %s, 값 -> %s", key, value));
            });
        });


        new Thread(() -> {
            try {
                Thread.sleep(9000);
                client.sender(topic, message);  //이런식으로 보낸다.
                client.close();  //종료는 이렇게!
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // MQTT 수신용
    public HashMap<Object,Object> subscribeMqtt() {

        final Function<HashMap<Object, Object>,HashMap<Object,Object>> pdk = (arg) -> {  //메시지를 받는 콜백 행위
            arg.forEach((key, value) -> {
                System.out.println(String.format("메시지 도착 : 키 -> %s, 값 -> %s", key, value));

                System.out.println("key : " + key);
                System.out.println("hrealPower".getClass().getName());

                if(key.equals("hRealPower")) {
                    System.out.println("equal");
                    String values = value.toString();
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                    setRealPredPower(values);
                }

                else{
                }

                minuteData.put(key,value);

            });
            return minuteData;
        };



        MyMqttClient client = new MyMqttClient(pdk);
        client.init("tcp://3.37.2.34:1883", "solar")
                .subscribe(new String[]{"temperature", "humidity", "panelCover","angular","panelTemperature","illumination","lastCleanTime", "hRealPower"});


        client.initConnectionLost((arg) -> {  //콜백행위1, 서버와의 연결이 끊기면 동작
            arg.forEach((key, value) -> {
                System.out.println(String.format("커넥션 끊김~! 키 -> %s, 값 -> %s", key, value));
            });
        });

        client.initDeliveryComplete((arg) -> {  //콜백행위2, 메시지를 전송한 이후 동작
            arg.forEach((key, value) -> {
                System.out.println(String.format("메시지 전달 완료~! 키 -> %s, 값 -> %s", key, value));
            });
        });


        new Thread(() -> {
            try {
                Thread.sleep(9000);
                client.sender("monitoring", "start");  //이런식으로 보낸다.
//                client.close();  //종료는 이렇게!
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("return minuteData : " + minuteData);
        return minuteData;
    }


    public MinuteMqtt getMinuteMqtt(){
        HashMap<Object, Object> minuteData = subscribeMqtt();


        return MinuteMqtt.builder()
                .temperature(minuteData.get("temperature"))
                .humidity(minuteData.get("humidity"))
                .panelCover(minuteData.get("illumination"))
                .angular(minuteData.get("angular"))
                .panelTemperature(minuteData.get("panelTemperature"))
                .illumination(minuteData.get("illumination"))
                .lastCleanTime(minuteData.get("lastCleanTime"))
                .build();
    }

    public void setRealPredPower(String value){

//        String userId = monitoring.getMonitoringMember().getUserId();
//        String userId = "hoon25";

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        SimpleDateFormat format4 = new SimpleDateFormat("HH");

        String year = format1.format(System.currentTimeMillis());
        String month = format2.format(System.currentTimeMillis());
        String day = format3.format(System.currentTimeMillis());
        String hour = format4.format(System.currentTimeMillis());

        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(hour);
//        monitoringMapper.sethRealPower(year,month, day, hour, value, userId);
        System.out.println("db 입력완료");

    }


}


