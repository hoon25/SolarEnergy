package solar.server.mqttService;

import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class MyMqttClient implements MqttCallback {

    private MqttClient client;
    private MqttConnectOptions option;
    private Function<HashMap<Object, Object>,HashMap<Object,Object>> FNC = null;  //메시지 도착 후 응답하는 함수
    private Consumer<HashMap<Object, Object>> FNC2 = null; //커넥션이 끊긴 후 응답하는 함수
    private Consumer<HashMap<Object, Object>> FNC3 = null; //전송이 완료된 이후 응답하는 함수.

    public MyMqttClient (Function<HashMap<Object, Object>,HashMap<Object,Object>> fnc){  //생성자를 추가하자.
        this.FNC = fnc;

    }



    public MyMqttClient init(String serverURI, String clientId){
        option = new MqttConnectOptions();
        option.setCleanSession(true);
        option.setKeepAliveInterval(30);
        try {
            client = new MqttClient(serverURI, clientId);
            client.setCallback(this);
            client.connect(option);
        } catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }


    @Override
    public void connectionLost(Throwable arg0) {
        if(FNC2 != null){
            HashMap<Object, Object> result = new HashMap<>();
            result.put("result", arg0);
            FNC2.accept(result);
            arg0.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        if(FNC3 != null){
            HashMap<Object, Object> result = new HashMap<>();
            try {
                result.put("result", arg0);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("result", "ERROR");
                result.put("error", e.getMessage());
            }
            FNC3.accept(result);
        }
    }

    //메시지 도착
    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        if(FNC != null){
            HashMap<Object, Object> result = new HashMap<>();
//            result.put("topic", arg0);
//            result.put("message", new String(arg1.getPayload(),"UTF-8"));
            result.put(arg0,new String(arg1.getPayload(),"UTF-8"));
            FNC.apply(result);  //콜백행위 실행
        }
    }


    // 구독대상 전달
    public boolean subscribe(String... topics){
        try{
            if(topics != null) {
                for (String topic : topics) {
                    client.subscribe(topic, 0);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 전송
    public boolean sender(String topic, String msg) throws MqttPersistenceException, MqttException{
        MqttMessage message = new MqttMessage();
        message.setPayload(msg.getBytes(StandardCharsets.UTF_8));
//        message.setPayload();
        client.publish(topic,message);
        return false;
    }

    /**
     * 커넥션이 끊어진 이후의 콜백행위를 등록합니다.<br>
     * 해쉬맵 형태의 결과에 키는 result, 값은 Throwable 객체를 반환 합니다.
     * **/
    public void initConnectionLost (Consumer<HashMap<Object, Object>> fnc){
        FNC2 = fnc;
    }

    /**
     * 커넥션이 끊어진 이후의 콜백행위를 등록합니다.<br>
     * 해쉬맵 형태의 결과에 키는 result, 값은 IMqttDeliveryToken 객체를 반환 합니다.
     * **/
    public void initDeliveryComplete (Consumer<HashMap<Object, Object>> fnc){
        FNC3 = fnc;
    }

    /**
     * 종료메소드입니다.<br>
     * 클라이언트를 종료 합니다.
     * */
    public void close(){
        if(client != null){
            try {
                client.disconnect();
                client.close();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }



}

