
package com.web.blog.dao;

import java.util.Optional;

import com.web.blog.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AdminDao extends JpaRepository<Admin, String> {
    Optional<Admin> findAdminByAdminIdAndPassword(String adminId, String password);
    Optional<Admin> getAdminByAdminIdAndPassword(String adminId, String password);
}
