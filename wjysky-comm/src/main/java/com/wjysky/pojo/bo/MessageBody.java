package com.wjysky.pojo.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MessageBody
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-14 15:02:15
 * @apiNote
 */
@Data
@NoArgsConstructor
public class MessageBody {

    // 消息id
    private String messageId;

    // body组装时间
    private long timestamp;

    // 来源 附加信息
    private String msgSource;

    // 发送内容
    private Object data;

    public MessageBody(String msgKey, Object data, String msgSource) {
        this.messageId = msgKey;
        this.data = data ;
        this.msgSource = msgSource;
        this.timestamp = System.currentTimeMillis();
    }
}

