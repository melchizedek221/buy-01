package com.secke.user_service.Controller;


import com.secke.user_service.Model.Product;
import com.secke.user_service.Model.User;
import com.secke.user_service.Model.UserCustomize;
import com.secke.user_service.Model.UserPrincipal;
import com.secke.user_service.Service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    // Add a rate limiter
    private Bucket bucket;

    public UserController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
    @Autowired
    UserService userServ;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserCustomize>> getAllUsers() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(userServ.getAllUsers());
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserCustomize> createUser(@Valid @RequestBody User user) {
        if (bucket.tryConsume(1)) {
            User createdUser = userServ.addUser(user);
            return new ResponseEntity<>(new UserCustomize(createdUser), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal.getId() == #id")
    @GetMapping("/{id}")
    public ResponseEntity<UserCustomize> getUserById(@PathVariable String id) {
            if (bucket.tryConsume(1)) {
                User user = userServ.getUserById(id);
                // we use the userCustomize to avoid to return the sensitives data in the resp like the password
                UserCustomize userCustomize  = new UserCustomize(user);
                return ResponseEntity.ok(userCustomize);
            }
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("authentication.principal.getId() == #id")
    @PutMapping("/{id}")
    public ResponseEntity<UserCustomize> updateUser(@PathVariable String id, @Valid @RequestBody User userDetails) {
        if (bucket.tryConsume(1)) {
            User user = userServ.updateUser(id, userDetails);
            return ResponseEntity.ok(new UserCustomize(user));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal.getId() == #id")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userServ.deleteUser(id);
    }

    @GetMapping("/me")
    @PostAuthorize("returnObject.body != null and returnObject.body.name == authentication.principal.user.getName()")
    public ResponseEntity<UserCustomize> getCurrentUser(@AuthenticationPrincipal UserPrincipal user) {
        if (bucket.tryConsume(1)) {
            UserCustomize userCustomize = new UserCustomize(user.user);
            return ResponseEntity.ok(userCustomize);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    // The user externals resources

    @GetMapping("get/{userId}")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable String userId) {
        List<Product> myProducts = userServ.getUserProducts(userId);
        return ResponseEntity.ok(myProducts);
    }
}
