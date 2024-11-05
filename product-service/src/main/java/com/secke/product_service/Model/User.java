package com.secke.product_service.Model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Field
    @NotNull(message = "name is require")
    private String name;

    @Field
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Indexed(unique = true)
    private String email;

    @Field
    @NotNull(message = "Password is required")
    @Size(min = 4, max = 8, message = "The password must be between 4 and 8 digits")
//    @JsonIgnore
    private String password;

    @Field
    private Role role = Role.CLIENT;


    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role == Role.SELLER ? role : Role.CLIENT;
    }

    // UserDetails implementation
    //    @Override
    //    public Collection<? extends GrantedAuthority> getAuthorities() {
    //        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.toUpperCase()));
    //    }
    //    @Override
    //    public String getUsername() {
    //
    //        return getName();
    //    }
    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
