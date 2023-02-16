package com.wjysky.mq.producer;

import com.wjysky.entity.bo.MessageBody;
import com.wjysky.enums.MQMsgTypeEnum;
import com.wjysky.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


/**
 * MQProducerService
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-14 14:44:35
 * @apiNote
 */
@Component
@Slf4j
public class MQProducerService {

    // 直接注入使用，用于发送消息到broker服务器
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private <V> void sendMsg(MQMsgTypeEnum msgType, String destination, Object payload, String msgSource, Function<SendResult, V> func) {
        String msgKey = ObjectUtil.getUUID();
        MessageBody msgBody = new MessageBody(msgKey, payload, msgSource);
        Message<MessageBody> message = MessageBuilder.withPayload(msgBody).setHeader("KEYS", msgKey).build();
        log.info("开始发送MQ消息，主题为[{}]，内容为[{}]......", destination, message);
        SendResult result = null;
        switch (msgType) {
            case ONEWAY: // 单向发送消息
                rocketMQTemplate.sendOneWay(destination, message);
                break;
            case ASYNC: // 异步消息发送
                rocketMQTemplate.asyncSend(destination, message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        if (func != null) {
                            func.apply(sendResult);
                        }
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        log.error("消息发送失败，topic:tag为[{}]", destination, throwable);
                    }
                });
                break;
            case SYNC: // 同步发送消息
                result = rocketMQTemplate.syncSend(destination, message);
                break;
        }
        log.info("主题为[{}]的MQ消息发送结束{}", destination, result != null ? ("，结果ID为[" + result.getMsgId() + "]") : "");
    }

    private void sendMsg(MQMsgTypeEnum msgType, String destination, Object payload, String msgSource) {
        sendMsg(msgType, destination, payload, msgSource, null);
    }

    /**
     * 同步发送消息,会确认应答
     *
     * @param destination
     * @param payload
     */
    public void syncSendMsg(String destination, Object payload, String msgSource) {
        sendMsg(MQMsgTypeEnum.SYNC, destination, payload, msgSource);
    }


    /**
     * 同步发送消息,会确认应答
     *
     * @param topic
     * @param tag
     * @param payload
     */
    public void syncSendMsg(String topic, String tag, Object payload, String msgSource) {
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        syncSendMsg(destination, payload, msgSource);
    }

    /**
     * 异步消息发送,异步日志确认异常
     *
     * @param destination
     * @param payload
     */
    public <V> void asyncSendMsg(String destination, Object payload, String msgSource, Function<SendResult, V> func) {
        sendMsg(MQMsgTypeEnum.ASYNC, destination, payload, msgSource, func);
    }

    /**
     * 异步消息发送,异步日志确认异常
     *
     * @param topic
     * @param tag
     * @param payload
     * @return
     */
    public <V> void asyncSendMsg(String topic, String tag, Object payload, String msgSource, Function<SendResult, V> func) {
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        asyncSendMsg(destination, payload, msgSource, func);
    }

    /**
     * 异步消息发送,异步日志确认异常
     *
     * @param destination
     * @param payload
     */
    public void asyncSendMsg(String destination, Object payload, String msgSource) {
        sendMsg(MQMsgTypeEnum.ASYNC, destination, payload, msgSource);
    }

    /**
     * 异步消息发送,异步日志确认异常
     *
     * @param topic
     * @param tag
     * @param payload
     * @return
     */
    public void asyncSendMsg(String topic, String tag, Object payload, String msgSource) {
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        asyncSendMsg(destination, payload, msgSource);
    }

    /**
     * 单向发送消息，不关注结果
     *
     * @param destination
     * @param payload
     */
    public void oneWaySendMsg(String destination, Object payload, String msgSource) {
        sendMsg(MQMsgTypeEnum.ONEWAY, destination, payload, msgSource);
    }

    /**
     * 单向发送消息，不关注结果
     *
     * @param topic
     * @param tag
     * @param payload
     */
    public void oneWaySendMsg(String topic, String tag, Object payload, String msgSource) {
        // 发送的消息体，消息体必须存在
        // 业务主键作为消息key
        String destination = topic + ":" + tag;
        oneWaySendMsg(destination, payload, msgSource);
    }
//
//    public <T> void send(T t) {
//        rocketMQTemplate.convertAndSend(topic + ":tag1", t);
////        rocketMQTemplate.send(topic + ":tag1", MessageBuilder.withPayload(user).build()); // 等价于上面一行
//    }
}