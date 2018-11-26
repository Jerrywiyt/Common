package com.lujunyu.guava.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CacheConfig {
    private long expireAfterWrite = 60L;
    private long refreshAfterWrite = 60L;
    private long maximumSize = 10000L;
    private boolean isCacheNull = true;
}