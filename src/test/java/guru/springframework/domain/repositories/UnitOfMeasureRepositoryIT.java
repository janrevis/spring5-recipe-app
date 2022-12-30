package guru.springframework.domain.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UnitOfMeasureRepositoryIT {


    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void findByUom() {
        UnitOfMeasure unit = unitOfMeasureRepository.findByUom("teaspoon").get();
        assertNotNull(unit);
        assertEquals(unit.getUom(), "teaspoon");
    }
}