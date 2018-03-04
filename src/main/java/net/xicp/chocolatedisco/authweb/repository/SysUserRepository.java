package net.xicp.chocolatedisco.authweb.repository;

import net.xicp.chocolatedisco.authweb.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {

    List<SysUser> findByIdIn(List<Long> ids);

    SysUser findByAccount(String account);
}
