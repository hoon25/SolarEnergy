package solar.server.mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Consumer;

public class MyMqttClientHoon implements MqttCallback {

    private MqttClient client;
    private MqttConnectOptions option;





    public MyMqttClientHoon init(String serverURI, String clientId){
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

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {

    }

    //메시지 도착
    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
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

