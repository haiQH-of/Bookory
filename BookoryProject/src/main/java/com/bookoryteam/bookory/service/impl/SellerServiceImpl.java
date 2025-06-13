package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.Seller;
import com.bookoryteam.bookory.repository.SellerRepository;
import com.bookoryteam.bookory.service.SellerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Optional<Seller> findById(Long userId) {
        return sellerRepository.findById(userId);
    }

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public void deleteById(Long userId) {
        sellerRepository.deleteById(userId);
    }

    @Override
    public Seller findByCompanyName(String companyName) {
        return sellerRepository.findByCompanyName(companyName);
    }

    @Override
    public List<Seller> findByDeletedFalse() {
        return sellerRepository.findByDeletedFalse();
    }

    @Override
    public void softDelete(Long userId) {
        Optional<Seller> sellerOptional = sellerRepository.findById(userId);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setDeleted(true);
            sellerRepository.save(seller);
        }
    }
}