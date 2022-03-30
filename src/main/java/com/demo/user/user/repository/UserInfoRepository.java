package com.demo.user.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.user.user.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {


}
