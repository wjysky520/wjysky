package com.wjysky.components.redis.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * RedisUtil
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-03-13 16:32:37
 * @apiNote Redis缓存服务工具类
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class RedisUtil {

//    private final RedisTemplate redisTemplate;
//
//    /**
//     * @bizName 根据redis的key查询redis是否存在该键值对
//     *
//     * @title hasKey
//     * @apiNote ${todo}
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:23
//     * @return boolean
//     **/
//    public boolean hasKey(Object key) {
//        try {
//            if (key != null) {
//                return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(Boolean.FALSE);
//            }
//        } catch (Exception e) {
//            log.error("查询Redis缓存时异常，key：[{}]", key, e);
//        }
//        return false;
//    }
//
//    /**
//     * @bizName 保存值
//     *
//     * @title set
//     * @apiNote key存在则覆盖原值，默认有效时长为60秒
//     * @param key redis的key
//     * @param value redis的value
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:24
//     * @return boolean
//     **/
//    public boolean set(Object key, Object value) {
//        return set(key, value, null, null);
//    }
//
//    /**
//     * @bizName 保存值
//     *
//     * @title set
//     * @apiNote key存在则覆盖原值，默认有效时长的单位为秒
//     * @param key redis的key
//     * @param value redis的value
//     * @param timeout 默认有效时长，单位为秒
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:25
//     * @return boolean
//     **/
//    public boolean set(Object key, Object value, Long timeout) {
//        return set(key, value, timeout, null);
//    }
//
//    /**
//     * @bizName 保存值
//     *
//     * @title set
//     * @apiNote key存在则覆盖原值
//     * @param key redis的key
//     * @param value redis的value
//     * @param timeout 默认有效时长，默认为1，当单位为秒时为60
//     * @param unit 时间单位，默认单位为秒
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:26
//     * @return boolean
//     **/
//    public boolean set(Object key, Object value, Long timeout, TimeUnit unit) {
//        try {
//            if (key == null) {
//                return false;
//            }
//            if (value == null) {
//                return false;
//            }
//            if (unit == null) {
//                unit = TimeUnit.SECONDS;
//            }
//            if (timeout == null || timeout <= 0) {
//                if (TimeUnit.SECONDS == unit) {
//                    timeout = 60L;
//                } else {
//                    timeout = 1L;
//                }
//            }
//            redisTemplate.opsForValue().set(key, value, timeout, unit);
//            return true;
//        } catch (Exception e) {
//            log.error("保存Redis缓存时异常，key：[{}]，value：[{}]，timeout：[{}]，unit：[{}]", key, value, timeout, unit, e);
//        }
//        return false;
//    }
//
//    /**
//     * @bizName 追加字符串
//     *
//     * @title setIfAbsent
//     * @apiNote 在原有的值基础上新增字符串到末尾，返回值的长度
//     * @param key redis的key
//     * @param value redis的value
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:16
//     * @return boolean
//     **/
//    public int append(Object key, String value) {
//        try {
//            if (key != null && value != null) {
//                return Optional.ofNullable(redisTemplate.opsForValue().append(key, value)).orElse(-1);
//            }
//        } catch (Exception e) {
//            log.error("追加Redis缓存内容时异常，key：[{}]，value：[{}]", key, value, e);
//        }
//        return -1;
//    }
//
//    /**
//     * @bizName 获取指定key的值减1
//     *
//     * @title decrement
//     * @apiNote 如果value不是Integer类型，会抛异常，如果key不存在会创建一个，默认value为0
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:53
//     * @return long
//     **/
//    public long decrement(Object key) throws Exception {
//        if (key == null) {
//            throw new Exception("redis的key不能为空");
//        } else {
//            return redisTemplate.opsForValue().decrement(key);
//        }
//    }
//
//    /**
//     * @bizName 获取指定key的值加1
//     *
//     * @title increment
//     * @apiNote 如果value不是Integer类型，会抛异常，如果key不存在会创建一个，默认value为0
//     * @param key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:55
//     * @return long
//     **/
//    public long increment(Object key) throws Exception {
//        if (key == null) {
//            throw new Exception("redis的key不能为空");
//        } else {
//            return redisTemplate.opsForValue().increment(key);
//        }
//    }
//
//    /**
//     * @bizName 删除值
//     *
//     * @title delete
//     * @apiNote ${todo}
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:07
//     * @return boolean
//     **/
//    public boolean delete(Object key) {
//        try {
//            if (key != null) {
//                return Optional.ofNullable(redisTemplate.opsForValue().getOperations().delete(key)).orElse(Boolean.FALSE);
//            }
//        } catch (Exception e) {
//            log.error("删除Redis缓存时异常，key：[{}]", key, e);
//        }
//        return Boolean.FALSE;
//    }
//
//    /**
//     * @bizName 批量删除字符串
//     *
//     * @title delete
//     * @apiNote ${todo}
//     * @param keyList redis的key的集合
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:08
//     * @return long
//     **/
//    public long delete(List<Object> keyList) {
//        if (CollectionUtils.isEmpty(keyList)) {
//            return -1L;
//        }
//        return Optional.ofNullable(redisTemplate.opsForValue().getOperations().delete(keyList)).orElse(-1L);
//    }
//
//    /**
//     * @bizName 获取保存的值
//     *
//     * @title get
//     * @apiNote key不存在返回null
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:49
//     * @return java.lang.String
//     **/
//    public Object get(Object key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * @bizName 批量获取保存的值
//     *
//     * @title multiGet
//     * @apiNote key不存在返回空的ArrayList
//     * @param keyList redis的key的集合
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:49
//     * @return java.util.List<java.lang.String>
//     **/
//    public List<Object> multiGet(List<Object> keyList) {
//        if (CollectionUtils.isEmpty(keyList)) {
//            return new ArrayList<>();
//        }
//        return Optional.ofNullable(redisTemplate.opsForValue().multiGet(keyList)).orElse(new ArrayList<>());
//    }
//
//    /**
//     * @bizName 保存HashMap
//     *
//     * @title put
//     * @apiNote redis的key相同则覆盖
//     * @param key redis的key
//     * @param hashKey redis中value为HashMap的key
//     * @param hashValue redis中value为HashMap的value
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:18
//     * @return boolean
//     **/
//    public boolean put(Object key, Object hashKey, Object hashValue) {
//        try {
//            if (key == null || "".equals(key)) {
//                return false;
//            }
//            if (hashKey == null) {
//                return false;
//            }
//            if (hashValue == null) {
//                return false;
//            }
//            redisTemplate.opsForHash().put(key, hashKey, hashValue);
//            return true;
//        } catch (Exception e) {
//            log.error("保存Redis缓存时异常，key：[{}]，hashKey：[{}]，hashValue：[{}]", key, hashKey, hashValue, e);
//        }
//        return false;
//    }
//
//    /**
//     * @bizName 保存HashMap
//     *
//     * @title putAll
//     * @apiNote ${todo}
//     * @param key
//     * @param map
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 16:31
//     * @return boolean
//     **/
//    public boolean putAll(Object key, Map<Object, Object> map) {
//        try {
//            if (key == null || "".equals(key)) {
//                return false;
//            }
//            if (map == null || map.isEmpty()) {
//                return false;
//            }
//            redisTemplate.opsForHash().putAll(key, map);
//            return true;
//        } catch (Exception e) {
//            log.error("保存Redis缓存时异常，key：[{}]，map：[{}]", key, JSON.toJSONString(map), e);
//        }
//        return false;
//    }
//
//    /**
//     * @bizName 批量插入
//     *
//     * @title multiSet
//     * @apiNote map的key/value对应redis的key/value，key值存在会覆盖原值
//     * @param map 插入内容集合
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/14 9:08
//     * @return boolean
//     **/
//    public boolean multiSet(Map<Object, Object> map) {
//        try {
//            if (map.isEmpty()) {
//                return false;
//            }
//            redisTemplate.opsForValue().multiSet(map);
//            return true;
//        } catch (Exception e) {
//            log.error("保存Redis缓存时异常，map：[{}]", JSON.toJSONString(map), e);
//        }
//        return false;
//    }
//
//    /**
//     * @bizName 批量插入
//     *
//     * @title multiSetIfAbsent
//     * @apiNote map的key/value对应redis的key/value，
//     *          如果里面的所有key都不存在，则全部插入，返回true，如果其中一个在redis中已存在，全不插入，返回false
//     * @param map 插入内容集合
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/14 9:13
//     * @return boolean
//     **/
//    public boolean multiSetIfAbsent(Map<Object, Object> map) {
//        try {
//            if (map.isEmpty()) {
//                return false;
//            }
//            return Optional.ofNullable(redisTemplate.opsForValue().multiSetIfAbsent(map)).orElse(Boolean.FALSE);
//        } catch (Exception e) {
//            log.error("保存Redis缓存时异常，map：[{}]", JSON.toJSONString(map), e);
//        }
//        return false;
//    }
//
//    /**
//     * @bizName 获取redis中的HashMap
//     *
//     * @title entries
//     * @apiNote ${todo}
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:20
//     * @return java.util.Map<java.lang.Object,java.lang.Object>
//     **/
//    public Map<Object, Object> entries(Object key) {
//        if (key == null || "".equals(key)) {
//            return new HashMap<>();
//        }
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * @bizName 获取redis中的HashMap的值
//     *
//     * @title get
//     * @apiNote 如果存在该HashMap的key则获取值，没有则返回null
//     * @param key redis的key
//     * @param hashKey redis中value为HashMap的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:22
//     * @return java.lang.Object
//     **/
//    public Object get(Object key, Object hashKey) {
//        if (key == null || "".equals(key)) {
//            return null;
//        }
//        if (hashKey == null || "".equals(hashKey)) {
//            return null;
//        }
//        return redisTemplate.opsForHash().get(key, hashKey);
//    }
//
//    /**
//     * @bizName 获取redis中的HashMap的key集合
//     *
//     * @title get
//     * @apiNote ${todo}
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:22
//     * @return java.lang.Object
//     **/
//    public List<Object> keys(Object key) {
//        if (key == null || "".equals(key)) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(redisTemplate.opsForHash().keys(key));
//    }
//
//    /**
//     * @bizName 获取redis中的HashMap的values集合
//     *
//     * @title values
//     * @apiNote ${todo}
//     * @param key redis的key
//     * @author 王俊元（wangjunyuan@talkweb.com.cn）
//     * @date 2023/3/16 17:28
//     * @return java.util.List<java.lang.Object>
//     **/
//    public List<Object> values(Object key) {
//        if (key == null || "".equals(key)) {
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(redisTemplate.opsForHash().values(key));
//    }
}
