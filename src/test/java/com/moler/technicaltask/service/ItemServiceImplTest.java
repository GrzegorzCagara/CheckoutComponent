package com.moler.technicaltask.service;


import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.exception.ItemNotFoundRunTimeException;
import com.moler.technicaltask.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceImplTest {

    @Mock
    private ItemRepository mockItemRepository;

    @InjectMocks
    private ItemServiceImpl testedObject;


    @Test
    public void shouldAddNewItem() {
        Item itemtoAdd = new Item();

        when(mockItemRepository.save(itemtoAdd)).thenReturn(itemtoAdd);

        testedObject.save(itemtoAdd);

        verify(mockItemRepository, times(1)).save(itemtoAdd);

    }

    @Test
    public void shouldReturnAllItems() {
        when(mockItemRepository.findAll()).thenReturn(getAllItems());

        List<Item> items = testedObject.findAll();

        assertThat(items).isNotNull();
        assertThat(items).isNotEmpty();
        assertThat(items.size()).isEqualTo(4);
    }

    @Test
    public void shouldReturnSpecificItemWhenIdPassed() {
        Item item = new Item();
        item.setId(15L);
        when(mockItemRepository.findItemById(15)).thenReturn(java.util.Optional.of((item)));

        Item foundItem = testedObject.findItemById(15);
        assertThat(foundItem).isNotNull();
        assertThat(item).isEqualTo(item);
        assertThat(foundItem.getId()).isEqualTo(15);
    }

    @Test(expected = ItemNotFoundRunTimeException.class)
    public void shouldThrowItemNotFoundRunTimeExceptionWhenItemNotFound() {
        when(mockItemRepository.findItemById(10)).thenReturn(Optional.empty());
        testedObject.findItemById(10);
    }

    private List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        result.add(new Item());
        result.add(new Item());
        result.add(new Item());
        result.add(new Item());
        return result;
    }
}