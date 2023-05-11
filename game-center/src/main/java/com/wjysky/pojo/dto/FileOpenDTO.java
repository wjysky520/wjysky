package com.wjysky.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对外接口返回对象
 *
 * @author chenzibo@talkweb.com.cn
 * @date 2022/9/23
 * @apiNote 对外接口返回对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileOpenDTO {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件字节数组
     */
    private byte[] data;

    /**
     * 服务标识
     */
    private String serviceFlag;

    /**
     * 接口描述
     */
    private String serviceMessage;

    /**
     * 文件id（下载使用）
     */
    private Long fileId;

    /**
     * 10(沃易购)，20(沃联盟)，30(沃音乐)，40(沃阅读)，50(10010商城)，60(内部商城)
     */
    private Integer systemType;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String msg;
}
