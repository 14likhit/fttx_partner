package com.fttx.partner.data.network.util

object EndPoints {

    object Login {
        const val LOGIN = "login"
        const val FORM_KEY_USERNAME = "username"
        const val FORM_KEY_PASSWORD = "password"
        const val FORM_KEY_DEVICE_ID = "device_id"
    }

    object Ticket {
        const val USER_TICKET = "gettickets"
        const val QUERY_USER_ID = "user_id"
        const val UPDATE_TICKET = "updateticket"
        const val QUERY_TICKET_ID = "ticket_id"
        const val QUERY_STATUS = "status"
        const val QUERY_LATITUDE = "latitude"
        const val QUERY_LONGITUDE = "longitude"
        const val QUERY_AGENT_IDS = "agent_ids"
    }

    object Agents {
        const val GET_AGENTS = "getagents"
        const val QUERY_AGENT_ID = "agent_id"
    }

    object Location {
        const val UPDATE_LOCATION_LOG = "locationlog"
    }
}