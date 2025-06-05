package com.ezee.identity.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.ezee.identity.exception.ErrorCode;
import com.ezee.identity.exception.ServiceException;

@Service
public class CacheService {
	@Autowired
	private CacheManager cacheManager;

	private static final Logger LOGGER = LogManager.getLogger("com.identity");

	public Object updateCache(String cacheName, String key, Object value) {
		try {
			Cache cache = cacheManager.getCache(cacheName);

			if (cache != null) {
				ValueWrapper getCache = cache.get(key);

				if (getCache != null) {
					value = cache.putIfAbsent(key, value).getClass();
				} else {
					throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
				}
			}

		} catch (Exception e) {
			LOGGER.info("Putting cahce value message", e.getMessage());
		}
		return value;
	}

	public <T> T getCache(String cacheName, String key, Class<T> getClass) {
		T result = null;

		try {
			Cache cache = cacheManager.getCache(cacheName);

			if (cache == null) {
				throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
			}

			result = cache.get(key, getClass);
		} catch (Exception e) {
			LOGGER.info("fetch cahce value message", e.getMessage());
		}

		return result;
	}

	public void removeCache(String cacheName, String key) {
		try {
			Cache cache = cacheManager.getCache(cacheName);

			if (cache == null) {
				throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
			}

			cache.evict(key);
		} catch (Exception e) {
			LOGGER.info("Evicting cahce value message", e.getMessage());

		}
	}

	public <T> List<T> getAllCache(String cacheName, Class<T> clazz) {
		Cache cache = cacheManager.getCache(cacheName);
		List<String> keys = cache.get(cacheName, List.class);

		List<T> result = new ArrayList<>();
		if (keys != null) {
			for (String key : keys) {
				T value = cache.get(key, clazz);
				if (value != null) {
					result.add(value);
				}
			}
		}
		return result;
	}

	public <T> T gettokenCache(String cacheName, String key, Class<T> getClass) {
		T result = null;

		try {
			Cache cache = cacheManager.getCache(cacheName);

			if (cache == null) {
				throw new ServiceException(ErrorCode.USER_NOT_FOUND_EXCEPTION);
			}

			result = cache.get(key, getClass);
		} catch (Exception e) {
			LOGGER.info("fetch cahce value message", e.getMessage());
		}

		return result;
	}
}
