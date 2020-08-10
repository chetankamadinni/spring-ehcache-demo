package com.io.chetan.kamadinni.springehcachedemo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
@EnableJpaRepositories(basePackages = "com.io.chetan.kamadinni.springehcachedemo.repository")
public class EhCacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(cacheMangerFactory());
	}

	@Bean
	public net.sf.ehcache.CacheManager cacheMangerFactory() {
		CacheConfiguration tenSecondCache = new CacheConfiguration();
		tenSecondCache.setName("ten-second-cache");
		tenSecondCache.setMemoryStoreEvictionPolicy("LRU");
		tenSecondCache.setMaxEntriesLocalHeap(1000);
		tenSecondCache.setTimeToLiveSeconds(10);

		CacheConfiguration twentySecondCache = new CacheConfiguration();
		twentySecondCache.setName("twenty-second-cache");
		twentySecondCache.setMemoryStoreEvictionPolicy("LRU");
		twentySecondCache.setMaxEntriesLocalHeap(1000);
		twentySecondCache.setTimeToLiveSeconds(20);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(tenSecondCache);
		config.addCache(twentySecondCache);

		return net.sf.ehcache.CacheManager.newInstance(config);
	}

}
