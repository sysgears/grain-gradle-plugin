package com.sysgears.grain.gradle

import org.gradle.api.Action
import org.gradle.listener.ActionBroadcast

/**
 * Extension for Grain plugin.
 */
class GrainPluginExtension {

    /** Broadcaster to notify about changing the Grain project directory,
     * so that project dependencies are added only when the directory is set. */
    private final ActionBroadcast<String> broadcast = new ActionBroadcast<String>()

    /** Relative path to Grain site project. */
    String projectDir

    /**
     * Sets Grain version, thus calling actions connected to the broadcaster.
     *
     * @param version version to set
     */
    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir
        broadcast.execute(projectDir)
    }

    /**
     * Connects the action to the broadcaster, so the action is called only when Grain version is set.
     *
     * @param action action to call
     */
    public void onSetProjectDir(Closure action) {
        broadcast.add(action as Action<String>)
    }
}
