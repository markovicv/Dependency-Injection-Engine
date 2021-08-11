package di;

import errors.DependencyExistException;
import errors.QualifierException;
import model.ImplTest;
import model.ImplTestWithoutQualifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.*;

class DependencySupplierTest {


    private DependencySupplier dependencySupplier;
    private Map<String, Class> classesForQualifier = Mockito.mock(HashMap.class);

    @BeforeEach
    void setup() {
        dependencySupplier = new DependencySupplier(classesForQualifier);
    }


    @Test
    public void shouldReturnDependencyForClass() {

        Mockito.when(classesForQualifier.get(anyString())).thenReturn(Object.class);

        assertThat(dependencySupplier.getDependencyForClass(anyString())).isInstanceOf(Class.class);
    }

    @Test
    public void shouldFailIfClassNotAnnotatedWithQualifier() {

        assertThatThrownBy(() -> dependencySupplier.addDependencyForClass(ImplTestWithoutQualifier.class)).isInstanceOf(QualifierException.class);

    }

    @Test
    @DisplayName("if a class with given qualifier already exists, than it should throw an exception")
    public void shouldFailIfDependencyAlreadyPresent() {

        Mockito.when(classesForQualifier.containsKey(anyString())).thenReturn(true);

        assertThatThrownBy(() -> dependencySupplier.addDependencyForClass(ImplTest.class)).isInstanceOf(DependencyExistException.class);
    }

    @Test
    @DisplayName("should pass if we add a class with Qualifier annotation that hasn't been added ")
    public void shouldAddDependency() {

        Mockito.when(classesForQualifier.containsKey(anyString())).thenReturn(false);
        dependencySupplier.addDependencyForClass(ImplTest.class);

        Mockito.verify(classesForQualifier, Mockito.times(1)).put(anyString(), eq(ImplTest.class));

    }

}