package com.example.azprojectpallet.service;

import com.example.azprojectpallet.domain.Pallet;
import com.example.azprojectpallet.repo.PalletRepository;
import com.example.azprojectpallet.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PalletService {
    private final PalletRepository palletRepository;
    public List<Pallet> findAll(){
        return palletRepository.findAll();
    }

}
