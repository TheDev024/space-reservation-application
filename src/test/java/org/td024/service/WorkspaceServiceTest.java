package org.td024.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.td024.dao.WorkspaceRepository;
import org.td024.entity.Workspace;
import org.td024.enums.WorkspaceType;
import org.td024.exception.NotFoundException;
import org.td024.exception.WorkspaceSaveFailed;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WorkspaceServiceTest {
    @Spy
    private WorkspaceRepository repository;

    private WorkspaceService service;

    @BeforeEach
    void setUp() {
        service = new WorkspaceService(repository);
    }

    @Test
    void shouldAssignIdWhenCreating() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        when(repository.save(workspace)).thenReturn(1);

        // Act
        int id = service.createWorkspace(workspace);

        // Assert
        assertEquals(1, id);
    }

    @Test
    void shouldReturnSameIdWhenEditing() throws WorkspaceSaveFailed {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = service.createWorkspace(workspace);
        workspace = new Workspace(id, "Edited Workspace", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act & Assert
        assertEquals(id, service.editWorkspace(id, workspace));
    }

    @Test
    void shouldThrowExceptionWhenEditingNonExistingWorkspace() {
        // Arrange
        Workspace workspace = new Workspace("Edited Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        when(repository.getById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(WorkspaceSaveFailed.class, () -> service.editWorkspace(1, workspace));
    }

    @Test
    void shouldReturnTrueWhenDeleteIsSuccessful() {
        when(repository.delete(1)).thenReturn(true);
        assertTrue(service.deleteWorkspace(1));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 10, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void shouldReturnFalseWhenDeleteIsUnsuccessful(int id) {
        when(repository.delete(id)).thenReturn(false);
        assertFalse(service.deleteWorkspace(id));
    }

    @Test
    void shouldReturnWorkspaceWhenFound() throws NotFoundException {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = service.createWorkspace(workspace);

        // Act
        workspace = service.getWorkspaceById(id);

        // Assert
        assertEquals(id, workspace.getId());
    }

    @Test
    void shouldThrowExceptionIfWorkspaceNotFound() {
        // Arrange
        when(repository.getById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> service.getWorkspaceById(1));
    }
}
