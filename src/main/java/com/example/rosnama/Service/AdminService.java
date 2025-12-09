package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    // connect to database
    private final AdminRepository adminRepository;

    // get all admins information from the database
    public List<Admin> getAllAdmins() {


        return adminRepository.findAll();
    }

    // add a new admin information to the database
    public  void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }


    // update admin information in the database

    public  void updateAdmin(Integer id, Admin admin) {


        Admin old = adminRepository.findAdminById(id);

        // check if the old admin  exists
        if (old == null) {
            throw new ApiException("admin not found");
        }

        // update
        old.setUsername(admin.getUsername());
        old.setEmail(admin.getEmail());
        old.setPassword(admin.getPassword());
        old.setPhoneNumber(admin.getPhoneNumber());

        // save
        adminRepository.save(old);
    }


    // delete admin information from the database
    public  void deleteAdmin(Integer id) {

        Admin admin = adminRepository.findAdminById(id);

        // check if the admin exists
        if (admin == null) {
            throw new ApiException("admin not found");
        }

        adminRepository.delete(admin);
    }


}
