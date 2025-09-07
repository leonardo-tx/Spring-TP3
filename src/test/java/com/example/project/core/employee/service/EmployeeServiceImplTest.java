package com.example.project.core.employee.service;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.employee.repository.EmployeeRepository;
import com.example.project.core.generic.exception.CoreException;
import com.example.project.core.generic.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void shouldReturnOnGetAll() {
        List<Employee> employees = List.of(mock(Employee.class), mock(Employee.class));
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> returnedEmployees = employeeService.getAll();

        assertEquals(2, returnedEmployees.size());
        assertSame(employees, returnedEmployees);

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOnGetById() {
        Employee employee = mock(Employee.class);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertSame(employee, employeeService.getById(1L));

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenNotFoundOnGetById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        CoreException exception = assertThrows(NotFoundException.class, () -> employeeService.getById(1L));

        assertEquals("employee.not.found", exception.getCode());
        assertEquals("Could not find the employee with the specified id.", exception.getMessage());

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteEmployee() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(2L);

        assertDoesNotThrow(() -> employeeService.delete(employee));

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void shouldNotDeleteEmployeeWithoutId() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.delete(employee)
        );

        assertEquals("The employee to delete must have an identifier.", exception.getMessage());
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void shouldCreateEmployee() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(null);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = assertDoesNotThrow(() -> employeeService.create(employee));

        assertSame(employee, createdEmployee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void shouldNotCreateEmployeeWithId() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(3L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.create(employee)
        );

        assertEquals("The employee to create must not have an identifier.", exception.getMessage());
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void shouldUpdateEmployee() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(4L);
        when(employeeRepository.existsById(4L)).thenReturn(true);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = assertDoesNotThrow(() -> employeeService.update(employee));

        assertSame(employee, updatedEmployee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void shouldNotUpdateEmployeeWithoutId() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.update(employee)
        );

        assertEquals("The employee to update must have an identifier.", exception.getMessage());
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void shouldNotUpdateIfNotFound() {
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(4L);
        when(employeeRepository.existsById(4L)).thenReturn(false);

        CoreException exception = assertThrows(NotFoundException.class, () -> employeeService.update(employee));

        assertEquals("employee.not.found", exception.getCode());
        assertEquals("Could not find the employee with the specified id.", exception.getMessage());

        verifyNoMoreInteractions(employeeRepository);
    }
}
