package com.moler.technicaltask;

import com.moler.technicaltask.entity.Item;
import com.moler.technicaltask.exception.ValidationException;
import com.moler.technicaltask.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class TechnicaltaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechnicaltaskApplication.class, args);
	}



	/**
	 * Initialize sample data
	 */
//    @Bean
//    public CommandLineRunner loadData(ItemService itemService){
//        return (args) -> {
//            log.info("INITIALIZE SAMPLE DATA");
//
//            Item pepsi = new Item.Builder()
//                    .withName("Pepsi").withPrice(4.0).withSpecialPrice(10.0).withUnit(4).build();
//            Item chips = new Item.Builder()
//                    .withName("Chips").withPrice(2.45).withSpecialPrice(4.0).withUnit(2).build();
//            Item beer = new Item.Builder()
//                    .withName("Beer").withPrice(2.55).withSpecialPrice(8.0).withUnit(4).build();
//            Item chocolate = new Item.Builder()
//                    .withName("Chocolate").withPrice(4.15).withSpecialPrice(10.0).withUnit(3).build();
//
//            try {
//                itemService.save(pepsi);
//                itemService.save(chips);
//                itemService.save(beer);
//                itemService.save(chocolate);
//            } catch (ValidationException e) {
//                log.info("UNABLE TO SET DATA");
//                log.error(e.getMessage());
//            }
//        };
//    }
}
