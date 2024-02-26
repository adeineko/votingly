package be.kdg.team9.integration4.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import be.kdg.team9.integration4.model.Form;

// @RunWith(SpringRunner.class)
@SpringBootTest
public class FormServiceUnitTest {
    @Autowired
    private FormService formService;
    
    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<Form> forms = formService.getAllForms();
    
        assertEquals(forms.size(), 3);
    }
    
}
