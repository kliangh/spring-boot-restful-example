package code.kliangh.user.repository;

import code.kliangh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    //find by name or surname
    User findByName(String name);
    User findBySurname(String surname);

    //find by name and surname
    User findByNameAndSurname(String name, String surname);
}
