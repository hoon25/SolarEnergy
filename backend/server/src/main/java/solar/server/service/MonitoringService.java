package solar.server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import solar.server.domain.Member;
import solar.server.domain.Monitoring;
import solar.server.domain.ServerTime;
import solar.server.domain.monitoring.MonitoringMember;
import solar.server.domain.monitoring.MinuteMqtt;
import solar.server.mapper.MemberMapper;
import solar.server.mapper.MonitoringMapper;
import solar.server.mqttService.MyMqttClient;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MemberMapper memberMapper;
    private final MonitoringMapper monitoringMapper;


    HashMap<Object,Object> minuteData = new HashMap<Object,Object>();

    public String getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();
        return userId;
    }




    public Monitoring monitoring() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
        String userId = getUserId();
        System.out.println(userId);
        Optional<Member> member = memberMapper.selectUser(userId);

        String userLoc = member.get().getUserLoc();
        Long userVolume = member.get().getUserVolume();

        return getMonitoring(userId, userLoc, userVolume);
    }

    // 모니터링 데이터 구성
    private Monitoring getMonitoring(String userId, String userLoc, Long userVolume) {

        ServerTime serverTime = getServerTime();
        String year = serverTime.getYear();
        String month = serverTime.getMonth();
        String day = serverTime.getDay();
        String hour = serverTime.getHour();


        MonitoringMember monitoringMember = MonitoringMember.builder()
                                                .userId(userId)
                                                .userLoc(userLoc)
                                                .userVolume(userVolume)
                                                .build();

        MinuteMqtt minuteMqtt = getMinuteMqtt();

        return Monitoring.builder()
                .serverTime(getServerTime())
                .monitoringMember(monitoringMember)
                .weatherFct(monitoringMapper.getWeatherFctData(year,month,day,hour,userLoc))
                .sunTime(monitoringMapper.getSunTimeData(month,day))
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
                .subscribe(new String[]{"temperature", "humidity", "panelCover","angular","panelTemperature","illumination","lastCleanTime","iring"});


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
    public HashMap<Object,Object> subscribeMqtt(String userId) {

        final Function<HashMap<Object, Object>,HashMap<Object,Object>> pdk = (arg) -> {  //메시지를 받는 콜백 행위
            for (HashMap.Entry<Object,Object> entry : arg.entrySet()) {
                minuteData.put(entry.getKey(), entry.getValue());
                System.out.println(String.format("메세지 도착 : 키 -> %s, 값 -> %s", entry.getKey(), entry.getValue()));

                if(entry.getKey().equals("hRealPower")) {
                    System.out.println("equal");
                    String values = entry.getValue().toString();
                    sethRealPower(userId, values);
                    setdRealPower(userId, values);
                    setmRealPower(userId, values);
                    break;
                }
            }
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
        String userId = getUserId();

        HashMap<Object, Object> minuteData = subscribeMqtt(userId);


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

    public void sethRealPower(String userId, String value){

        ServerTime serverTime = getServerTime();
        String year = serverTime.getYear();
        String month = serverTime.getMonth();
        String day = serverTime.getDay();
        String hour = serverTime.getHour();
        String minute = serverTime.getMinute();

        System.out.println(year + month + day + hour + minute );

        monitoringMapper.sethRealPower(year,month, day, hour, value, userId);
        System.out.println("hRealdb 입력완료");
    }

    public void setdRealPower(String userId, String value) {

        ServerTime serverTime = getServerTime();
        String year = serverTime.getYear();
        String month = serverTime.getMonth();
        String day = serverTime.getDay();


        String dRealId = year + month + day + userId;

        try {
            monitoringMapper.setdRealPower(dRealId, year, month, day, value, userId);
            System.out.println("dRealdb 입력완료");
        } catch (Exception e) {
            System.out.println("이미 오늘 데이터가 있습니다");
            monitoringMapper.updatedRealPower(dRealId, value);
            System.out.println("dRealdb 업데이트 완료");
        }
    }

    public void setmRealPower(String userId, String value) {

        ServerTime serverTime = getServerTime();
        String year = serverTime.getYear();
        String month = serverTime.getMonth();

        String mRealId = year + month + userId;


        try {
            monitoringMapper.setmRealPower(mRealId, year, month, value, userId);
            System.out.println("mRealdb 입력완료");
        } catch (Exception e) {
            System.out.println("이미 이번달 데이터가 있습니다");
            monitoringMapper.updatemRealPower(mRealId, value);
            System.out.println("mRealdb 업데이트 완료");
        }

//        monitoringMapper.setmRealPower(mRealId, year, month, value, userId);
//        System.out.println("mRealdb 입력완료");

    }

    public ServerTime getServerTime(){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        SimpleDateFormat format4 = new SimpleDateFormat("HH");
        SimpleDateFormat format5 = new SimpleDateFormat("mm");

        String year = format1.format(System.currentTimeMillis());
        String month = format2.format(System.currentTimeMillis());
        String day = format3.format(System.currentTimeMillis());
        String hour = format4.format(System.currentTimeMillis());
        String minute = format5.format(System.currentTimeMillis());

        return ServerTime.builder()
                .year(year)
                .month(month)
                .day(day)
                .hour(hour)
                .minute(minute).build();
    }


}


