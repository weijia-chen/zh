package com.weijia.zh.manage.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================
    /**
     * 指定缓存失效时间
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }


    // ============================String=============================

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 递增
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * 递减
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }


    // ================================Map=================================

    /**
     * HashGet
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * HashSet 并设置时间
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }


    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }


    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }


    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }


    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */

    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 说明：
     *  *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *  *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *  *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *  *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    public <T> List<T> lGet(String key, long start, long end,Class<T> clazz) {
        try {
            return (List<T>)redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 通过索引 获取list中的值
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 修改list指定索引的值
     * @param key   键
     * @param value 值
     */
    public boolean lSet(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key,index,value);
            return true;
        } catch (Exception e) {
            log.info("使用lSet(String key, long index,Object value)出错");
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSetFromList(String key, List value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean lSetOrNull(String key, List value,long time) {

        try {
            if (value == null || value.size()<=0)
                log.info("该问题没有回答，不缓存");
//                redisTemplate.opsForList().rightPush(key,"null");
            else
                redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }




    /**
     * 指定List的泛型
     */
    public boolean lSet(String key, List value, long time) {
        log.info("使用：lSet(String key, List value, long time)");
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
//    =====================Zset==========================================================

    /**
     * 向(key对应的)zset中添加(item, score)
     *
     * 注: item为entryKey成员项， score为entryValue分数值
     * **/
    public  boolean zAdd(String key, String item, Long score) {
        log.info("zAdd(...) => key -> {}, item -> {}, score -> {}", key, item, score);
        Boolean result = redisTemplate.opsForZSet().add(key, item, score);
        log.info("zAdd(...) => result -> {}", result);
        if (result == null) {
            throw new NullPointerException("未知异常");
        }
        return result;
    }

    public  boolean zAdd(String key, Long item, Long score) {

        return this.zAdd(key,String.valueOf(item),score);
    }

    /**
     * 按分数降序排序
     * @param key
     * @param start
     * @param end
     * @return
     */
    public  Set<Object> zReverseRange(String key, long start, long end){
        log.info("zReverseRange(...) => key -> {}, start -> {}, end -> {}", key, start, end);
        Set<Object> result = redisTemplate.opsForZSet().reverseRange(key, start, end);
        log.info("zReverseRange(...) => result -> {}", result);
        return result;
    }

    public Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(String key, long start, long end){
        log.info("zReverseRange(...) => key -> {}, start -> {}, end -> {}", key, start, end);
        Set<ZSetOperations.TypedTuple<Object>> result = this.redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        log.info("zReverseRange(...) => result -> {}", result);
        return result;
    }

    /**
     * 从(key对应的)zset中移除项
     *
     * 注:若key不存在，则返回0
     *
     * @param key
     *            定位set的key
     * @param items
     *            要移除的项集
     *
     * @return  实际移除了的项的个数
     * @date 2020/3/11 17:20:12
     */
    public  long zRemove(String key, Object... items) {
        log.info("zRemove(...) => key -> {}, items -> {}", key, items);
        Long count = redisTemplate.opsForZSet().remove(key, items);
        log.info("zRemove(...) => count -> {}", count);
        if (count == null) {
            throw new NullPointerException("未知异常");
        }
        return count;
    }


    /**
     * 增/减 (key对应的zset中,)item的分数值
     *
     * @param key
     *            定位zset的key
     * @param item
     *            项
     * @param delta
     *            变化量(正 - 增, 负 - 减)
     * @return 修改后的score值
     * @date 2020/3/12 8:55:38
     */
    public  Double zIncrementScore(String key, String item, Double delta) {
        log.info("zIncrementScore(...) => key -> {}, item -> {}, delta -> {}", key, item, delta);
        Double scoreValue  = redisTemplate.opsForZSet().incrementScore(key, item, delta);
        log.info("zIncrementScore(...) => scoreValue -> {}", scoreValue);
        return scoreValue;
    }



    /**
     * 增加item的分数值 1
     * */
    public  Double zIncrementScore(String key, String item) {
        return this.zIncrementScore(key,item,1.0);
    }


    /**
     * 根据索引位置， 获取(key对应的)zset中排名处于[start, end]中的item项集
     *
     * 注: 不论是使用正向排名，还是使用反向排名, 使用此方法时, 应保证 startIndex代表的元素的
     *      位置在endIndex代表的元素的位置的前面， 如:
     *      示例一: RedisUtil.ZSetOps.zRange("name", 0, 2);
     *      示例二: RedisUtil.ZSetOps.zRange("site", -2, -1);
     *      示例三: RedisUtil.ZSetOps.zRange("foo", 0, -1);
     *
     * 注: 若key不存在, 则返回空的集合。
     *
     * 注: 当[start, end]的范围比实际zset的范围大时, 返回范围上"交集"对应的项集合。
     *
     * @param key
     *            定位zset的key
     * @param start
     *            排名开始位置
     * @param end
     *            排名结束位置
     *
     * @return  对应的item项集
     * @date 2020/3/12 9:50:40
     */
    public  Set<Object> zRange(String key, long start, long end) {
        log.info("zRange(...) => key -> {}, start -> {}, end -> {}", key, start, end);
        Set<Object> result = redisTemplate.opsForZSet().range(key, start, end);
        log.info("zRange(...) => result -> {}", result);
        return result;
    }


    /**
     * 获取(key对应的)zset中的所有item项

     * @param key
     *            定位zset的键
     *
     * @return  (key对应的)zset中的所有item项
     * @date 2020/3/12 10:02:07
     */
    public  Set<Object> zWholeZSetItem(String key) {
        log.info("zWholeZSetItem(...) => key -> {}", key);
        Set<Object> result = redisTemplate.opsForZSet().range(key, 0, -1);
        log.info("zWholeZSetItem(...) =>result -> {}", result);
        return result;
    }

        /**
         * 返回item在(key对应的)zset中的(按score从小到大的)排名
         *
         * 注: 排名从0开始。 即意味着，此方法等价于: 返回item在(key对应的)zset中的位置索引。
         * 注: 若key或item不存在， 返回null。
         * 注: 排序规则是score,item, 即:优先以score排序，若score相同，则再按item排序。
         *
         * @param key
         *            定位zset的key
         * @param item
         *            项
         *
         * @return 排名(等价于: 索引)
         * @date 2020/3/12 9:14:09
         */
    public  long zRank(String key, Object item) {
        log.info("zRank(...) => key -> {}, item -> {}", key, item);
        Long rank = redisTemplate.opsForZSet().rank(key, item);
        log.info("zRank(...) => rank -> {}", rank);
        return rank;
    }


    /**
     * 移除(key对应的)zset中, score范围在[minScore, maxScore]内的item
     *
     * 提示: 虽然删除范围包含两侧的端点(即:包含minScore和maxScore), 但是由于double存在精度问题，所以建议:
     *          设置值时，minScore应该设置得比要删除的项里，最小的score还小一点
     *                   maxScore应该设置得比要删除的项里，最大的score还大一点
     *          追注: 本人简单测试了几组数据，暂未出现精度问题。
     *
     * 注:若key不存在，则返回0
     *
     * @param key
     *            定位set的key
     * @param minScore
     *            score下限(含这个值)
     * @param maxScore
     *            score上限(含这个值)
     *
     * @return  实际移除了的项的个数
     * @date 2020/3/11 17:20:12
     */
    public  long zRemoveRangeByScore(String key, double minScore, double maxScore) {
        log.info("zRemoveRangeByScore(...) => key -> {}, startIndex -> {}, startIndex -> {}",
                key, minScore, maxScore);
        Long count = redisTemplate.opsForZSet().removeRangeByScore(key, minScore, maxScore);
        log.info("zRemoveRangeByScore(...) => count -> {}", count);
        return count;
    }


    /**
     * 移除(key对应的)zset中, 排名范围在[startIndex, endIndex]内的item
     *
     * 注:默认的，按score.item升序排名， 排名从0开始
     *
     * 注: 类似于List中的索引, 排名可以分为多个方式:
     *     从前到后(正向)的排名: 0、1、2...
     *     从后到前(反向)的排名: -1、-2、-3...
     *
     * 注: 不论是使用正向排名，还是使用反向排名, 使用此方法时, 应保证 startRange代表的元素的位置
     *     在endRange代表的元素的位置的前面， 如:
     *      示例一: RedisUtil.ZSetOps.zRemoveRange("name", 0, 2);
     *      示例二: RedisUtil.ZSetOps.zRemoveRange("site", -2, -1);
     *      示例三: RedisUtil.ZSetOps.zRemoveRange("foo", 0, -1);
     *
     * 注:若key不存在，则返回0
     *
     * @param key
     *            定位set的key
     * @param startRange
     *            开始项的排名
     * @param endRange
     *            结尾项的排名
     *
     * @return  实际移除了的项的个数
     * @date 2020/3/11 17:20:12
     */
    public  long zRemoveRange(String key, long startRange, long endRange) {
        log.info("zRemoveRange(...) => key -> {}, startRange -> {}, endRange -> {}",
                key, startRange, endRange);
        Long count = redisTemplate.opsForZSet().removeRange(key, startRange, endRange);
        log.info("zRemoveRange(...) => count -> {}", count);
        if (count == null) {
            throw new NullPointerException();
        }
        return count;
    }





}