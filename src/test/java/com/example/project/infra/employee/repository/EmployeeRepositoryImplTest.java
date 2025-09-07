package com.example.project.infra.employee.repository;

import com.example.project.core.employee.model.Employee;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.employee.persistence.EmployeeJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryImplTest {
    @Mock
    private EmployeeRepositoryJPA employeeRepositoryJPA;

    @Mock
    private Mapper<Employee, EmployeeJPA> employeeJPAMapper;

    @InjectMocks
    private EmployeeRepositoryImpl employeeRepository;

    @Test
    void shouldFindAll() {
        List<EmployeeJPA> entities = List.of(mock(EmployeeJPA.class), mock(EmployeeJPA.class));
        when(employeeRepositoryJPA.findAll()).thenReturn(entities);

        List<Employee> categories = assertDoesNotThrow(() -> employeeRepository.findAll());

        assertEquals(entities.size(), categories.size());
        verify(employeeJPAMapper, times(entities.size())).toModel(any(EmployeeJPA.class));
    }

    @Test
    void shouldFindById() {
        EmployeeJPA mockEmployeeJPA = mock(EmployeeJPA.class);
        Employee mockEmployee = mock(Employee.class);

        when(employeeRepositoryJPA.findById(3L)).thenReturn(Optional.of(mockEmployeeJPA));
        when(employeeJPAMapper.toModel(mockEmployeeJPA)).thenReturn(mockEmployee);

        Optional<Employee> optionalEmployee = assertDoesNotThrow(() -> employeeRepository.findById(3L));

        assertTrue(optionalEmployee.isPresent());
        assertSame(mockEmployee, optionalEmployee.get());

        verify(employeeJPAMapper, times(1)).toModel(mockEmployeeJPA);
    }

    @Test
    void shouldReturnOptionEmptyWhenNotFindById() {
        when(employeeRepositoryJPA.findById(3L)).thenReturn(Optional.empty());

        Optional<Employee> optionalEmployee = assertDoesNotThrow(() -> employeeRepository.findById(3L));

        assertTrue(optionalEmployee.isEmpty());

        verifyNoInteractions(employeeJPAMapper);
    }

    @Test
    void shouldDelete() {
        Employee mockEmployee = mock(Employee.class);
        when(mockEmployee.getId()).thenReturn(4L);

        assertDoesNotThrow(() -> employeeRepository.delete(mockEmployee));

        verify(employeeRepositoryJPA).deleteById(4L);
    }

    @Test
    void shouldSave() {
        Employee mockEmployee = mock(Employee.class);
        Employee mockSavedEmployee = mock(Employee.class);
        EmployeeJPA mockEmployeeJPA = mock(EmployeeJPA.class);
        EmployeeJPA mockSavedEmployeeJPA = mock(EmployeeJPA.class);

        when(employeeJPAMapper.toEntity(mockEmployee)).thenReturn(mockEmployeeJPA);
        when(employeeRepositoryJPA.save(mockEmployeeJPA)).thenReturn(mockSavedEmployeeJPA);
        when(employeeJPAMapper.toModel(mockSavedEmployeeJPA)).thenReturn(mockSavedEmployee);

        Employee returnedEmployee = assertDoesNotThrow(() -> employeeRepository.save(mockEmployee));

        assertSame(mockSavedEmployee, returnedEmployee);
        verify(employeeJPAMapper, times(1)).toEntity(mockEmployee);
        verify(employeeJPAMapper, times(1)).toModel(mockSavedEmployeeJPA);
        verify(employeeRepositoryJPA).save(mockEmployeeJPA);
    }

    @Test
    void shouldReturnIfExists() {
        when(employeeRepositoryJPA.existsById(4L)).thenReturn(true);
        assertTrue(employeeRepository.existsById(4L));

        when(employeeRepositoryJPA.existsById(2L)).thenReturn(false);
        assertFalse(employeeRepository.existsById(2L));
    }
}
