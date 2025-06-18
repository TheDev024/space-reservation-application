package org.td024.dao;

import org.td024.entity.Workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WorkspaceRepository extends Repository<Workspace> {
    private static final ArrayList<Workspace> workspaces = new ArrayList<>();

    public Optional<Workspace> getWorkspaceById(int id) {
        if (id <= 0 || id > workspaces.size() || workspaces.get(id - 1) == null) return Optional.empty();
        return Optional.ofNullable(workspaces.get(id - 1));
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaces.stream().filter(Objects::nonNull).toList();
    }

    /**
     * If id is 0, create a new workspace, otherwise update an existing workspace
     *
     * @return id of the created/updated workspace, if not successful, -1
     */
    public int save(Workspace workspace) {
        int id = workspace.getId();
        if (id == 0) {
            id = workspaces.size() + 1;
            workspace.setId(id);
            workspaces.add(workspace);
        } else {
            if (id > workspaces.size() || id < 0 || workspaces.get(id - 1) == null)
                return -1;
            workspaces.set(id - 1, workspace);
        }

        return id;
    }

    /**
     * @return if delete is successful, true, otherwise, false
     */
    public boolean deleteWorkspace(int id) {
        if (id <= 0 || id > workspaces.size()) return false;
        return workspaces.set(id - 1, null) != null;
    }

    @Override
    protected final ArrayList<Workspace> getData() {
        return workspaces;
    }

    @Override
    protected final void setData(ArrayList<Workspace> data) {
        workspaces.clear();
        workspaces.addAll(data);
    }
}
