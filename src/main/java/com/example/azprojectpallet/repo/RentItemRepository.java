package com.example.azprojectpallet.repo;

import com.example.azprojectpallet.domain.PalleItemState;
import com.example.azprojectpallet.domain.Pallet;
import com.example.azprojectpallet.domain.RentItem;
import com.example.azprojectpallet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentItemRepository extends JpaRepository<RentItem,Long> {
//    RentItem findByPallet(Pallet pallet);
    List<RentItem> findByRentAccount(User user);
    RentItem findByPalletId(String id);
}
