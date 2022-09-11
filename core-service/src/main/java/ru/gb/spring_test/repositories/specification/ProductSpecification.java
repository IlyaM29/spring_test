package ru.gb.spring_test.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.spring_test.entities.Product;

public class ProductSpecification {

    public static Specification<Product> costGreaterOrElseThan(Integer cost) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost));
    }

    public static Specification<Product> costLessOrElseThan(Integer cost) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), cost));
    }

    public static Specification<Product> titleEquals(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title)));
    }
}
