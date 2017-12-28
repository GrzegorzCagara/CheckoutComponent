package com.moler.technicaltask.service;


import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ItemServiceImplTest {

    private ItemService itemService;
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        itemRepository = Mockito.mock(ItemRepository.class);

        itemService = new ItemServiceImpl(itemRepository);
    }

    @Test
    public void shouldAddNewItem() {
        Item itemtoAdd = new Item();

        when(itemRepository.save(itemtoAdd)).thenReturn(itemtoAdd);

        itemService.save(itemtoAdd);

        verify(itemRepository, times(1)).save(itemtoAdd);

    }

    @Test
    public void shouldReturnAllItems() {
        when(itemRepository.findAll()).thenReturn(getAllItems());

        List<Item> items = itemService.findAll();

        assertThat(items).isNotNull();
        assertThat(items).isNotEmpty();
        assertThat(items.size()).isEqualTo(4);
    }

    @Test
    public void shouldFindItemById() {
        Item item = new Item();
        item.setId(15L);
        when(itemRepository.findItemById(15)).thenReturn(java.util.Optional.of((item)));

        Item foundItem = itemService.findItemById(15);
        assertThat(foundItem).isNotNull();
        assertThat(item).isEqualTo(item);
        assertThat(foundItem.getId()).isEqualTo(15);

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