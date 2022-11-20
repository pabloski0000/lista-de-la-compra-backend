package com.web.listacompra.infrastracture.userRepository;

import java.util.List;
import java.util.Optional;

import com.web.listacompra.domain.userDomain.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserConnectionMongoDBRepository extends MongoRepository<User, String> {
    @Query("{nickName:'?0'}")
    Optional<User> findUserByNickName(String nickName);
    void deleteUserByNickName(String nickName);
    
}