CREATE DATABASE novel DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE novel;

CREATE TABLE user_info(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(32) NOT NULL COMMENT '用户名',
  `password` VARCHAR(256) NOT NULL COMMENT '用户密码',
  nickname VARCHAR(64) NOT NULL COMMENT '昵称',
  tel VARCHAR(16) COMMENT '联系电话',
  email VARCHAR(128) COMMENT '邮箱',
  avatar_file_id BIGINT COMMENT '用户头像文件ID，对应upload_file_info表主键',
  deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除',
  update_at BIGINT NOT NULL COMMENT '更新时间',
  create_at BIGINT NOT NULL COMMENT '创建时间',
  UNIQUE(tel)
) COMMENT = '用户信息表';

