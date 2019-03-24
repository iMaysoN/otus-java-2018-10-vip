package ru.otus.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Phone> phones = new HashSet<>();

    public User() {
    }

    public User(String name, String password) {
        this.setId(-1);
        this.name = name;
        this.password = password;
        this.age = 18;
    }

    public User(String name, int age) {
        this.setId(-1);
        this.name = name;
        this.password = "password";
        this.age = age;
    }

    public User(String name, int age, String phone, String address) {
        this(name, age);
        this.phones = Set.of(new Phone(phone, this));
        this.address = new Address(address);
    }

    public User(String name, int age, Set<Phone> phones, Address address) {
        this.setId(-1);
        this.name = name;
        this.age = age;
        this.phones = phones;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public void setPhone(Phone phone) { this.phones.add(phone); }

    @Override
    public String toString() {
        long id = this.id;
        String name = this.name;
        String address = this.address.toString();
        String phones = this.phones.toString();
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return (id == user.id)
                && (age == user.age)
                && Objects.equals(name, user.name)
                && Objects.equals(address, user.address)
                && phones != null ? phones.stream().anyMatch(phone -> user.phones.contains(phone)) : user.phones == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        return result;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
