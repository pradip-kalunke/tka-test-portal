package com.tka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.ModeratorDao;
import com.tka.model.Moderator;

@Service
public class ModeratorService {
	
    @Autowired
    private ModeratorDao moderatorDao;

    public Moderator saveModerator(Moderator moderator) {
        return moderatorDao.save(moderator);
    }

    public Optional<Moderator> findById(int cid) {
        return moderatorDao.findById(cid);
    }

    public List<Moderator> findAll() {
        return moderatorDao.findAll();
    }

    public boolean deleteModerator(int cid) {
        if (moderatorDao.existsById(cid)) {
            moderatorDao.deleteById(cid);
            return true;
        }
        return false;
    }

    public Moderator authenticateModerator(String email, String mobile) {
        return moderatorDao.findByEmailAndMobile(email, mobile);
    }

    public boolean emailExists(String email) {
        return moderatorDao.findByEmail(email) != null;
    }
    
    
    

}
