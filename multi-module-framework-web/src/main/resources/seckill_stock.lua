-- 秒杀库存扣减Lua脚本（原子操作，防超卖/防重复秒杀）
-- 适配Redis Key前缀：seckill_stock_活动ID、seckill_user_order_活动ID
-- KEYS[1]：库存Key（如seckill_stock_1）
-- KEYS[2]：用户防重Key（如seckill_user_order_1）
-- ARGV[1]：用户ID（如1001）
-- ARGV[2]：秒杀数量（茅台秒杀固定为1）

-- 1. 检查用户是否已参与秒杀（防重复下单）
-- sismember：判断userId是否在userOrderKey集合中，存在返回1，不存在返回0
if redis.call('sismember', KEYS[2], ARGV[1]) == 1 then
    return 0  -- 返回0：用户已秒杀，禁止重复下单
end

-- 2. 获取当前库存并校验是否充足
-- get：获取库存Key的值，若Key不存在返回nil，用or "0"转为字符串0
local stock = tonumber(redis.call('get', KEYS[1]) or "0")
local num = tonumber(ARGV[2])  -- 秒杀数量转为数字

-- 库存不足时直接返回
if stock < num then
    return -1  -- 返回-1：库存不足
end

-- 3. 原子扣减库存（decrby是原子操作，避免并发超卖）
redis.call('decrby', KEYS[1], num)

-- 4. 记录用户秒杀记录（加入集合，防重复）
redis.call('sadd', KEYS[2], ARGV[1])

-- 5. 返回秒杀成功标识
return 1  -- 返回1：秒杀成功