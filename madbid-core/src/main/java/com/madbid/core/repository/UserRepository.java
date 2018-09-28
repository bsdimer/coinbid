package com.madbid.core.repository;

import com.madbid.core.model.Coin;
import com.madbid.core.model.Role;
import com.madbid.core.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by dimer.
 */
public interface UserRepository extends MadbidRepository<User> {
    User findByEmail(String email);

    User findByUsername(String username);

    @Query("select u from User u left join fetch u.addresses where u.email =?1")
    User findByEmailDeepAddress(String email);

    @Query("select c from Coin c where c.user = :user and c.bid is NULL")
    List<Coin> findAvailableCoins(@Param("user") User user);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.coins where u.id = ?1")
    User findUserWithCoinsDeep(Long id);

    List<User> findByRole(Role role);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.addresses a " +
            "where u.id = ?1")
    User findOneWithAddressesDeep(Long id);
}
