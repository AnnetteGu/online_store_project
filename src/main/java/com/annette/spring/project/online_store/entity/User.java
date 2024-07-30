package com.annette.spring.project.online_store.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private double balance;

    @Column(name = "role")
    private String role;

    @Column(name = "access_mode")
    private boolean accessMode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id")
    private Settings settings;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<PurchaseHistory> purchaseHistories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private List<Product> sellerProducts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Review> userReviews;

    @ManyToMany(cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    })
    @JoinTable(
        name = "users_baskets",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "basket_id")
    )
    private List<Basket> userBaskets;

    public User(int id, String name, String login, String password, double balance, String role, boolean accessMode) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.role = role;
        this.accessMode = accessMode;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", login=" + login + ", password=" + password + ", balance="
                + balance + ", role=" + role + ", accessMode=" + accessMode + "]";
    }
    
}
