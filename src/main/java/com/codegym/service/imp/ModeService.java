package com.codegym.service.imp;

import com.codegym.model.Mode;
import com.codegym.repository.IModeRepository;
import com.codegym.service.IModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeService implements IModeService {
    @Autowired
    IModeRepository modeRepository;
    @Override
    public Mode findModeById(Long id) {
        return modeRepository.findById(id).get();
    }
}
