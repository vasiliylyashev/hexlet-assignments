package exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

// BEGIN
@Data
@Table
@Entity
public class Product {
    @EqualsAndHashCode.Include
    String title;
    @EqualsAndHashCode.Include
    int price;
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    long id;
}
// END
