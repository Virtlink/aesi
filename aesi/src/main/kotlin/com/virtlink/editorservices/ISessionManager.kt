package com.virtlink.editorservices

/**
 * Manages sessions.
 */
interface ISessionManager {

    /**
     * Gets the current session ID; or null when there is no active session.
     */
    val currentSessionId: SessionId?

}