package org.sfcmn.basics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

class Address implements Cloneable, Serializable {
  private String street;
  private String city;
  private String country;

  @SuppressWarnings("unused")
  public Address() {
  }

  public Address(String street, String city, String country) {
    this.street = street;
    this.city = city;
    this.country = country;
  }

  public Address(Address that) {
    this(that.getStreet(), that.getCity(), that.getCountry());
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  @Override
  public Address clone() {
    try {
      return (Address) super.clone();
    } catch (CloneNotSupportedException e) {
      return new Address(this.street, this.getCity(), this.getCountry());
    }
  }
}

class User implements Cloneable, Serializable {
  private String firstName;
  private String lastName;
  private Address address;

  @SuppressWarnings("unused")
  public User() {
  }

  public User(String firstName, String lastName, Address address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
  }

  @SuppressWarnings("CopyConstructorMissesField")
  public User(User that) {
    this(that.getFirstName(), that.getLastName(), new Address(that.getAddress()));
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Address getAddress() {
    return address;
  }

  @Override
  public User clone() {
    User user;
    try {
      user = (User) super.clone();
    } catch (CloneNotSupportedException e) {
      user = new User(this.getFirstName(), this.getLastName(), this.getAddress());
    }
    user.address = this.address.clone();
    return user;
  }
}

public class DeepClone {
  private static void shallowClone() {
    Address address = new Address("Downing St 10", "London", "England");
    User user1 = new User("Prime", "Minister", address);

    @SuppressWarnings("UnnecessaryLocalVariable")
    User user2 = user1;

    user1.getAddress().setCity("Xi'an");
    System.out.println(user2.getAddress().getCity().equals(user1.getAddress().getCity()));
  }

  private static void deepCloneWithConstructor() {
    Address address = new Address("Downing St 10", "London", "England");
    User user1 = new User("Prime", "Minister", address);

    User user2 = new User(user1);

    user1.getAddress().setCity("Xi'an");
    System.out.println(user2.getAddress().getCity().equals(user1.getAddress().getCity()));
  }

  private static void deepCloneWithCloneableInterface() {
    Address address = new Address("Downing St 10", "London", "England");
    User user1 = new User("Prime", "Minister", address);

    User user2 = user1.clone();

    user1.getAddress().setCity("Xi'an");
    System.out.println(user2.getAddress().getCity().equals(user1.getAddress().getCity()));
  }

  private static void deepCloneWithApacheCommonLang() {
    Address address = new Address("Downing St 10", "London", "England");
    User user1 = new User("Prime", "Minister", address);

    User user2 = SerializationUtils.clone(user1);

    user1.getAddress().setCity("Xi'an");
    System.out.println(user2.getAddress().getCity().equals(user1.getAddress().getCity()));
  }

  private static void deepCloneWithGoogleGson() {
    Address address = new Address("Downing St 10", "London", "England");
    User user1 = new User("Prime", "Minister", address);

    Gson gson = new Gson();
    User user2 = gson.fromJson(gson.toJson(user1), User.class);

    user1.getAddress().setCity("Xi'an");
    System.out.println(user2.getAddress().getCity().equals(user1.getAddress().getCity()));
  }

  private static void deepCloneWithJackson() {
    Address address = new Address("Downing St 10", "London", "England");
    User user1 = new User("Prime", "Minister", address);

    ObjectMapper objectMapper = new ObjectMapper();
    User user2;
    try {
      user2 = objectMapper.readValue(objectMapper.writeValueAsString(user1), User.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    user1.getAddress().setCity("Xi'an");
    System.out.println(user2.getAddress().getCity().equals(user1.getAddress().getCity()));
  }

  public static void main(String[] args) {
    // 浅拷贝
    shallowClone(); // 拷贝前后一致

    // 构造函数深拷贝
    deepCloneWithConstructor(); // 拷贝前后不一致
    // 实现 Cloneable 接口进行深拷贝
    deepCloneWithCloneableInterface(); // 拷贝前后不一致

    // Apache Common Lang 深拷贝
    deepCloneWithApacheCommonLang(); // 拷贝前后不一致

    // Google Gson 深拷贝
    deepCloneWithGoogleGson(); // 拷贝前后不一致

    // Jackson 深拷贝
    deepCloneWithJackson(); // 拷贝前后不一致
  }
}
