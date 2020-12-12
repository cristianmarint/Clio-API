/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:11
 */

package com.biblioteca.demeter.boot;

import com.biblioteca.demeter.model.Category;
import com.biblioteca.demeter.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@Order(2)
@Slf4j
public class CategoryDataLoader implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("----------CATEGORY DATALOADER----------");
        categoryRepository.deleteAllInBatch();
        Category category1 = Category
                .builder()
                .name("Novela de aventuras")
                .description("[Description] Novela de aventuras")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category
                .builder()
                .name("Novela de ciencia ficción")
                .description("[Description] Novela de ciencia ficcion")
                .build();
        categoryRepository.save(category2);

        Category category3 = Category
                .builder()
                .name("Cuentos de hadas")
                .description("[Description] Cuentos de hadas")
                .build();
        categoryRepository.save(category3);

        Category category4 = Category
                .builder()
                .name("Novela gotica")
                .description("[Description] Novela gotica")
                .build();
        categoryRepository.save(category4);

        Category category5 = Category
                .builder()
                .name("Novela policiaca")
                .description("[Description] Novela policiaca")
                .build();
        categoryRepository.save(category5);

        Iterator<Category> iterator = categoryRepository.findAll().iterator();
        while (iterator.hasNext()){
            log.info("{}",iterator.next().toString());
        }
    }
}
