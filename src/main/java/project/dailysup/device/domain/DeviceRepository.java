package project.dailysup.device.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.dailysup.account.domain.Account;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {


    List<Device> findByAccount(Account account);

    @Query("select d from Device d " +
            "join fetch d.account a " +
            "where a in :accounts")
    List<Device> findByAccountList(@Param("accounts") List<Account> accountList);
}
