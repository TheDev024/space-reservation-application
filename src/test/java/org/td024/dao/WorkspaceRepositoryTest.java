package org.td024.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.td024.entity.Workspace;
import org.td024.enums.WorkspaceType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WorkspaceRepositoryTest {
    private WorkspaceRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new WorkspaceRepository();
        repository.setData(new ArrayList<>());
    }

    @Test
    void saveShouldReturnId() {
        // Arrange
        Workspace workspace1 = new Workspace("Test Workspace 1", WorkspaceType.OPEN, BigDecimal.ONE);
        Workspace workspace2 = new Workspace("Test Workspace 2", WorkspaceType.OPEN, BigDecimal.ONE);
        Workspace workspace3 = new Workspace("Test Workspace 3", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act
        int id1 = repository.save(workspace1);
        int id2 = repository.save(workspace2);
        int id3 = repository.save(workspace3);

        // Assert
        assertEquals(1, id1);
        assertEquals(2, id2);
        assertEquals(3, id3);
    }

    @Test
    void saveShouldAssignWorkspaceId() {
        // Arrange
        Workspace workspace1 = new Workspace("Test Workspace 1", WorkspaceType.OPEN, BigDecimal.ONE);
        Workspace workspace2 = new Workspace("Test Workspace 2", WorkspaceType.OPEN, BigDecimal.ONE);
        Workspace workspace3 = new Workspace("Test Workspace 3", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act
        repository.save(workspace1);
        repository.save(workspace2);
        repository.save(workspace3);

        // Assert
        assertEquals(1, workspace1.getId());
        assertEquals(2, workspace2.getId());
        assertEquals(3, workspace3.getId());
    }

    @Test
    void saveShouldEditWorkspaceById() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = repository.save(workspace);
        workspace = new Workspace(id, "Edited Workspace", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act
        repository.save(workspace);

        // Assert
        repository.getWorkspaceById(id).ifPresentOrElse(edited -> assertEquals("Edited Workspace", edited.getName()), () -> {
        });
    }

    @Test
    void saveShouldReturnSameIdForEdit() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = repository.save(workspace);
        workspace = new Workspace(id, "Edited Workspace", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act
        int editedId = repository.save(workspace);

        // Assert
        assertEquals(id, editedId);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 10, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void shouldReturnNegative1IfUnsuccessful(int id) {
        // Arrange
        Workspace workspace = new Workspace(id, "Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act
        id = repository.save(workspace);

        // Assert
        assertEquals(-1, id);
    }

    @Test
    void deletedWorkspaceEditShouldBeUnsuccessful() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = repository.save(workspace);
        repository.deleteWorkspace(id);
        workspace = new Workspace(id, "Edited Workspace", WorkspaceType.OPEN, BigDecimal.ONE);

        // Act
        int editedId = repository.save(workspace);

        // Assert
        assertEquals(-1, editedId);
    }

    @Test
    void shouldReturnTrueOnSuccessfulDelete() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = repository.save(workspace);

        // Act
        boolean deleted = repository.deleteWorkspace(id);

        // Assert
        assert deleted;
    }

    @Test
    void shouldReturnEmptyOptionalForDeletedId() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = repository.save(workspace);

        // Act
        repository.deleteWorkspace(id);

        // Assert
        assertFalse(repository.getWorkspaceById(id).isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 10, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void shouldReturnFalseForUnsuccessfulDelete(int id) {
        assertFalse(repository.deleteWorkspace(id));
    }

    @Test
    void shouldReturnFalseForAlreadyDeletedId() {
        // Arrange
        Workspace workspace = new Workspace("Test Workspace", WorkspaceType.OPEN, BigDecimal.ONE);
        int id = repository.save(workspace);

        // Act
        repository.deleteWorkspace(id);

        // Assert
        assertFalse(repository.deleteWorkspace(id));
    }

    @Test
    void shouldReturnAllWorkspaces() {
        repository.save(new Workspace("Test Workspace 1", WorkspaceType.OPEN, BigDecimal.ONE));
        repository.save(new Workspace("Test Workspace 2", WorkspaceType.OPEN, BigDecimal.ONE));
        repository.save(new Workspace("Test Workspace 3", WorkspaceType.OPEN, BigDecimal.ONE));

        assertEquals(3, repository.getAllWorkspaces().size());
    }

    @Test
    void shouldReturnEmptyListIfNoWorkspaces() {
        assert repository.getAllWorkspaces().isEmpty();
    }

    @Test
    void shouldReturnEntitiesInRightOrder() {
        // Arrange
        repository.save(new Workspace("Test Workspace 1", WorkspaceType.OPEN, BigDecimal.ONE));
        repository.save(new Workspace("Test Workspace 2", WorkspaceType.OPEN, BigDecimal.ONE));
        repository.save(new Workspace("Test Workspace 3", WorkspaceType.OPEN, BigDecimal.ONE));

        // Act
        List<Workspace> workspaces = repository.getAllWorkspaces();

        // Assert
        assertEquals("Test Workspace 1", workspaces.get(0).getName());
        assertEquals("Test Workspace 2", workspaces.get(1).getName());
        assertEquals("Test Workspace 3", workspaces.get(2).getName());
    }

    @Test
    void shouldExcludeDeletedWorkspaces() {
        repository.save(new Workspace("Test Workspace 1", WorkspaceType.OPEN, BigDecimal.ONE));
        repository.save(new Workspace("Test Workspace 2", WorkspaceType.OPEN, BigDecimal.ONE));

        repository.deleteWorkspace(1);

        List<Workspace> workspaces = repository.getAllWorkspaces();

        assertEquals(1, workspaces.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 10, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void shouldReturnEmptyOptionalForInvalidId(int id) {
        assertFalse(repository.getWorkspaceById(id).isPresent());
    }
}
