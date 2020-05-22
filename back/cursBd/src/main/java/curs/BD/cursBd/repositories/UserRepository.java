package curs.BD.cursBd.repositories;

import curs.BD.cursBd.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String name);
}
