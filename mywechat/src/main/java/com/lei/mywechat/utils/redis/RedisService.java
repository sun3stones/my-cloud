package com.lei.mywechat.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service  
public class RedisService {  
    @Autowired  
    private ShardedJedisPool  shardedJedisPool;  
      
    public <T>T excute(Function<T,ShardedJedis> fun){  
        ShardedJedis jedisInfo=null;  
        try{  
            jedisInfo=shardedJedisPool.getResource();  
            return fun.callBack(jedisInfo);  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            if(jedisInfo!=null){  
                
            }  
              
        }  
        return null;  
    }  
    /** 
     * 获取值 
     * @param key 
     * @return 
     */  
    public String get(final String key){  
        return this.excute(new Function<String,ShardedJedis>(){  
            @Override  
            public String callBack(ShardedJedis e) {  
                return e.get(key);  
                  
            }  
              
        });  
    }  
    /** 
         * 获取值 
         * @param key 
         * @return 
         */  
        public Set<String> hvals(){  
                return this.excute(new Function<Set<String>,ShardedJedis>(){  
                        @Override  
                        public  Set<String> callBack(ShardedJedis e) {  
                            return e.hkeys("1*");  
                        }  
                          
                });  
        }  
      
    /** 
     * 设置值 
     * @param key 
     * @param value 
     * @return 
     */  
    public String set(final String key,final String value){  
        return this.excute(new Function<String,ShardedJedis>(){  
            @Override  
            public String callBack(ShardedJedis e) {  
                return e.set(key, value);  
            }  
              
        });  
    }  
      
      
    public String set(final String key,final Map<String,String>hash){  
            return this.excute(new Function<String,ShardedJedis>(){  
                    @Override  
                    public String callBack(ShardedJedis e) {  
                            return e.hmset(key, hash);  
                    }  
                      
            });  
    }  
               public List<String> get(final String key,final String fields){  
                    return this.excute(new Function<List<String> ,ShardedJedis>(){  
                            @Override  
                            public List<String>  callBack(ShardedJedis e) {  
                                    return e.hmget(key, fields);  
                            }  
                              
                    });  
            }  
          
      
    /** 
         * 设置值 
         * @param key 
         * @param value 
         * @return 
         */  
        public String set(final byte[] key,final byte[]value){  
                return this.excute(new Function<String,ShardedJedis>(){  
                        @Override  
                        public String callBack(ShardedJedis e) {  
                                return e.set(key, value);  
                        }  
                          
                });  
        }  
          
        /** 
         * 通过字节获取key 
         * @param key 
         * @param value 
         * @return 
         */  
        public byte[] get(final byte[] key){  
                return this.excute(new Function<byte[],ShardedJedis>(){  
                        @Override  
                        public byte[] callBack(ShardedJedis e) {  
                                return e.get(key);  
                        }  
                          
                });  
        }  
    /** 
     * 删除 
     * @param key 
     * @return 
     */  
    public Long decr(final String key){  
        return this.excute(new Function<Long,ShardedJedis>(){  
            @Override  
            public Long callBack(ShardedJedis e) {  
                return e.decr(key);  
            }  
              
        });  
    }  
    /** 
         * 删除 
         * @param key 
         * @return 
         */  
        public Long del(final String key){  
                return this.excute(new Function<Long,ShardedJedis>(){  
                        @Override  
                        public Long callBack(ShardedJedis e) {  
                                return e.del(key);  
                        }  
                          
                });  
        }  
    /** 
     * 设置时间 
     * @param key 
     * @param seconds 
     * @return 
     */  
   public Long expir(final String key,final Integer seconds){  
       return this.excute(new Function<Long,ShardedJedis>(){  
        @Override  
        public Long callBack(ShardedJedis e) {  
            return e.expire(key, seconds);  
        }  
       });  
   }  
     
    /** 
     * 插入的同时设置时间 
     * @param key 
     * @param seconds 
     * @return 
     */  
   public String  setAndExpir(final String key,final String value,final Integer seconds){  
       return this.excute(new Function<String,ShardedJedis>(){  
        @Override  
        public String callBack(ShardedJedis e) {  
            String str=e.set(key, value);  
            e.expire(key, seconds);  
            return str;  
        }  
       });  
   }  
     
   public static byte[]serialize(Object object){  
       ObjectOutputStream oos=null;  
       ByteArrayOutputStream baos=null;  
       try{  
           baos=new ByteArrayOutputStream();  
           oos=new ObjectOutputStream(baos);  
           oos.writeObject(object);  
           byte[]bytes=baos.toByteArray();  
           return bytes;  
       }catch(Exception e){  
           e.printStackTrace();  
       }finally{  
           try {    
               if(oos!=null){  
                   oos.close();  
               }  
               if(baos!=null){  
                   baos.close();  
               }  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
       return null;  
   }  
     
   public static Object unserialize(byte[]bytes){  
       ByteArrayInputStream bais=null;  
       ObjectInputStream ois =null;  
       try{  
           bais=new ByteArrayInputStream(bytes);  
            ois =new ObjectInputStream(bais);  
           return ois.readObject();  
       }catch(Exception e){  
           e.printStackTrace();  
       }finally{  
           try {  
               if(bais!=null){  
                   bais.close();  
               }  
               if(ois!=null){  
                   ois.close();  
               }  
             
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
       }  
       return null;  
   }  
     
     
}  
