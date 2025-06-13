package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.Publisher;
import com.bookoryteam.bookory.repository.PublisherRepository;
import com.bookoryteam.bookory.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    @Override
    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public Publisher findByName(String name) {
        return publisherRepository.findByName(name);
    }

    @Override
    public List<Publisher> findByDeletedFalse() {
        return publisherRepository.findByDeletedFalse();
    }

    @Override
    public void softDelete(Long id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            Publisher publisher = publisherOptional.get();
            publisher.setDeleted(true);
            publisherRepository.save(publisher);
        }
    }

    // ✅ THÊM MỚI: Hàm đếm số lượng publisher
    @Override
    public int countPublishers() {
        return (int) publisherRepository.count();
    }
}
