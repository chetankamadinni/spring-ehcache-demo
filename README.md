# spring-ehcache-demo
Demo of caching in a spring-boot app using EhCache provider


EhCache is one of the cache provider. 
Here we are creating a simple spring-boot app with the following dependency
- WEB
- JPA
- H2(in-memory DB)
- Cache
- Lombok
- Ehcache

We are storing certain fixed set of entries(Employee) into the H2 DB once the app starts up and provide an endpoint to fetch the these details.
To check the caching functionality we have introduced certain delay in our service layer.
We need to provide certain configurations for spring to identify the cache provider during boot up. 
To do so, create a configuration class and annotate it with `@EnableCaching` and provide a bean for cacheManager.
To this cacheManager provide configuration related to ehCache by defining a bean with all the cache related configurations. 
Here you can provide multiple cache configurations based on your requirements.



```java

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
```

After this configuration, you can add caching functionlities to your modules by annotating the methods as below
```java
@Cacheable(value = "twenty-second-cache", key = "#empName")
public Employee getEmployee(String empName) {
	log.info("Get Employee {} from repository", empName);
	try {
		Thread.sleep(3000L);
	} catch (InterruptedException e) {
		log.error("Exception while calling Thread.sleep : " + e);
	}
	return empRepository.findByFirstName(empName);
}
```
Here, `value` is the name of the cache that is configured before in the configuration class and `key` holds the cache key which is used for storing and retriving.
There are some other annotations such as `@CacheEvict` to remove the cache entries and `@CachePut` to update the cache entries. 
These annotations support some properties like `condition`, `unless`,  that can be used as a clauses for validating the cache operations.
To test the cache functionality, you can hit the endpoint http://localhost:8085/api/employee/Chetan. 
Here first call will take more that 3secs(because of delay that is introduced in the code) but correspoding calls will take few milli secs.
