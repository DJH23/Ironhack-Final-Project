package com.LessonLab.forum.Controllers.interfaces;

import com.LessonLab.forum.Models.Role;
import com.LessonLab.forum.dtos.RoleToUserDTO;

public interface RoleControllerInterface {
    /**
     * Save a new role
     *
     * @param role the role to be saved
     */
    void saveRole(Role role);

    /**
     * Add a role to a user
     *
     * @param roleToUserDTO object containing the username and role name to be added
     *                      to the user
     */
    void addRoleToUser(RoleToUserDTO roleToUserDTO);
}
