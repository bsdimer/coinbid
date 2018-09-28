package com.madbid.core.service;

import com.ecwid.mailchimp.MailChimpException;
import com.madbid.core.dto.RegistrationData;
import com.madbid.core.exception.DuplicateEmailException;
import com.madbid.core.exception.DuplicateUsernameException;
import com.madbid.core.model.*;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.UserRepository;
import com.madbid.core.repository.specification.UserNameSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by nikolov.
 */
@Service
@Transactional
public class UserService extends BaseService<User> implements ServiceContract<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void delete(User entity) {
        entity.setActive(false);
        super.save(entity);
    }

    public List<User> findByFullName(String name, PageRequest pageRequest) {
        return userRepository.findAll(new UserNameSpecification(name), pageRequest).getContent();
    }

    public User registerNewUserAccount(RegistrationData userAccountData) throws DuplicateEmailException, DuplicateUsernameException, IOException, MailChimpException {
        LOGGER.debug("Registering new user account with information: {}", userAccountData);

        if (emailExist(userAccountData.getEmail())) {
            LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());
            throw new DuplicateEmailException("The email address: " + userAccountData.getEmail() + " is already in use.");
        } else if (usernameExist(userAccountData.getUsername())) {
            LOGGER.debug("Username: {} exists. Throwing exception.", userAccountData.getUsername());
            throw new DuplicateUsernameException("The username: " + userAccountData.getUsername() + " is already in use.");
        }

        LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());

        User registered = createUserObject(userAccountData);
        Address address = createUserAddressObject(userAccountData);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        registered.setAddresses(addresses);

        LOGGER.debug("Persisting new user with information: {}", registered);

        return userRepository.save(registered);
    }

    private User createUserObject(RegistrationData userAccountData) {
        String encodedPassword = encodePassword(userAccountData);

        User registered = new User();
        registered.setUsername(userAccountData.getUsername());
        registered.setEmail(userAccountData.getEmail());
        registered.setFirstName(userAccountData.getFirstName());
        registered.setLastName(userAccountData.getLastName());
        registered.setPassword(encodedPassword);
        registered.setRole(userAccountData.getRole());
        registered.setMobileNumber(userAccountData.getMobileNumber());
        registered.setNewsSubscribed(userAccountData.isNewsSubscribed());
        registered.setActive(userAccountData.isActive());

        if (userAccountData.isSocialSignIn()) {
            registered.setSignInProvider(userAccountData.getSignInProvider());
        }

        return registered;
    }

    private Address createUserAddressObject(RegistrationData userAccountData) {
        Address address = new Address();
        address.setCountry(userAccountData.getCountry());
        address.setCity(userAccountData.getCity());
        address.setStreet1(userAccountData.getStreet1());
        address.setStreet2(userAccountData.getStreet2());
        address.setPostcode(userAccountData.getPostcode());
        return address;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailDeepAddresses(String email) {
        return userRepository.findByEmailDeepAddress(email);
    }

    public User findOneWithAddressesDeep(Long id) {
        return userRepository.findOneWithAddressesDeep(id);
    }

    private boolean emailExist(String email) {
        LOGGER.debug("Checking if email {} is already found from the database.", email);

        User user = userRepository.findByEmail(email);

        if (user != null) {
            LOGGER.debug("User account: {} found with email: {}. Returning true.", user, email);
            return true;
        }

        LOGGER.debug("No user account found with email: {}. Returning false.", email);

        return false;
    }

    private boolean usernameExist(String username) {
        LOGGER.debug("Checking if username {} is already found from the database.", username);

        User user = userRepository.findByUsername(username);

        if (user != null) {
            LOGGER.debug("User account: {} found with username: {}. Returning true.", user, username);
            return true;
        }

        LOGGER.debug("No user account found with username: {}. Returning false.", username);

        return false;
    }

    private String encodePassword(RegistrationData dto) {
        String encodedPassword = null;

        if (dto.isNormalRegistration()) {
            LOGGER.debug("Registration is normal registration. Encoding password.");
            encodedPassword = passwordEncoder.encode(dto.getPassword());
        }

        return encodedPassword;
    }

    public List<Coin> findAvailableCoins(User user) {
        return userRepository.findAvailableCoins(user);
    }

    // ToDo: performance killer must be moved in CoinService, becaouse user.getCoins
    public void addCoins(User user, BigDecimal value, int quantity, Currency currency) {
        float coinValue = value.floatValue() / quantity;
        List<Coin> coinsList = new ArrayList<>();
        while (quantity > 0) {
            Coin coin = new Coin();
            coin.setValue(BigDecimal.valueOf(coinValue));
            coin.setCurrency(currency);
            coin.setUser(user);
            quantity--;
            coinsList.add(coin);
        }

        if (!coinsList.isEmpty()) {
            user.getCoins().addAll(coinsList);
            this.save(user);
        }
    }

    public int getCoinsAvailable(User user) {
        //ToDo: must be implemented by JPQL
        int coins = 0;
        for (Coin coin : user.getCoins()) {
            if (coin.getBid() == null) {
                coins++;
            }
        }
        return coins;
    }

    public int getCoinsSpent(User user) {
        //ToDo: must be implemented by JPQL
        int coins = 0;
        for (Coin coin : user.getCoins()) {
            if (coin.getBid() != null) {
                coins++;
            }
        }
        return coins;
    }

    public void addCoins(User user, CoinPacket coinPacket) {
        addCoins(user, coinPacket.getValue(), coinPacket.getQuantity(), coinPacket.getCurrency());
    }

    public void addCoins(User user, int quantity, Payment payment) {
        addCoins(user, payment.getAmount(), quantity, payment.getCurrency());
    }

    public MadbidRepository getRepository() {
        return userRepository;
    }

    public User findUserWithCoinsDeep(Long id) {
        return userRepository.findUserWithCoinsDeep(id);
    }

    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
