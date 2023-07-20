package com.eroulri.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.eroulri.api.model.RefreshToken;

public interface RedisRefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
