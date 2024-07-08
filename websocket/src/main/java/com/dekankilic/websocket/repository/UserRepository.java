package com.dekankilic.websocket.repository;

import com.dekankilic.websocket.model.User;
import com.dekankilic.websocket.model.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByStatus(Status status);
}
