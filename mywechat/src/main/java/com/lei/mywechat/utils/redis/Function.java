package com.lei.mywechat.utils.redis;
  
public interface Function<T,E> {  
  
      public T callBack(E e);  
}  