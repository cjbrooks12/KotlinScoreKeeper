package com.caseyjbrooks.scorekeeper.core.premium.permissions

import minimatch.Minimatch


class PermissionManager(val permissions: Set<Permission>) {

    fun test(action: String, permissibleActions: List<String>): Int {
        // make sure the tested action is a registered action
        if(!permissions.any { it.name == action }) {
            return -1
        }

        val permissiblePermissions = permissibleActions.map { ac -> permissions.find { it.name == ac } }.filterNotNull()

        // make sure all the user's permissible actions are registered actions
        if(permissiblePermissions.size != permissibleActions.size) {
            return -2
        }

        // if the action is one of the user's permissible actions, grant access
        for(permission in permissiblePermissions) {
            if(action == permission.name) {
                return 1
            }
            else if(permission.includes != null && Minimatch.minimatch(action, permission.includes)) {
                return 2
            }
        }

        return -3
    }

    fun can(action: String, permissibleActions: List<String>): Boolean {
        return test(action, permissibleActions) > 0
    }

}