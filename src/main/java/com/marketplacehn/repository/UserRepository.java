package com.marketplacehn.repository;

import com.marketplacehn.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE " +
            "u.userName = :userName " +
            "AND u.userStatus = :userStatus")
    Optional<User> findByUserNameAndUserStatus(@Param("userName") String userName,
                                               @Param("userStatus") int userStatus);

    Page<User> findByUserNameContaining(String userName, Pageable pageRequest);
}
