package com.sysgears.grain.gradle

import org.gradle.api.Action
import org.gradle.listener.ActionBroadcast

/**
 * Extension for Grain plugin.
 */
class GrainPluginExtension {

    /** Broadcaster to notify about changing the Grain version,
     * so that project dependencies are added only when the version is set. */
    private final ActionBroadcast<String> broadcast = new ActionBroadcast<String>()

    /** Relative path to Grain site project. */
    String base

    /** Grain version to use to launch Grain actions. */
    String version

    /**
     * Sets Grain version, thus calling actions connected to the broadcaster.
     *
     * @param version version to set
     */
    public void setVersion(String version) {
        this.version = version
        broadcast.execute(version)
    }

    /**
     * Connects the action to the broadcaster, so the action is called only when Grain version is set.
     *
     * @param action action to call
     */
    public void onSetVersion(Closure action) {
        broadcast.add(action as Action<String>)
    }
}
