package com.example.azprojectpallet.repo;

import com.example.azprojectpallet.domain.PalleItemState;
import com.example.azprojectpallet.domain.Pallet;
import com.example.azprojectpallet.domain.RentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface PalletRepository extends JpaRepository<Pallet,Long> {
    Optional<Pallet> findByName(String name);
    Optional<Pallet> findByFront(String name);

}
