package org.havenapp.main.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import org.havenapp.main.HavenApp
import java.util.*

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 20/5/18.
 */
@Entity(tableName = "EVENT")
class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id : Long? = null
        get() = field

    @ColumnInfo(name = "M_START_TIME")
    var mStartTime : Date? = Date()

    @Ignore
    private var mEventTriggers : MutableList<EventTrigger> = mutableListOf()

    fun addEventTrigger(eventTrigger: EventTrigger) {
        mEventTriggers.add(eventTrigger)
        eventTrigger.mEventId = id
    }

    /**
     * Get the list of event triggers associated with this event.
     * <p>
     * When [mEventTriggers] is empty this method performs a blocking db lookup.
     */
    fun getEventTriggers() : MutableList<EventTrigger> {

        if (mEventTriggers.size == 0) {
            val eventTriggers = HavenApp.getDataBaseInstance().getEventTriggerDAO().getEventTriggerList(id)
            mEventTriggers.addAll(eventTriggers)
        }

        return mEventTriggers
    }

    fun getEventTriggerCount(): Int {
        if (mEventTriggers.size == 0) {
            return getEventTriggers().size
        }

        return mEventTriggers.size
    }
}