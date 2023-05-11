package com.wjysky.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.wjysky.pojo.bo.MessageBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * ComsumerListener
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-14 15:07:11
 * @apiNote
 */
@RocketMQMessageListener(topic = "${rocketmq.topic[0]}", nameServer = "${rocketmq.name-server}",
        selectorExpression = "${rocketmq.tag[0]}",
        consumerGroup = "CONSUMER_TEST", messageModel = MessageModel.CLUSTERING)
@Component
@Slf4j
public class ConsumerListener implements RocketMQListener<MessageBody> {

    @Override
    public void onMessage(MessageBody messageBody) {
        log.info("----------------" + JSON.toJSONString(messageBody));
    }
}
