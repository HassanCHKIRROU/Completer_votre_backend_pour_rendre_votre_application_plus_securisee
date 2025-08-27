package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {

    private UserRepository repo;
    private PasswordEncoder encoder;
    private UserService service;

    
    @BeforeEach
    void setUp() {
        repo = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        service = new UserServiceImpl(repo, encoder);
    }

    
    
    @Test
    void findAll_returnsList() {
        when(repo.findAll()).thenReturn(List.of(
                buildUser(1, "u1"),
                buildUser(2, "u2")
        ));

        assertThat(service.findAll())
                .hasSize(2)
                .extracting(User::getUsername)
                .containsExactly("u1", "u2");
    }

    
    
    @Test
    void findById_existing_returnsValue() {
        User u = buildUser(10, "user");
        when(repo.findById(10)).thenReturn(Optional.of(u));

        assertThat(service.findById(10)).isPresent();
    }

    
    
    @Test
    void findById_unknown_returnsEmpty() {
        when(repo.findById(5)).thenReturn(Optional.empty());
        assertThat(service.findById(5)).isEmpty();
   
    }

    
    /*
    @Test
    void findByUsername_delegatesToRepo_andWrapsOptional() {
        User u = buildUser(1, "john");
        when(repo.findByUsername("john")).thenReturn(u);

        assertThat(service.findByUsername("john"))
                .isPresent()
                .get()
                .extracting(User::getUsername)
                .isEqualTo("john");
    }
*/
   
    
    @Test
    void save_encodesPassword_andPersists() {
        User u = new User();
        u.setUsername("john");
        u.setPassword("RawPass1!");
        u.setFullname("John D");
        u.setRole("USER");

        when(encoder.encode("RawPass1!")).thenReturn("ENCODED");
        when(repo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = service.save(u);

        verify(encoder).encode("RawPass1!");
        verify(repo).save(u);
        assertThat(saved.getPassword()).isEqualTo("ENCODED");
    }

    
    
    @Test
    void update_existing_updatesAndEncodesIfProvided() {
        User existing = buildUser(1, "old");
        existing.setPassword("OLD_HASH");
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(encoder.encode("NewPass1!")).thenReturn("NEW_HASH");
        when(repo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User changes = new User();
        changes.setUsername("new");
        changes.setFullname("New Name");
        changes.setRole("ADMIN");
        changes.setPassword("NewPass1!"); // sera encodé

        User updated = service.update(1, changes);

        assertThat(updated.getUsername()).isEqualTo("new");
        assertThat(updated.getFullname()).isEqualTo("New Name");
        assertThat(updated.getRole()).isEqualTo("ADMIN");
        assertThat(updated.getPassword()).isEqualTo("NEW_HASH");
        verify(repo).save(existing);
    }

    
    
    @Test
    void update_existing_doesNotChangePassword_whenBlank() {
        User existing = buildUser(1, "user");
        existing.setPassword("EXISTING_HASH");
        when(repo.findById(1)).thenReturn(Optional.of(existing));
        when(repo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User changes = new User();
        changes.setUsername("user2");
        changes.setFullname("User Two");
        changes.setRole("USER");
        changes.setPassword(""); // blank => ne doit pas encoder/changer

        User updated = service.update(1, changes);

        assertThat(updated.getPassword()).isEqualTo("EXISTING_HASH");
        verify(encoder, never()).encode(anyString());
        verify(repo).save(existing);
    }

    
    
    @Test
    void update_unknown_throws() {
        when(repo.findById(404)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(404, new User()))
                .isInstanceOf(NoSuchElementException.class);
        verify(repo, never()).save(any());
    }

    
    
    @Test
    void delete_existing_ok() {
        when(repo.existsById(2)).thenReturn(true);
        service.deleteById(2);
        verify(repo).deleteById(2);
    }

    
    
    @Test
    void delete_unknown_throws() {
        when(repo.existsById(3)).thenReturn(false);
        assertThatThrownBy(() -> service.deleteById(3))
                .isInstanceOf(NoSuchElementException.class);
        verify(repo, never()).deleteById(anyInt());
    }

    // helper
    private static User buildUser(Integer id, String username) {
        User u = new User();
        u.setId(id);
        u.setUsername(username);
        u.setPassword("hash");
        u.setFullname("Full " + username);
        u.setRole("USER");
        return u;
    }
}
